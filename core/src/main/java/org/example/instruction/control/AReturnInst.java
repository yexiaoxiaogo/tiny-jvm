package org.example.instruction.control;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.util.Utils;
public class AReturnInst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Utils.doReturn1();
    }

    @Override
    public String format() {
        return "areturn";
    }
}
