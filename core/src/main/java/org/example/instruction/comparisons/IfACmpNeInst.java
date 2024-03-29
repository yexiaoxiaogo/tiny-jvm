package org.example.instruction.comparisons;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IfACmpNeInst implements Instruction {
    public final int offset;

    public IfACmpNeInst(int offset) {
        this.offset = offset;
    }

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void execute(Frame frame) {
        Object val2= frame.popRef();
        Object val1= frame.popRef();
        if (val1 != val2) {
            frame.nextPc = frame.getPc() + offset;
        }
    }

    @Override
    public String format() {
        return "if_acmpne " + offset;
    }
}
