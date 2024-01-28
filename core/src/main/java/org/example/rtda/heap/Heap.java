package org.example.rtda.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * 堆,线程共享的内存区域
 * 运行时数据区域，存放对象实例
 */
public abstract class Heap {
    private static final Map<String, NativeMethod> NATIVE_METHOD_MAP;
    private static final Map<String, Class> STRING_K_CLASS_MAP;

    static {
        NATIVE_METHOD_MAP = new HashMap<>();
        STRING_K_CLASS_MAP = new HashMap<>();
    }

    public static void registerMethod(String key, NativeMethod method) {
        NATIVE_METHOD_MAP.put(key, method);
    }

    public static void registerClass(String name, Class clazz) {
        STRING_K_CLASS_MAP.put(name, clazz);
    }

    public static NativeMethod findMethod(String key) {
        return NATIVE_METHOD_MAP.get(key);
    }

    public static Class findClass(String name) {
        return STRING_K_CLASS_MAP.get(name);
    }


}
