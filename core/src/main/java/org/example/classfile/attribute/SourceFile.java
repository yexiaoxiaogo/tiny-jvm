package org.example.classfile.attribute;

import org.example.classfile.Attribute;

public class SourceFile extends Attribute {
    public final String name;

    public SourceFile(String name) {
        this.name = name;
    }
}
