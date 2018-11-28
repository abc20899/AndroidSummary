package cn.junechiu.architecturedemo.demozhihu.data.remote.api;

import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuResponse;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ApiZhihu.java
 */
public interface ApiZhihu {

    @GET("api/4/news/latest")
    Call<ZhihuResponse> getLatestNews();

    @GET("/api/4/news/before/{date}")
    Call<ZhihuResponse> getTheDaily(@Path("date") String date);

    @GET("api/4/news/{id}")
    Call<ZhihuStoryDetail> getZhiHuStoryDetail(@Path("id") String id);
}
