package info.qianlong.basicdemo.media_provider.bean;

/**
 * @描述 图片文件夹的bean类
 */
public class ImgFolderBean {

    public String dir; //当前文件夹的路径

    public String fistImgPath; //第一张图片的路径

    public String name; //文件夹名

    public int count; //文件夹中图片的数量

    @Override
    public String toString() {
        return "ImgFolderBean{" +
                "dir='" + dir + '\'' +
                ", fistImgPath='" + fistImgPath + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
