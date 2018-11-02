package cn.junechiu.architecturedemo.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModel(@NonNull application: Application, name: String) : AndroidViewModel(application) {

    var name: String? = null

    init {
        this.name = name
    }

    fun show() {
        Toast.makeText(getApplication(), "测试...", Toast.LENGTH_SHORT).show()
    }

    class Factroy(var application: Application, var name: String) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AppViewModel(application, name) as T
        }
    }

}