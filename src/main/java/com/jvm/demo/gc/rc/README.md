#验证引用记数法GC
-verbose:gc -XX:+PrintGCDetails

#JVM参数释义
-verbose:gc              在控制台输出GC情况
-XX:+PrintGCDetails      在控制台输出详细的GC情况

# 只有-verbose:gc打印的控制台信息
[GC (System.gc())  6717K->568K(251392K), 0.0018547 secs]
[Full GC (System.gc())  568K->418K(251392K), 0.0128603 secs]

# 两者都有后打印的控制台信息
[GC (System.gc()) [PSYoungGen: 6717K->480K(76288K)] 6717K->488K(251392K), 0.0137013 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (System.gc()) [PSYoungGen: 480K->0K(76288K)] [ParOldGen: 8K->418K(175104K)] 488K->418K(251392K), [Metaspace: 3310K->3310K(1056768K)], 0.0136285 secs] [Times: user=0.01 sys=0.00, real=0.02 secs]
Heap
PSYoungGen      total 76288K, used 655K [0x000000076ab00000, 0x0000000770000000, 0x00000007c0000000)
eden space 65536K, 1% used [0x000000076ab00000,0x000000076aba3ee8,0x000000076eb00000)
from space 10752K, 0% used [0x000000076eb00000,0x000000076eb00000,0x000000076f580000)
to   space 10752K, 0% used [0x000000076f580000,0x000000076f580000,0x0000000770000000)
ParOldGen       total 175104K, used 418K [0x00000006c0000000, 0x00000006cab00000, 0x000000076ab00000)
object space 175104K, 0% used [0x00000006c0000000,0x00000006c0068af0,0x00000006cab00000)
Metaspace       used 3317K, capacity 4496K, committed 4864K, reserved 1056768K
class space    used 368K, capacity 388K, committed 512K, reserved 1048576K


# 日志分析
程序中每个ReferenceCountingGC对象占用的堆内存在2MB多，GC之后新生代和老年带占用的内存都是小于4096KB的，说明堆内存中的两个对象已被回收（GC算法使用的不是引用计数器算法）