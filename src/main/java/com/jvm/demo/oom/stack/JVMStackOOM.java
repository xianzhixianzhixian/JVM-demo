package com.jvm.demo.oom.stack;

/**
 * JVM栈OutOfMemory的异常
 * @author xianzhixianzhixian
 * @date 2020/11/11 12:16 上午
 */
public class JVMStackOOM {

    private void dontStop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JVMStackOOM jVMStackOOM = new JVMStackOOM();
        jVMStackOOM.stackLeakByThread();
    }
}
