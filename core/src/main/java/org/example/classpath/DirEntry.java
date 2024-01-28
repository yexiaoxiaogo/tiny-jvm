package org.example.classpath;

import org.example.classfile.ClassReader;
import java.io.IOException;
import org.example.classfile.ClassFile;

public class DirEntry implements Entry{
    public final String dirPath;

    public DirEntry(String dirPath) {
        this.dirPath = dirPath;
    }


    @Override
    public ClassFile findClass(String clazzName) {
        try {
            return ClassReader.read(this.dirPath + "/" + clazzName + ".class");
        } catch (IOException e) {
            return null;
        }
    }
}
