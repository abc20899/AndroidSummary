### 一、UI卡顿原理

①60fps-->16ms ：Android系统每16ms会重新发出信号，触发对UI进行渲染，如果每次渲染都成功，就能达到60fps（60帧/秒）。1000 / 60 = 16ms。每次Dalvik虚拟机进行GC的时候，所有线程都会暂停，GC结束之后，线程才能接续执行。当对UI进行渲染的时候，出现了大量的GC，会造成渲染时间不够，造成UI卡顿。

②overDraw : 过度绘制，某些像素区域被绘制很多次，浪费CPU和GPU资源。

### 二、UI卡顿原因分析

1.人为在UI线程中作轻微耗时操作，导致UI线程卡顿。

2.布局Layout过于复杂，无法在16ms内完成渲染。

3.同一时间动画执行的次数过多，导致CPU或GPU负载过重。

4.View过度绘制，导致某些像素在同一帧时间内被绘制多次，从而使CPU或GPU负载过重。

5.View频繁触发measure、layout，导致measure、layout累计耗时过多及整个View频繁的重新渲染。

6.内存频繁触发GC过多，导致暂时阻塞渲染操作。

7.冗余资源及逻辑等导致加载和执行缓慢。

8.ANR（Application Not Responding）：在主线程中做了耗时操作，导致程序不能响应。

### 三、UI卡顿总结

1.布局优化 ：可以使用include、merge和ViewStub标签；尽量不存在冗余嵌套，并把不需要显示的控件设为GONE。尽量使用weight替代长和宽减少运算。Item如果存在非常复杂的布局时，可以考虑使用自定义的View来取代，这样可以减少measure和layout的次数，从而提高UI性能。

2.列表及Adapter优化 ：尽量复用ListView中的getView()方法，不要重复获取实例。列表在滑动的时候，不要进行元素的更新，只有停止滑动的时候才加载图片和数据。在划动的时候可以只显示图片的默认值或者缩略图。

3.背景和图片等内存分配优化 ：尽量减少整个背景中不必要的背景设置。图片要进行压缩处理。

4.避免ANR ：不要在UI线程中做耗时操作，一定要在子线程中做耗时操作。使用Android中提供的良好的异步处理框架：Handler、AsyncTask、HandlerThread等等。
