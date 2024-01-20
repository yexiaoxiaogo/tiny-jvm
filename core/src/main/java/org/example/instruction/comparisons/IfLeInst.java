package org.example.instruction.comparisons;


import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class IfLeInst implements Instruction {
  public final int offset;

  public IfLeInst(int offset) {
    this.offset = offset;
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public void execute(Frame frame) {
    Integer val= frame.popInt();
    if (val <= 0) {
      frame.nextPc = frame.getPc() + offset;
    }
  }

  @Override
  public String format() {
    return "if_le " + offset;
  }
}
