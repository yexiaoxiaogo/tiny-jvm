package org.example.classfile.cp;

import org.example.classfile.ConstantInfo;

public class MethodType extends ConstantInfo {
    public final int descriptorIndex;

    public MethodType(int infoEnum, int descriptorIndex) {
        super(infoEnum);
        this.descriptorIndex = descriptorIndex;
    }
}
