package info.qianlong.interview.sound;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.qianlong.interview.R;

/**
 * Created by junzhao on 2018/3/13.
 */

public class PlayByteActivity extends AppCompatActivity {

    private boolean isPlaying = false;

    private ExecutorService mExcutorservice = null;

    private int bufferSize = 2048;

    private byte[] buffer;

    private TextView infoText;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                infoText.setText("播放失败");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_playbyte);

        infoText = findViewById(R.id.audio_info);

        mExcutorservice = Executors.newSingleThreadExecutor();
        String filePath = getIntent().getStringExtra("file");

        buffer = new byte[bufferSize];
        (findViewById(R.id.playBtn)).setOnClickListener(v -> {
            mExcutorservice.submit(() -> {
                if (!isPlaying && !TextUtils.isEmpty(filePath)) {
                    doPlay(filePath);
                }
            });
        });
    }

    public void doPlay(String audioPath) {
        isPlaying = true;
        //配置播放器
        //循环写数据到播放器
        int streamType = AudioManager.STREAM_MUSIC;
        int sampleRate = 44100;
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int mode = AudioTrack.MODE_STREAM;
        int minBuffersize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        AudioTrack audioTrack = new AudioTrack(streamType, sampleRate, channelConfig, audioFormat,
                Math.max(minBuffersize, bufferSize), mode);
        FileInputStream inputStream = null;
        try {
            audioTrack.play();
            //从文件流中读取数据
            File audioFile = new File(audioPath);
            inputStream = new FileInputStream(audioFile);
            int read;
            while ((read = inputStream.read(buffer)) > 0) {
                int ret = audioTrack.write(buffer, 0, read);
                switch (ret) {
                    case AudioTrack.ERROR_INVALID_OPERATION:
                    case AudioTrack.ERROR_BAD_VALUE:
                    case AudioTrack.ERROR_DEAD_OBJECT:
                        playFail();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            playFail();
            stopPlay();
        } finally {
            //关闭输入流
            isPlaying = false;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            audioTrack.stop();
            audioTrack.release();
        }
    }

    public void playFail() {
        handler.sendEmptyMessage(-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlay();
        if (mExcutorservice != null) {
            mExcutorservice.shutdownNow();
        }
    }

    public void stopPlay() {
        isPlaying = false;
    }
}
