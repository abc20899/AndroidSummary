package cn.junechiu.architecturedemo.demozhihu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import cn.junechiu.architecturedemo.demozhihu.data.DataRepository;
import cn.junechiu.architecturedemo.demozhihu.data.remote.model.ZhihuStoryDetail;

/**
 * GirlViewModel.java
 */
public class ZhihuViewModel extends AndroidViewModel {

    private DataRepository mDataRepository = null;

    private final String mZhihuId;

    private ZhihuViewModel(Application application, DataRepository dataRepository, String zhihuId) {
        super(application);
        this.mZhihuId = zhihuId;
        mDataRepository = dataRepository;
    }

    public LiveData<ZhihuStoryDetail> getZhihuDetail() {
        return mDataRepository.getZhihuDetail(mZhihuId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mDataRepository;

        private final String mGirlId;

        public Factory(@NonNull Application application, DataRepository dataRepository, String girlId) {
            mDataRepository = dataRepository;
            mApplication = application;
            mGirlId = girlId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ZhihuViewModel(mApplication, mDataRepository, mGirlId);
        }
    }
}
