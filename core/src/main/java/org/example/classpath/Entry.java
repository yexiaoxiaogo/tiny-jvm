package org.example.classpath;

import org.example.classfile.ClassFile;

public interface Entry {
    public ClassFile findClass(String name);
}
