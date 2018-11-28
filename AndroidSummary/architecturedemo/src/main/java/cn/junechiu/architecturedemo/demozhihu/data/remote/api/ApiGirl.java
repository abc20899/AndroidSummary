package cn.junechiu.architecturedemo.demozhihu.data.remote.api;

import cn.junechiu.architecturedemo.demozhihu.data.remote.model.GirlResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ApiGirl.java
 */
public interface ApiGirl {

    @GET("api/data/福利/10/{index}")
    Call<GirlResponse> getGirlsData(@Path("index") int index);
}
