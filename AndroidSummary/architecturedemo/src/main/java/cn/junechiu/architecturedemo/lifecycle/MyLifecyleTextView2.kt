package cn.junechiu.architecturedemo.lifecycle

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

@SuppressLint("AppCompatCustomView")
class MyLifecyleTextView2 : TextView, LifecycleObserver {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d("AAA", "oncreat...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d("AAA", "onstart...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d("AAA", "onresume...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pasue() {
        Log.d("AAA", "onpause...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d("AAA", "onstop...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d("AAA", "ondestroy...")
    }

}
