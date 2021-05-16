package com.jvm.demo.gc.rc;

/**
 * 引用计数器GC示范
 * @author xianzhixianzhixian
 * @date 2021/5/16 11:08 下午
 */
public class ReferenceCountingGC {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    /**
     * 1KB = 1024byte，1MB = 1024KB
     * 这个成员属性唯一的意义就是占用2M内存，一边在GC日志中看清楚是否有回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        //假设在这行发生GC，objA和objB是否能被回收
        System.gc();
    }
}
