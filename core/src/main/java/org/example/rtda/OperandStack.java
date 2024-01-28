package org.example.rtda;

import org.example.rtda.heap.Instance;

public class OperandStack {
    private final Slot[] slots;
    /**
     * 指针指的栈地址
     */
    private int top;

    public OperandStack(Integer size) {
        slots = new Slot[size];
        top = 0;
    }

    public void push(Slot slot) {
        this.slots[top++] = slot;
    }

    public Slot pop() {
        top--;
        final Slot slot = this.slots[top];
        this.slots[top] = null; // gc
        return slot;
    }

    public void pushInt(int val) {
        this.slots[top++] = new Slot(val);
    }

    public int popInt() {
        return this.pop().num;
    }


    public void pushRef(Instance val) {
        this.push(new Slot(val));
    }

    public Slot popSlot() {
        return this.pop();
    }

    public void pushSlot(Slot val) {
        this.push(val);
    }
}
