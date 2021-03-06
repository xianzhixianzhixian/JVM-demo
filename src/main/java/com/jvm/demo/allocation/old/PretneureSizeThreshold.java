package com.jvm.demo.allocation.old;

/**
 * 测试大对象直接分配在老年代
 * JVM参数：-XX:+UseParNewGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * @author xianzhixianzhixian
 * @date 2021/6/9 23:00
 */
public class PretneureSizeThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation;
        allocation = new byte[4 * _1MB]; //对象直接分配在老年代
    }

}
