package cn.junechiu.architecturedemo.demozhihu.data.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ZhihuStory.java
 */
@Entity(tableName = "zhihustorys")
public class ZhihuStory {

    @NonNull
    @PrimaryKey
    private String id;

    private int type;

    private String ga_prefix;

    private String title;

    private String[] images;

    public ZhihuStory() {
    }

    public ZhihuStory(ZhihuStory zhihuStory) {
        this.id = zhihuStory.getId();
        this.type = zhihuStory.getType();
        this.ga_prefix = zhihuStory.getGa_prefix();
        this.title = zhihuStory.getTitle();
        this.images = zhihuStory.getImages();
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
