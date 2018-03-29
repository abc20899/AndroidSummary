package info.qianlong.interview.sound

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Toast
import info.qianlong.interview.R
import kotlinx.android.synthetic.main.activity_sound_record.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by junzhao on 2018/3/11.
 */
class RecordFileActivity : AppCompatActivity() {

    var mExcutorservice: ExecutorService? = null

    var mediaRecord: MediaRecorder? = null

    var audioFile: File? = null

    var startRecordTime: Long = 0

    var stopRecordTime: Long = 0

    var hanlder = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                -1 -> {
                    Toast.makeText(this@RecordFileActivity, "失败", Toast.LENGTH_LONG).show()
                }
                2 -> {
                    audio_info.text = msg!!.obj.toString()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_record)

        btnRecord.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    statrtRecord()
                }
                MotionEvent.ACTION_MOVE -> {

                }
                MotionEvent.ACTION_UP -> {
                    stopRecord()
                }
            }
            true
        }

        playRecord.setOnClickListener {
//            startActivity<PlayActivity>("file" to audioFile!!.absolutePath)
        }

        //录音jni函数不具备线程安全性 所有使用单线程
        mExcutorservice = Executors.newSingleThreadExecutor()
    }

    fun statrtRecord() {
        btnRecord.text = "正在说话"

        mExcutorservice!!.submit {
            //释放之前的录音
            releaseRecord()
            //开始录音 ，录音失败
            if (!doStart()) {
                recordFail()
            }
        }
    }

    fun stopRecord() {
        btnRecord.text = "开始说话"
        mExcutorservice!!.submit {
            //执行录音停止逻辑
            //是否失败
            if (!doStop()) {
                recordFail()
            }
            //释放mediaRecord
            releaseRecord()
        }
    }

    fun releaseRecord() {
        if (mediaRecord != null) {
            mediaRecord!!.release()
            mediaRecord = null
        }
    }

    fun doStart(): Boolean {
        try {
            //创建mediaRecord
            mediaRecord = MediaRecorder()

            //创建录音文件
            audioFile = File(Environment.getExternalStorageDirectory().absolutePath + "/junechiu/" +
                    System.currentTimeMillis() + ".m4a")
            audioFile!!.parentFile.mkdirs()
            audioFile!!.createNewFile()

            //配置mediaRecord
            //从麦克风采集
            mediaRecord!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            //保存为mp4格式
            mediaRecord!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            //采样频率
            mediaRecord!!.setAudioSamplingRate(44100)
            //文件编码 aac
            mediaRecord!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            //设置音质
            mediaRecord!!.setAudioEncodingBitRate(96000)
            //设置录音文件位置
            mediaRecord!!.setOutputFile(audioFile!!.absolutePath)

            //开始录音
            mediaRecord!!.prepare()
            mediaRecord!!.start()
            //统计时长
            startRecordTime = System.currentTimeMillis()
            //录音成功
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun doStop(): Boolean {
        try {
            //停止录音
            mediaRecord!!.stop()
            //记录时间
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
            return false
        }
        //停止成功
        return true
    }

    fun recordFail() {
        audioFile = null
        //主线程 执行
        hanlder.sendEmptyMessage(-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        //避免内存泄漏
        mExcutorservice!!.shutdownNow()
        releaseRecord()
    }
}