package info.qianlong.interview.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junzhao on 2017/12/17.
 * 被观察者实现
 */
public class ConcreteObserverable implements Observerable {

    private List<Observer> observers;

    public ConcreteObserverable() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        if (o != null) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i > 0) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update();
        }
    }

    public void callObservers() {
        notifyObservers();
    }
}
