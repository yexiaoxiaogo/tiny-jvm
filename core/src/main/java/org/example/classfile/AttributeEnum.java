package org.example.classfile;

/**
 * jvm的属性表中的枚举类
 */
public abstract class AttributeEnum {
    // Five attributes are critical to correct interpretation of the class file by the Java Virtual Machine:
    public static final String Code = "Code";

    //  Six attributes are not critical to correct interpretation of the class file by either the Java Virtual Machine or the class libraries of the Java SE platform, but are useful for tools:
    public static final String SourceFile = "SourceFile";

    public static final String LineNumberTable = "LineNumberTable";
}
