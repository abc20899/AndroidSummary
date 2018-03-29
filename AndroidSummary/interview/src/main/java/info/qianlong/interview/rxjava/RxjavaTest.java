package info.qianlong.interview.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by junzhao on 2017/12/17.
 */

public class RxjavaTest {

    public static void main(String[] args) {
        new RxjavaTest().doRxjava();
    }

    public void doRxjava() {
        //1、创建观察者
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("ssss");
                e.onNext("2222");  //发射器
            }
        });

        Observable observable2 = Observable.just("1", "2");

        String str[] = {"sss", "gggg"};
        Observable observable3 = Observable.fromArray(str);

        //2、创建被观察者
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object o) {
                System.out.println(o.toString());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        //3、订阅
        observable.subscribe(observer);
    }

}
