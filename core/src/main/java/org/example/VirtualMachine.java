package org.example;

import org.example.classpath.Entry;
import org.example.nativebridge.java.lang.ObjectBridge;
import org.example.rtda.Frame;
import org.example.rtda.UnionSlot;
import org.example.rtda.heap.*;
import org.example.rtda.heap.Class;
import org.example.util.EnvHolder;
import org.example.classpath.Classpath;
import org.example.util.Utils;
import org.example.classloader.ClassLoader;
import org.example.rtda.Thread;
import org.example.rtda.MetaSpace;
import org.example.interpret.Interpreter;

/**
 * java虚拟机运行
 */
public class VirtualMachine {
    public void run(Args cmd) {
        if (cmd.verbose) {
            EnvHolder.verbose = true;
        }
        if (cmd.verboseTrace) {
            EnvHolder.verboseTrace = true;
        }

        if (cmd.verboseCall) {
            EnvHolder.verboseCall = true;
        }
        if (cmd.verboseClass) {
            EnvHolder.verboseClass = true;
        }
        if (cmd.verboseDebug) {
            EnvHolder.verboseDebug = true;
        }

        Entry entry = Classpath.parse(Utils.classpath(cmd.classpath));
        ClassLoader classLoader = new ClassLoader("boot", entry);
        initVm(classLoader);
        String mainClass = Utils.replace(cmd.clazz, '.', EnvHolder.FILE_SEPARATOR.toCharArray()[0]);
        classLoader.loadClass(mainClass);

        Class clazz = Heap.findClass(mainClass);
        Method method = clazz.getMainMethod();
        if (method == null) {
            throw new IllegalStateException("not found main method");
        }

        Interpreter.runMain(method, cmd.args);
    }

    public static void initVm(ClassLoader classLoader) {
        MetaSpace.main = new Thread(1024);
        loadLibrary();
    }

    public static void loadLibrary() {
        ObjectBridge.registerNatives0();
    }

}
