package cn.junechiu.architecturedemo.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModule(application: Application, var name: String) : AndroidViewModel(application) {

    fun show() {
        Toast.makeText(getApplication(), "测试...", Toast.LENGTH_SHORT).show()
    }

    class Factroy(var application: Application, var name: String) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyViewModule(application, name) as T
        }
    }
}

/**
 *
 * 创建 MyViewModule 对象
MyViewModule.Factroy factroy = new MyViewModule.Factroy(getApplication(), "AAA");
MyViewModule myViewModule = ViewModelProviders.of(this, factroy).get(MyViewModule.class);
 * */