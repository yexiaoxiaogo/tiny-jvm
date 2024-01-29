package org.example.rtda;

import org.example.rtda.heap.Instance;

/**
 * 最基本的存储单元
 */
public class Slot {
    public Integer num;
    /**
     * 访问索引
     */
    public Instance ref;

    public Slot(int num) {
        this.num = num;
        this.ref = null;
    }

    public Slot(Instance ref) {
        this.num = null;
        this.ref = ref;
    }

    @Override
    public String toString() {
        String sb = "Slot{" + "num=" + num +
                ", ref=" + ref +
                '}';
        return sb;
    }
}
