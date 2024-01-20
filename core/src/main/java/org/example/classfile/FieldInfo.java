package org.example.classfile;

public class FieldInfo {

    /**
     * 字段访问标识
     */
    public final int accessFlags;
    /**
     * 字段名称索引项
     */
    public final String name;
    /**
     * 字段描述符索引项
     */
    public final Descriptor descriptor;
    /**
     * 属性表
     */
    public final Attributes attributes;

    public FieldInfo(int accessFlags, String name, Descriptor descriptor, Attributes attributes) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
    }
}
