package org.example.instruction.references;

import org.example.instruction.Instruction;
import org.example.interpret.Interpreter;
import org.example.rtda.Frame;
import org.example.rtda.heap.Heap;
import org.example.rtda.heap.Instance;
import org.example.rtda.heap.NativeMethod;
import org.example.rtda.heap.Class;
import org.example.rtda.heap.Method;
import org.example.classloader.ClassLoader;

public class NewInst implements Instruction {
    public final String clazz;

    public NewInst(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void execute(Frame frame) {
        Class cls = Heap.findClass(clazz);

        if (cls == null) {
            ClassLoader loader = frame.method.clazz.classLoader;
            cls = loader.loadClass(clazz);
        }

        if (cls == null) {
            throw new IllegalStateException(ClassNotFoundException.class.getName());
        }

        if (!cls.getStat()) {
            // interfaceInit
            Method cinit = cls.getClinitMethod();
            if (cinit == null) {
                cls.setStat(2);
                frame.nextPc = frame.getPc();
                return;
            }

            String clNm = cinit.nativeMethodKey();
            NativeMethod clm = Heap.findMethod(clNm);
            if (clm != null) {
                clm.invoke(frame);
            } else {
                cls.setStat(1);
                Interpreter.execute(cinit);
                cls.setStat(2);
            }
        }

        Instance obj = cls.newInstance();
        frame.pushRef(obj);
    }

    @Override
    public String format() {
        return "new " + clazz;
    }
}
