package info.qianlong.interview.dagger2.demo2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by junzhao on 2018/1/7.
 */

@Module
public class CupModule {

    @Provides
    public Cup providerCup(){
        return new Cup();
    }
}
