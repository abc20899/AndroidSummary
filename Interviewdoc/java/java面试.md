### 面向对象的特征
#### 抽象(抽离共有部分)
我们都知道在面向对象的领域一切都是对象，同时所有的对象都是通过类来描述的，但是并不是所有的类都是来描述对象的。如果一个类没有足够的信息来描述一个具体的对象，而需要其他具体的类来支撑它，那么这样的类我们称它为抽象类。比如new Animal()，我们都知道这个是产生一个动物Animal对象，但是这个Animal具体长成什么样子我们并不知道，它没有一个具体动物的概念，所以他就是一个抽象类，需要一个具体的动物，如狗、猫来对它进行特定的描述，我们才知道它长成啥样。<br>
包含抽象方法的类称为抽象类，抽象类是为继承而存在的，对于一个父类，如果它的某个方法在父类中实现出来没有任何意义，必须根据子类的实际需求来进行不同的实现，那么就可以将这个方法声明为abstract方法，此时这个类也就成为abstract类了。

**在使用抽象类时需要注意几点：**<br>

1. 抽象类不能被实例化，实例化工作交由其子类来完成，它只需要一个引用即可。
2. 抽象方法必须由子类来进行重写。
3. 抽象类中可以包含抽象方法也可以不包含抽象方法。
4. 子类中的抽象方法不能与父类中的抽象方法同名
5. abstract不能与final并列修饰同一个类
6. abstract不能与final、private、static或native并列修饰一个方法

#### 继承
继承允许和鼓励类的重用，它提供了一种明确表述共性的方法。对象的一个新类可以从现有的类中派生，这个过程称为类继承。新类继承了原始类的特性，新类称为原始类的派生类（子类），而原始类称为新类的基类（父类）。派生类可以从它的基类那里继承方法和实例变量，并且类可以修改或增加新的方法使之更适合特殊的需要。
#### 封装
封装是把过程和数据包围起来，对数据的访问只能通过已定义的界面。面向对象计算始于这个基本概念，即现实世界可以被描绘成一系列完全自治、封装的对象，这些对象通过一个受保护的接口访问其他对象。#### 多态性
多态性是指允许不同类的对象对同一消息作出响应。多态性包括参数化多态性和包含多态性。多态性语言具有灵活、抽象、行为共享、代码共享的优势，很好的解决了应用程序函数同名问题。

### String类
基本数据类型包括byte、int、char、long、float、double、boolean和short。java.lang.String类是final类型的，因此不可以继承这个类、不能修改这个类。为了提高效率节省空间，我们应该用StringBuffer类

### Collection 和 Collections的区别
Collection是集合类的上级接口，继承与他的接口主要有Set 和List.Collections是针对集合类的一个帮助类，他提供一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作。

### Hashmap与Hashtable的区别
① 历史原因: Hashtable是给予陈旧的Dictonary类的,  HashMap是Java1.2引进的Map接口的一个实现

② HashMap允许空的键值对, 而HashTable不允许

③ HashTable同步，而HashMap非同步，效率上比HashTable要高

### final, finally, finalize的区别
final修饰方法、变量、类<br>
finally是异常处理语句结构的异步，表示总是执行。<br>
finalize是object类的一个方法，垃圾收集器执行的时候回调用被回收对象的此方法。

### sleep() 和 wait() 有什么区别
① 这两个方法来自不同的类分别是，sleep来自Thread类，和wait来自Object类。

sleep是Thread的静态类方法，谁调用的谁去睡觉，即使在a线程里调用b的sleep方法，实际上还是a去睡觉，要让b线程睡觉要在b的代码中调用sleep。

② 锁: 最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。

sleep不出让系统资源；wait是进入线程等待池等待，出让系统资源，其他线程可以占用CPU。一般wait不会加时间限制，因为如果wait线程的运行资源不够，再出来也没用，要等待其他线程调用notify/notifyAll唤醒等待池中的所有线程，才会进入就绪队列等待OS分配系统资源。sleep(milliseconds)可以用时间指定使它自动唤醒过来，如果时间不到只能调用interrupt()强行打断。

Thread.sleep(0)的作用是“触发操作系统立刻重新进行一次CPU竞争”。

③ 使用范围：wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用。

   synchronized(x){ 
      x.notify() 
     //或者wait() 
   }
### 请简述在异常当中，throw和throws有什么区别   
① throw代表动作，表示抛出一个异常的动作；throws代表一种状态，代表方法可能有异常抛出<br>
② throw用在方法实现中，而throws用在方法声明中<br>
③ throw只能用于抛出一种异常，而throws可以抛出多个异常<br>
### 内存溢出和内存泄露的区别
内存溢出 out of memory，是指程序在申请内存时，没有足够的内存空间供其使用，出现out of memory；比如申请了一个integer,但给它存了long才能存下的数，那就是内存溢出。

内存泄露 memory leak，是指程序在申请内存后，无法释放已申请的内存空间，一次内存泄露危害可以忽略，但内存泄露堆积后果很严重，无论多少内存,迟早会被占光。

memory leak会最终会导致out of memory！

内存溢出就是你要求分配的内存超出了系统能给你的，系统不能满足需求，于是产生溢出。 
内存泄漏是指你向系统申请分配内存进行使用(new)，可是使用完了以后却不归还(delete)，结果你申请到的那块内存你自己也不能再访问（也许你把它的地址给弄丢了），而系统也不能再次将它分配给需要的程序。一个盘子用尽各种方法只能装4个果子，你装了5个，结果掉倒地上不能吃了。这就是溢出！比方说栈，栈满时再做进栈必定产生空间溢出，叫上溢，栈空时再做退栈也产生空间溢出，称为下溢。就是分配的内存不足以放下数据项序列,称为内存溢出。

### String，StringBuffer 和 StringBuilder的区别
①可变与不可变
　　String类中使用字符数组保存字符串，如下就是，因为有“final”修饰符，所以可以知道string对象是不可变的。

　　　　private final char value[];

　　StringBuilder与StringBuffer都继承自AbstractStringBuilder类，在AbstractStringBuilder中也是使用字符数组保存字符串，如下就是，可知这两种对象都是可变的。

　　　　char[] value;

②是否多线程安全

　　String中的对象是不可变的，也就可以理解为常量，显然线程安全。

　　AbstractStringBuilder是StringBuilder与StringBuffer的公共父类，定义了一些字符串的基本操作，如expandCapacity、append、insert、indexOf等公共方法。

　　StringBuffer对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。

StringBuilder并没有对方法进行加同步锁，所以是非线程安全的。

③StringBuilder与StringBuffer共同点

StringBuilder与StringBuffer有公共父类AbstractStringBuilder(抽象类)。

　　抽象类与接口的其中一个区别是：抽象类中可以定义一些子类的公共方法，子类只需要增加新的功能，不需要重复写已经存在的方法；而接口中只是对方法的申明和常量的定义。

　　StringBuilder、StringBuffer的方法都会调用AbstractStringBuilder中的公共方法，如super.append(...)。只是StringBuffer会在方法上加synchronized关键字，进行同步。

　　最后，如果程序不是多线程的，那么使用StringBuilder效率高于StringBuffer。

### 数组和链表的区别
二者都属于一种数据结构<br>
从逻辑结构来看：<br>
① 数组必须事先定义固定的长度（元素个数），不能适应数据动态地增减的情况。当数据增加时，可能超出原先定义的元素个数；当数据减少时，造成内存浪费；数组可以根据下标直接存取。<br>
② 链表动态地进行存储分配，可以适应数据动态地增减的情况，且可以方便地插入、删除数据项。（数组中插入、删除数据项时，需要移动其它数据项，非常繁琐）链表必须根据next指针找到下一个元素。
从内存存储来看：<br>
①(静态)数组从栈中分配空间，对于程序员方便快速,但是自由度小。<br>
②链表从堆中分配空间，自由度大但是申请管理比较麻烦。<br>
从上面的比较可以看出，如果需要快速访问数据，很少或不插入和删除元素，就应该用数组；相反，如果需要经常插入和删除元素就需要用链表数据结构了。　　　　

### ArrayList和LinkedList的区别
①ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。 <br>
②对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。 <br>
③对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。 <br>

### 设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。写出程序。

```
public class Thread4Test {

    private int j = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            Inc inc = new Thread4Test().new Inc();
            Dec dec = new Thread4Test().new Dec();
            Thread tt = new Thread(inc);
            tt.start();
            Thread tt2 = new Thread(dec);
            tt2.start();
        }
    }

    //增加方法
    public synchronized void inc() {
        if (j <= 0) {
            j += 1;
        }
        System.out.println(Thread.currentThread().getName() + "--inc: j=" + j);
    }

    //减少方法
    public synchronized void dec() {
        if (j >= 100) {
            j -= 1;
        }
        System.out.println(Thread.currentThread().getName() + "--dec: j=" + j);
    }

    //增加线程
    class Inc implements Runnable {
        @Override
        public void run() {
            inc();
        }
    }

    //减少线程
    class Dec implements Runnable {
        @Override
        public void run() {
            dec();
        }
    }
}
```

### Set里的元素是不能重复的，那么用什么方法来区分重复与否呢
Set是Collection容器的一个子接口，它不允许出现重复元素。重写了equals。<br>
==是判断对象的内存地址，s1==s2，是判断s1的引用是否与s2相同。<br>
Object类的equals 也是判断对象的内存地址。底层也是用的==。<br>
有一些类复写了equals（），判断的是此对象的具体内容  

### 构造器Constructor是否可被override
构造器Constructor不能被继承，因此不能重写Overriding，但可以被重载Overloading。

### 是值传递还是引用传递
Java是按值传递的，但可以达到按引用传递的效果。



