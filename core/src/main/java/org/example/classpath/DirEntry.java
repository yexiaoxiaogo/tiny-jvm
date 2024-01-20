package org.example.classpath;

import org.example.classfile.ClassReader;
import org.example.util.EnvHolder;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.example.classfile.ClassFile;

public class DirEntry implements Entry{
    public final String dirPath;

    public DirEntry(String dirPath) {
        this.dirPath = dirPath;
    }


    @Override
    public ClassFile findClass(String clazzName) {
        if (!clazzName.contains("/")) {
            String[] list = new File(dirPath).list();
            if (list == null) {
                throw new IllegalArgumentException();
            }
            for (String name : list) {
                if (Objects.equals(name, clazzName + ".class")) {
                    String path = dirPath + EnvHolder.FILE_SEPARATOR + clazzName + ".class";
                    ClassFile cf = null;
                    try {
                        cf = ClassReader.read(path);
                    } catch (IOException e) {
                        throw new IllegalArgumentException();
                    }
                    cf.setSource(path);
                    return cf;
                }
            }
            return null;
        }

        int idx = clazzName.indexOf("/");
        String subDir = clazzName.substring(0, idx);
        String subPath = dirPath + EnvHolder.FILE_SEPARATOR + subDir;
        if (!new File(subPath).exists()) {
            return null;
        }
        return new DirEntry(subPath).findClass(clazzName.substring(idx + 1));
    }
}
