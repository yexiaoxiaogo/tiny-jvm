package org.example.util;

public class Const {
    // class stat
    public static final int CLASS_LOADED = 1;
    public static final int CLASS_LINKED = 2;
    public static final int CLASS_INITING = 3;
    public static final int CLASS_INITED = 4;

    // ACCESS
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_NATIVE = 0x0100;

    // FRAME
    public static final int FAKE_FRAME = 1;
    public static final int FAKE_FRAME_END = 2;
}
