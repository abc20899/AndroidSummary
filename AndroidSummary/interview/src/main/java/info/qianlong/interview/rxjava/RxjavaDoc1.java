package info.qianlong.interview.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by junzhao on 2017/12/19.
 */

public class RxjavaDoc1 {

    public static void main(String agrs[]) {
//        test2();
        test3();
    }

    public static void test1() {
        //创建被观察者 (上游)
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                //发送数据1、2、3
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        //创建观察者（下游）
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object o) {
                System.out.println("接收到数据：" + o);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("数据接收完成");
            }
        };

        //建立连接 订阅
        observable.subscribe(observer);
    }

    //测试Disposable
    public static void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("发送数据 1");
                e.onNext(1);
                System.out.println("发送数据 2");
                e.onNext(2);
                System.out.println("发送数据 3");
                e.onNext(3);
                System.out.println("发送数据 4");
                e.onNext(4);
                e.onComplete();
                System.out.println("发送数据完成");
            }
        }).subscribe(new Observer<Integer>() {
            Disposable mDisposable;
            Integer count;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe()");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                count = integer;
                System.out.println("接收到数据:" + count);
                if (count == 2) {
                    System.out.println("dispose()切断:");
                    mDisposable.dispose();  //切断接收数据
                    System.out.println("is dispose:" + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public static void test3() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("上游所在线程：" + Thread.currentThread());
                e.onNext(1);
                e.onComplete();
            }
        });

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("下游所在线程：" + Thread.currentThread());
            }
        };
        observable.subscribeOn(Schedulers.newThread())     //指定上游线程
                .observeOn(AndroidSchedulers.mainThread())  //指定下游线程
                .subscribe(consumer);
        //上游所在线程：Thread[RxNewThreadScheduler-1,5,main]
        //下游所在线程：Thread[main,5,main]
    }

    public static void test4() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                System.out.println("上游所在线程：" + Thread.currentThread());
            }
        });

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
            }
        };

        observable.subscribeOn(Schedulers.newThread()) //上游线程切换第一次
                .subscribeOn(Schedulers.io())  //上游线程切换第二次
                .observeOn(AndroidSchedulers.mainThread()) //下游第一次切换线程 mian
                .observeOn(Schedulers.io())//下游第二次切换线程 io
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("下游所在线程：" + Thread.currentThread());
                    }
                }).observeOn(AndroidSchedulers.mainThread())  //下游第三次切换线程 mian
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("下游所在线程：" + Thread.currentThread());
                    }
                }).subscribe(consumer);
        /**
         上游所在线程：Thread[RxNewThreadScheduler-1,5,main]
         下游所在线程：Thread[RxCachedThreadScheduler-2,5,main]
         下游所在线程：Thread[main,5,main]
         * */
    }

    public static void test5() {

    }

}

