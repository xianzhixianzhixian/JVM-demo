#验证空间分配担保
JVM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:HandlePromotionFailure=false

-XX:HandlerPromotionFailure=true时，在发生Minor GC之前，虚拟机必须先检查老年代最大可用的连续空间是否大于新生代所有对象
总空间，如果这个条件成立，那这一次Minor GC可以确保是安全的。如果不成立，则虚拟机会先查看-XX:HandlePromotionFailure参数
的设置值是否允许担保失败（Handle Promotion Failure）；如果允许，那会继续检查老年代最大可用的连续空间是否大于历次晋升到老
年代对象的平均大小，如果大于将尝试进行一次Minor GC，尽管这次Minor GC是有风险的；如果小于，或者-XX:HandlePromotionFailure=false
则进行一次Full GC

## JDK7及以后-XX:HandlerPromotionFailure会报错
Unrecognized VM option 'HandlePromotionFailure'
Did you mean '(+/-)PromotionFailureALot'?
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.