package cn.junechiu.architecturedemo.demozhihu.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.util.Util;

public class GirlActivity extends AppCompatActivity {

    private static final String GIRL_IMG_URL = "girl_img_url";

    private String mGirlImgUrl = null;

    public static void startGirlActivity(Activity activity, String girlUrl) {
        if (activity == null || TextUtils.isEmpty(girlUrl)) {
            return;
        }
        Intent intent = new Intent(activity, GirlActivity.class);
        intent.putExtra(GIRL_IMG_URL, girlUrl);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_girl);
        readIntent();
        initView();
    }

    private void readIntent() {
        Intent intent = GirlActivity.this.getIntent();
        if (intent.hasExtra(GIRL_IMG_URL)) {
            mGirlImgUrl = intent.getStringExtra(GIRL_IMG_URL);
        }
    }

    private void initView() {
        ImageView photoView = findViewById(R.id.photoView);
        photoView.setOnLongClickListener(view -> {
//            showSaveGirlDialog();
            return true;
        });
        Glide.with(this)
                .load(mGirlImgUrl)
                .into(photoView);
    }

    private void showSaveGirlDialog() {
        new AlertDialog.Builder(GirlActivity.this)
                .setMessage(getString(R.string.girl_save))
                .setNegativeButton(android.R.string.cancel, (anInterface, i) -> anInterface.dismiss())
                .setPositiveButton(android.R.string.ok, (anInterface, i) -> {
                    anInterface.dismiss();
                    saveGirl();
                }).show();
    }

    private void saveGirl() {
        File directory = new File(Environment.getExternalStorageDirectory(), "download");
        if (!directory.exists())
            directory.mkdirs();
//        Bitmap drawingCache = .getImageView().getDrawingCache();
        try {
            File file = new File(directory, new Date().getTime() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
//            drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            GirlActivity.this.getApplicationContext().sendBroadcast(intent);
//            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_succeed));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_failed));
        }
    }
}
