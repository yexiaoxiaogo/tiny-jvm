package org.example.instruction.loads;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class ILoad1Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Integer tmp = frame.getInt(1);
        frame.pushInt(tmp);
    }

    @Override
    public String format() {
        return "iload_1";
    }
}
