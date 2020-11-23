## JVM参数配置
-verbose:gc -Xms20M -Xmx20M -Xss160k _-XX:+PrintGCDetails_ -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./

## 参数释义
-Xss160k                         设置每个线程栈占用内存大小为160K(The stack size specified is too small, Specify at least 160k)

## 结果分析
### 递归调用自身
虚拟机为HotSpot：<br>
HotSpot虚拟机栈在创建时已经计算好了大小，栈空间在创建后不允许动态拓展；当程序不停递归调用自己时，栈中的局部变量表、操作数栈、动态连接、方法出口占用的空间都会随着运行而增大，所以最终会导致StackOverflowError

虚拟机为Classic：<br>
Classic虚拟机栈允许动态拓展，在程序不断递归调用自身栈不断拓展的过程中会到达JVM内存使用上限，这个时候不能申请更多的内错，导致报错OutOfMemoryError

### 不断创建线程
32位系统限制单进程最大占用内存为2GB，JVM最大占用内存为2GB；2GB - 堆内存 - 方法区内存 - 程序计数器内存 - 直接内存 - JVM虚拟机消耗的内存 = 栈内存
通过不断创建线程（不是递归调用自身）可以导致创建线程时内存不够用报错OutOfMemory的报错

m.demo.oom.stack.JVMStackOOM.access$000(JVMStackOOM.java:8)
	at com.jvm.demo.oom.stack.JVMStackOOM$1.run(JVMStackOOM.java:20)
	at java.lang.Thread.run(Thread.java:748)

"Thread-911" #921 prio=5 os_prio=31 tid=0x00007ff758920000 nid=0x75e03 runnable [0x000070004a7a9000]
   java.lang.Thread.State: RUNNABLE
	at com.jvm.demo.oom.stack.JVMStackOOM.dontStop(JVMStackOOM.java:11)
	at com.jvm.demo.oom.stack.JVMStackOOM.access$000(JVMStackOOM.java:8)
	at com.jvm.demo.oom.stack.JVMStackOOM$1.run(JVMStackOOM.java:20)
	at java.lang.Thread.run(Thread.java:748)