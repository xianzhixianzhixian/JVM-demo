#验证长期存活对象进入老年带，以及动态对象年龄判定
JVM参数：-XX:+UseParNewGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
参数释义：
-XX:+UseParNewGC                 将新生代垃圾收集器设置为ParNew
-XX:MaxTenuringThreshold=1       控制新生代需要经历多少次GC晋升到老年代中的最大阈值
-XX:+PrintTenuringDistribution   在每次新生代GC时，打印出幸存区中对象的年龄分布

#结果分析
##-XX:MaxTenuringThreshold=1
[GC (Allocation Failure) [ParNew
Desired survivor size 524288 bytes, new threshold 1 (max 1)
- age   1:     430008 bytes,     430008 total
  : 7008K->440K(9216K), 0.0138319 secs] 7008K->3512K(19456K), 0.0138583 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
  Heap
  par new generation   total 9216K, used 2570K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  eden space 8192K,  26% used [0x00000007bec00000, 0x00000007bee14930, 0x00000007bf400000)
  from space 1024K,  42% used [0x00000007bf500000, 0x00000007bf56e040, 0x00000007bf600000)
  to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
  tenured generation   total 10240K, used 3072K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
  the space 10240K,  30% used [0x00000007bf600000, 0x00000007bf900020, 0x00000007bf900200, 0x00000007c0000000)
  Metaspace       used 3320K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 368K, capacity 388K, committed 512K, reserved 1048576K
  
eden区总共8M，from区和to区各1M，allocation1、allocation2、allocation3在新生代已占用7M；分配新的2M时新生代的内存不够并且-XX:MaxTenuringThreshold=1，此时
新生代进行垃圾回收，allocation3 = null的2M内存由于没有引用被回收掉；allocation1、allocation2占用的3M内存经历过一次内存回收后被移动到老年带，所以老年带（tenured generation）
占用内存为3 * 1024 = 3027K，allocation3新分配占用的2M内存在新生代的eden区

##-XX:MaxTenuringThreshold=15
[GC (Allocation Failure) [ParNew
Desired survivor size 524288 bytes, new threshold 1 (max 15)
- age   1:     932168 bytes,     932168 total
  : 6496K->935K(9216K), 0.0015274 secs] 6496K->935K(19456K), 0.0015847 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
  Heap
  par new generation   total 9216K, used 5199K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  eden space 8192K,  52% used [0x00000007bec00000, 0x00000007bf02a1e8, 0x00000007bf400000)
  from space 1024K,  91% used [0x00000007bf500000, 0x00000007bf5e9ca8, 0x00000007bf600000)
  to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
  tenured generation   total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
  the space 10240K,   0% used [0x00000007bf600000, 0x00000007bf600000, 0x00000007bf600200, 0x00000007c0000000)
  Metaspace       used 3316K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 368K, capacity 388K, committed 512K, reserved 1048576K
  
可以看到经过一次Minor GC后eden区存活的对象被移动到了from区

#-XX:MaxTenuringThreshold=15，动态 对象年龄判断
[GC (Allocation Failure) [ParNew
Desired survivor size 524288 bytes, new threshold 15 (max 15)
- age   1:     429968 bytes,     429968 total
  : 7868K->433K(9216K), 0.0139436 secs] 7868K->6577K(19456K), 0.0139756 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
  [GC (Allocation Failure) [ParNew: 4529K->4529K(9216K), 0.0000160 secs][Tenured: 6144K->6562K(10240K), 0.0022846 secs] 10673K->6562K(19456K), [Metaspace: 3307K->3307K(1056768K)], 0.0023342 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
  Heap
  par new generation   total 9216K, used 4178K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
  eden space 8192K,  51% used [0x00000007bec00000, 0x00000007bf014930, 0x00000007bf400000)
  from space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
  to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
  tenured generation   total 10240K, used 6562K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
  the space 10240K,  64% used [0x00000007bf600000, 0x00000007bfc68ba0, 0x00000007bfc68c00, 0x00000007c0000000)
  Metaspace       used 3314K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 368K, capacity 388K, committed 512K, reserved 1048576K

  
HotSpot虚拟机并不是要求对象的年龄达到MaxTenuringThreshold所要求的值后才能到达老年带，当新生代中年龄相同的对象占用内存大小大于survivor（from或to）大小的一半后，年
龄大于等于该年龄的对象就能直接进入老年带；本例中存活的allocation1、allocation2占用内存6M > 512K -- from/to区的一半且年龄都为1，直接进入老年带