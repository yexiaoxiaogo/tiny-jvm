package org.example.rtda;

import org.example.rtda.heap.Heap;

public class MetaSpace {
    public static Thread main;

    public static Thread getMainEnv() {
        return main;
    }
}
