package info.qianlong.basicdemo.media_provider.bean;

/**
 * @描述 文件, 可以是文档、apk、压缩包
 */
public class FileBean {

    public String path; //文件的路径

    public int iconId; //文件图片资源的id，drawable或mipmap文件中已经存放doc、xml、xls等文件的图片

    public FileBean(String path, int iconId) {
        this.path = path;
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "path='" + path + '\'' +
                ", iconId=" + iconId +
                '}';
    }
}
