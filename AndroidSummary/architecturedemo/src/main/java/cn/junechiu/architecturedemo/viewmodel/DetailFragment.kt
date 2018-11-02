package cn.junechiu.architecturedemo.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class DetailFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        model.getSelected()?.observe(this, Observer { item ->
            //更新ui
        })
    }

    /**
     * 在获取 ViewModelProvider 时两个 fragment 都使用 getActivity() 方法。
     * 这意味着它们都将会收到被 activity 限定的同一个 SharedViewModel 实例。
     * */
}