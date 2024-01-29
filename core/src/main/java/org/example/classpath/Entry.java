package org.example.classpath;

import org.example.classfile.ClassFile;

public interface Entry {
    ClassFile findClass(String name);
}
