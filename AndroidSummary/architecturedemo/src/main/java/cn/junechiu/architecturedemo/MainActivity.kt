package cn.junechiu.architecturedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 总结一下就是用 LiveData 改造了一下 repositroy 数据层，UI 层通过 ViewModule 获取这个 LiveData，建立通道联系刷新数据，
 * 这样把整个 app 基于数据流改造成响应式架构，适应组件化，平台化高度封装，业务模块分离的需求。
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
