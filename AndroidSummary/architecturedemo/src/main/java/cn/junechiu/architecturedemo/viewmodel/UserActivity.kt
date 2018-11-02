package cn.junechiu.architecturedemo.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.junechiu.architecturedemo.R
import kotlinx.android.synthetic.main.activity_viewmodel.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_viewmodel)
        val model = ViewModelProviders.of(this).get(UserViewModel::class.java)
        //liveData添加观察者
        model.getUsers().observe(this, Observer { users ->
            // 更新 UI
            showValue.text = users[0].name
        })

        /**
         * 如果 activity 被重新创建，它将会收到由之前 activity 创建的同一个 MyViewModel 实例。当所有者 activity 结束后，
         * Framework 会调用 ViewModel 的 onCleared() 方法来清理资源。
         * */
    }
}

/**
 * ViewModel 就是 MVP 中，P 的角色，当然 ViewModel 还有他的独特之处。ViewModel 的生命周期虽然总体上还是跟着 view 的，
 * 但是 ViewModel 存在的时间比 view 要长一些。
 *
 * 屏幕旋转，系统主动杀死造成的 Activity 或者 Fragment 被销毁或重新创建，所以保存于其中的数据有可能会丢失
 * 在 Activity 或者 Fragment 中会经常发起一些需要一定时间才会返回结果的异步请求调用
 *
 * 还有一个 AndroidViewModel ，期中可以获取 application ，只不过这个 application 需要我们自己传入，
 * 另外 ViewModel 的 of 方法还可以接受一个 ViewModelProvider.NewInstanceFactory 的参数，可以支持自定义构造方法，
 * 想传几个参数都没问题
 *
 * 注：由于 ViewModel 存活的比个别的 activity 和 fragment 实例，所以它决不能引用 View，或任何持有
 * activity context 的引用的类。如果 ViewModel 需要 Application 的 context（如：调用系统服务），
 * 可以继承 AndroidViewModel 类，可以在构造函数中接受 Application（因为 Application 继承了 Context）。
 *
 * 在 Fragment 之间共享数据
 *
 * */