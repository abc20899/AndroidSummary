package cn.junechiu.architecturedemo.demozhihu.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;

/**
 * IDataSource.java
 * 数据源接口
 */
public interface IDataSource {

    /**
     * Girl 相关方法
     */
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Boolean> isLoadingGirlList();


    /**
     * Zhihu 相关方法
     */
    LiveData<List<ZhihuStory>> getLastZhihuList();

    LiveData<List<ZhihuStory>> getMoreZhihuList(String date);

    LiveData<ZhihuStoryDetail> getZhihuDetail(String id);

    LiveData<Boolean> isLoadingZhihuList();
}
