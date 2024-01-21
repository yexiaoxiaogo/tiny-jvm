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

}
