package cn.junechiu.architecturedemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var select: MutableLiveData<Item>? = null

    fun setItem(item: Item) {
        if (select == null) {
            select = MutableLiveData()
        }
        select?.value = item
    }

    fun getSelected(): MutableLiveData<Item>? {
        return select
    }

}