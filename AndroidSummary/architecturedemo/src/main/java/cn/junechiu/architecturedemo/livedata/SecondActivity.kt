package cn.junechiu.architecturedemo.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cn.junechiu.architecturedemo.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)

        LocationLiveData.instance.init(this)
        //添加观察者
        LocationLiveData.instance.observe(this, Observer { location ->
            dataText.text = "位置信息: " + location.latitude + " " + location.longitude
        })
    }
}