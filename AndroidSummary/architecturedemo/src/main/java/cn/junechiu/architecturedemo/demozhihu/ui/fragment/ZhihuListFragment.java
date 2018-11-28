package cn.junechiu.architecturedemo.demozhihu.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.junechiu.architecturedemo.App;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.data.Injection;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.ui.activity.ZhihuActivity;
import cn.junechiu.architecturedemo.demozhihu.ui.adapter.ZhihuListAdapter;
import cn.junechiu.architecturedemo.demozhihu.ui.listener.OnItemClickListener;
import cn.junechiu.architecturedemo.demozhihu.util.Util;
import cn.junechiu.architecturedemo.demozhihu.viewmodel.ZhihuListViewModel;

/**
 * ZhihuListFragment.java
 */
public class ZhihuListFragment extends Fragment {

    public static final String TAG = "ZhihuListFragment";

    // ZhihuListFragment 所对应的 ViewModel 类的对象
    private ZhihuListViewModel mListViewModel = null;

    private SwipeRefreshLayout mRefreshLayout = null;

    private ZhihuListAdapter mAdapter = null;

    private ProgressBar mLoadMorebar = null;

    private View mRLZhihuRoot = null;

    // 自定义接口，将 RecyclerView 的 Adapter 对其中每个 Item 的点击事件会传到 ZhihuListFragment 中。
    private final OnItemClickListener<ZhihuStory> mZhihuOnItemClickListener =
            new OnItemClickListener<ZhihuStory>() {
                @Override
                public void onClick(ZhihuStory zhihuStory) {
                    if (Util.isNetworkConnected(App.Companion.getINSTANCE())) {
                        ZhihuActivity.startZhihuActivity(getActivity(), zhihuStory.getId(),
                                zhihuStory.getTitle());
                    } else {
                        Util.showSnackbar(mRLZhihuRoot, getString(R.string.network_error));
                    }
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_list, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    /**
     * 将 ZhihuListFragment 对应的 ZhihuListViewModel 类中的 LiveData 添加注册监听到
     * 此 ZhihuListFragment
     */
    private void subscribeUI() {
        // 通过 ViewModelProviders 创建对应的 ZhihuListViewModel 对象
        ZhihuListViewModel.Factory factory = new ZhihuListViewModel
                .Factory(App.Companion.getINSTANCE()
                , Injection.getDataRepository(App.Companion.getINSTANCE()));
        mListViewModel = ViewModelProviders.of(ZhihuListFragment.this, factory).get(ZhihuListViewModel.class);
        mListViewModel.getZhihuList().observe(this, new Observer<List<ZhihuStory>>() {
            @Override
            public void onChanged(@Nullable List<ZhihuStory> stories) {
                if (stories == null || stories.size() <= 0) {
                    return;
                }
                Log.i(TAG, "size is " + stories.size());
                mAdapter.setStoryList(stories);
            }
        });
        mListViewModel.isLoadingZhihuList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) {
                    return;
                }
                Log.i(TAG, "state " + aBoolean);
                mRefreshLayout.setRefreshing(false);
                mLoadMorebar.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE);
            }
        });
        mListViewModel.refreshZhihusData();
    }

    /**
     * 初始化页面 UI
     *
     * @param view Fragment 的 View
     */
    private void initView(View view) {
        if (view == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ZhihuListAdapter(getActivity(), mZhihuOnItemClickListener);
        RecyclerView recyclerView = view.findViewById(R.id.rv_zhihu_list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ZhihuOnScrollListener());

        mRefreshLayout = view.findViewById(R.id.srl_zhihu);
        mRefreshLayout.setOnRefreshListener(new ZhihuSwipeListener());
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mLoadMorebar = view.findViewById(R.id.bar_load_more_zhihu);
        mRLZhihuRoot = view.findViewById(R.id.rl_zhihu_root);
    }

    /**
     * ZhihuSwipeListener 用于 SwipeRefreshLayout 下拉刷新操作
     */
    private class ZhihuSwipeListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mAdapter.clearStoryList();
            mListViewModel.refreshZhihusData();
        }
    }

    /**
     * ZhihuOnScrollListener 用于 RecyclerView 下拉到最低端时的上拉加载更多操作
     */
    private class ZhihuOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
            int lastPosition = layoutManager
                    .findLastCompletelyVisibleItemPosition();
            if (lastPosition == mAdapter.getItemCount() - 1) {
                // 上拉加载更多数据
                mListViewModel.loadNextPageZhihu(lastPosition);
            }
        }
    }
}
