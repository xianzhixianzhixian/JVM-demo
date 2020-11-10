## JVM参数配置
-verbose:gc -Xms20M -Xmx20M -Xss160k  -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./

## 参数释义
-Xss160k                         设置每个线程堆占用内存大小为160K(The stack size specified is too small, Specify at least 160k)

## 结果分析
虚拟机为HotSpot：<br>
HotSpot虚拟机栈在创建时已经计算好了大小，栈空间在创建后不允许动态拓展；当程序不停递归调用自己时，栈中的局部变量表、操作数栈、动态连接、方法出口占用的空间都会随着运行而增大，所以最终会导致StackOverflowError

虚拟机为Classic：<br>
Classic虚拟机栈允许动态拓展，在程序不断递归调用自身栈不断拓展的过程中会到达JVM内存使用上限，这个时候不能申请更多的内错，导致报错OutOfMemoryError