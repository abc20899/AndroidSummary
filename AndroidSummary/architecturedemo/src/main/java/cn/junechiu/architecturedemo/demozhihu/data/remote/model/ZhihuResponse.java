package cn.junechiu.architecturedemo.demozhihu.data.remote.model;

import java.util.List;

import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;

/**
 * ZhihuResponse.java
 */
public class ZhihuResponse {

    private String date;

    private List<ZhihuStory> stories;

    private List<ZhihuStory> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuStory> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuStory> stories) {
        this.stories = stories;
    }

    public List<ZhihuStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuStory> top_stories) {
        this.top_stories = top_stories;
    }
}
