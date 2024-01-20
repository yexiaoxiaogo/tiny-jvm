package org.example.classfile.attribute;

import org.example.classfile.Attribute;
import org.example.classfile.Attributes;
import org.example.classfile.ExceptionTable;
import org.example.instruction.Instruction;

import java.util.LinkedHashMap;
import java.util.Map;

public class Code extends Attribute {
    public final int maxStacks;
    public final int maxLocals;
    public final Instruction[] instructions;
    public final ExceptionTable exceptionTable;
    public final Attributes attributes;

    public Code(int maxStacks, int maxLocals, Instruction[] instructions,
                ExceptionTable exceptionTable, Attributes attributes) {
        this.maxStacks = maxStacks;
        this.maxLocals = maxLocals;
        this.instructions = instructions;
        this.exceptionTable = exceptionTable;
        this.attributes = attributes;
    }

    public Map<Integer, Instruction> getInstructions() {
        Map<Integer, Instruction> map = new LinkedHashMap<>(instructions.length);
        int pc = 0;
        for (Instruction instruction : instructions) {
            map.put(pc, instruction);
            pc += instruction.offset();
        }
        return map;
    }
}
