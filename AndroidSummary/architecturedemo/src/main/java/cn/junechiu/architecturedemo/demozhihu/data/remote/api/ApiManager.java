package cn.junechiu.architecturedemo.demozhihu.data.remote.api;

import cn.junechiu.architecturedemo.demozhihu.data.local.LocalDataSource;
import cn.junechiu.junecore.net.retrofit.RestCreator;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiManager.java
 */
public class ApiManager {

    public static final String BASE_URL = "https://www.vfans.fun/fans/";

    private static final String GIRL_URL = "http://gank.io/";

    private static final String ZHIHU_URL = "https://news-at.zhihu.com/";

    private static ApiGirl sApiGirl;

    private static ApiZhihu sApiZhihu;

    private ApiManager() {
    }

    private static final class Holder {
        public static final ApiManager INSTANCE = new ApiManager();
    }

    public static ApiManager getInstance() {
        return ApiManager.Holder.INSTANCE;
    }

    public ApiGirl getApiGirl() {
        if (sApiGirl == null) {
            synchronized (ApiManager.class) {
                if (sApiGirl == null) {
                    sApiGirl = new Retrofit.Builder()
                            .baseUrl(GIRL_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiGirl.class);
                }
            }
        }
        return sApiGirl;
    }

    public ApiZhihu getApiZhihu() {
        if (sApiZhihu == null) {
            synchronized (ApiManager.class) {
                if (sApiZhihu == null) {
                    sApiZhihu = new Retrofit.Builder()
                            .baseUrl(ZHIHU_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiZhihu.class);
                }
            }
        }
        return sApiZhihu;
    }
}
