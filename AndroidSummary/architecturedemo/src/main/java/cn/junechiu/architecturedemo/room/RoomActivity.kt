package cn.junechiu.architecturedemo.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.junechiu.architecturedemo.R
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_room)

        addBtn.setOnClickListener {
            val user = UserEntity("june", "chiu")
            AppDataBase.getDefault(applicationContext).userDao().insertAll(user)
        }

        delBtn.setOnClickListener {
            val user = UserEntity("june", "chiu")
            AppDataBase.getDefault(applicationContext).userDao().delete(user)
        }

        updateBtn.setOnClickListener {
            val user = UserEntity("june", "chiu")
            AppDataBase.getDefault(applicationContext).userDao().update(user)
        }

        queryBtn.setOnClickListener {
            val user = AppDataBase.getDefault(applicationContext).userDao().findByName("june", "chiu")
            showValue.text = user.firstName + " " + user.lastName
        }

    }
}