package org.example.instruction.constants;


import org.example.instruction.Instruction;
import org.example.rtda.Frame;

public class BiPushInst implements Instruction {

  public final byte val;

  public BiPushInst(byte val) {
    this.val = val;
  }

  @Override
  public int offset() {
    return 2;
  }

  @Override
  public void execute(Frame frame) {
    frame.pushInt(this.val);
  }

}
