package org.example.classfile.cp;

import org.example.classfile.ConstantInfo;

public class FloatCp extends ConstantInfo {
    public final float val;

    public FloatCp(int infoEnum, float val) {
        super(infoEnum);
        this.val = val;
    }
}
