package com.jvm.demo.allocation.eden;

/**
 * JVM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author xianzhixianzhixian
 * @date 2021/6/8 11:20 下午
 */
public class EdenMemoryAllocation {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allication1, allication2, allication3, allication4;
        allication1 = new byte[2 * _1MB];
        allication2 = new byte[2 * _1MB];
        allication3 = new byte[2 * _1MB];
        allication4 = new byte[2 * _1MB]; //此时新生代内存10MB已被占用满，出现一次Minor GC
    }
}
