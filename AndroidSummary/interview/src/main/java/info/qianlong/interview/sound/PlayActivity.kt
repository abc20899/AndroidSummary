package info.qianlong.interview.sound

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import info.qianlong.interview.R
import kotlinx.android.synthetic.main.activity_sound_play.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by junzhao on 2018/3/13.
 */
class PlayActivity : AppCompatActivity() {

    var isPlaying = false

    var mExcutorservice: ExecutorService? = null

    var mediaPlayer: MediaPlayer? = null

    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == -1) {
                file_info.text = "播放失败"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_play)

        mExcutorservice = Executors.newSingleThreadExecutor()

        var filePath = intent.extras["file"] as String

        playBtn.setOnClickListener {
            if (!isPlaying && !TextUtils.isEmpty(filePath)) {
                mExcutorservice!!.submit {
                    doPlay(filePath)
                }
            }
        }
    }

    fun doPlay(audioPath: String) {
        //配置播放器
        mediaPlayer = MediaPlayer()
        try {
            //设置声音文件
            mediaPlayer!!.setDataSource(audioPath)
            //设置监听回调
            mediaPlayer!!.setOnCompletionListener {
                //播放结束，释放播放器
                stopPlay()
            }
            mediaPlayer!!.setOnErrorListener { mp, what, extra ->
                playFail()
                stopPlay()
                true
            }
            //音量，是否循环
            mediaPlayer!!.setVolume(1f, 1f)
            mediaPlayer!!.isLooping = false
            //准备，开始
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
            playFail()
            stopPlay()
        }
        //设置监听回调
        //异常处理
    }

    fun playFail() {
        handler.sendEmptyMessage(-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlay()
        if (mExcutorservice != null) {
            mExcutorservice!!.shutdownNow()
        }
    }

    fun stopPlay() {
        isPlaying = false
        if (mediaPlayer != null) {
            mediaPlayer!!.setOnCompletionListener(null)
            mediaPlayer!!.setOnErrorListener(null)


            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
}