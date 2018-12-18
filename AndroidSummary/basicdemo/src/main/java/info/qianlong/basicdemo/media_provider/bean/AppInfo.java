package info.qianlong.basicdemo.media_provider.bean;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public class AppInfo {

    public ApplicationInfo applicationInfo;

    public long versionCode = 0;

    public Drawable icon; //图片的icon

    public String apkName; //程序的名字

    public long apkSize; //程序大小

    /**
     * 表示到底是用户app还是系统app
     * 如果表示为true 就是用户app
     * 如果是false表示系统app
     */
    public boolean isUserApp;

    public boolean isRom; //放置的位置

    public String apkPackageName; //包名

    @Override
    public String toString() {
        return "AppInfo{" +
                "applicationInfo=" + applicationInfo +
                ", versionCode=" + versionCode +
                ", icon=" + icon +
                ", apkName='" + apkName + '\'' +
                ", apkSize=" + apkSize +
                ", isUserApp=" + isUserApp +
                ", isRom=" + isRom +
                ", apkPackageName='" + apkPackageName + '\'' +
                '}';
    }
}
