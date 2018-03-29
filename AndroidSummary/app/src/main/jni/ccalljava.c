#include "cn_junechiu_interview_jni_CCallJava.h"

/*
 * Class:     cn_junechiu_interview_jni_CCallJava
 * Method:    callbackAdd
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_junechiu_interview_jni_CCallJava_callbackAdd
        (JNIEnv *env, jobject jobj) {
    //1. 加载类得到class对象
    jclass jc = (*env)->FindClass(env, "cn/junechiu/interview/jni/CCallJava");
    //2. 得到对应方法的Method对象
    jmethodID method = (*env)->GetMethodID(env, jc, "add", "(II)I");
    //3. 创建类对象
    jobject obj2 = (*env)->AllocObject(env, jc);
    //4. 调用方法
    (*env)->CallIntMethod(env, obj2, method, 3, 4);
    (*env)->DeleteLocalRef(env, obj2);
}

/*
 * Class:     cn_junechiu_interview_jni_CCallJava
 * Method:    callbackHelloFromJava
 * Signature: ()V
 * 产生Local Reference的操作有：
1.FindClass
2.NewString/ NewStringUTF/NewObject/NewByteArray
3.GetObjectField/GetObjectClass/GetObjectArrayElement
4.GetByteArrayElements和GetStringUTFChars
 */
JNIEXPORT void JNICALL Java_cn_junechiu_interview_jni_CCallJava_callbackHelloFromJava
        (JNIEnv *env, jobject jobj) {
    //1. 加载类得到jclass对象:
    //jclass (*FindClass)(JNIEnv*, const char*);
    jclass jc = (*env)->FindClass(env, "cn/junechiu/interview/jni/CCallJava");
    //2. 得到对应方法的Method对象 : GetMethodId()
    //jmethodID (*GetMethodID)(JNIEnv*, jclass, const char*, const char*)
    jmethodID method = (*env)->GetMethodID(env, jc, "helloFromJava", "()V");
    //3. 创建类对象
//    jobject (*AllocObject)(JNIEnv*, jclass);
    jobject obj2 = (*env)->AllocObject(env, jc);
    //4. 调用方法
    (*env)->CallVoidMethod(env, obj2, method);
//    用完java传递过来的obj之后，释放
    (*env)->DeleteLocalRef(env, obj2);
}

/*
 * Class:     cn_junechiu_interview_jni_CCallJava
 * Method:    callbackPrintString
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_junechiu_interview_jni_CCallJava_callbackPrintString
        (JNIEnv *env, jobject jobj) {
    //1. 加载类得到class对象
    jclass jc = (*env)->FindClass(env, "cn/junechiu/interview/jni/CCallJava");
//2. 得到对应方法的Method对象
    jmethodID method = (*env)->GetMethodID(env, jc, "printString", "(Ljava/lang/String;)V");
//3. 创建类对象
    jobject obj2 = (*env)->AllocObject(env, jc);
//4. 调用方法
    jstring js = (*env)->NewStringUTF(env, "I from C");
    (*env)->CallVoidMethod(env, obj2, method, js);
    (*env)->DeleteLocalRef(env, obj2);
}

/*
 * Class:     cn_junechiu_interview_jni_CCallJava
 * Method:    callbackSayHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_junechiu_interview_jni_CCallJava_callbackSayHello
        (JNIEnv *env, jobject jobj) {
    //1. 加载类得到class对象
    jclass jc = (*env)->FindClass(env, "cn/junechiu/interview/jni/CCallJava");
    //2. 得到对应方法的Method对象
    jmethodID method = (*env)->GetStaticMethodID(env, jc, "sayHello", "(Ljava/lang/String;)V");
    //3. 调用方法
    jstring js = (*env)->NewStringUTF(env, "I from C");
    (*env)->CallStaticVoidMethod(env, jc, method, js);
    (*env)->DeleteLocalRef(env, js);
}