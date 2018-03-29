package info.qianlong.interview.observer;

/**
 * Created by junzhao on 2017/12/17.
 * 被观察者接口
 */
public interface Observerable {
    void registerObserver(Observer o); //注册观察者

    void removeObserver(Observer o); //移除观察者

    void notifyObservers(); //通知观察者
}
