package org.example.instruction.references;


import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.Thread;
import org.example.rtda.heap.Instance;

public class AThrowInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Thread thread = frame.thread;
    Instance exc = frame.popRef();
    String name = exc.clazz.name;

    Integer handlerPc = frame.method.getHandlerPc(frame.getPc(), name);
    while (handlerPc == null && !thread.empty()) {
      Frame ef = thread.popFrame();
      String msg = ef.getCurrentMethodFullName() + "(" + ef.getCurrentSource() + ":" + ef.getCurrentLine() + ")";
      System.err.println(msg);
      if (thread.empty()) {
        break;
      }
      final Frame f = thread.topFrame();
      handlerPc = f.method.getHandlerPc(f.getPc(), name);
    }

    // no exception handler ...
    if (handlerPc == null) {
      System.err.println(exc);
      throw new RuntimeException("no exception handler");
    }

    thread.topFrame().pushRef(exc);
    thread.topFrame().nextPc = handlerPc;
  }

  @Override
  public String format() {
    return "athrow";
  }
}
