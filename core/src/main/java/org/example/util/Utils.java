package org.example.util;

import org.example.classfile.ConstantPool;
import org.example.classfile.cp.ClassCp;
import org.example.classfile.cp.Utf8;
import org.example.rtda.Frame;
import org.example.rtda.MetaSpace;
import org.example.rtda.Slot;
import org.example.rtda.Thread;

import java.io.DataInputStream;
import java.io.IOException;

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



}
