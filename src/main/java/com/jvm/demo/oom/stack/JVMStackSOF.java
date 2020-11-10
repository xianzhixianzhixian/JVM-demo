package com.jvm.demo.oom.stack;

/**
 * @author xianzhixianzhixian
 * @date 2020/11/10 12:23 上午
 */
public class JVMStackSOF {

    private static int stackLength = 1;

    public static void stackLeak() {
        stackLength ++;
        stackLeak();
    }

    public static void main(String[] args) {
        try {
            JVMStackSOF.stackLeak();
        } catch (Exception e) {
            System.out.println("stack length：" + stackLength);
            throw e;
        }
    }
}
