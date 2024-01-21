package org.example.rtda.heap;

import org.example.util.EnvHolder;
import org.example.util.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class Heap {
    private static final Map<String, NativeMethod> NATIVE_METHOD_MAP;
    private static final Map<String, Class> STRING_K_CLASS_MAP;

    static {
        NATIVE_METHOD_MAP = new HashMap<>();
        STRING_K_CLASS_MAP = new HashMap<>();
    }

    public static void registerMethod(String key, NativeMethod method) {
        if (NATIVE_METHOD_MAP.containsKey(key)) {
            throw new IllegalStateException();
        }
        NATIVE_METHOD_MAP.put(key, method);
    }

    public static NativeMethod findMethod(String key) {
        return NATIVE_METHOD_MAP.get(key);
    }

    public static Class findClass(String name) {
        return STRING_K_CLASS_MAP.get(name);
    }

    public static void registerClass(String name, Class clazz) {
        if (EnvHolder.verboseClass) {
            String source = clazz.classLoader.getName();
            if (clazz.classFile != null && clazz.classFile.getSource() != null) {
                source = clazz.classFile.getSource();
            }
            Logger.clazz("[Loaded ".concat(name).concat(" from ").concat(source) + "]");
        }
        STRING_K_CLASS_MAP.putIfAbsent(name, clazz);
    }
}
