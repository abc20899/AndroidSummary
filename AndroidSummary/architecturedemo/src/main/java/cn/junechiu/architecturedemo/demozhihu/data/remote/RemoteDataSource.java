package cn.junechiu.architecturedemo.demozhihu.data.remote;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.junechiu.architecturedemo.demozhihu.data.IDataSource;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.AppDatabaseManager;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.data.remote.api.ApiGirl;
import cn.junechiu.architecturedemo.demozhihu.data.remote.api.ApiManager;
import cn.junechiu.architecturedemo.demozhihu.data.remote.api.ApiZhihu;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.GirlResponse;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuResponse;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RemoteDataSource.java
 * 远程数据源操作
 */
public class RemoteDataSource implements IDataSource {

    private final MutableLiveData<Boolean> mIsLoadingGirlList;

    private final MutableLiveData<Boolean> mIsLoadingZhihuList;

    private final MutableLiveData<List<Girl>> mGirlList;

    private final MutableLiveData<List<ZhihuStory>> mZhihuList;

    private final MutableLiveData<ZhihuStoryDetail> mZhihuDetail;

    private final ApiGirl mApiGirl;

    private final ApiZhihu mApiZhihu;

    private String mZhihuPageDate;

    private static final class Holder {
        public static final RemoteDataSource INSTANCE = new RemoteDataSource();
    }

    public static RemoteDataSource getInstance() {
        return RemoteDataSource.Holder.INSTANCE;
    }

    private RemoteDataSource() {
        mIsLoadingGirlList = new MutableLiveData<>();
        mGirlList = new MutableLiveData<>();

        mIsLoadingZhihuList = new MutableLiveData<>();
        mZhihuDetail = new MutableLiveData<>();
        mZhihuList = new MutableLiveData<>();

        mApiGirl = ApiManager.getInstance().getApiGirl();
        mApiZhihu = ApiManager.getInstance().getApiZhihu();
    }

    //加载girl列表数据
    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        mIsLoadingGirlList.setValue(true); //显示加载状态
        mApiGirl.getGirlsData(index)
                .enqueue(new Callback<GirlResponse>() {
                    @Override
                    public void onResponse(Call<GirlResponse> call, Response<GirlResponse> response) {
                        mIsLoadingGirlList.setValue(false);
                        if (response.isSuccessful() || !response.body().error) {
                            mGirlList.setValue(response.body().results);
                            refreshLocalGirlList(response.body().results);
                        }
                    }

                    @Override
                    public void onFailure(Call<GirlResponse> call, Throwable t) {
                        mIsLoadingGirlList.setValue(false);
                    }
                });
        return mGirlList;
    }

    @Override
    public MutableLiveData<Boolean> isLoadingGirlList() {
        return mIsLoadingGirlList;
    }

    @Override
    public LiveData<List<ZhihuStory>> getLastZhihuList() {
        mIsLoadingZhihuList.setValue(true);
        mApiZhihu.getLatestNews()
                .enqueue(new Callback<ZhihuResponse>() {
                    @Override
                    public void onResponse(Call<ZhihuResponse> call, Response<ZhihuResponse> response) {
                        if (response.isSuccessful()) {
                            mZhihuList.setValue(response.body().getStories());
                            refreshLocalZhihuList(response.body().getStories());
                            mZhihuPageDate = response.body().getDate();
                        }
                        mIsLoadingZhihuList.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ZhihuResponse> call, Throwable t) {
                        mIsLoadingZhihuList.setValue(false);
                    }
                });
        return mZhihuList;
    }

    @Override
    public LiveData<List<ZhihuStory>> getMoreZhihuList(String date) {
        mIsLoadingZhihuList.setValue(true);
        mApiZhihu.getTheDaily(mZhihuPageDate)
                .enqueue(new Callback<ZhihuResponse>() {
                    @Override
                    public void onResponse(Call<ZhihuResponse> call, Response<ZhihuResponse> response) {
                        if (response.isSuccessful()) {
                            mZhihuList.setValue(response.body().getStories());
                            refreshLocalZhihuList(response.body().getStories());
                            mZhihuPageDate = response.body().getDate();
                        }
                        mIsLoadingZhihuList.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ZhihuResponse> call, Throwable t) {
                        mIsLoadingZhihuList.setValue(false);
                    }
                });
        return mZhihuList;
    }

    @Override
    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        mApiZhihu.getZhiHuStoryDetail(id)
                .enqueue(new Callback<ZhihuStoryDetail>() {
                    @Override
                    public void onResponse(Call<ZhihuStoryDetail> call, Response<ZhihuStoryDetail> response) {
                        if (response.isSuccessful()) {
                            mZhihuDetail.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ZhihuStoryDetail> call, Throwable t) {

                    }
                });
        return mZhihuDetail;
    }

    @Override
    public MutableLiveData<Boolean> isLoadingZhihuList() {
        return mIsLoadingZhihuList;
    }

    //添加进数据库
    private void refreshLocalGirlList(List<Girl> girlList) {
        if (girlList == null || girlList.isEmpty()) {
            return;
        }
        AppDatabaseManager.getInstance().insertGirlList(girlList);
    }

    //添加进数据库
    private void refreshLocalZhihuList(List<ZhihuStory> zhihuStoryList) {
        if (zhihuStoryList == null || zhihuStoryList.isEmpty()) {
            return;
        }
        AppDatabaseManager.getInstance().insertZhihuList(zhihuStoryList);
    }
}
