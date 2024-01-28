package org.example.instruction;

import org.example.rtda.Frame;

public interface Instruction {
    default int offset() {
        return 1;
    }

    void execute(Frame frame);
}
