package cn.junechiu.architecturedemo.demozhihu.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import cn.junechiu.architecturedemo.App;
import cn.junechiu.architecturedemo.demozhihu.data.DataRepository;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.util.Util;

/**
 * ZhihuListViewModel.java
 */
public class ZhihuListViewModel extends AndroidViewModel {

    // 请求接口中查询的日期参数
    private MutableLiveData<String> mZhihuPageDate = new MutableLiveData<>();

    // Zhihu 列表的数据
    private final LiveData<List<ZhihuStory>> mZhihuList;

    // 数据源
    private DataRepository mDataRepository = null;

    private ZhihuListViewModel(Application application, DataRepository dataRepository) {
        super(application);
        mDataRepository = dataRepository;
        // 使用 Transformations.switchMap() 方法，当 View 改变 mZhihuPageDate 参数的值时，则进行 zhihu 列表数据的请求
        mZhihuList = Transformations.switchMap(mZhihuPageDate, new Function<String, LiveData<List<ZhihuStory>>>() {
            @Override
            public LiveData<List<ZhihuStory>> apply(String input) {
                return mDataRepository.getZhihuList(input);
            }
        });
    }

    /**
     * 获取 Zhihu 列表数据
     *
     * @return Zhihu 列表数据
     */
    public LiveData<List<ZhihuStory>> getZhihuList() {
        return mZhihuList;
    }

    /**
     * 数据请求状态由 DataRepository 控制，包括下拉刷新和上拉加载更多
     *
     * @return 是否在进行数据请求
     */
    public LiveData<Boolean> isLoadingZhihuList() {
        return mDataRepository.isLoadingZhihuList();
    }

    /**
     * 下拉刷新，获取最新的 Zhihu 列表数据
     */
    public void refreshZhihusData() {
        mZhihuPageDate.setValue("today");
    }

    /**
     * 上拉加载更多时，获取 Zhihu 历史列表数据
     *
     * @param positon 表示列表滑动到最后一项
     */
    public void loadNextPageZhihu(int positon) {
        if (!Util.isNetworkConnected(App.Companion.getINSTANCE())) {
            return;
        }
        mZhihuPageDate.setValue(String.valueOf(positon));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mGirlsDataRepository;

        public Factory(@NonNull Application application, DataRepository girlsDataRepository) {
            mApplication = application;
            mGirlsDataRepository = girlsDataRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ZhihuListViewModel(mApplication, mGirlsDataRepository);
        }
    }
}
