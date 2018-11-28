package cn.junechiu.architecturedemo.demozhihu.data.local.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;

/**
 * AppDatabaseManager.java
 * 单例模式 数据库操作类
 */
public class AppDatabaseManager {

    private static final String DATABASE_NAME = "zhihu-db";

    private final MutableLiveData<Boolean> mIsLoadingGirlList; //可观察数据-是否加载girl列表

    private final MutableLiveData<Boolean> mIsLoadingZhihuList;

    private final MutableLiveData<List<Girl>> mGirlList; //girl 列表数据

    private final MutableLiveData<List<ZhihuStory>> mZhihuList;

    private AppDatabase mDB = null; //room

    private AppDatabaseManager() {
        mIsLoadingGirlList = new MutableLiveData<>();
        mIsLoadingZhihuList = new MutableLiveData<>();
        mGirlList = new MutableLiveData<>();
        mZhihuList = new MutableLiveData<>();
    }

    private static final class Holder {
        public static final AppDatabaseManager INSTANCE = new AppDatabaseManager();
    }

    public static AppDatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    //创建数据库
    public void createDB(Context context) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... params) {
                Context context = params[0].getApplicationContext();
                mDB = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME).build();
                return null;
            }
        }.execute(context.getApplicationContext());
    }

    //插入girls数据
    public void insertGirlList(final List<Girl> girls) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mDB.beginTransaction();
                try {
                    mDB.girlDao().insertGirls(girls);
                    mDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mDB.endTransaction();
                }
                return null;
            }
        }.execute();

    }

    public void insertZhihuList(final List<ZhihuStory> zhihuStoryList) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mDB.beginTransaction();
                try {
                    mDB.zhihuDao().insertZhihuList(zhihuStoryList);
                    mDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mDB.endTransaction();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<List<Girl>> loadGirlList() {
        mIsLoadingGirlList.setValue(true);
        new AsyncTask<Void, Void, List<Girl>>() {
            @Override
            protected List<Girl> doInBackground(Void... voids) {
                List<Girl> results = new ArrayList<>();
                mDB.beginTransaction();
                try {
                    results.addAll(mDB.girlDao().loadAllGirls());
                    mDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mDB.endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<Girl> aVoid) {
                super.onPostExecute(aVoid);
                mIsLoadingGirlList.setValue(false);
                mGirlList.setValue(aVoid);
            }

            @Override
            protected void onCancelled(List<Girl> aVoid) {
                super.onCancelled(aVoid);
                mIsLoadingGirlList.setValue(false);
            }
        }.execute();
        return mGirlList;
    }

    public LiveData<List<ZhihuStory>> loadZhihuList() {
        mIsLoadingZhihuList.setValue(true);
        new AsyncTask<Void, Void, List<ZhihuStory>>() {
            @Override
            protected List<ZhihuStory> doInBackground(Void... voids) {
                List<ZhihuStory> results = new ArrayList<>();
                mDB.beginTransaction();
                try {
                    results.addAll(mDB.zhihuDao().loadAllZhihus());
                    mDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mDB.endTransaction();
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<ZhihuStory> aVoid) {
                super.onPostExecute(aVoid);
                mIsLoadingZhihuList.setValue(false);
                mZhihuList.setValue(aVoid);
            }

            @Override
            protected void onCancelled(List<ZhihuStory> aVoid) {
                super.onCancelled(aVoid);
                mIsLoadingZhihuList.setValue(false);
            }
        }.execute();
        return mZhihuList;
    }

    public LiveData<Boolean> isLoadingGirlList() {
        return mIsLoadingGirlList;
    }

    public MutableLiveData<Boolean> isLoadingZhihuList() {
        return mIsLoadingZhihuList;
    }
}
