package info.qianlong.basicdemo.android_server

import java.io.Serializable

class FileItem : Serializable {
    var name: String = ""
    var path: String = ""
    var isDir: Int = 0
    var children = mutableListOf<FileItem>()
}