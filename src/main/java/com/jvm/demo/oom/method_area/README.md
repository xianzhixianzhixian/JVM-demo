## 运行时常量池溢出
JVM参数：-XX:PermSize=6m -XX:MaxPermSize=6m -XX:+PrintTenuringDistribution

-XX:PermSize      非堆内存(永久带)初始值，默认是物理内存的1/64，也就是永久代内存的初始大小
-XX:MaxPermSize   最大非堆内存(永久带)大小，默认是物理内存1/4
-XX:+PrintTenuringDistribution 打印出对象的年龄分布，可以根据GC的日志优化对象进入老年带经过Minor GC的次数
-XX:MaxTenuringThreshold  对象经过多少次Minor GC会进入到老年代，默认15次

### 结果分析
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=6m; support was removed in 8.0
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=6m; support was removed in 8.0
[GC (Allocation Failure) [PSYoungGen: 65536K->6576K(76288K)] 65536K->6584K(251392K), 0.0064962 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 72112K->6608K(141824K)] 72120K->6616K(316928K), 0.0081982 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 137680K->6896K(141824K)] 137688K->6912K(316928K), 0.0065997 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 

尴尬的是 -XX:PermSize和-XX:MaxPermSize在Java8中已经取消了支持，所以无法验证书中的内容了

[GC (Allocation Failure) [PSYoungGen: 65536K->6576K(76288K)] 65536K->6584K(251392K), 0.0064962 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]<br>
Allocation Failure表示向young generation(eden)给新对象申请空间，但是young generation(eden)剩余的合适空间不够所需的大小导致的minor gc
本地中造成频繁Minor GC的原因是String.valueOf()函数每调用一次就会返回一个new String()实例，占用了堆内存

public static String toString(int i) {
    if (i == Integer.MIN_VALUE)
        return "-2147483648";
    int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
    char[] buf = new char[size];
    getChars(i, size, buf);
    return new String(buf, true);
}