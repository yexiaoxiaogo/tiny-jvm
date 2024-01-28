package org.example.interpret;

import org.example.rtda.Frame;
import org.example.rtda.MetaSpace;
import org.example.rtda.heap.*;
import org.example.rtda.Thread;
import org.example.util.Const;
import org.example.instruction.Instruction;


/**
 * 解释器
 */
public class Interpreter {

    public static void runMain(Method method) {
        Frame frame = new Frame(method);
        execute(frame);
    }

    public static void execute(Frame newFrame) {
        final Thread env = MetaSpace.getMainEnv();
        env.pushFrame(newFrame);

        newFrame.stat = Const.FRAME_RUNNING;
        do {
            Frame frame = env.topFrame();
            Instruction instruction = frame.getInst();
            frame.nextPc += instruction.offset();

            instruction.execute(frame);
        } while (newFrame.stat == Const.FRAME_RUNNING);

        System.out.println("execute result: \n" + newFrame.debugLocalVars(""));
    }
}
