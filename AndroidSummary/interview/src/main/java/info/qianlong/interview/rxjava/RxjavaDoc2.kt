package cn.junechiu.interview.rxjava

import android.content.Context
import android.util.Log
import info.qianlong.interview.contentprovider.FileItem
import info.qianlong.interview.contentprovider.MediaLoadManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by junzhao on 2017/12/19.
 */

class RxjavaDoc2(context: Context) {

    private var manager: MediaLoadManager? = null

    init {
        manager = MediaLoadManager(context)
    }

    fun queryData() {
        Observable.create<MutableList<FileItem>> { e ->
            var data = manager?.allPhoto
            e.onNext(data!!)
        }.subscribeOn(Schedulers.io())  //io线程查询
                .observeOn(AndroidSchedulers.mainThread()) //主线程显示
                .subscribe { data ->
                    data.forEach { fileItem ->
                        Log.d("queryData---", "fileItem: " + fileItem.getmFilePath())
                    }
                }
    }
}

