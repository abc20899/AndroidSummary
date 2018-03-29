package info.qianlong.interview.dagger2.demo;

import javax.inject.Inject;

/**
 * Created by junzhao on 2018/1/7.
 */

public class College {

    @Inject
    Student student;

    public College() {
        DaggerCollegeCompoment.builder().build().inject(this);
    }
}
