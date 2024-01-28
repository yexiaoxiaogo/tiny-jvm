package org.example.rtda.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * 堆,线程共享的内存区域
 * 运行时数据区域，存放对象实例
 */
public abstract class Heap {
    private static final Map<String, Class> STRING_K_CLASS_MAP;

    static {
        STRING_K_CLASS_MAP = new HashMap<>();
    }

    public static void registerClass(String name, Class clazz) {
        STRING_K_CLASS_MAP.put(name, clazz);
    }

    public static Class findClass(String name) {
        return STRING_K_CLASS_MAP.get(name);
    }


}
