package com.jvm.demo.oom.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xianzhixianzhixian
 * @date 20201109 22:30
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
