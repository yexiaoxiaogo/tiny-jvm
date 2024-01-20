package org.example.instruction.constants;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class Lconst0Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        frame.pushLong(0L);
    }
}
