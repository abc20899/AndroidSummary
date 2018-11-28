package cn.junechiu.architecturedemo.demozhihu.data.remote.model;

import java.util.List;

import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;

/**
 * GirlResponse.java
 */
public class GirlResponse {

    public final boolean error;

    public final List<Girl> results;

    public GirlResponse(boolean error, List<Girl> results) {
        this.error = error;
        this.results = results;
    }
}
