package cn.junechiu.junecore.app;

import android.content.Context;
import android.os.Handler;

import com.blankj.utilcode.util.Utils;

import java.util.WeakHashMap;

/**
 * Created by junzhao on 2017/12/2.
 * 不允许继承
 */
public final class June {

    //Context context 为application
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        Utils.init(context);
        return Configurator.getInstance();
    }

    public static WeakHashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getJuneConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfigration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Handler getHandler() {
        return getConfigration(ConfigKeys.HANDLER);
    }

    public static Context getApplication() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT.name());
    }

}
