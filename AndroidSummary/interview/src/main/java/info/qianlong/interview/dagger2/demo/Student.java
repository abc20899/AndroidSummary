package info.qianlong.interview.dagger2.demo;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by junzhao on 2018/1/7.
 */

public class Student {

    @Inject
    public Student(){
        Log.d("Student","Student的构造函数");
    }

    public Student(String name){
    }

}
