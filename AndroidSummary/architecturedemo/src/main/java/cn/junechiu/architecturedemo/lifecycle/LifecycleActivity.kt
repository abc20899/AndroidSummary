package cn.junechiu.architecturedemo.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cn.junechiu.architecturedemo.R
import kotlinx.android.synthetic.main.activity_lifecycle.*

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lifecycle)

        //注册 lifeText观察者
        lifecycle.addObserver(lifeText)

        //
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun start() {
                Log.d("AAA", "new_onstart...");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun resume() {
                Log.d("AAA", "new_onresume...");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun pasue() {
                Log.d("AAA", "new_onpause...");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun stop() {
                Log.d("AAA", "new_onstop...");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroy() {
                Log.d("AAA", "new_ondestroy...");
            }
        })

        //Lifecycle.State 通过 Lifecycle 我们还可以获取当前 view 的生命周期状态
        val currentState = lifecycle.currentState
        if (currentState == Lifecycle.State.STARTED) {
            Log.d("currentState", "start...");
        }
    }
}

/*
*
* 上面的代码基本包含了 Lifecycle 的常用应用场景了，不管你事让某个自定义 view hook 某个生命周期也行，
* 还是直接注册一个观察者对象也行，在代码上我们完全脱离了页面生命周期函数里，
* 页面或是 penserent ，viewmodule 都不用再保存相关的 callback ，proxy，listener 在页面的生命周期函数里执行了，
* 在 app 代码层面实现了基于观察者模式的代码思路。减少强制持有属性，功能，代理对象，这也是一种解耦啊，和以前对象.方法比起来，
* 代码是越写越灵活啊，这就是趋势啊。
*
*
* 保持 UI 控制器（Activity 和 Fragment）尽可能的精简。它们不应该试图去获取它们所需的数据；相反，要用 ViewModel 来获取，并且观察 LiveData 将数据变化反映到视图中。

尝试编写数据驱动（data-driven）的 UI，即 UI 控制器的责任是在数据改变时更新视图或者将用户的操作通知给 ViewModel。

将数据逻辑放到 ViewModel 类中。ViewModel 应该作为 UI 控制器和应用程序其它部分的连接服务。注意：不是由 ViewModel 负责获取数据（例如：从网络获取）。相反，ViewModel 调用相应的组件获取数据，然后将数据获取结果提供给 UI 控制器。

使用 Data Binding 来保持视图和 UI 控制器之间的接口干净。这样可以让视图更具声明性，并且尽可能减少在 Activity 和 Fragment 中编写更新代码。如果你喜欢在 Java 中执行该操作，请使用像 Butter Knife 这样的库来避免使用样板代码并进行更好的抽象化。

如果 UI 很复杂，可以考虑创建一个 Presenter 类来处理 UI 的修改。虽然通常这样做不是必要的，但可能会让 UI 更容易测试。

不要在 ViewModel 中引用 View 或者 Activity 的 context。因为如果 ViewModel 存活的比 Activity 时间长（在配置更改的情况下），Activity 将会被泄漏并且无法被正确的回收。

作者：前行的乌龟
链接：https://www.jianshu.com/p/b8a29407b6e1
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
* **/