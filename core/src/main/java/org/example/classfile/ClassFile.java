package org.example.classfile;

public class ClassFile {

    public final int magic;//魔数,确认是否能让虚拟机接受的class文件 在java中 通常为0xCAFEBABE（咖啡宝贝？）
    public final int minorVersion;//jvm的次版本号
    public final int majorVersion;//jvm的主版本号
    public final int constantPoolSize;//常量池大小(常量池常量个数) 用来解析后面的常量池
    public final ConstantPool cpInfo;//常量池结构
    public final int accessFlags;//类访问标志,16位的“bitmask”指出class文件定义的是类还是接口，访问级别是public还是private，
    public final int thisClass;//类索引,class文件存储的类名类似完全限定名
    public final int superClass;//父类索引,同上
    public final int interfacesCount;//接口索引数量
    public final int fieldCount;//字段数
    public final int methodsCount;//方法数
    public final Methods methods;//方法结构，存储方法信息
    public final int attributesCount;//属性数
    public final Attributes attributes;//属性结构，存储属性信息

    public ClassFile(int magic, int minorVersion, int majorVersion, int constantPoolSize,
                     ConstantPool cpInfo, int accessFlags, int thisClass, int superClass, int interfacesCount,
                     int fieldCount, int methodsCount, Methods methods, int attributesCount,
                     Attributes attributes) {
        this.magic = magic;
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPoolSize = constantPoolSize;
        this.cpInfo = cpInfo;
        this.accessFlags = accessFlags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfacesCount = interfacesCount;
        this.fieldCount = fieldCount;
        this.methodsCount = methodsCount;
        this.methods = methods;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "ClassFile{" +
                "\n, magic=" + magic +
                "\n, minorVersion=" + minorVersion +
                "\n, majorVersion=" + majorVersion +
                "\n, constantPoolSize=" + constantPoolSize +
                "\n, cpInfo=" + cpInfo +
                "\n, accessFlags=" + accessFlags +
                "\n, thisClass=" + thisClass +
                "\n, superClass=" + superClass +
                "\n, interfacesCount=" + interfacesCount +
                "\n, fieldCount=" + fieldCount +
                "\n, methodsCount=" + methodsCount +
                "\n, methods=" + methods +
                "\n, attributesCount=" + attributesCount +
                "\n, attributes=" + attributes +
                "\n}";
    }
}
