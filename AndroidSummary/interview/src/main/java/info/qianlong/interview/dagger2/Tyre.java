package info.qianlong.interview.dagger2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by junzhao on 2018/1/7.
 * 轮胎类使用Inject标记为被依赖的目标对象
 */
public class Tyre {

    @Inject
    public Tyre() {
    }

    public void size() {
        Log.d("轮胎号码", "50cm");
    }
}
