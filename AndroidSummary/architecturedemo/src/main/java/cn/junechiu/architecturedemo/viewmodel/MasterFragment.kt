package cn.junechiu.architecturedemo.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class MasterFragment : Fragment() {

    private var model: SharedViewModel? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        //设置数据
        model?.setItem(Item(0, "hhh"))
    }

    /**
     * 在获取 ViewModelProvider 时两个 fragment 都使用 getActivity() 方法。
     * 这意味着它们都将会收到被 activity 限定的同一个 SharedViewModel 实例。
     * */
}