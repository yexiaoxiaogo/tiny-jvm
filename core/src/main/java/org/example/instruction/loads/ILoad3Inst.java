package org.example.instruction.loads;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class ILoad3Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Integer tmp = frame.getInt(3);
        frame.pushInt(tmp);
    }

}
