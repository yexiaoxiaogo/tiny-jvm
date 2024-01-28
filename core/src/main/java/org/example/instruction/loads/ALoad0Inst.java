package org.example.instruction.loads;


import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.heap.Instance;

public class ALoad0Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Instance tmp = frame.getRef(0);
    frame.pushRef(tmp);
  }
}
