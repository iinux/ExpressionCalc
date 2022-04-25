package com.mycompany.helloworld.netty.directbuffer.weakreference;


import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class TestPhantomReference {
    private static final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    private static final List<PhantomReference<Object>> references = new ArrayList<>();
    private static List<DataObj> dataObjs = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 32; ++i) {
            DataObj obj = new DataObj();
            PhantomReference<Object> phantomReference = new PhantomReference<>(obj, referenceQueue);
            references.add(phantomReference);
            dataObjs.add(obj);
        }

        // 设置为null 让相关对象编程虚可达 被GC特殊对待一下
        dataObjs = null;

        System.out.println("before gc");
        System.gc();
        System.out.println("after gc");

        // 直接在主线程看
        Reference<?> reference = null;
        while ((reference = referenceQueue.poll()) != null) {
            System.out.println(reference);
            reference.clear();
        }

        System.out.println("end");
    }
}

class DataObj {
}


