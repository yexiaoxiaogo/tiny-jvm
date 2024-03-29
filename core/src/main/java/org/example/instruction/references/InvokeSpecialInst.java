package org.example.instruction.references;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.heap.Heap;
import org.example.rtda.heap.Class;
import org.example.rtda.heap.Method;
import org.example.rtda.heap.NativeMethod;
import org.example.util.Utils;

public class InvokeSpecialInst implements Instruction {
    public final String clazz;
    public final String methodName;
    public final String methodDescriptor;

    public InvokeSpecialInst(String clazz, String methodName, String methodDescriptor) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
    }

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void execute(Frame frame) {
        NativeMethod nm = Heap.findMethod(Utils.genNativeMethodKey(clazz, methodName, methodDescriptor));
        if (nm != null) {
            nm.invoke(frame);
            return;
        }

        Class aClass = Heap.findClass(clazz);
        if (aClass == null) {
            throw new IllegalStateException();
        }

        Method method = aClass.getMethod(methodName, methodDescriptor);
        if (method == null) {
            System.out.println(Utils.genNativeMethodKey(clazz, methodName, methodDescriptor));
            throw new IllegalStateException();
        }

        if (method.isNative()) {
            throw new IllegalStateException("un impl native method call, " + method);
        }

        Utils.invokeMethod(method);
    }

    @Override
    public String format() {
        return "invokespecail " + clazz + " " + methodName + " " + methodDescriptor;
    }
}
