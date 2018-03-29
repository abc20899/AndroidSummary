package info.qianlong.interview.observer;

/**
 * Created by junzhao on 2017/12/17.
 */

public class Test {

    public static void main(String[] args) {
        ConcreteObserver observer1 = new ConcreteObserver("A");
        ConcreteObserver observer2 = new ConcreteObserver("B");
        ConcreteObserver observer3 = new ConcreteObserver("C");

        ConcreteObserverable observerable = new ConcreteObserverable();
        observerable.registerObserver(observer1);
        observerable.registerObserver(observer2);
        observerable.registerObserver(observer3);

        observerable.callObservers();
    }
}
