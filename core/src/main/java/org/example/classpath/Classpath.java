package org.example.classpath;

/**
 * 类路径搜索
 * Java虚拟机规范并没有规定虚拟机应该从哪里寻找类，因此不
 * 同的虚拟机实现可以采用不同的方法
 *。按照搜索的先后顺序，类路径可以
 * 分为以下3个部分：
 * ·启动类路径（bootstrap classpath）
 * ·扩展类路径（extension classpath）
 * ·用户类路径（user classpath）
 */
public abstract class Classpath {
    public static Entry parse(String classpath) {
        return new DirEntry(classpath);
    }
}
