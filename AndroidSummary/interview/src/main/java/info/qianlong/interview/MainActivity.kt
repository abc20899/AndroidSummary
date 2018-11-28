package info.qianlong.interview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.load.model.LazyHeaders

class MainActivity : AppCompatActivity() {

    var mQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Example of a call to a native method
//        sample_text.text = stringFromJNI()

        //第一步需要获取一个RequestQueue对象
        mQueue = Volley.newRequestQueue(this)
        testVolley()
        //测试jni
//        testJni()

        //测试socket
//        Thread({
//            SocketDemo().tcpSendMessage("ddddd")
//        }).start()

        //测试rxjava
//        RxjavaDoc1.test4()
//        RealmHelper.use {
//            executeTransactionAsync({ realm ->
//                val user = realm.createObject(UserEntity::class.java)
//                user.userid = 1
//                user.phone = "18511666666"
//            }, {
//                //refresh()  success
//            }, { error ->
//            })
//        }

//        Car()

//        RxjavaDoc2(this).queryData()

//        RxjavaDoc4().demo3()

        var image = findViewById<ImageView>(R.id.image)
        //Authorization 请求头信息
        val headers = LazyHeaders.Builder()
//                addHeader("User-Agent", token)
                .addHeader("Referer", "http://i.meizitu.net").build()
        //url 要加载的图片的地址，imageView 显示图片的ImageView
//        Glide.with(this).load<GlideUrl>(GlideUrl(
//                "http://i.meizitu.net/2018/01/19c48.jpg", headers)).into(image)
    }

    /////////////////////////----------------Volley----------------/////////////////////////////
    fun testVolley() {
        stringRequest()
//        volleyJsonRequest()
    }

    //volley字符串请求
    fun stringRequest() {
        //第二步创建请求
        var stringQuest = StringRequest("http://192.168.199.121:8001/meizi/get?id=11", { response ->
            Log.d("stringRequest", "onResponse: " + response);
        }, { volleyError ->
            Log.d("stringRequest", "error: " + volleyError.message);
        })
        //第三步将请求添加到序列
        mQueue!!.add(stringQuest)
    }

    //volleyJson请求
    fun volleyJsonRequest() {
        //第二步
        var jsonObjectRequest = JsonObjectRequest("http://www.weather.com.cn/data/sk/101010100.html",
                null, { response ->
            Log.d("jsonObjectRequest", "onResponse: " + response.toString());
        }, { error ->
            Log.e("jsonObjectRequest", "onErrorResponse: " + error.message);
        })
        //第三步
        mQueue!!.add(jsonObjectRequest);
    }

//    fun testJni() {
//        var hello = HelloJni()
//        Log.d("HelloJni: ", hello.getStrFromC());
//        Log.d("HelloJni: ", hello.sum(3, 5).toString());
//        Log.d("HelloJni: ", hello.sayHello("hehe"));
//        var arr = intArrayOf(1, 2, 3, 4, 5, 6, 7)
//        var arr2 = hello.increaseArrayEles(arr)
//        arr2.forEach {
//            Log.d("HelloJni: ", it.toString());
//        }
//
//        var check = CheckPasswd()
//        Log.d("CheckPasswd: ", check.checkPwd("123456").toString());
//
//        var callJava = CCallJava()
//        callJava.callbackHelloFromJava()
//        callJava.callbackAdd()
//        callJava.printString("ddddd")
//        CCallJava.sayHello("syahello")
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
