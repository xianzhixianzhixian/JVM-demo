package com.jvm.demo.oom.method_area;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author xianzhixianzhixian
 * @date 2020/11/23 11:29 下午
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        //使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<>();
        //在short范围内足矣让6m的PermSize产生OOM了
        short i = 0;
        while (true) {
            //String::intern()是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于String对象的字符串，则返回代表池中这个字符串的String对象的引用；否则，会将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
            set.add(String.valueOf(i++).intern());
        }
    }
}
