package com.jvm.demo.gc.finalize;

/**
 * 代码演示了两点
 * 1、对象可以在被GC时自我拯救
 * 2、1中的机会只有一次，因为一个对象的finalize()方法最多只会被调用一次
 * @author xianzhixianzhixian
 * @date 2021/5/17 11:16 下午
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed！");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();

        //对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();
        //因为finalize()方法优先级很低，暂停0.5s等待其执行
        Thread.sleep(500L);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("SAVE_HOOK revoveried");
        }

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500L);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("SAVE_HOOK revoveried");
        }
    }
}
