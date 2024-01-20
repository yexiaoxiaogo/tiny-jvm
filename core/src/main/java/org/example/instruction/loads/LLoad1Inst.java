package org.example.instruction.loads;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class LLoad1Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Long tmp = frame.getLong(1);
        frame.pushLong(tmp);
    }
}
