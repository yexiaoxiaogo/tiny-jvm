package org.example.classfile.cp;

import org.example.classfile.ConstantInfo;

public class ClassCp extends ConstantInfo {
    public final int nameIndex;

    public ClassCp(int infoEnum, int nameIndex) {
        super(infoEnum);
        this.nameIndex = nameIndex;
    }
}
