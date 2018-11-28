package cn.junechiu.architecturedemo.demozhihu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.junechiu.architecturedemo.App;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.data.Injection;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.ui.activity.GirlActivity;
import cn.junechiu.architecturedemo.demozhihu.ui.adapter.GirlListAdapter;
import cn.junechiu.architecturedemo.demozhihu.ui.listener.OnItemClickListener;
import cn.junechiu.architecturedemo.demozhihu.util.Util;
import cn.junechiu.architecturedemo.demozhihu.viewmodel.GirlListViewModel;

/**
 * GirlListFragment.java
 */
public class GirlListFragment extends Fragment {

    public static final String TAG = "GirlListFragment";

    private GirlListViewModel mGirlListViewModel = null;

    private GirlListAdapter mGirlListAdapter = null;

    private SwipeRefreshLayout mRefreshLayout = null;

    private ProgressBar mLoadMorebar = null;

    private View RLGirlRoot = null;

    private final OnItemClickListener<Girl> mGirlClickListener = new OnItemClickListener<Girl>() {
        @Override
        public void onClick(Girl girl) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                if (Util.isNetworkConnected(App.Companion.getINSTANCE())) {
                    GirlActivity.startGirlActivity(getActivity(), girl.getUrl());
                } else {
                    Util.showSnackbar(RLGirlRoot, getString(R.string.network_error));
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_list, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    private void subscribeUI() {
        if (!isAdded()) { //是否添加到activity中
            return;
        }
        //实例化viewModel
        GirlListViewModel.Factory factory = new GirlListViewModel.Factory(App.Companion.getINSTANCE(),
                Injection.getDataRepository(App.Companion.getINSTANCE()));
        mGirlListViewModel = ViewModelProviders.of(this, factory).get(GirlListViewModel.class);
        //获取可观察数据 驱动ui
        mGirlListViewModel.getGilrsLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(@Nullable List<Girl> girls) {
                if (girls == null || girls.size() == 0) {
                    return;
                }
                Log.i(TAG, "girls size " + girls.size());
                mGirlListAdapter.setGirlList(girls);
            }
        });
        //判断是否加载数据中
        mGirlListViewModel.getLoadMoreState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean state) {
                if (state == null) {
                    return;
                }
                Log.i(TAG, "state " + state);
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    mLoadMorebar.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
        //刷新数据
        mGirlListViewModel.refreshGrilsData();
        mRefreshLayout.setRefreshing(true);
    }

    private void initView(View view) {
        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.rv_girl_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                RecyclerView.VERTICAL, false));
        mGirlListAdapter = new GirlListAdapter(mGirlClickListener);
        recyclerView.setAdapter(mGirlListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                int lastPosition = layoutManager
                        .findLastVisibleItemPosition();
                if (lastPosition == mGirlListAdapter.getItemCount() - 1) {
                    // 上拉加载更多数据
                    mGirlListViewModel.loadNextPageGirls();
                }
            }
        });

        mRefreshLayout = view.findViewById(R.id.srl);
        mRefreshLayout.setOnRefreshListener(() -> {
            mGirlListAdapter.clearGirlList();
            mRefreshLayout.setRefreshing(true);
            mGirlListViewModel.refreshGrilsData();
        });
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mLoadMorebar = view.findViewById(R.id.load_more_bar);
        RLGirlRoot = view.findViewById(R.id.rl_girl_root);
    }
}
