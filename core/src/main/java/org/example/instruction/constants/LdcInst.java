package org.example.instruction.constants;

import org.example.instruction.Instruction;
import org.example.interpret.Interpreter;
import org.example.rtda.Frame;
import org.example.rtda.heap.Field;
import org.example.rtda.heap.Heap;
import org.example.rtda.heap.Class;
import org.example.rtda.heap.PrimitiveArray;
import org.example.rtda.heap.Instance;
import org.example.rtda.UnionSlot;

public class LdcInst implements Instruction {
    public final String descriptor;
    public final Object val;

    @Override
    public int offset() {
        return 2;
    }

    public LdcInst(String descriptor, Object val) {
        this.descriptor = descriptor;
        this.val = val;
    }

    @Override
    public void execute(Frame frame) {
        switch (descriptor) {
            case "I":
                frame.pushInt(((Integer) val));
                break;
            case "F":
                frame.pushFloat(((float) val));
                break;
            case "Ljava/lang/String":
                Class klass = Heap.findClass("java/lang/String");
                if (klass == null) {
                    klass = frame.method.clazz.classLoader.loadClass("java/lang/String");
                }
                if (!klass.getStat()) {
                    klass.setStat(1);
                    Interpreter.execute(klass.getMethod("<clinit>", "()V"));
                    klass.setStat(2);
                }
                Instance object = klass.newInstance();
                Field field = object.getField("value", "[C");
                char[] chars = val.toString().toCharArray();

                final PrimitiveArray array = PrimitiveArray.charArray(chars.length);
                for (int i = 0; i < array.len; i++) {
                    array.ints[i] = chars[i];
                }

                field.val = UnionSlot.of(array);
                frame.pushRef(object);
                break;
            case "L":
                Class klass2 = Heap.findClass(val.toString());
                if (klass2 == null) {
                    klass2 = frame.method.clazz.classLoader.loadClass(val.toString());
                }
                frame.pushRef(klass2.getRuntimeClass());
                break;
            default:
                frame.pushRef((Instance) val);
                break;
        }
    }

    @Override
    public String format() {
        return "ldc " + descriptor + " " + val;
    }

    @Override
    public String toString() {
        return "LdcInst{" +
                "descriptor='" + descriptor + '\'' +
                ", val=" + val +
                '}';
    }
}
