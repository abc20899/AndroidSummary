package cn.junechiu.architecturedemo.livedata

import android.util.Log
import androidx.lifecycle.LiveData

class MyLiveData : LiveData<String>() {

    override fun onActive() {
        super.onActive()
        Log.d("MyLiveData", "onActive...绑定")
    }

    override fun onInactive() {
        super.onInactive()
        Log.d("MyLiveData", "onInactive...解绑")
    }

    override fun setValue(value: String?) {
        super.setValue(value)
        Log.d("MyLiveData", "setValue..." + value)
    }

    fun updateValue(value: String?) {
        setValue(value)
    }
}