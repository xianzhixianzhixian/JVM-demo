package com.jvm.demo.oom.direct_memory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 * @author xianzhixianzhixian
 * @date 2021/5/14 12:17 上午
 */
public class DirectMemoryOOM {

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(10240L);
        }
    }
}
