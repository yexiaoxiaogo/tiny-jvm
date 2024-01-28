package org.example;

import org.example.classpath.Entry;
import org.example.rtda.heap.*;
import org.example.rtda.heap.Class;
import org.example.classpath.Classpath;
import org.example.classloader.ClassLoader;
import org.example.rtda.Thread;
import org.example.rtda.MetaSpace;
import org.example.interpret.Interpreter;

/**
 * java虚拟机运行
 */
public class VirtualMachine {
    public void run(Args cmd) {

        Entry entry = Classpath.parse(cmd.classpath);
        ClassLoader classLoader = new ClassLoader("boot", entry);

        // 初始化
        MetaSpace.main = new Thread(1024);

        // 加载主类
        String mainClass = cmd.clazz;
        classLoader.loadClass(mainClass);

        // 查找主函数
        Class clazz = Heap.findClass(mainClass);
        Method method = clazz.getMainMethod();

        //运行主函数
        Interpreter.runMain(method);
    }
}
