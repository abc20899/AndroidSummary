package cn.junechiu.architecturedemo.demozhihu.data.model

import android.content.Context
import android.text.TextUtils
import cn.junechiu.junecore.net.interceptors.GlobalHttpHandler
import cn.junechiu.junecore.net.interceptors.RequestInterceptor
import cn.junechiu.junecore.net.model.BaseJson
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GlobalHttpHandlerImpl(private val context: Context) : GlobalHttpHandler {

    var count = 0

    //有结果返回
    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain?, response: Response?): Response {
        /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */
        try {
            if (!TextUtils.isEmpty(httpResult) &&
                    RequestInterceptor.isJson(response!!.body()!!.contentType())) {
                var resp = Gson().fromJson<BaseJson<Any>>(httpResult, BaseJson::class.java)
//                ALogger.json("responseHandle", httpResult)
//                if (resp != null && resp.code != RESPONSE_OK) {
//                    if (resp.code == TOKEN_ERROR) {
//                        if (count <= 0) {  //只执行一次
//                            getToken({ token ->
//                                //使用新的Token，创建新的请求
//                                var newRequest = chain!!.request()
//                                        .newBuilder()
//                                        .header("Authorization", "Bearer" + " " + token)
//                                        .build();
//                                //重新请求
//                                chain.proceed(newRequest)
//                            })
//                        }
//                    } else {
////                        ArmsUtils.snackbarText(resp.message)  //显示错误信息
//                    }
//                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response!!
    }

    fun getToken(callback: (token: String) -> Unit) {
        count += 1
//        val userEntity = Realm.getDefaultInstance().where(UserEntity::class.java).findFirst()
//        ArmsUtils.obtainAppComponentFromContext(context).repositoryManager()
//                .obtainRetrofitService(UserService::class.java)
//                .refreshToken("Refresh " + userEntity.refreshToken)
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Consumer<String> {
//                    override fun accept(json: String?) {
//                        ALogger.json("getToken", json)
//                        var jsonobj = JSON.parseObject(json)
//                        var token = jsonobj.getString("token")
//                        var refreshToken = jsonobj.getString("refreshToken")
//                        //更新数据库数据
//                        Realm.getDefaultInstance().executeTransaction { realm ->
//                            var userEntity = realm.where(UserEntity::class.java).findFirst()
//                            userEntity.token = token
//                            userEntity.refreshToken = refreshToken
//                            realm.insertOrUpdate(userEntity)
//                        }
//                        callback(token)
//                    }
//                })
    }

    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的request如增加header,不做操作则直接返回request参数
                       return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        return request
    }
}
