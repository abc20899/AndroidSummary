package cn.junechiu.architecturedemo.demozhihu.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.dao.GirlDao;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.dao.ZhihuDao;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;

/**
 * AppDatabase.java
 */
@Database(entities = {Girl.class, ZhihuStory.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract GirlDao girlDao();

    public abstract ZhihuDao zhihuDao();
}
