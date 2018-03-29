package info.qianlong.interview.proxystatic;

/**
 * Created by junzhao on 2017/12/17.
 */

public class RealObject extends AbstractObject {

    @Override
    public void opration() {
        System.out.println("RealObject's opration()");
    }
}
