package info.qianlong.interview.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import info.qianlong.interview.R;

/**
 * Created by junzhao on 2018/1/6.
 */

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

//        loadSampleImg();
        loadImage();
    }

    public void loadSampleImg() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=" +
                "1515251087604&di=1ff2f71782c4d7093cedf6c5ae06acad&imgtype=0&src=http%3A%2F%2F" +
                "image.tianjimedia.com%2FuploadImages%2F2015%2F209%2F42%2F6H182F668EP9.jpg";
        ImageView imageView = findViewById(R.id.sample_image);
        Glide.with(this).load(url).into(imageView);
    }

    public void loadImage() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=" +
                "1515251087604&di=1ff2f71782c4d7093cedf6c5ae06acad&imgtype=0&src=http%3A%2F%2F" +
                "image.tianjimedia.com%2FuploadImages%2F2015%2F209%2F42%2F6H182F668EP9.jpg";
        ImageView imageView = findViewById(R.id.sample_image);
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.btn_indicator)
                .override(500, 500)//指定图片尺寸
                .fitCenter() //指定图片缩放类型
                .centerCrop()//指定图片缩放类型
                .skipMemoryCache(true) //跳过内存缓存 还会使用磁盘缓存
                .crossFade(1000)//设置渐变式显示时间
                .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
                //DiskCacheStrategy.SOURCE 全分辨率图像
                //DiskCacheStrategy.RESULT 缓存最终的图像
                .priority(Priority.HIGH) //指定优先级
                .into(imageView);
    }

}
