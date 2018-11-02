package cn.junechiu.architecturedemo.lifecycle

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

//观察者对象
class MyLifecyleTextView : TextView, LifecycleObserver {

    val TAG = "MyLifecyleTextView"

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d(TAG, "oncreat...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d(TAG, "onstart...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d(TAG, "onresume...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pasue() {
        Log.d(TAG, "onpause...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d(TAG, "onstop...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d(TAG, "ondestroy...")
    }
}