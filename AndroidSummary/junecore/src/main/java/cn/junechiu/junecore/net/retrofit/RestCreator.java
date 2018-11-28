package cn.junechiu.junecore.net.retrofit;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import cn.junechiu.junecore.app.ConfigKeys;
import cn.junechiu.junecore.app.June;
import cn.junechiu.junecore.net.rx.RxRestService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by junzhao on 2017/12/2.
 */
public class RestCreator {

    public static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    /**
     * 构建全局的Retrofit客户端
     */
    private static final class RetrofitHolder {

        private static final String BASE_URL = (String) June.getConfigurations().get(ConfigKeys.API_HOST);

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OKHTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 构建全局的OKhttp客户端
     */
    private static final class OKHttpHolder {

        private static final int TIMEOUT = 60;

        private static final ArrayList<Interceptor> INTERCEPTORS = June.getConfigration(ConfigKeys.INTERCEPTOR);

        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static final OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OKHTTP_CLIENT = addInterceptor()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构建全局的Service接口
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * 返回OkHttpClient
     */
    public static final OkHttpClient getOkHttpClient() {
        return OKHttpHolder.OKHTTP_CLIENT;
    }

    /**
     * 返回Retrofit 客户端
     */
    public static final Retrofit getRetrofit() {
        return RetrofitHolder.RETROFIT_CLIENT;
    }

    /**
     * 返回Retrofit Service
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 构建全局的RxService接口
     */
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    /**
     * 返回rx Retrofit Service
     */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }
}
