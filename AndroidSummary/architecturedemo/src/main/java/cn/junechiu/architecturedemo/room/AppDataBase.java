package cn.junechiu.architecturedemo.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

//在实例化 AppDatabase 对象时应该遵循单例设计模式
@Database(entities = {UserEntity.class}, version = 1)
@TypeConverters({ConversionFactory.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    public static AppDataBase getDefault(Context context) {
        return buildDatabase(context);
    }

    public static AppDataBase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "user.db")
                .allowMainThreadQueries()
                .build();
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }
}
