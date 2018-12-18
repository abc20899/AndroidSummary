#include <malloc.h>
#include "cn_junechiu_interview_jni_CheckPasswd.h"

//函数声明
char *_JString2CStr2(JNIEnv *env, jstring jstr);

JNIEXPORT jint JNICALL Java_cn_junechiu_interview_jni_CheckPasswd_checkPwd
        (JNIEnv *env, jobject jobj, jstring js){
    //1. 将jString转换为char*
    char* cs = _JString2CStr2(env, js);
    char* pwd = "123456";
    //2. 比较两个字符串是否相等
    int result = strcmp(cs, pwd);
    //3. 根据比较的结果返回不同的值
    if(result==0) {
        return 200;
    }
    return 400;
}

/**
* 工具函数
* 把一个jstring转换成一个c语言的char* 类型.
*/
char *_JString2CStr2(JNIEnv *env, jstring jstr) {
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