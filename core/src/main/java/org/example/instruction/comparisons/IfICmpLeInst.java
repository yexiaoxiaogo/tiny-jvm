package org.example.instruction.comparisons;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IfICmpLeInst implements Instruction {
  public final int offset;

  public IfICmpLeInst(int offset) {
    this.offset = offset;
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public void execute(Frame frame) {
    Integer val2= frame.popInt();
    Integer val1= frame.popInt();
    if (val1 <= val2) {
      frame.nextPc = frame.getPc() + offset;
    }
  }

  @Override
  public String format() {
    return "if_icmple " + offset;
  }

}
