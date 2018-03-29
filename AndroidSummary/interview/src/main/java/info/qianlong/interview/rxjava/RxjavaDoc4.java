package info.qianlong.interview.rxjava;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by junzhao on 2018/1/9.
 * <p>
 * 如果下游没有处理事件的能力，也就是没有调用的Subscription.request()方法
 * 同步：
 * 首先第一个同步的代码, 为什么上游发送第一个事件后下游就抛出了MissingBackpressureException异常,
 * 这是因为下游没有调用request, 上游就认为下游没有处理事件的能力, 而这又是一个同步的订阅, 既然下游处理不了,
 * 那上游不可能一直等待吧, 如果是这样, 万一这两根水管工作在主线程里, 界面不就卡死了吗, 因此只能抛个异常来提醒我们.
 * 那如何解决这种情况呢, 很简单啦, 下游直接调用request(Long.MAX_VALUE)就行了, 或者根据上游发送事件的数量
 * 来request就行了, 比如这里request(3)就可以了.
 * 异步：
 * 为什么上下游没有工作在同一个线程时, 上游却正确的发送了所有的事件呢? 这是因为在Flowable里默认有一个大小为128的水缸,
 * 当上下游工作在不同的线程中时, 上游就会先把事件发送到这个水缸中, 因此, 下游虽然没有调用request,
 * 但是上游在水缸中保存着这些事件, 只有当下游调用request时, 才从水缸里取出事件发给下游.
 */

public class RxjavaDoc4 {

    public static String TAG = "RxjavaDoc4";

    //Flowable在设计的时候采用了一种新的思路也就是响应式拉取的方式来更好的解决上下游流速不均衡的问题
    public void testFlowable() {
        //上游下游都在主线程中
        Flowable<String> upstream = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                e.onNext("c");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR); //BackpressureStrategy.ERROR 背压策略,抛出异常

        Subscriber<String> downstream = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                //向上游请求事件 如果
                s.request(1000);
            }

            @Override
            public void onNext(String s) {
                Log.d("onNext", ":" + s);
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
                Log.d("onComplete", ":");
            }
        };
        upstream.subscribe(downstream);
    }

    public void test2() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                for (int i = 0; i < 129; i++) {
                    e.onNext(i + "");
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //在同一个线程中 不拉取数据 会抛出异常
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("onNext", ":" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void demo3() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "current requested: " + emitter.requested());
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
//                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }
}
