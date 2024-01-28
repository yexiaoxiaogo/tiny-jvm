package org.example.instruction.references;

import org.example.instruction.Instruction;
import org.example.rtda.Frame;
import org.example.rtda.heap.Heap;
import org.example.rtda.heap.Class;
import org.example.rtda.heap.Method;
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

        Class aClass = Heap.findClass(clazz);

        Method method = aClass.getMethod(methodName, methodDescriptor);

        Utils.invokeMethod(method);
    }
}
