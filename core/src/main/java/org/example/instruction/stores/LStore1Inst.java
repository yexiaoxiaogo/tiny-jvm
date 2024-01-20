package org.example.instruction.stores;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class LStore1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long tmp = frame.popLong();
    frame.setLong(1, tmp);
  }
}
