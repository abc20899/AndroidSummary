package cn.junechiu.architecturedemo.demozhihu.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;
import cn.junechiu.architecturedemo.demozhihu.util.Util;

/**
 * DataRepository.java
 * 数据源操作---本地和网络数据
 */
public class DataRepository {

    private static DataRepository INSTANCE = null;

    private final IDataSource mRemoteDataSource;  //远程数据源

    private final IDataSource mLocalDataSource;   //本地数据源

    private static Application sApplication = null;

    private DataRepository(@NonNull IDataSource remoteDataSource,
                           @NonNull IDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static DataRepository getInstance(@NonNull IDataSource remoteDataSource,
                                             @NonNull IDataSource localDataSource,
                                             Application application) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(remoteDataSource, localDataSource);
                    sApplication = application;
                }
            }
        }
        return INSTANCE;
    }

    //获取girl列表数据 如果无网络取缓存
    public LiveData<List<Girl>> getGirlList(int index) {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.getGirlList(index);
        } else {
            return mLocalDataSource.getGirlList(index);
        }
    }

    //是否加载更多
    public LiveData<Boolean> isLoadingGirlList() {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.isLoadingGirlList();
        } else {
            return mLocalDataSource.isLoadingGirlList();
        }
    }

    public LiveData<List<ZhihuStory>> getZhihuList(@NonNull String date) {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            if (date.equals("today")) {
                return mRemoteDataSource.getLastZhihuList();
            } else {
                return mRemoteDataSource.getMoreZhihuList(date);
            }
        } else {
            if (date.equals("today")) {
                return mLocalDataSource.getLastZhihuList();
            } else {
                return mLocalDataSource.getMoreZhihuList(date);
            }
        }
    }

    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        return mRemoteDataSource.getZhihuDetail(id);
    }

    public LiveData<Boolean> isLoadingZhihuList() {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.isLoadingZhihuList();
        } else {
            return mLocalDataSource.isLoadingZhihuList();
        }
    }
}
