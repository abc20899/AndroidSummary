package cn.junechiu.architecturedemo

import android.app.Application
import cn.junechiu.architecturedemo.livedata.MyLiveData

class App : Application() {

    companion object {
        var INSTANCE: App? = null
    }

    var liveData: MyLiveData? = null

    override fun onCreate() {
        super.onCreate()
        liveData = MyLiveData()
        INSTANCE = this
    }
}