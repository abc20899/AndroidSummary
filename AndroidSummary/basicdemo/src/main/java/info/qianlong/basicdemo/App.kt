package info.qianlong.basicdemo

import android.app.Application
import info.qianlong.basicdemo.android_server.NIOHttpServer
import info.qianlong.basicdemo.media_provider.utils.FileManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FileManager.getInstance().init(this)
        NIOHttpServer.instance.init(this)
    }
}