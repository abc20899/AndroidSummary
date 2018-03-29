package info.qianlong.interview.observer;

/**
 * Created by junzhao on 2017/12/17.
 */

public class ConcreteObserver implements Observer {

    private String name;//名字

    private int coin;//价格

    private int date; //期刊

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + "接受到了通知...");
    }
}
