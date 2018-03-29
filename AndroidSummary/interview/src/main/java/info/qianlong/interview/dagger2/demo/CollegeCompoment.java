package info.qianlong.interview.dagger2.demo;

import dagger.Component;

/**
 * Created by junzhao on 2018/1/7.
 */

@Component
public interface CollegeCompoment {

    void inject(College college);
}
