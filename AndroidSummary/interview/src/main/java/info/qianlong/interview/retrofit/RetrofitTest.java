package info.qianlong.interview.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by junzhao on 2017/12/17.
 * 1.添加retrofit依赖，添加网络权限
 * 2.创建服务器返回的bean类
 * 3.用注解创建描述网络请求的接口
 * 4.创建retrofit实例
 * 5.创建网络接口的实例
 * 6.发送网络请求
 * 7.处理服务器返回的数据
 */
public class RetrofitTest {

    public void github() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(ScalarsConverterFactory.create()) //数据转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //网络回调器
                .build();
        GithubService service = retrofit.create(GithubService.class);
        Call<String> call = service.listRepos("abc20899");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


}
