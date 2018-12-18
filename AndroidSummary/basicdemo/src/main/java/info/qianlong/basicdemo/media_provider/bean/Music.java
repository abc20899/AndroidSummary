package info.qianlong.basicdemo.media_provider.bean;

public class Music {

    public String name; //歌曲名

    public String path; //路径

    public String album; //所属专辑

    public String artist; //艺术家(作者)

    public long size; //文件大小

    public int duration; //时长

    public String pinyin; //PinyinUtils.getPinyin(name);

    public Music(String name, String path, String album, String artist, long size, int duration, String pinyin) {
        this.name = name;
        this.path = path;
        this.album = album;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
