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

    public int getTop() {
        return top;
    }

    public void pushInt(int val) {
        this.slots[top++] = new Slot(val);
    }

    public int popInt() {
        return this.pop().num;
    }

    public long popLong() {
        int low = this.pop().num;
        int high = this.pop().num;

        long l1 = (high & 0x000000ffffffffL) << 32;
        long l2 = low & 0x00000000ffffffffL;
        return l1 | l2;
    }

    public void pushRef(Instance val) {
        this.push(new Slot(val));
    }

    public Instance popRef() {
        return this.pop().ref;
    }
    public Slot popSlot() {
        return this.pop();
    }

    public void pushSlot(Slot val) {
        this.push(val);
    }

    public Slot[] getSlots() {
        return this.slots;
    }

    public String debug(String space) {
        StringBuilder sb = new StringBuilder();
        sb.append(space).append(String.format("OperandStack: %d", this.slots.length)).append("\n");
        for (int i = 0; i < this.slots.length; i++) {
            Slot slot = this.slots[i];
            if (slot == null) {
                sb.append(space).append(String.format("%d | null      | null", i)).append("\n");
                continue;
            }
            if (slot.ref != null) {
                sb.append(space).append(String.format("%d | ref       | %s", i, slot.ref)).append("\n");
                continue;
            }
            sb.append(space).append(String.format("%d | primitive | %s ", i, slot.num)).append("\n");
        }
        return sb.append("\n").toString();
    }
}
