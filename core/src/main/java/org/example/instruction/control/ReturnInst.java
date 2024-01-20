package org.example.instruction.control;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.util.Utils;

public class ReturnInst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Utils.doReturn0();
    }

    @Override
    public String format() {
        return "return";
    }
}
