package cn.junechiu.architecturedemo.demozhihu.data;

import android.app.Application;

import cn.junechiu.architecturedemo.demozhihu.data.local.LocalDataSource;
import cn.junechiu.architecturedemo.demozhihu.data.remote.RemoteDataSource;

/**
 * Injection.java
 * 实例化DataRepository数据源
 */
public class Injection {

    public static DataRepository getDataRepository(Application application) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(), application);
    }
}
