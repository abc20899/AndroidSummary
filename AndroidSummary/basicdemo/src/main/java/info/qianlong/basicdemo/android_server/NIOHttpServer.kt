package info.qianlong.basicdemo.android_server

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson

import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import info.qianlong.basicdemo.media_provider.utils.FileManager

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.URLDecoder

class NIOHttpServer private constructor() {

    var server: AsyncHttpServer? = AsyncHttpServer()

    var mAsyncServer: AsyncServer? = AsyncServer()

    var context: Context? = null

    object NIOHttpServerHolder {
        val INSTANCE = NIOHttpServer()
    }

    fun init(mContext: Context) {
        context = mContext
    }

    //仿照nanohttpd的写法
    enum class Status private constructor(val requestStatus: Int, val description: String) {
        REQUEST_OK(200, "请求成功"),
        REQUEST_ERROR(500, "请求失败"),
        REQUEST_ERROR_API(501, "无效的请求接口"),
        REQUEST_ERROR_CMD(502, "无效命令"),
        REQUEST_ERROR_DEVICEID(503, "不匹配的设备ID"),
        REQUEST_ERROR_ENV(504, "不匹配的服务环境")
    }

    //开启本地服务
    fun startServer() {
        //如果有其他的请求方式，例如下面一行代码的写法
        //        server.addAction("OPTIONS", "[\\d\\D]*", this);
        //        server.get("[\\d\\D]*", this);
        //        server.post("[\\d\\D]*", this);
        server!!.listen(mAsyncServer!!, PORT_LISTEN_DEFALT)
        //        mAsyncServer.listen()
        addLibrary("/jquery-1.7.2.min.js")
        addLibrary("/layui/.*")
        index()
        sdcard()
        player()
        lsSdcard()
        lsDir()
        getVideo()
        sendStream()
    }

    //停止服务
    fun stopServer() {
        if (server != null) {
            server!!.stop()
        }
        if (mAsyncServer != null) {
            mAsyncServer!!.stop()
        }
    }

    fun index() { //返回html 执行js函数
        server!!.get("/") { request, response ->
            try {
                response.send(getHtmlContent("index.html"))
            } catch (e: IOException) {
                e.printStackTrace()
                response.code(500).end()
            }
        }
    }

    fun sdcard() {
        server!!.get("/sdcard") { request, response ->
            try {
                response.send(getHtmlContent("sdcard.html"))
            } catch (e: IOException) {
                e.printStackTrace()
                response.code(500).end()
            }
        }
    }

    fun player(){
        server!!.get("/sdcard_player") { request, response ->
            try {
                response.send(getHtmlContent("sdcard_player.html"))
            } catch (e: IOException) {
                e.printStackTrace()
                response.code(500).end()
            }
        }
    }

    private fun addLibrary(path: String) {
        server!!.get(path, HttpServerRequestCallback { request, response ->
            try {
                var fullPath = request.path
                fullPath = fullPath.replace("%20", " ")
                var resourceName = fullPath
                if (resourceName.startsWith("/")) {
                    resourceName = resourceName.substring(1)
                }
                if (resourceName.indexOf("?") > 0) {
                    resourceName = resourceName.substring(0, resourceName.indexOf("?"))
                }
                if (!TextUtils.isEmpty(getContentTypeByResourceName(resourceName))) {
                    response.setContentType(getContentTypeByResourceName(resourceName))
                }
//                response.setContentType("application/javascript")
                val bInputStream = BufferedInputStream(context?.assets?.open(resourceName))
                response.sendStream(bInputStream, bInputStream.available().toLong())
            } catch (e: IOException) {
                e.printStackTrace()
                response.code(404).end()
                return@HttpServerRequestCallback
            }
        })
    }

    private fun getHtmlContent(htmlPath: String): String {
        return context?.assets?.open(htmlPath)?.bufferedReader().use {
            it!!.readText()
        }
    }

    val wifiIp: String
        get() {
            if (isWifiConnected) {
                try {
                    val info = (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                    if (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI) {
                        val wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
                        val infowifi = wifiManager.connectionInfo
                        return intIP2StringIP(infowifi.ipAddress)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "127.0.0.1"
                }

            } else {
                return "127.0.0.1"
            }
            return "127.0.0.1"
        }

    private val isWifiConnected: Boolean
        get() {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }

    //浏览sdcrad目录
    private fun lsSdcard() {
        server!!.get("/ls_sdcard") { request, response ->
            val fileArray = mutableListOf<FileItem>()
            listDir(Environment.getExternalStorageDirectory().path, fileArray)
            response.send(Gson().toJson(fileArray))
        }
    }

    private fun listDir(dir: String, fileArray: MutableList<FileItem>) {
        val fileNames = File(dir).list()  //列目录
        if (fileNames != null && fileNames.isNotEmpty()) {
            fileNames.forEach { fileName ->
                val file = File(dir, fileName)
                if (file.exists()) { //如果文件存在  && !file.name.startsWith(".")
                    val fileItem = FileItem()
                    fileItem.name = fileName
                    fileItem.path = file.absolutePath
                    if (file.isDirectory) { //文件夹
                        fileItem.isDir = 1
                        listDir(fileItem.path, fileItem.children)
                    } else { //文件
                        fileItem.isDir = 0
                    }
                    fileArray.add(fileItem)
                }
            }
            fileArray.sortBy { if (it.isDir == 1) 0 else 1 }
        }
    }

    private fun lsDir() {
        server!!.get("/ls_dir") { request, response ->
            val path = request.query.getString("path")
            val dir = File(path)
            val fileNames = dir.list()  //列目录
            if (fileNames != null && fileNames.isNotEmpty()) {
                val fileArray = mutableListOf<FileItem>()
                fileNames.forEach { fileName ->
                    val file = File(dir, fileName)
                    if (file.exists()) { //如果文件存在
                        val fileItem = FileItem()
                        fileItem.name = fileName
                        fileItem.path = file.absolutePath
                        if (file.isDirectory) { //文件夹
                            fileItem.isDir = 1
                        } else { //文件
                            fileItem.isDir = 0
                        }
                        fileArray.add(fileItem)
                    }
                }
                fileArray.sortBy { if (it.isDir == 1) 0 else 1 }
                response.send(Gson().toJson(fileArray))
            }
        }
    }

    private fun getVideo() {
        server!!.get("/video") { request, response ->
            val array = JSONArray()
            object : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg voids: Void): Void? {
                    try {
                        FileManager.getInstance().videos.forEach { video ->
                            Log.d("video--", video.toString())
                            val jsonObject = JSONObject()
                            jsonObject.put("name", video.name)
                            jsonObject.put("path", video.path)
                            array.put(jsonObject)
                        }
                        response.send(array.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return null
                }
            }.execute()
        }
    }

    //返回可读取流
    private fun sendStream() {
        server!!.get("/files/.*") { request, response ->
            var path = request.path.replace("/files/", "")
            try {
                path = URLDecoder.decode(path, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            val file = File(path)
            if (file.exists() && file.isFile) {
                try {
                    object : AsyncTask<Void, Void, Void>() {
                        override fun doInBackground(vararg voids: Void): Void? {
                            val fis = FileInputStream(file)
                            response.sendStream(fis, fis.available().toLong())
                            return null
                        }
                    }.execute()
                } catch (e: Exception) {
                    e.printStackTrace()
                    response.code(500).send("error!")
                }
            } else {
                response.code(404).send("Not found!")
            }
        }
    }

    private fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    private fun getContentTypeByResourceName(resourceName: String): String {
        if (resourceName.endsWith(".css")) {
            return CSS_CONTENT_TYPE
        } else if (resourceName.endsWith(".js")) {
            return JS_CONTENT_TYPE
        } else if (resourceName.endsWith(".swf")) {
            return SWF_CONTENT_TYPE
        } else if (resourceName.endsWith(".png")) {
            return PNG_CONTENT_TYPE
        } else if (resourceName.endsWith(".jpg") || resourceName.endsWith(".jpeg")) {
            return JPG_CONTENT_TYPE
        } else if (resourceName.endsWith(".woff")) {
            return WOFF_CONTENT_TYPE
        } else if (resourceName.endsWith(".ttf")) {
            return TTF_CONTENT_TYPE
        } else if (resourceName.endsWith(".svg")) {
            return SVG_CONTENT_TYPE
        } else if (resourceName.endsWith(".eot")) {
            return EOT_CONTENT_TYPE
        } else if (resourceName.endsWith(".mp3")) {
            return MP3_CONTENT_TYPE
        } else if (resourceName.endsWith(".mp4")) {
            return MP4_CONTENT_TYPE
        }
        return ""
    }

    companion object {
        private val TAG = "NIOHttpServer"
        var PORT_LISTEN_DEFALT = 5000
        private val TEXT_CONTENT_TYPE = "text/html;charset=utf-8"
        private val CSS_CONTENT_TYPE = "text/css;charset=utf-8"
        private val BINARY_CONTENT_TYPE = "application/octet-stream"
        private val JS_CONTENT_TYPE = "application/javascript"
        private val PNG_CONTENT_TYPE = "application/x-png"
        private val JPG_CONTENT_TYPE = "application/jpeg"
        private val SWF_CONTENT_TYPE = "application/x-shockwave-flash"
        private val WOFF_CONTENT_TYPE = "application/x-font-woff"
        private val TTF_CONTENT_TYPE = "application/x-font-truetype"
        private val SVG_CONTENT_TYPE = "image/svg+xml"
        private val EOT_CONTENT_TYPE = "image/vnd.ms-fontobject"
        private val MP3_CONTENT_TYPE = "audio/mp3"
        private val MP4_CONTENT_TYPE = "video/mpeg4"

        val instance: NIOHttpServer
            get() = NIOHttpServerHolder.INSTANCE
    }
}