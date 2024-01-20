package org.example.classfile;

import org.example.classfile.ConstantInfo;

public class ConstantPool {
    public final ConstantInfo[] infos;

    public ConstantPool(int size) {
        this.infos = new ConstantInfo[size];
    }
}
