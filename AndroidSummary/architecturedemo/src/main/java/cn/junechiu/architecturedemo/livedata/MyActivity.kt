package cn.junechiu.architecturedemo.livedata

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cn.junechiu.architecturedemo.App
import cn.junechiu.architecturedemo.R
import cn.junechiu.architecturedemo.util.PermissionUtils
import kotlinx.android.synthetic.main.activity_livedata.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_livedata)

        //注册 添加观察者
        App.INSTANCE?.liveData?.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                showValue.text = t //更新控件中的数据
                Log.d("liveData", "数据改变..." + t)
            }
        })

        dataBtn.setOnClickListener {
            App.INSTANCE?.liveData?.updateValue("数据改变了...")
            startActivity(Intent(this, SecondActivity::class.java))
        }

        PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, object : PermissionUtils.PermissionGrant {
            override fun onPermissionGranted(requestCode: Int) {
            }
        })
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, object : PermissionUtils.PermissionGrant {
            override fun onPermissionGranted(requestCode: Int) {
            }
        })
    }
}

/*
* liveData 是可以使用泛型的，Google 推出 liveData 的初衷，就是用来包裹数据，包装成一个Observable的
*
* LiveData 是一个数据持有者类，它持有一个值并允许观察该值。不同于普通的可观察者，LiveData 遵守应用程序组件的生命周期，
* 以便 Observer 可以指定一个其应该遵守的 Lifecycle。
*
* onActive()
* 当 LiveData 有一个处于活动状态的观察者时该方法被调用
*
* onInactive()
* 当 LiveData 没有任何处于活动状态的观察者时该方法被调用。
*
* setValue()
* 调用该方法更新 LiveData 实例的值，并将此变更通知给处于活动状态的观察者。
*
* 观察者在注册到 liveData 后，不会触发执行一次 setvalue 方法，这点搞清楚基本就 OK 了
*
*
LiveData 有以下优点：
没有内存泄漏：因为 Observer 被绑定到它们自己的 Lifecycle 对象上，所以，当它们的 Lifecycle 被销毁时，它们能自动的被清理。

不会因为 activity 停止而崩溃：如果 Observer 的 Lifecycle 处于闲置状态（例如：activity 在后台时），它们不会收到变更事件。

始终保持数据最新：如果 Lifecycle 重新启动（例如：activity 从后台返回到启动状态）将会收到最新的位置数据（除非还没有）。

正确处理配置更改：如果 activity 或 fragment 由于配置更改（如：设备旋转）重新创建，将会立即收到最新的有效位置数据。

资源共享：可以只保留一个 MyLocationListener 实例，只连接系统服务一次，并且能够正确的支持应用程序中的所有观察者。

不再手动管理生命周期你可能已经注意到，fragment 只是在需要的时候观察数据，不用担心被停止或者在停止之后启动观察。
由于 fragment 在观察数据时提供了其 Lifecycle，所以 LiveData 会自动管理这一切。
*
*
* LiveData 的转换
LiveData<String> userName = Transformations.map(userLiveData, user -> {
      user.name + " " + user.lastName
  });

  LiveData<String> userId = ...;
LiveData<UserEntity> user = Transformations.switchMap(userId, id -> getUser(id) );
* **/