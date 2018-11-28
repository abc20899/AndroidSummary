package info.qianlong.interview.customview.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.qianlong.interview.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MainAdapter mainAdapter;

    private String[] activities;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customview_activity_main);

        initView();
        initData();
    }

    public void initView() {
        // Example of a call to a native method
        recyclerView = findViewById(R.id.view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initData() {
        activities = getResources().getStringArray(R.array.activities);
        mainAdapter = new MainAdapter(this);
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //打开指定activity
                openActivityByName(MainActivity.this, activities[position]);
            }
        });
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setDataList(activities);
    }

    public void openActivityByName(Activity activity, String activityName) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(activityName);
            if (clazz != null) {
                Intent intent = new Intent();
                intent.setClass(activity, clazz);
                activity.startActivity(intent);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
