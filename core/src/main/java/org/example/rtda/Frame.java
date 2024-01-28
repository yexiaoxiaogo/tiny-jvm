package org.example.rtda;

import org.example.instruction.Instruction;
import org.example.rtda.heap.Instance;
import org.example.rtda.heap.Method;

import java.util.Map;

/**
 * 栈帧-随着方法调用而创建，是线程私有的。空间分配在虚拟机栈中。
 * 存储一个个方法的信息
 * 每次调用一个方法就push一个栈帧，调用完毕则pop一个栈帧。
 */
public class Frame {
    /**
     * 方法
     */
    public final Method method;
    /**
     * 局部变量表
     */
    private final LocalVars localVars;
    /**
     * 操作数栈
     */
    private final OperandStack operandStack;
    private final Map<Integer, Instruction> instructionMap;
    public final Thread thread;
    public int nextPc;
    private int pc;

    public int stat;

    public Frame(Method method) {
        this.method = method;
        this.localVars = new LocalVars(method.maxLocals);
        this.operandStack = new OperandStack(method.maxStacks);
        this.thread = MetaSpace.getMainEnv();
        this.instructionMap = method.instructionMap;
    }

    public Frame(Method method, LocalVars localVars, Thread thread) {
        this.method = method;
        this.localVars = localVars;
        this.operandStack = new OperandStack(method.maxStacks);
        this.thread = thread;
        this.instructionMap = method.instructionMap;
    }

    public Instruction getInst() {
        this.pc = nextPc;
        return this.instructionMap.get(this.pc);
    }

    public String getLocalVars() {
        return this.localVars.toString();
    }

    // operand stack operation

    public void pushInt(int val) {
        this.operandStack.pushInt(val);
    }

    public int popInt() {
        return this.operandStack.popInt();
    }


    public void pushRef(Instance val) {
        this.operandStack.pushRef(val);
    }

    // local vars operation

    public void setInt(int index, int val) {
        this.localVars.setInt(index, val);
    }

    public int getInt(int index) {
        return this.localVars.getInt(index);
    }

    public Instance getRef(int index) {
        return this.localVars.getRef(index);
    }


    public Slot pop() {
        return this.operandStack.popSlot();
    }

    public void push(Slot val) {
        this.operandStack.pushSlot(val);
    }

    public void set(int i, Slot val) {
        this.localVars.set(i, val);
    }
}
