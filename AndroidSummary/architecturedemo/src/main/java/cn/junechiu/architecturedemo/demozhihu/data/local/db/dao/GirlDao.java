package cn.junechiu.architecturedemo.demozhihu.data.local.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;

/**
 * GirlDao.java
 */
@Dao
public interface GirlDao {

    @Query("SELECT * FROM girls")
    List<Girl> loadAllGirls();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGirls(List<Girl> girls);

    @Query("SELECT * FROM girls WHERE _id = :id")
    LiveData<Girl> loadGirl(String id);

    @Query("SELECT * FROM girls WHERE _id = :id")
    Girl loadGirlSync(String id);
}
