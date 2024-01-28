package org.example.util;

import org.example.classfile.ConstantInfo;
import org.example.classfile.ConstantPool;
import org.example.classfile.cp.ClassCp;
import org.example.classfile.cp.MethodDef;
import org.example.classfile.cp.NameAndType;
import org.example.classfile.cp.Utf8;
import org.example.classloader.ClassLoader;
import org.example.interpret.Interpreter;
import org.example.rtda.*;
import org.example.rtda.Thread;
import org.example.rtda.heap.*;
import org.example.rtda.heap.Class;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Utils {
    public static String getClassName(ConstantPool cp, int classIndex) {
        int nameIndex = ((ClassCp) cp.infos[classIndex - 1]).nameIndex;
        return ((Utf8) cp.infos[nameIndex - 1]).getString();
    }

    public static byte[] readNBytes(DataInputStream stm, int n) throws IOException {
        byte[] bytes = new byte[n];
        for (int i = 0; i < n; i++) {
            bytes[i] = stm.readByte();
        }
        return bytes;
    }

    public static String getString(ConstantPool cp, int index) {

        return ((Utf8) cp.infos[index - 1]).getString();
    }

    public static String genNativeMethodKey(Method method) {
        return genNativeMethodKey(method.clazz.name, method.name, method.descriptor);
    }

    public static List<String> parseMethodDescriptor(String descriptor) {
        if (descriptor.startsWith("()")) {
            return new ArrayList<>();
        }

        descriptor = descriptor.substring(descriptor.indexOf("(") + 1, descriptor.indexOf(")"));

        List<Character> base = Arrays.asList('V', 'Z', 'B', 'C', 'S', 'I', 'J', 'F', 'D');
        List<String> rets = new ArrayList<>();
        for (int i = 0; i < descriptor.length(); i++) {
            if (base.contains(descriptor.charAt(i))) {
                rets.add(String.valueOf(descriptor.charAt(i)));
                continue;
            }
            // array
            if (descriptor.charAt(i) == '[') {
                int temp = i;
                i++;
                while (descriptor.charAt(i) == '[') {
                    i++;
                }
                if (base.contains(descriptor.charAt(i))) {
                    rets.add(descriptor.substring(temp, i + 1));
                    continue;
                }
                int idx = descriptor.indexOf(';', i);
                rets.add(descriptor.substring(temp, idx));
                i = idx;
                continue;
            }
            // class
            if (descriptor.charAt(i) == 'L') {
                int idx = descriptor.indexOf(';', i);
                rets.add(descriptor.substring(i, idx));
                i = idx;
                continue;
            }
        }
        return rets;
    }

    public static boolean isStatic(int accessFlags) {
        return (accessFlags & Const.ACC_STATIC) != 0;
    }

    public static String genNativeMethodKey(String clazz, String name, String descriptor) {
        return clazz + "_" + name + "_" + descriptor;
    }

    public static Instance str2Obj(String str, ClassLoader classLoader) {
        Class klass = Heap.findClass("java/lang/String");
        Instance object = klass.newInstance();
        Field field = object.getField("value", "[C");
        char[] chars = str.toCharArray();
        final PrimitiveArray arr = PrimitiveArray.charArray(chars.length);
        for (int i = 0; i < chars.length; i++) {
            arr.ints[i] = chars[i];
        }

        field.val = UnionSlot.of(arr);
        return object;
    }

    public static void doReturn0() {
        doReturn(0);
    }

    /**
     * 返回操作
     * @param slotSize 返回值占用 slot 大小
     */
    public static void doReturn(int slotSize) {
        final Thread env = MetaSpace.getMainEnv();
        final Frame old = env.popFrame();

        // 解释器同步执行方法的结束条件
        if (old.stat == Const.FRAME_RUNNING) {
            old.stat = Const.FRAME_END;
        }

        if (slotSize == 0) {
            return;
        }

        final Frame now = env.topFrame();
        if (slotSize == 1) {
            now.push(old.pop());
            return;
        }

        if (slotSize == 2) {
            final Slot v2 = old.pop();
            final Slot v1 = old.pop();
            now.push(v1);
            now.push(v2);
            return;
        }

        throw new IllegalStateException();
    }


    public static void invokeMethod(Method method) {
        NativeMethod nmb = Heap.findMethod(Utils.genNativeMethodKey(method));
        if (nmb != null) {
            nmb.invoke(MetaSpace.getMainEnv().topFrame());
            return;
        }

        if (Utils.isNative(method.accessFlags)) {
            final String key = method.getKey();
            NativeMethod nm = MetaSpace.findNativeMethod(key);
            if (nm == null) {
                throw new IllegalStateException("not found native method: " + key);
            }
            nm.invoke(MetaSpace.getMainEnv().topFrame());
        } else {
            Frame newFrame = new Frame(method);
            final Thread env = MetaSpace.getMainEnv();
            final Frame old = env.topFrame();

            // 传参
            final int slots = method.getArgSlotSize();
            for (int i = slots - 1; i >= 0; i--) {
                newFrame.set(i, old.pop());
            }

            env.pushFrame(newFrame);
        }
    }

    /**
     * 判定访问标志是否是 native
     */
    public static boolean isNative(int accessFlags) {
        return (accessFlags & Const.ACC_NATIVE) != 0;
    }

    public static String getClassNameByMethodDefIdx(ConstantPool constantPool, int mdIdx) {
        ConstantInfo methodInfo = constantPool.infos[mdIdx - 1];
        MethodDef methodDef = (MethodDef) methodInfo;
        return getClassName(constantPool, methodDef.classIndex);
    }

    public static String getMethodNameByMethodDefIdx(ConstantPool cp, int mdIdx) {
        ConstantInfo methodInfo = cp.infos[mdIdx - 1];
        MethodDef methodDef = (MethodDef) methodInfo;
        return getNameByNameAndTypeIdx(cp, methodDef.nameAndTypeIndex);
    }

    public static String getNameByNameAndTypeIdx(ConstantPool cp, int natIdx) {
        int nameIndex = ((NameAndType) cp.infos[natIdx - 1]).nameIndex;
        return getString(cp, nameIndex);
    }

    public static String getMethodTypeByMethodDefIdx(ConstantPool cp, int mdIdx) {
        ConstantInfo methodInfo = cp.infos[mdIdx - 1];
        MethodDef methodDef = (MethodDef) methodInfo;
        return getTypeByNameAndTypeIdx(cp, methodDef.nameAndTypeIndex);
    }

    public static String getTypeByNameAndTypeIdx(ConstantPool cp, int natIdx) {
        int idx = ((NameAndType) cp.infos[natIdx - 1]).descriptionIndex;
        return getString(cp, idx);
    }
}
