package org.example.rtda.heap;

import org.example.rtda.Frame;
import org.example.rtda.UnionSlot;

public class Field {
    /**
     * 字段访问标志，占2个字节16位
     */
    public final int accessFlags;
    /**
     * 字段名称索引
     */
    public final String name;
    /**
     * 字段描述索引
     */
    public final String descriptor;

    public UnionSlot val;

    public Field(int accessFlags, String name, String descriptor) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
    }

    public Field(int accessFlags, String name, String descriptor, UnionSlot val) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.val = val;
    }

    public boolean isStatic() {
        return (accessFlags & 0x0008) != 0;
    }

    /**
     * 初始化
     */
    public void init() {
        switch (descriptor) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                val = UnionSlot.of(0);
                break;
            case "J":
                val = UnionSlot.of(0L);
                break;
            case "F":
                val = UnionSlot.of(0f);
                break;
            case "D":
                val = UnionSlot.of(0d);
                break;
            default: // ref
                val = UnionSlot.of((Instance) null);
                break;
        }
    }

    /**
     * 获取字段的值，并推送到栈帧
     */
    public void get(Frame frame) {
        switch (descriptor) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                frame.pushInt(val.getInt());
                break;
            case "J":
                frame.pushLong(val.getLong());
                break;
            case "F":
                frame.pushFloat(val.getFloat());
                break;
            case "D":
                frame.pushDouble(val.getDouble());
                break;
            default: // ref
                frame.pushRef(val.getRef());
                break;
        }
    }

    /**
     * 设置字段的值，从栈帧中弹出相应的数据 */
    public void set(Frame frame) {
        switch (descriptor) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                val.setInt(frame.popInt());
                break;
            case "J":
                val.setLong(frame.popLong());
                break;
            case "F":
                val.setFloat(frame.popFloat());
                break;
            case "D":
                val.setDouble(frame.popDouble());
                break;
            default: // ref
                val.setRef(frame.popRef());
                break;
        }
    }

    /**
     * 设置为新的 slot
     */
    public void set(UnionSlot neo) {
        val.set(neo);
    }
}
