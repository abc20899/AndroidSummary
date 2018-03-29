package info.qianlong.interview.jni;

import android.util.Log;

/**
 * Created by android on 2017/12/14.
 */
public class CCallJava {

    //回调带int参数方法
    public native void callbackAdd();

    //回调一般方法(无参无返回)
    public native void callbackHelloFromJava();

    //回调带String参数方法
    public native void callbackPrintString();

    //回调静态方法
    public native void callbackSayHello();

    public int add(int x, int y) {
        Log.e("CCallJava", "add() x=" + x + " y=" + y);
        return x + y;
    }

    public void helloFromJava() {
        Log.e("CCallJava", "helloFromJava()");
    }

    public void printString(String s) {
        Log.e("CCallJava", "C中输入的：" + s);
    }

    public static void sayHello(String s) {
        Log.e("CCallJava", "我是java代码中的JNI."
                + "java中的sayHello(String s)静态方法，我被C调用了:" + s);
    }

}
