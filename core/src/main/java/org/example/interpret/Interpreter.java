package org.example.interpret;

import org.example.rtda.Frame;
import org.example.rtda.MetaSpace;
import org.example.rtda.heap.*;
import org.example.rtda.Thread;
import org.example.rtda.heap.Class;
import org.example.util.Const;
import org.example.instruction.Instruction;
import org.example.util.EnvHolder;
import org.example.util.Logger;
import org.example.util.Utils;

/**
 * 解释器
 */
public class Interpreter {
    /**
     * 同步执行指定方法
     */
    public static void execute(Method method) {
        final Thread env = MetaSpace.getMainEnv();
        Frame newFrame = new Frame(method);
        // 传参
        final int slots = method.getArgSlotSize();
        if (slots > 0) {
            final Frame old = env.topFrame();
            for (int i = slots - 1; i >= 0; i--) {
                newFrame.set(i, old.pop());
            }
        }
        execute(newFrame);
    }

    public static void runMain(Method method, String[] args) {
        Frame frame = new Frame(method);

        Instance[] kargs = new Instance[args.length];
        for (int i = 0; i < args.length; i++) {
            kargs[i] = Utils.str2Obj(args[i], frame.method.clazz.classLoader);
        }
        Class arrClazz = Heap.findClass("[Ljava/lang/String;");
        if (arrClazz == null) {
            arrClazz = new Class(1, "[Ljava/lang/String;", method.clazz.classLoader, null);
            Heap.registerClass(arrClazz.name, arrClazz);
        }
        InstanceArray array = new InstanceArray(arrClazz, kargs);
        frame.setRef(0, array);

        execute(frame);
    }

    public static void execute(Frame newFrame) {
        final Thread env = MetaSpace.getMainEnv();
        env.pushFrame(newFrame);

        newFrame.stat = Const.FAKE_FRAME;
        do {
            Frame frame = env.topFrame();
            Instruction instruction = frame.getInst();
            frame.nextPc += instruction.offset();
            traceBefore(instruction, frame);

            instruction.execute(frame);
        } while (newFrame.stat == Const.FAKE_FRAME);
    }

    private static void traceBefore(Instruction inst, Frame frame) {
        if (EnvHolder.verboseDebug) {
            debugBefore(inst, frame);
        }
        // verboseTrace
        if (EnvHolder.verboseTrace) {
            trace(inst, frame);
        }
        // verboseCall
        if (EnvHolder.verboseCall) {
            call(inst, frame);
        }
    }

    static void debugBefore(Instruction inst, Frame frame) {
        String space = genSpace((frame.thread.size() - 1) * 2);
        Logger.debug(
                space.concat(Integer.toString(frame.thread.size()))
                        .concat(" <> ").concat(frame.method.name).concat("_").concat(frame.method.descriptor).concat(
                                " =============================="));
        Logger.debug(inst.debug(space + frame.getPc() + " "));
        Logger.debug(frame.debugNextPc(space));
        Logger.debug(frame.debugLocalVars(space));
        Logger.debug(frame.debugOperandStack(space));
        Logger.debug(space + "---------------------");
        Logger.debug(space + "\n");
    }

    private static void trace(Instruction inst, Frame frame) {
        String space = genSpace((frame.thread.size() - 1) * 2);
        Logger.trace(space.concat(Integer.toString(frame.getPc()).concat(" ").concat(inst.format())));
    }

    public static String genSpace(int size) {
        String x = "";
        for (int i = 0; i < size; i++) {
            x = x.concat(" ");
        }
        return x;
    }

    private static void call(Instruction inst, Frame frame) {
        if (!inst.format().startsWith("invoke")) {
            return;
        }
        String space = genSpace((frame.thread.size() - 1) * 2);
        Logger.trace(space.concat(Integer.toString(frame.getPc()).concat(" ").concat(inst.format())));
    }
}
