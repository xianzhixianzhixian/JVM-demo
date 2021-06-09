#测试大对象直接分配在老年代
JVM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728

-XX:PretenureSizeThreshold=3145728 表示大于3145728byte大小的对象直接分配在老年代，单位是字节

#结果分析
## 4M内存时
Heap
PSYoungGen      total 9216K, used 7008K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
eden space 8192K, 85% used [0x00000007bf600000,0x00000007bfcd81f8,0x00000007bfe00000)
from space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
to   space 1024K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007bff00000)
ParOldGen       total 10240K, used 0K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
object space 10240K, 0% used [0x00000007bec00000,0x00000007bec00000,0x00000007bf600000)
Metaspace       used 3314K, capacity 4496K, committed 4864K, reserved 1056768K
class space    used 368K, capacity 388K, committed 512K, reserved 1048576K

4M内存分配在新生代了，为啥呢？
因为-XX:PretenureSizeThreshold参数只在Serial和ParNew两款新生代收集器才有用，所以在本次代码中会失效

## 7M内存时
Heap
PSYoungGen      total 9216K, used 1888K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
eden space 8192K, 23% used [0x00000007bf600000,0x00000007bf7d81e8,0x00000007bfe00000)
from space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
to   space 1024K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007bff00000)
ParOldGen       total 10240K, used 7168K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
object space 10240K, 70% used [0x00000007bec00000,0x00000007bf300010,0x00000007bf600000)
Metaspace       used 3319K, capacity 4496K, committed 4864K, reserved 1056768K
class space    used 368K, capacity 388K, committed 512K, reserved 1048576K

7M内存分配在老年带了，为啥？
因为新生代占用内存1888K + 7M > Eden默认大小10M * 4 / 5 = 8M了，所以对象直接备份陪在老年带中