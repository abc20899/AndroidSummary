### 一、ProGuard到底是什么

ProGuard工具是用于压缩、优化、混淆代码，主作用是可以移除代码中的无用类、字段、方法和属性同时混淆。

### 二、ProGuard技术的功能

1.压缩 ：检查并移除代码中无用的类。

2.优化 ：可以移除.class中无用的指令。

3.混淆 ：将定义的名称变成无意义的名称。防止反编译。

4.预检测 ：对java平台上的处理过的代码再次进行检测。

### 三、ProGuard工作原理

EntryPoint ：是ProGuard中不会被处理的类或者方法。

ProGuard的作用 ：对即将要发布出去的程序进行重新的组织和处理，使得处理前后的代码有相同的功能，但代码是不一样的，不容易被反编译。
