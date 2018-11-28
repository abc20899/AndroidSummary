package info.qianlong.interview.sound

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import info.qianlong.interview.R
import kotlinx.android.synthetic.main.activity_sound_playbyte.*
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by junzhao on 2018/3/13.
 */
class PlayByteActivityd : AppCompatActivity() {

    var isPlaying = false

    var mExcutorservice: ExecutorService? = null

    var read = 0

    var bufferSize = 2048

    var buffer = ByteArray(bufferSize)

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
        setContentView(R.layout.activity_sound_playbyte)

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
        //循环写数据到播放器
        var streamType = AudioManager.STREAM_MUSIC
        var sampleRate = 44100
        var channelConfig = AudioFormat.CHANNEL_OUT_MONO
        var audioFormat = AudioFormat.ENCODING_PCM_16BIT
        var mode = AudioTrack.MODE_STREAM
        var minBuffersize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        var audioTrack = AudioTrack(streamType, sampleRate, channelConfig, audioFormat,
                Math.max(minBuffersize, bufferSize), mode)
        var inputStream: FileInputStream? = null
        try {
            //从文件流中读取数据
            var audioFile = File(audioPath)
            inputStream = FileInputStream(audioFile)
            while (readByte(inputStream) > 0) {
                var ret = audioTrack.write(buffer, 0, readByte(inputStream))
                when (ret) {
                    AudioTrack.ERROR_INVALID_OPERATION -> {
                        playFail()
                    }
                    AudioTrack.ERROR_BAD_VALUE -> {
                        playFail()
                    }
                    AudioTrack.ERROR_DEAD_OBJECT -> {
                        playFail()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            playFail()
            stopPlay()
        } finally {
            //关闭输入流
            isPlaying = false
            if (inputStream != null) {
                inputStream.close()
            }
            audioTrack.stop()
            audioTrack.release()
        }
    }

    fun readByte(inputStream: FileInputStream): Int {
        read = inputStream.read(buffer)
        return read
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
    }
}