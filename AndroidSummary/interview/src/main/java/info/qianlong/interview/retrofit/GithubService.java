package info.qianlong.interview.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by junzhao on 2017/12/17.
 */

public interface GithubService {

    @GET("users/{user}/repos")
    Call<String> listRepos(@Path("user") String user);
}
