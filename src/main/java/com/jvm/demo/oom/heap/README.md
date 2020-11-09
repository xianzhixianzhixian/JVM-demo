## JVM参数配置
-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./

## 参数释义
-verbose:gc                      在控制台输出GC情况

-Xms20M                          JVM初始化堆内存
-Xmx20M                          JVM最大堆内存
服务器一般设置-Xms、-Xmx相等以避免在每次GC 后调整堆的大小

-XX:+PrintGCDetails              打印GC详细信息
打印出来的信息示例
–Heap
– def new generation   total 13824K, used 11223K [0x27e80000, 0x28d80000, 0x28d80000)
– eden space 12288K,  91% used [0x27e80000, 0x28975f20, 0x28a80000)
– from space 1536K,   0% used [0x28a80000, 0x28a80000, 0x28c00000)
– to space 1536K,   0% used [0x28c00000, 0x28c00000, 0x28d80000)
– tenured generation   total 5120K, used 0K [0x28d80000, 0x29280000, 0x34680000)
– the space 5120K,   0% used [0x28d80000, 0x28d80000, 0x28d80200, 0x29280000)
– compacting perm gen  total 12288K, used 142K [0x34680000, 0x35280000, 0x38680000)
– the space 12288K,   1% used [0x34680000, 0x346a3a90, 0x346a3c00, 0x35280000)
– ro space 10240K,  44% used [0x38680000, 0x38af73f0, 0x38af7400, 0x39080000)
– rw space 12288K,  52% used [0x39080000, 0x396cdd28, 0x396cde00, 0x39c80000)



-XX:SurvivorRatio=8              新生代中Eden区域和Survivor区域
From幸存区或To幸存区的比例，默认为8，也就是说Eden占新生代的8/10，From幸存区和To幸存区各占新生代的1/10

-XX:+HeapDumpOnOutOfMemoryError 当JVM发生OOM时，自动生成DUMP文件