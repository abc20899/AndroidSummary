LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hello#指定生成的so文件的唯一标识

LOCAL_SRC_FILES := hello.c checkpasswd.c ccalljava.c #指定包含JNI函数的c文件名

LOCAL_LDLIBS    := -llog  #log库

include $(BUILD_SHARED_LIBRARY)