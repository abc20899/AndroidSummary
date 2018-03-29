package info.qianlong.interview.dagger2;

import javax.inject.Inject;

/**
 * Created by junzhao on 2018/1/7.
 */

public class Car {

    @Inject
    Tyre tyre; //使用@Inject标记的变量不能为private

    public Car() {
        DaggerCarCompoment.builder().build().injectCar(this);
        tyre.size();
    }
}
