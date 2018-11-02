package cn.junechiu.architecturedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData


class UserViewModel : ViewModel() {

    private var users: MutableLiveData<List<User>>? = null

    fun getUsers(): LiveData<List<User>> {
        if (users == null) {
            users = MutableLiveData()
            loadUsers()
        }
        return users as MutableLiveData<List<User>>
    }

    fun loadUsers() {
        // 执行异步操作获取用户
        var list = mutableListOf<User>()
        for (i in 0..10) {
            list.add(User(i, "user-${i}"))
        }
        users?.value = list
    }

}