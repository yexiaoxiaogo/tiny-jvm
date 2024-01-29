package org.example.rtda.heap;

import org.example.classfile.attribute.LineNumberTable;
import org.example.instruction.Instruction;
import org.example.util.Utils;

import java.util.Map;
import java.util.Objects;

public class Method {
    /**
     * 方法访问标识
     */
    public final int accessFlags;
    /**
     * 方法名称索引项
     */
    public final String name;
    /**
     * 方法描述索引项
     */
    public final String descriptor;

    public final int maxStacks;
    public final int maxLocals;
    public final Map<Integer, Instruction> instructionMap;

    public final LineNumberTable lineNumberTable;

    public Class clazz;

    public Method(int accessFlags, String name, String descriptor, int maxStacks, int maxLocals,
                  Map<Integer, Instruction> instructionMap,
                  LineNumberTable lineNumberTable) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.maxStacks = maxStacks;
        this.maxLocals = maxLocals;
        this.instructionMap = instructionMap;
        this.lineNumberTable = lineNumberTable;
    }

    public int getArgSlotSize() {
        int cnt = 0;
        for (String it : Utils.parseMethodDescriptor(this.descriptor)) {
            if (Objects.equals("J", it)) {
                cnt += 2;
                continue;
            }
            if (Objects.equals("D", it)) {
                cnt += 2;
                continue;
            }
            cnt++;
        }
        return cnt;
    }

    @Override
    public String toString() {
        return "KMethod{" +
                "accessFlags=" + accessFlags +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", maxStacks=" + maxStacks +
                ", maxLocals=" + maxLocals +
                ", instructionMap=" + instructionMap +
                ", clazz=" + clazz.name +
                '}';
    }

}
