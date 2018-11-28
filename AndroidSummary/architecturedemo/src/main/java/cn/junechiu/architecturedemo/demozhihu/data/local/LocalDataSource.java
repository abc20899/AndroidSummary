package cn.junechiu.architecturedemo.demozhihu.data.local;

import java.util.List;

import androidx.lifecycle.LiveData;
import cn.junechiu.architecturedemo.demozhihu.data.IDataSource;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.AppDatabaseManager;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;

/**
 * LocalDataSource.java
 * 本地数据源--->实现DataSource数据源接口
 */
public class LocalDataSource implements IDataSource {

    private LocalDataSource() {
    }

    private static final class Holder {
        public static final LocalDataSource INSTANCE = new LocalDataSource();
    }

    public static LocalDataSource getInstance() {
        return LocalDataSource.Holder.INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        return AppDatabaseManager.getInstance().loadGirlList();
    }

    @Override
    public LiveData<Boolean> isLoadingGirlList() {
        return AppDatabaseManager.getInstance().isLoadingGirlList();
    }

    @Override
    public LiveData<List<ZhihuStory>> getLastZhihuList() {
        return AppDatabaseManager.getInstance().loadZhihuList();
    }

    @Override
    public LiveData<List<ZhihuStory>> getMoreZhihuList(String date) {
        return null;
    }

    @Override
    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        return null;
    }

    @Override
    public LiveData<Boolean> isLoadingZhihuList() {
        return AppDatabaseManager.getInstance().isLoadingZhihuList();
    }
}
