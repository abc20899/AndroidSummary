package info.qianlong.interview.sound

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import info.qianlong.interview.R
import kotlinx.android.synthetic.main.activity_sound_recordbyte.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by junzhao on 2018/3/11.
 */
class RecordByteActivity : AppCompatActivity() {

    var mExcutorservice: ExecutorService? = null

    var isRecording = false

    var audioFile: File? = null

    var audioRecord: AudioRecord? = null

    var startRecordTime: Long = 0

    var stopRecordTime: Long = 0

    var fileOutputStream: FileOutputStream? = null

    var bufferSize = 2048

    var buffer = ByteArray(bufferSize)

    var hanlder = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                -1 -> {
                    Toast.makeText(this@RecordByteActivity, "失败", Toast.LENGTH_LONG).show()
                }
                2 -> {
                    audio_info.text = msg!!.obj.toString()
                }
                3 -> {
                    audio_info.text = msg!!.obj.toString()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_recordbyte)

        //录音jni函数不具备线程安全性 所有使用单线程
        mExcutorservice = Executors.newSingleThreadExecutor()
        btnRecord.setOnClickListener {
            start()
        }

        playRecord.setOnClickListener {
//            startActivity<PlayByteActivity>("file" to audioFile!!.absolutePath)
        }
    }

    fun start() {
        if (isRecording) {
            btnRecord.text = "开始"
            isRecording = false
        } else {
            btnRecord.text = "停止"
            isRecording = true
            mExcutorservice!!.submit {
                if (!startRecord()) {
                    recordFail()
                }
            }
        }
    }

    fun recordFail() {
        isRecording = false
        var msg = Message()
        msg.what = 3
        msg.obj = "录音失败"
        hanlder.sendMessage(msg)
    }

    fun startRecord(): Boolean {
        try {
            //创建录音文件
            audioFile = File(Environment.getExternalStorageDirectory().absolutePath + "/junechiu/" +
                    System.currentTimeMillis() + ".pcm")
            audioFile!!.parentFile.mkdirs()
            audioFile!!.createNewFile()
            //创建文件输出流
            fileOutputStream = FileOutputStream(audioFile)
            //配置AudioRecord
            var audioSource = MediaRecorder.AudioSource.MIC
            var simpleRate = 44100
            //单声道
            var channelConfig = AudioFormat.CHANNEL_IN_MONO
            //所有android 通用
            var audioFormat = AudioFormat.ENCODING_PCM_16BIT
            var minBufferSize = AudioRecord.getMinBufferSize(simpleRate, channelConfig, audioFormat)
            audioRecord = AudioRecord(audioSource, simpleRate, channelConfig, audioFormat,
                    Math.max(minBufferSize, bufferSize))
            //开始录音
            audioRecord!!.startRecording()
            //统计时长
            startRecordTime = System.currentTimeMillis()
            //循环读取数据，写入到输出流中
            while (isRecording) {
                var read = audioRecord!!.read(buffer, 0, bufferSize)
                if (read > 0) {
                    fileOutputStream!!.write(buffer, 0, read)
                } else {
                    //读取失败
                    return false
                }
            }
            //退出循环，释放资源
            return stopRecord()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            //释放资源
            if (audioRecord != null) {
                audioRecord!!.release()
            }
        }
    }

    fun stopRecord(): Boolean {
        try {
            //停止录音 关闭输出流
            audioRecord!!.stop()
            audioRecord!!.release()
            audioRecord = null
            fileOutputStream!!.close()
            //记录结束时间，统计时长
            stopRecordTime = System.currentTimeMillis()
            //只接受超过3秒的录音
            var duration = ((stopRecordTime - startRecordTime) / 1000).toInt()
            if (duration > 3) {
                //在主线程 ui显示
                var msg = Message()
                msg.what = 2
                msg.obj = "录音成功 " + duration + "秒"
                hanlder.sendMessage(msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mExcutorservice!!.shutdownNow()
    }
}