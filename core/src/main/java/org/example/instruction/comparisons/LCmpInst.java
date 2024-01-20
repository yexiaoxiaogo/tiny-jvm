package org.example.instruction.comparisons;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

/**
 * 比较指令
 */
public class LCmpInst implements Instruction {
    @Override
    public void execute(Frame frame) {
        Long v2 = frame.popLong();
        Long v1 = frame.popLong();
        if (v1.equals(v2)) {
            frame.pushInt(0);
            return;
        }
        if (v1 < v2) {
            frame.pushInt(-1);
            return;
        }
        frame.pushInt(1);
    }
}
