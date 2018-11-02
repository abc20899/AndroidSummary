package cn.junechiu.architecturedemo.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<UserEntity> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<UserEntity> loadAllUsers(int userIds[]);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    UserEntity findByName(String first, String last);

    @Insert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);

    @Update()
    void update(UserEntity user);
}
