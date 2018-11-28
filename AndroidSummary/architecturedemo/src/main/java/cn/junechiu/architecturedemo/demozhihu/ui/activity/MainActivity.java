package cn.junechiu.architecturedemo.demozhihu.ui.activity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.ui.fragment.GirlListFragment;
import cn.junechiu.architecturedemo.demozhihu.ui.fragment.ZhihuListFragment;
import cn.junechiu.junecore.widget.bottomtab.BottomBarLayout;
import cn.junechiu.junecore.widget.bottomtab.TabEntity;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList = new ArrayList<>();

    private ViewPager mViewPager = null;

    private BottomBarLayout bottomBar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zhihu_activity_main);

        initView();
    }

    private void initView() {
        bottomBar = findViewById(R.id.bottomBar);
        mViewPager = findViewById(R.id.viewPager);

        mFragmentList.add(new GirlListFragment());
        mFragmentList.add(new ZhihuListFragment());

        List<TabEntity> tabEntityList = new ArrayList();
        String[] tabText = {"girl", "zhihu"};

        int[] normalIcon = {R.mipmap.home_discover_icon, R.mipmap.home_mine_icon};
        int[] selectIcon = {R.mipmap.home_discover_icon_s, R.mipmap.home_mine_icon_s};
        int normalTextColor = getResources().getColor(R.color.cbbbbbb);
        int selectTextColor = getResources().getColor(R.color.c222222);
        for (int i = 0; i < tabText.length; i++) {
            TabEntity item = new TabEntity();
            item.text = tabText[i];
            item.normalIconId = normalIcon[i];
            item.selectIconId = selectIcon[i];
            tabEntityList.add(item);
        }
        bottomBar.setNormalTextColor(normalTextColor);
        bottomBar.setSelectTextColor(selectTextColor);
        bottomBar.setTabList(tabEntityList);
        bottomBar.setOnItemClickListener(position -> {
            mViewPager.setCurrentItem(position, false);
        });
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
