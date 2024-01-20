package org.example.classfile.cp;

import org.example.classfile.ConstantInfo;

public class StringCp extends ConstantInfo {
    public final int stringIndex;

    public StringCp(int infoEnum, int stringIndex) {
        super(infoEnum);
        this.stringIndex = stringIndex;
    }
}
