package org.example.instruction.constants;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IConst1Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        frame.pushInt(1);
    }

    @Override
    public String format() {
        return "iconst_1";
    }
}
