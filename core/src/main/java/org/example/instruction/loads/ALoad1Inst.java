package org.example.instruction.loads;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.heap.Instance;

public class ALoad1Inst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Instance tmp = frame.getRef(1);
        frame.pushRef(tmp);
    }

    @Override
    public String format() {
        return "aload_1";
    }
}
