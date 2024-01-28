package org.example.rtda.heap;

public class PrimitiveArray extends ArrayInstance{
    public int[] ints;

    public PrimitiveArray(Class clazz, int len) {
        super(clazz, len);
    }

    public static PrimitiveArray charArray(int size) {
        final Class arrCls = Heap.findClass("[C");
        final PrimitiveArray array = new PrimitiveArray(arrCls, size);
        array.ints = new int[size];
        return array;
    }
}
