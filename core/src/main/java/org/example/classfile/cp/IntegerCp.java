package org.example.classfile.cp;

import org.example.classfile.ConstantInfo;

public class IntegerCp extends ConstantInfo {

    public final int val;
    public IntegerCp(int infoEnum,  int val) {
        super(infoEnum);
        this.val = val;
    }
}
