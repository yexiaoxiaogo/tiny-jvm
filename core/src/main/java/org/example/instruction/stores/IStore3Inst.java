package org.example.instruction.stores;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IStore3Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Integer tmp = frame.popInt();
        frame.setInt(3, tmp);
    }
}
