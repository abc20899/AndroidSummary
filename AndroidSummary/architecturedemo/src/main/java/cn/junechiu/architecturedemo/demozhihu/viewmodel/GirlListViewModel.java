package cn.junechiu.architecturedemo.demozhihu.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import cn.junechiu.architecturedemo.App;
import cn.junechiu.architecturedemo.demozhihu.data.DataRepository;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.Girl;
import cn.junechiu.architecturedemo.demozhihu.util.Util;

/**
 * GirlListViewModel.java
 */
public class GirlListViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mGirlPageIndex = new MutableLiveData<>();

    private final LiveData<List<Girl>> mGirls;

    private DataRepository mGirlsDataRepository = null;

    private GirlListViewModel(Application application, DataRepository girlsDataRepository) {
        super(application);
        mGirlsDataRepository = girlsDataRepository;
        //转换数据
        mGirls = Transformations.switchMap(mGirlPageIndex, new Function<Integer, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Integer input) {
                return mGirlsDataRepository.getGirlList(input); //获取数据
            }
        });
    }

    public LiveData<List<Girl>> getGilrsLiveData() {
        return mGirls;
    }

    //刷新数据 加载第一页数据
    public void refreshGrilsData() {
        mGirlPageIndex.setValue(1);
    }

    //分页加载数据 加载下一页
    public void loadNextPageGirls() {
        if (!Util.isNetworkConnected(App.Companion.getINSTANCE())) {
            return;
        }
        mGirlPageIndex.setValue((mGirlPageIndex.getValue() == null ? 1 : mGirlPageIndex.getValue() + 1));
    }

    public LiveData<Boolean> getLoadMoreState() {
        return mGirlsDataRepository.isLoadingGirlList();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mGirlsDataRepository;

        public Factory(@NonNull Application application, DataRepository girlsDataRepository) {
            mApplication = application;
            mGirlsDataRepository = girlsDataRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new GirlListViewModel(mApplication, mGirlsDataRepository);
        }
    }
}
