package info.qianlong.interview.dagger2;

/**
 * Created by junzhao on 2018/1/7.
 */

public class InterfaceClassA implements InterfaceClassB {

    InterfaceClassB classB;

    @Override
    public void setClassB(InterfaceClassB classB) {
        this.classB = classB;
    }
}
