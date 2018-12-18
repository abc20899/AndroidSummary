package info.qianlong.basicdemo.media_provider.bean;

public class Video {

    public int id = 0;

    public String path = null;

    public String name = null;

    public String resolution = null;// 分辨率

    public long size = 0;

    public long date = 0;

    public long duration = 0;

    public Video(int id, String path, String name, String resolution, long size, long date, long duration) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.resolution = resolution;
        this.size = size;
        this.date = date;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Video [id=" + id + ", path=" + path + ", name=" + name + ", resolution=" + resolution + ", size=" + size + ", date=" + date
                + ", duration=" + duration + "]";
    }

}
