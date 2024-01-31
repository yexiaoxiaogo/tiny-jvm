package org.example.classloader;

import org.example.classfile.ClassFile;
import org.example.classfile.MethodInfo;
import org.example.classfile.attribute.Code;
import org.example.classpath.Entry;
import org.example.rtda.heap.Class;
import org.example.rtda.heap.Heap;
import org.example.rtda.heap.Method;
import org.example.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClassLoader {
    private final String name;
    private final Entry entry;

    public ClassLoader(String name, Entry entry) {
        this.name = name;
        this.entry = entry;
    }

    public Class loadClass(String interfaceName) {
        Class cache = Heap.findClass(interfaceName);
        if (cache != null) {
            return cache;
        }

        Class clazz = doLoadClass(interfaceName);
        doRegister(clazz);

        return clazz;
    }

    public Class doLoadClass(String name) {

        // 此处添加 java.lang.Object.class 的空实现
        if (name.equals("java/lang/Object")) {
            return new Class(1,"java/lang/Object", null);
        }

        ClassFile clazz = entry.findClass(name);

        Class aClass = doLoadClass(name, clazz);

        // superclass
        if (aClass.superClassName != null) {
            aClass.setSuperClass(this.loadClass(aClass.superClassName));
        }

        return aClass;
    }
    public Class doLoadClass(String name, ClassFile classFile) {
        List<Method> methods = new ArrayList<>();

        for (MethodInfo methodInfo : classFile.methods.methodInfos) {
            methods.add(this.map(methodInfo));
        }

        int scIdx = classFile.superClass;
        String superClassName = null;
        if (scIdx != 0) {
            superClassName = Utils.getClassName(classFile.cpInfo, scIdx);
        }


        return new Class(classFile.accessFlags, name, superClassName, null, methods,
                classFile.cpInfo, this, classFile);
    }

    public Method map(MethodInfo cfMethodInfo) {
        Code code = cfMethodInfo.getCode();
        if (code == null) {
            return new Method(cfMethodInfo.accessFlags, cfMethodInfo.name, cfMethodInfo.descriptor.descriptor, 0, 0,
                    null, cfMethodInfo.getLineNumber());
        }
        return new Method(cfMethodInfo.accessFlags, cfMethodInfo.name, cfMethodInfo.descriptor.descriptor,
                code.maxStacks, code.maxLocals, code.getInstructions(),
                cfMethodInfo.getLineNumber());
    }

    public void doRegister(Class clazz) {
        Heap.registerClass(clazz.name, clazz);
    }

}
