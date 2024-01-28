package org.example.rtda;

import org.example.rtda.heap.Instance;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public String toString() {
        return Arrays.stream(slots).map(t -> t == null ? "" : t.toString()).collect(Collectors.joining("\n"));
    }

}
