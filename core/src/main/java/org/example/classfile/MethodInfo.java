package org.example.classfile;
import org.example.classfile.attribute.Code;
import org.example.classfile.attribute.LineNumberTable;

public class MethodInfo {

    /**
     *方法访问标识
     */
    public final int accessFlags;
    /**
     *方法名索引项
     */
    public final String name;
    /**
     *方法描述索引项
     */
    public final Descriptor descriptor;
    /**
     * 属性表
     */
    public final Attributes attributes;

    public MethodInfo(int accessFlags, String name, Descriptor descriptor, Attributes attributes) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
    }

    public Code getCode() {
        for (Attribute attribute : attributes.attributes) {
            if (attribute instanceof Code) {
                return ((Code) attribute);
            }
        }
        return null;
    }

    public LineNumberTable getLineNumber() {
        if (this.getCode() == null) {
            return null;
        }
        for (Attribute attribute : this.getCode().attributes.attributes) {
            if (attribute instanceof LineNumberTable) {
                return ((LineNumberTable) attribute);
            }
        }
        return null;
    }
}
