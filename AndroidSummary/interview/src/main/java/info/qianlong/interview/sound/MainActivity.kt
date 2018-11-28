package info.qianlong.interview.sound

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import info.qianlong.interview.R
import kotlinx.android.synthetic.main.activity_main_sound.*


/**
 * Created by junzhao on 2018/3/11.
 */
class MainActivity : AppCompatActivity() {

    private val MY_PERMISSION_REQUEST_CODE = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sound)
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        val isAllGranted = checkPermissionAllGranted(
                arrayOf(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
        )

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSION_REQUEST_CODE
        )

        fileMode.setOnClickListener { v ->
//            startActivity<RecordFileActivity>()
        }
        bytemode.setOnClickListener {
//            startActivity<RecordByteActivity>()
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false
            }
        }
        return true
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull
    permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            var isAllGranted = true

            // 判断是否所有的权限都已经授予了
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
            }
        }
    }

}