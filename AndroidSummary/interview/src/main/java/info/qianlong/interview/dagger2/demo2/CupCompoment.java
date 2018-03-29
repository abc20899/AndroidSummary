package info.qianlong.interview.dagger2.demo2;

import dagger.Component;

/**
 * Created by junzhao on 2018/1/7.
 */

@Component(modules = CupModule.class)
public interface CupCompoment {

    void injectCup(Cup cup);
}
