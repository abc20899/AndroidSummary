package cn.junechiu.architecturedemo

import android.app.Application
import cn.junechiu.architecturedemo.demozhihu.data.local.db.AppDatabaseManager
import cn.junechiu.architecturedemo.demozhihu.data.model.GlobalHttpHandlerImpl
import cn.junechiu.architecturedemo.demozhihu.data.remote.api.ApiManager
import cn.junechiu.architecturedemo.livedata.MyLiveData
import cn.junechiu.junecore.app.June
import cn.junechiu.junecore.net.interceptors.BasicParamsInterceptor
import cn.junechiu.junecore.net.interceptors.RequestInterceptor
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    companion object {
        var INSTANCE: App? = null
    }

    var liveData: MyLiveData? = null

    override fun onCreate() {
        super.onCreate()
        liveData = MyLiveData()
        INSTANCE = this

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        //zhihudemo 创建数据库
        AppDatabaseManager.getInstance().createDB(this)

        val basicParamsInterceptor = BasicParamsInterceptor.Builder()
                .addQueryParam("version", packageManager.getPackageInfo(packageName, 0).versionName)
                .build()
        June.init(this).withApiHost(ApiManager.BASE_URL)
                .withInterceptor(RequestInterceptor(GlobalHttpHandlerImpl(this), RequestInterceptor.Level.NONE))
                .configure()
    }
}