package info.qianlong.interview.app;

import android.app.Application;

//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by junzhao on 2017/12/19.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        Realm.init(this);
//        RealmConfiguration reamlConfig = new RealmConfiguration.Builder()
//                .name("user.realm")
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(reamlConfig);
    }
}
