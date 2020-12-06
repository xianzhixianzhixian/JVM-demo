package com.jvm.demo.oom.method_area;

/**
 * 测试在不同版本JDK中String.intern()产生对象存放内存的区别
 * JDK6中String.intern()创建的对象实际存储在常量池中，new出来的对象实际存储在Java堆中
 * JDK7中String对象实际存储位置是在Java堆中，常量池中只是存放Java对象的引用
 *
 * 所以
 * 在JDK6中两个都是false，new出来的都是在堆中，intern()方法创建的都是在常量池中
 * 在JDK7中第一个是false，"计算机软件"原来在常量池中不存在引用，只存在与堆中；第二个是true，"java"是在加载sun.misc.Version这个类的时候进入常量池的，str2.intern()和str2是同一个常量池引用
 *
 * @author xianzhixianzhixian
 * @date 2020/12/6 9:47 下午
 */
public class StringInternTest {

    public static void main(String[] args) {
        String str1= new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
