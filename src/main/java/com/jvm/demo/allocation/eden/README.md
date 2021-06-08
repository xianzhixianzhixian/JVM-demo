#验证内存优先在Eden区分配
JVM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8

-verbose:gc               在控制台输出GC情况
-XX:+PrintGCDetails       在控制台输出详细的GC情况
-Xms20M                   堆初始化内存大小，默认为物理内存的1/64
-Xmx20M                   堆最大内存大小，默认为物理内存的1/4
-Xmn10M                   年轻带内存大小
-XX:SurvivorRatio         年轻代中Eden与Survivor区的比值，默认为1/8，新生代由Eden区和Survivor区构成，Survivor区由两份内存大小相等的From Survivor和To Survivor区构成

#GC日志分析
[GC (Allocation Failure) [PSYoungGen: 6155K->519K(9216K)] 6155K->4615K(19456K), 0.0052211 secs] [Times: user=0.01 sys=0.01, real=0.01 secs]
Heap
PSYoungGen      total 9216K, used 5020K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
eden space 8192K, 54% used [0x00000007bf600000,0x00000007bfa65650,0x00000007bfe00000)
from space 1024K, 50% used [0x00000007bfe00000,0x00000007bfe81ca0,0x00000007bff00000)
to   space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
ParOldGen       total 10240K, used 4096K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
object space 10240K, 40% used [0x00000007bec00000,0x00000007bf000020,0x00000007bf600000)
Metaspace       used 2995K, capacity 4556K, committed 4864K, reserved 1056768K
class space    used 319K, capacity 392K, committed 512K, reserved 1048576K

程序运行到分配内存的最后一行时，新生代内存10M占用完毕，出发一次Minor GC，存货的对象被移动到From Survivor区，Eden区被回收