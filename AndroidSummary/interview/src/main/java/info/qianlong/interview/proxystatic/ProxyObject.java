package info.qianlong.interview.proxystatic;

/**
 * Created by junzhao on 2017/12/17.
 */

public class ProxyObject extends AbstractObject {

    //持有目标类的引用
    private RealObject mRealObject;

    public ProxyObject(RealObject realObject) {
        mRealObject = realObject;
    }

    @Override
    public void opration() {
        if (mRealObject == null) {
            mRealObject = new RealObject();
        }
        System.out.println("do something before realObject's opration");
        mRealObject.opration();
        System.out.println("do something after realObject's opration");
    }
}
