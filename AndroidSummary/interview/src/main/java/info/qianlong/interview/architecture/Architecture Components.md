如果您不能使用应用程序组件来存储应用程序数据和状态，应该如何构建应用程序？你应该关注的最重要的事情是在你的应用程序中的关注点分离。将所有代码写入activity或fragment是常见的错误。任何不处理用户界面或操作系统交互的代码都不应该在这些类中。尽可能保持他们的精益将允许您避免许多生命周期相关的问题。不要忘记，你不拥有这些类，它们只是体现os和你的应用程序之间的契约的胶水类。Android操作系统可能会随时基于用户交互或其他因素（如低内存）来销毁它们。最好尽量减少你对它们的依赖，以提供一个可靠的用户体验。第二个重要的原则是你应该从一个模型，最好是一个持久的模型驱动你的用户界面。持久性是理想的，原因有两个：如果操作系统破坏你的应用程序以释放资源，你的用户不会丢失数据，即使网络连接不好或没有连接，你的应用程序也将继续工作。模型是负责处理应用程序数据的组件。它们独立于应用程序中的视图和应用程序组件，因此它们与这些组件的生命周期问题是隔离的。保持简单的UI代码和免费的应用程序逻辑，使管理更容易。将您的应用程序基于具有明确定义的管理数据责任的模型类将使其可测试，并使您的应用程序保持一致。

##https://github.com/googlesamples/android-architecture-components

### Architecture Components架构组件

架构组件的基本框架包括：

Room - 一个SQLite对象映射器。非常类似于其他库，如ORMlite或greenDAO。它使用SQL，同时仍然允许对查询的编译时保证。<br>
LiveData - 一个Lifecycle可观察的核心组件。<br>
ViewModel - 应用程序的其他部分与Activities/Fragmets通讯点。它们与UI代码无关。<br>
Lifecycle - 架构自检的核心部分，它包含组件(例如一个Activity)的生命状态信息。<br>
LifecycleOwner - 具有Lifecycle(Activity，Fragment，Process，自定义组件)的组件的核心接口。<br>
LifecycleObserver - 指定出发某些生命周期方法是应该发生的情况。创建LifecycleObserver允许组件自包含。<br>

#### 库依赖

```
dependencies {
    // ViewModel and LiveData
    implementation "android.arch.lifecycle:extensions:1.0.0"
    annotationProcessor "android.arch.lifecycle:compiler:1.0.0"

    // Room
    implementation "android.arch.persistence.room:runtime:1.0.0"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"

    // Paging
    implementation "android.arch.paging:runtime:1.0.0-alpha4-1"

    // Test helpers for LiveData
    testImplementation "android.arch.core:core-testing:1.0.0"

    // Test helpers for Room
    testImplementation "android.arch.persistence.room:testing:1.0.0"
}

//Java 8 Support for Lifecycles 如果是java8 可以替换android.arch.lifecycle:compiler.
implementation "android.arch.lifecycle:common-java8:1.0.0"

//rxjava的支持
// RxJava support for Room
    implementation "android.arch.persistence.room:rxjava2:1.0.0"

// ReactiveStreams support for LiveData
implementation "android.arch.lifecycle:reactivestreams:1.0.0"

// Lifecycles only (no ViewModel or LiveData)
implementation "android.arch.lifecycle:runtime:1.0.3"
annotationProcessor "android.arch.lifecycle:compiler:1.0.0"
```

####ViewModel
视图模型为特定的UI组件（例如fragment或activity）提供数据，并处理与数据处理业务部分的通信，例如调用其他组件来加载数据或转发用户修改。
视图模型不知道视图，并且不受配置变化（例如由于旋转而重新创建activity）的影响。

```
public class UserProfileViewModel extends ViewModel {
    private String userId;
    private User user;

    public void init(String userId) {
        this.userId = userId;
    }
    public User getUser() {
        return user;
    }
}
```
**视图模型与ui组件进行绑定**

```
public class UserProfileFragment extends Fragment {
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile, container, false);
    }
}
```
####LiveData
livedata是一个可观察的数据持有者。它允许应用程序中的组件观察livedata对象的更改，而不会在它们之间创建明确的和严格的依赖关系路径。livedata还尊重您的应用程序组件（活动，片段，服务）的生命周期状态，并做正确的事情，以防止对象泄漏，使您的应用程序不会消耗更多的内存。<br>
**注意：**如果您已经在使用像rxjava或agera这样的库，则可以继续使用它们而不是livedata。但是在使用它们或其他方法时，请确保正确处理生命周期，以便在相关lifecycleowner停止时数据流暂停，并且在生命周期所有者被销毁时销毁流。您还可以添加android.arch.lifecycle：reactivestreams库，以与其他反应流库（例如rxjava2）一起使用livedata。

修改ViewModel类

```
public class UserProfileViewModel extends ViewModel {
    ...
    //private User user;
    private LiveData<User> user;
    public LiveData<User> getUser() {
        return user;
    }
}
```
修改UserProfileFragment类

```
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel.getUser().observe(this, user -> {
      // update UI 每次更新用户数据时，都会调用onchanged回调函数，并刷新ui。
    });
}

```
我们也没有做任何特别的处理配置变化（例如，用户旋转屏幕）。
视图模型会在配置发生变化时自动恢复，所以一旦新的模块生效，它将接收到相同的viewmodel实例，回调会立即被当前数据调用。这就是viewmodels不应该直接引用视图的原因。它们可以超越视图的生命周期。查看视图模型的生命周期。

#### 获取数据

```
public interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}
```

#####数据存储库
存储库模块负责处理数据操作。他们提供了应用程序的其余部分。他们知道从哪里获得数据，以及在更新数据时调用哪些API。您可以将它们视为不同数据源（持久模型，Web服务，缓存等）之间的中介。

```
public class UserRepository {
    private Webservice webservice;
    // ...
    public LiveData<User> getUser(int userId) {
        // This is not an optimal implementation, we'll fix it below
        final MutableLiveData<User> data = new MutableLiveData<>();
        webservice.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // error case is left out for brevity
                data.setValue(response.body());
            }
        });
        return data;
    }
}
```
#####管理组件之间的依赖关系：
1. Dagger 2方式
2. Service Locator ：Service Locator 提供了一个注册表，类可以获得它们的依赖关系，而不是构建它们。比依赖注入（di）实现起来要容易一些，所以如果你不熟悉di，可以使用Service Locator。

####连接 ViewModel 和 repository

```
public class UserProfileViewModel extends ViewModel {
    private LiveData<User> user;
    private UserRepository userRepo;

    @Inject // UserRepository parameter is provided by Dagger 2
    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(String userId) {
        if (this.user != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
```

####数据缓存
上面的存储库实现对抽象调用Web服务是有好处的，但是因为它只依赖一个数据源，所以它不是很有用。上面的UserRepository实现的问题是，在获取数据之后，它并没有保留在任何地方。如果用户离开了userprofilefragment并返回到该应用程序，则应用程序会重新获取数据。这是不好的，原因有两个：浪费宝贵的网络带宽并强制用户等待新的查询完成。为了解决这个问题，我们将添加一个新的数据源到我们的UserRepository中，这将会把用户对象缓存在内存中。

```
@Singleton  // informs Dagger that this class should be constructed once
public class UserRepository {
    private Webservice webservice;
    // simple in memory cache, details omitted for brevity
    private UserCache userCache;
    public LiveData<User> getUser(String userId) {
        LiveData<User> cached = userCache.get(userId);
        if (cached != null) {
            return cached;
        }

        final MutableLiveData<User> data = new MutableLiveData<>();
        userCache.put(userId, data);
        // this is still suboptimal but better than before.
        // a complete implementation must also handle the error cases.
        webservice.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }
        });
        return data;
    }
}
```
####数据持久化Room
当ui组件旋转等操作

```
@Entity
class User {
  @PrimaryKey
  private int id;
  private String name;
  private String lastName;
  // getters and setters for fields
}
```

然后继承Roomdatabase来创建一个数据库类

```
@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}

```
```
@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void save(User user);
    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> load(String userId);
}
```
请注意load方法返回一个livedata <user>。
room知道数据库何时被修改，当数据改变时它会自动通知所有活动的观察者。
因为它使用的是livedata，所以这将是有效的，因为只有当至少有一个活跃的观察者时才会更新数据。

```
@Singleton
public class UserRepository {
    private final Webservice webservice;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        // return a LiveData directly from the database.
        return userDao.load(userId);
    }

    private void refreshUser(final String userId) {
        executor.execute(() -> {
            // running in a background thread
            // check if user was fetched recently
            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
            if (!userExists) {
                // refresh the data
                Response response = webservice.getUser(userId).execute();
                // TODO check for error etc.
                // Update the database.The LiveData will automatically refresh so
                // we don't need to do anything else here besides updating the database
                userDao.save(response.body());
            }
        });
    }
}
```
最后结构
![](final-architecture.png)


####网络状态
我们演示一种使用资源类来公开网络状态的方法来封装数据及其状态。

```
//a generic class that describes a data with a status
public class Resource<T> {
    @NonNull public final Status status;
    @Nullable public final T data;
    @Nullable public final String message;
    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
```
因为从磁盘上加载数据的时候是一个常见的用例，我们要创建一个可以在多个地方重复使用的helper类networkboundresource。下面是networkboundresource的决策树：
![](network-bound-resource.png)
它通过观察资源的数据库开始。当条目是第一次从数据库加载时，networkboundresource检查结果是否足够好以便分派和/或从网络中获取。请注意，这两者都可能同时发生，因为您可能希望在从网络更新缓存数据时显示缓存的数据。如果网络呼叫成功完成，则将响应保存到数据库中并重新初始化流。如果网络请求失败，我们直接发送失败。

```
// ResultType: Type for the Resource data
// RequestType: Type for the API response
public abstract class NetworkBoundResource<ResultType, RequestType> {
    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database
    @NonNull @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData();
}
```
```
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource() {
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource,
                newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                saveResultAndReInit(response);
            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(
                                Resource.error(response.errorMessage, newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(ApiResponse<RequestType> response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response.body);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                result.addSource(loadFromDb(),
                        newData -> result.setValue(Resource.success(newData)));
            }
        }.execute();
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
```
```
class UserRepository {
    Webservice webservice;
    UserDao userDao;

    public LiveData<Resource<User>> loadUser(final String userId) {
        return new NetworkBoundResource<User,User>() {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return rateLimiter.canFetch(userId) && (data == null || !isFresh(data));
            }

            @NonNull @Override
            protected LiveData<User> loadFromDb() {
                return userDao.load(userId);
            }

            @NonNull @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return webservice.getUser(userId);
            }
        }.getAsLiveData();
    }
}
```

###指导建议
1. 您在清单中定义的入口点（activity，service，广播接收器等）不是数据的来源。相反，他们只应该协调与该入口点相关的数据子集。因为每个应用程序组件都相当短暂，这取决于用户与其设备的交互以及运行时的整体当前运行状况，因此您不希望这些入口点中的任何一个成为数据源。
2. 在您的应用程序的各个模块之间创建明确界定的责任。
例如，不要将从网络加载数据的代码跨越代码库中的多个类或包。
同样，不要把不相关的职责 - 比如数据缓存和数据绑定 - 放到同一个类中。
3. 尽可能少地暴露每个模块。
4. 在定义模块之间的交互时，请考虑如何使每个模块独立地进行测试。
例如，具有定义好的从网络获取数据的API将使测试将数据保存在本地数据库中的模块更容易。如果你把这两个模块的逻辑混合到一个地方，或者把你的网络代码放在你的整个代码库中，那么测试就更加困难了，如果不是不可能的话
5. 不要花费时间重复发明轮子，或者一次又一次地写出相同的样板代码。相反，将精力集中在让您的应用独特的东西上，让android架构组件和其他推荐的库处理重复的样板。
6. 持久化尽可能多的相关和新鲜的数据，以便您的应用程序在设备处于离线模式时可用。虽然您可以享受持续和高速的连接，但您的用户可能不会。
7. 您的存储库应该指定一个数据源作为单一的事实来源。
每当你的应用程序需要访问这一块数据，它应该始终来自单一的事实来源。



