package info.qianlong.interview.dagger2;

import dagger.Component;

/**
 * Created by junzhao on 2018/1/7.
 */

@Component
public interface CarCompoment {
    void injectCar(Car car);
}
