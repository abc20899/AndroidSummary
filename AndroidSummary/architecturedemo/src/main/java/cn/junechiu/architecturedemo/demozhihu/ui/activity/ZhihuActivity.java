package cn.junechiu.architecturedemo.demozhihu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.junechiu.architecturedemo.App;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.data.Injection;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;
import cn.junechiu.architecturedemo.demozhihu.viewmodel.ZhihuViewModel;

public class ZhihuActivity extends AppCompatActivity {

    public static final String ZHIHU_TITLE = "zhihu_title";

    public static final String ZHIHU_ID = "zhihu_id";

    private WebView mWebView = null;

    private String mZhihuId = null;


    public static void startZhihuActivity(Activity activity, String zhihuId, String zhihuTitle) {
        if (activity == null || TextUtils.isEmpty(zhihuId)
                || TextUtils.isEmpty(zhihuTitle)) {
            return;
        }
        Intent intent = new Intent(activity, ZhihuActivity.class);
        intent.putExtra(ZhihuActivity.ZHIHU_ID, zhihuId);
        intent.putExtra(ZhihuActivity.ZHIHU_TITLE, zhihuTitle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        readIntent();
        initView();
        subscribeUI();
    }

    private void subscribeUI() {
        ZhihuViewModel.Factory factory = new ZhihuViewModel.Factory(App.Companion.getINSTANCE(),
                Injection.getDataRepository(App.Companion.getINSTANCE()), mZhihuId);
        ZhihuViewModel zhihuViewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel.class);
        zhihuViewModel.getZhihuDetail().observe(this, new Observer<ZhihuStoryDetail>() {
            @Override
            public void onChanged(@Nullable ZhihuStoryDetail detail) {
                if (detail == null) {
                    return;
                }
                mWebView.loadUrl(detail.getShare_url());
/*                if (TextUtils.isEmpty(detail.getBody())) {
                    mWebView.loadUrl(detail.getShare_url());
                } else {
                    String body = detail.getBody();
                    String[] cssList = detail.getCss();
                    String htmlTemp = WebUtil.buildHtmlWithCss(body, cssList, false);
                    mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, htmlTemp, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
                }*/
            }
        });
    }

    private void initView() {
        mWebView = findViewById(R.id.wv_zhihu);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    private void readIntent() {
        mZhihuId = this.getIntent().getStringExtra(ZHIHU_ID);
    }
}
