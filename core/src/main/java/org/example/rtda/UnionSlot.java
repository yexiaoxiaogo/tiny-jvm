package org.example.rtda;

import org.example.rtda.heap.Instance;

public class UnionSlot {
    private Slot high;
    private Slot low;

    private UnionSlot(Slot high, Slot low) {
        this.high = high;
        this.low = low;
    }

    public static UnionSlot of(Instance instance) {
        return new UnionSlot(new Slot(instance), null);
    }

    public static UnionSlot of(int val) {
        return new UnionSlot(new Slot(val), null);
    }



    public static UnionSlot of(double val) {
        return of(Double.doubleToLongBits(val));
    }


    public void setInt(int val) {
        high.num = val;
    }




    public int getInt() {
        return high.num;
    }



}
