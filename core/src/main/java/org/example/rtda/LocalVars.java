package org.example.rtda;

import org.example.rtda.heap.Instance;

/**
 * 本地变量
 */
public class LocalVars {
    private final Slot[] slots;

    public LocalVars(int size) {
        this.slots = new Slot[size];
    }

    public void setInt(int index, int val) {
        slots[index] = new Slot(val);
    }

    public int getInt(int index) {
        return slots[index].num;
    }

    public void setRef(int index, Instance ref) {
        slots[index] = new Slot(ref);
    }

    public Instance getRef(int index) {
        return slots[index].ref;
    }

    public void set(int i, Slot val) {
        this.slots[i] = val;
    }

    public String debug(String space) {
        StringBuilder sb = new StringBuilder();
        sb.append(space).append(String.format("LocalVars: %d", this.slots.length)).append("\n");
        for (int i = 0; i < this.slots.length; i++) {
            Slot slot = this.slots[i];
            if (slot == null) {
                sb.append(space).append(String.format("%d | null | null", i)).append("\n");
                continue;
            }
            if (slot.ref != null) {
                sb.append(space).append(String.format("%d | ref       | %s", i, slot.ref)).append("\n");
                continue;
            }
            sb.append(space).append(String.format("%d | primitive | %s", i, slot.num)).append("\n");
        }
        return sb.append("\n").toString();
    }
}
