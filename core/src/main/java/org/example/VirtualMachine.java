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
/*        loadFoundationClass(classLoader);

        initSystemOut(classLoader);
        initSystemErr(classLoader);*/
    }

    public static void loadLibrary() {
        ObjectBridge.registerNatives0();
//        ClassBridge.registerNatives0();
//        SystemBridge.registerNatives0();
//        FileOutputStreamBridge.registerNatives0();
//        FileDescriptorBridge.registerNative0();
//        MathBridge.registerNatives0();
//        UnsafeBridge.registerNatives0();
//        IntegerBridge.registerNatives0();
//        FloatBridge.registerNatives0();
//        DoubleBridge.registerNatives0();
//        StringBridge.registerNatives0();
//        RandomBridge.registerNatives0();
//        ExceptionBridge.registerNatives0();
//        ThrowableBridge.registerNatives0();
//        AtomicLongBridge.registerNatives0();
//        ReflectionBridge.registerNatives0();
//        ClassLoaderBridge.registerNatives0();
//        AccessControllerBridge.registerNative0();
//        PropertiesBridge.registerNative0();
//        CharsetBridge.registerNative0();
//        UnixFileSystemBridge.registerNatives0();
//        FileInputStreamBridge.registerNatives0();
//        ZipFileBridge.registerNatives0();
//        NativeInputStreamBridge.registerNatives0();
    }


    private static void loadFoundationClass(ClassLoader classLoader) {
        // class
        Class metaClass = classLoader.loadClass("java/lang/Class");
        for (Class cls : Heap.getClasses()) {
            if (cls.getRuntimeClass() == null) {
                Instance obj = metaClass.newInstance();
                cls.setRuntimeClass(obj);
                obj.setMetaClass(cls);
            }
        }

        // primitive
        classLoader.loadPrimitiveClass("char");
        classLoader.loadPrimitiveClass("boolean");
        classLoader.loadPrimitiveClass("byte");
        classLoader.loadPrimitiveClass("short");
        classLoader.loadPrimitiveClass("int");
        classLoader.loadPrimitiveClass("long");
        classLoader.loadPrimitiveClass("float");
        classLoader.loadPrimitiveClass("double");
        classLoader.loadPrimitiveClass("void");

        // string
        classLoader.loadClass("java/lang/String");

        // primitive wrapper
        classLoader.loadClass("java/lang/Character");
        classLoader.loadClass("java/lang/Boolean");
        classLoader.loadClass("java/lang/Byte");
        classLoader.loadClass("java/lang/Short");
        classLoader.loadClass("java/lang/Integer");
        classLoader.loadClass("java/lang/Long");
        classLoader.loadClass("java/lang/Float");
        classLoader.loadClass("java/lang/Double");
        classLoader.loadClass("java/lang/Void");

        // primitvie Arry class
        classLoader.loadPrimitiveArrayClass("[B");
        classLoader.loadPrimitiveArrayClass("[C");
        classLoader.loadPrimitiveArrayClass("[Z");
        classLoader.loadPrimitiveArrayClass("[S");
        classLoader.loadPrimitiveArrayClass("[I");
        classLoader.loadPrimitiveArrayClass("[F");
        classLoader.loadPrimitiveArrayClass("[L");
        classLoader.loadPrimitiveArrayClass("[D");
    }

    private static void initSystemOut(ClassLoader classLoader) {
        Class fdCls = classLoader.loadClass("java/io/FileDescriptor");
        Instance outFdObj = fdCls.newInstance();
        Method fdInitMethod = fdCls.getMethod("<init>", "(I)V");
        Frame f1 = new Frame(fdInitMethod);
        f1.setRef(0, outFdObj);
        f1.setInt(1, 1);
        Interpreter.execute(f1);

        Class fosCls = classLoader.loadClass("java/io/FileOutputStream");
        Instance fosObj = fosCls.newInstance();
        Method fosInitMethod = fosCls.getMethod("<init>", "(Ljava/io/FileDescriptor;)V");
        Frame f2 = new Frame(fosInitMethod);
        f2.setRef(0, fosObj);
        f2.setRef(1, outFdObj);
        Interpreter.execute(f2);

        Class psCls = classLoader.loadClass("java/io/PrintStream");
        Instance psObj = psCls.newInstance();
        Method psInitMethod = psCls.getMethod("<init>", "(Ljava/io/OutputStream;Z)V");
        Frame frame = new Frame(psInitMethod);
        frame.setRef(0, psObj);
        frame.setRef(1, fosObj);
        frame.setInt(2, 1);
        Interpreter.execute(frame);

        Class sysCls = classLoader.loadClass("java/lang/System");
        Field outField = sysCls.getField("out", "Ljava/io/PrintStream;");
        outField.val = UnionSlot.of(psObj);
    }

    private static void initSystemErr(ClassLoader classLoader) {
        Class fdCls = classLoader.loadClass("java/io/FileDescriptor");
        Instance outFdObj = fdCls.newInstance();
        Method fdInitMethod = fdCls.getMethod("<init>", "(I)V");
        Frame f1 = new Frame(fdInitMethod);
        f1.setRef(0, outFdObj);
        f1.setInt(1, 2);
        Interpreter.execute(f1);

        Class fosCls = classLoader.loadClass("java/io/FileOutputStream");
        Instance fosObj = fosCls.newInstance();
        Method fosInitMethod = fosCls.getMethod("<init>", "(Ljava/io/FileDescriptor;)V");
        Frame f2 = new Frame(fosInitMethod);
        f2.setRef(0, fosObj);
        f2.setRef(1, outFdObj);
        Interpreter.execute(f2);

        Class psCls = classLoader.loadClass("java/io/PrintStream");
        Instance psObj = psCls.newInstance();
        Method psInitMethod = psCls.getMethod("<init>", "(Ljava/io/OutputStream;Z)V");
        Frame frame = new Frame(psInitMethod);
        frame.setRef(0, psObj);
        frame.setRef(1, fosObj);
        frame.setInt(2, 1);
        Interpreter.execute(frame);

        Class sysCls = classLoader.loadClass("java/lang/System");
        Field outField = sysCls.getField("err", "Ljava/io/PrintStream;");
        outField.val = UnionSlot.of(psObj);

    }
}
