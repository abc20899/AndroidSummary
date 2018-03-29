package info.qianlong.interview.jni;

/**
 * Created by android on 2017/12/14.
 */

public class HelloJni {

    static {
        System.loadLibrary("hello");
    }

//    public native String getStrFromC();

    //将传入的两个int值相加并返回
//    public native int sum(int x, int y);

    //将两个字符串拼接后返回
//    public native String sayHello(String s);

    //将数组中的每个元素增加10
//    public native int[] increaseArrayEles(int[] intArray);
}
