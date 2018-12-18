package info.qianlong.basicdemo.media_provider

import android.Manifest
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import info.qianlong.basicdemo.R
import info.qianlong.basicdemo.media_provider.utils.FileManager
import info.qianlong.basicdemo.media_provider.utils.FileUtils
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    val perms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    )

    private val PERMISSION_REQUESTCODE = 10011

    protected fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *perms)) {
        } else {
            EasyPermissions.requestPermissions(
                    this, getString(R.string.title_settings_rationale),
                    PERMISSION_REQUESTCODE, *perms
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()

//        getApps()

        getImageFolders()
    }

    fun getApps() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    FileManager.getInstance().appInfos.forEach { app ->
                        Log.d("app--", app.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    fun getVideos() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    FileManager.getInstance().videos.forEach { video ->
                        Log.d("video--", video.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    fun getMusics() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    FileManager.getInstance().musics.forEach { music ->
                        Log.d("music--", music.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    fun getFilesByType(type: Int) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    FileManager.getInstance().getFilesByType(type).forEach { file ->
                        Log.d("file--", file.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    fun getImageFolders() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    FileManager.getInstance().imageFolders.forEach { folders ->
                        Log.d("folders--", folders.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).setRequestCode(PERMISSION_REQUESTCODE).build()
                    .show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        getVideos()
//        getMusics()
//        getFilesByType(FileUtils.TYPE_DOC)
//        getImageFolders()
    }
}