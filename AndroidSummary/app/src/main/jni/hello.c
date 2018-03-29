#include "cn_junechiu_interview_jni_HelloJni.h"
#include "../../../../../../../Library/Android/sdk/ndk-bundle/platforms/android-19/arch-mips/usr/include/malloc.h"

//函数声明
char *_JString2CStr(JNIEnv *env, jstring jstr);

JNIEXPORT jstring JNICALL Java_cn_junechiu_interview_jni_HelloJni_getStrFromC
        (JNIEnv *env, jobject jobj) {

    jstring jstr = (*env)->NewStringUTF(env, "Hello from C");
//    (*env)->DeleteLocalRef(env, jstr); //释放
    return jstr;
}

JNIEXPORT jint JNICALL Java_cn_junechiu_interview_jni_HelloJni_sum
        (JNIEnv *env, jobject jobj, jint x, jint y) {
    int sum = x + y; //jint可以直接进行算术运算
    //stdio.h头文件
    printf("printf c sum=%d\n", sum);//问题: 直接输出,在logcat中看不到输出
    LOGD("printf c sum=%d\n", sum);
    return sum; //可直接将int类型数据作为jint返回
}

/**
* 字符串操作: 将两个字符串拼接后返回
//1. 将jstring类型的js转换为char*类型数据
//2. 指定另一部分字符串
//3. 将拼接两个char*类型字符串拼接在第一个上
//string.h---char* strcat(char *, const char *);
//4. 将结果转换为jstring类型返回
//jstring (*NewStringUTF)(JNIEnv*, const char*)
*/
JNIEXPORT jstring JNICALL Java_cn_junechiu_interview_jni_HelloJni_sayHello
        (JNIEnv *env, jobject jobj, jstring js) {
    //1. 将jstring类型的js转换为char*类型数据
    char *cs1 = _JString2CStr(env, js);
    //2. 指定另一部分字符串
    char *cs2 = " Hello By C";
    //3. 将拼接两个char*类型字符串拼接在第一个上
    char *cs3 = strcat(cs1, cs2);
    //4. 将结果转换为jstring类型返回
    jstring jstr = (*env)->NewStringUTF(env, cs3);
//    (*env)->DeleteLocalRef(env, jstr); //释放
    return jstr;
}

/**
* 数组运算: 将数组中的每个元素增加10
1. 得到数组的长度
jsize (*GetArrayLength)(JNIEnv*, jarray);
2. 得到数组
jint* (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
3. 遍历数组, 并将每个元素+10
4. 返回数组
*/
JNIEXPORT jintArray JNICALL Java_cn_junechiu_interview_jni_HelloJni_increaseArrayEles
        (JNIEnv *env, jobject jobj, jintArray arr) {
    //1. 得到数组的长度
    //jsize (*GetArrayLength)(JNIEnv*, jarray);
    jsize length = (*env)->GetArrayLength(env, arr);
    //2. 得到数组
    //jint* (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
    jint *array = (*env)->GetIntArrayElements(env, arr, JNI_FALSE);
    //3. 遍历数组, 并将每个元素+10
    int i;
    for (i = 0; i < length; i++) {
        *(array + i) += 10;
        LOGD("printf c arr=%d\n", (*(array + i) + 10)); //遍历
    }
    //4. 返回数组
    return arr;
}

/**
* 工具函数
* 把一个jstring转换成一个c语言的char* 类型.
*/
char *_JString2CStr(JNIEnv *env, jstring jstr) {
    char *rtn;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);

    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}