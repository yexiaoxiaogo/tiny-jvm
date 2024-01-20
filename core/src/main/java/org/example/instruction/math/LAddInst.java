package org.example.instruction.math;


import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class LAddInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long a1 = frame.popLong();
    Long a2 = frame.popLong();
    frame.pushLong(a1 + a2);
  }
}
