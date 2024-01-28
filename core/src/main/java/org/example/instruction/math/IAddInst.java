package org.example.instruction.math;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IAddInst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Integer a1 = frame.popInt();
        Integer a2 = frame.popInt();
        frame.pushInt(a1 + a2);
    }
}
