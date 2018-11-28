package cn.junechiu.architecturedemo.demozhihu.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;

/**
 * GirlDao.java
 */
@Dao
public interface ZhihuDao {

    @Query("SELECT * FROM zhihustorys")
    List<ZhihuStory> loadAllZhihus();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZhihuList(List<ZhihuStory> girls);
}
