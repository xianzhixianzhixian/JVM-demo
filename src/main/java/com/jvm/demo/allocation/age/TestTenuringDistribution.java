package com.jvm.demo.allocation.age;

/**
 * JVM参数：-XX:+UseParNewGC -verbose:gc -Xms20M -Xmx20M -Xmn20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * @author xianzhixianzhixian
 * @date 2021/6/15 23:42
 */
public class TestTenuringDistribution {

    private static final int _1MB = 1024 * 1024;

    /**
     * -XX:MaxTenuringThreshold=1时，allocation1 = 1M，allocation2 = allocation3 = 2M
     * -XX:MaxTenuringThreshold=15时，allocation1 = allocation2 = 0.25M，allocation3 = 4M
     * @param args
     */
    public static void main(String[] args) {
        byte[] allocation1,allocation2,allocation3;
        //什么时候进入老年带取决于-XX:MaxTenuringThreshold的值
        allocation1 = new byte[_1MB / 4];

        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[_1MB * 4];
        allocation3 = null;
        allocation3 = new byte[_1MB * 4];
    }
}
