package org.example.rtda;

import org.example.rtda.heap.Heap;
import org.example.rtda.heap.NativeMethod;

public class MetaSpace {
    public static Thread main;

    public static Thread getMainEnv() {
        return main;
    }

    public static NativeMethod findNativeMethod(String key) {
        return Heap.findMethod(key);
    }
}
