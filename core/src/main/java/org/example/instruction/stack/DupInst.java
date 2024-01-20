package org.example.instruction.stack;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.Slot;

public class DupInst implements Instruction {
    @Override
    public void execute(Frame frame) {
        final Slot val = frame.pop();
        frame.push(val);
        frame.push(val);
    }

    @Override
    public String format() {
        return "dup";
    }
}
