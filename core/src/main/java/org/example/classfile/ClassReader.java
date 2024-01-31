package org.example.classfile;

import org.example.classfile.attribute.Code;
import org.example.classfile.attribute.LineNumberTable;
import org.example.classfile.cp.ClassCp;
import org.example.classfile.cp.MethodDef;
import org.example.classfile.cp.NameAndType;
import org.example.classfile.cp.Utf8;
import org.example.instruction.Instruction;
import org.example.util.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassReader {
    /**
     * 读class文件
     * @param path
     * @return
     * @throws IOException
     */
    public static ClassFile read(String path) throws IOException {

        try (InputStream is = new FileInputStream(path);
             DataInputStream stm = new DataInputStream(is) {
             }) {
            return read(stm);
        }
    }

    /**
     * 解析class文件
     * @param is
     * @return 类结构
     * @throws IOException
     */
    public static ClassFile read(DataInputStream is) throws IOException {
        //读前四个字节为魔数 并返回读取的整数值
        int magic = is.readInt();
        //再读两个字节次版本号
        int minorVersion = is.readUnsignedShort();
        //继续读两个字节为主版本号
        int majorVersion = is.readUnsignedShort();

        //常量池表大小
        int cpSize = is.readUnsignedShort();
        //表头给出的常量池大小比实际大1。假设表头给出的值是n，那么常
        //量池的实际大小是n–1。第二，有效的常量池索引是1~n–1。0是无效索引，表示不指向任何常量
        ConstantPool constantPool = readConstantPool(is, cpSize - 1);

        //类访问标志
        int accessFlag = is.readUnsignedShort();
        //类索引
        int thisClass = is.readUnsignedShort();
        //超类索引
        int superClass = is.readUnsignedShort();
        //接口数
        int interfaceCount = is.readUnsignedShort();
        //解析接口结构类

        //字段数
        int fieldCount = is.readUnsignedShort();
        //解析字段结构 null


        //方法数
        int methodCount = is.readUnsignedShort();
        //解析方法结构
        Methods methods = readMethods(is, methodCount, constantPool);

        //属性数
        int attributeCount = is.readUnsignedShort();
        //解析属性结构
        Attributes attributes = readAttributes(is, attributeCount, constantPool);

        //放回一个类结构对象
        return new ClassFile(
                magic,
                minorVersion,
                majorVersion,
                cpSize,
                constantPool,
                accessFlag,
                thisClass,
                superClass,
                interfaceCount,
                fieldCount,
                methodCount,
                methods,
                attributeCount,
                attributes
        );
    }

    //method_info {
//    u2             access_flags;
//    u2             name_index;
//    u2             descriptor_index;
//    u2             attributes_count;
//    attribute_info attributes[attributes_count];
//    }
    private static Methods readMethods(DataInputStream is, int methodCount,
                                       ConstantPool constantPool) throws IOException {
        Methods methods = new Methods(methodCount);

        for (int i = 0; i < methodCount; i++) {
            int accessFlag = is.readUnsignedShort();
            int nameIndex = is.readUnsignedShort();
            int descriptorIndex = is.readUnsignedShort();
            int attributesCount = is.readUnsignedShort();

            Attributes attributes = readAttributes(is, attributesCount, constantPool);

            ConstantInfo info = constantPool.infos[nameIndex - 1];
            String name = ((Utf8) info).getString();

            String descriptor = ((Utf8) constantPool.infos[descriptorIndex - 1]).getString();

            methods.methodInfos[i] = new MethodInfo(accessFlag, name, new Descriptor(descriptor), attributes);
        }
        return methods;
    }

    private static ConstantPool readConstantPool(DataInputStream is, int cpSize) throws IOException {
        ConstantPool constantPool = new ConstantPool(cpSize);
        for (int i = 0; i < cpSize; i++) {
            int tag = is.readUnsignedByte();

            int infoEnum = tag;
            ConstantInfo info = null;
            switch (infoEnum) {
                case ConstantPoolInfoEnum.CONSTANT_Methodref:
                    info = new MethodDef(infoEnum, is.readUnsignedShort(), is.readUnsignedShort());
                    break;
                case ConstantPoolInfoEnum.CONSTANT_Class:
                    info = new ClassCp(infoEnum, is.readUnsignedShort());
                    break;
                case ConstantPoolInfoEnum.CONSTANT_NameAndType:
                    info = new NameAndType(infoEnum, is.readUnsignedShort(), is.readUnsignedShort());
                    break;
                case ConstantPoolInfoEnum.CONSTANT_Utf8:
                    int length = is.readUnsignedShort();
                    byte[] bytes = Utils.readNBytes(is, length);
                    info = new Utf8(infoEnum, bytes);
                    break;
            }
            constantPool.infos[i] = info;
        }
        return constantPool;
    }

    private static Attributes readAttributes(DataInputStream is, int attributeCount,
                                             ConstantPool constantPool)
            throws IOException {
        Attributes attributes = new Attributes(attributeCount);

        for (int i = 0; i < attributeCount; i++) {
            Attribute attribute = null;
            int attributeNameIndex = is.readUnsignedShort();
            String attributeName = Utils.getString(constantPool, attributeNameIndex);

            int attributeLength = is.readInt();
            switch (attributeName) {
                case AttributeEnum.SourceFile:
                    int sourceFileIndex = is.readUnsignedShort();
                    String file = Utils.getString(constantPool, sourceFileIndex);
                    System.out.println("source file:" + file);
                    break;
                case AttributeEnum.Code:
                    int maxStack = is.readUnsignedShort();
                    int maxLocals = is.readUnsignedShort();

                    int codeLength = is.readInt();
                    byte[] byteCode = Utils.readNBytes(is, codeLength);

                    Instruction[] instructions = readByteCode(byteCode, constantPool);

                    // exceptionTableLength 为空
                    is.readUnsignedShort();

                    //
                    int codeAttributeCount = is.readUnsignedShort();
                    Attributes codeAttributes = readAttributes(is, codeAttributeCount, constantPool);

                    attribute = new Code(maxStack, maxLocals, instructions, codeAttributes);
                    break;
                case AttributeEnum.LineNumberTable:
                    int length = is.readUnsignedShort();
                    LineNumberTable.Line[] lines = new LineNumberTable.Line[length];
                    for (int i1 = 0; i1 < length; i1++) {
                        lines[i1] = new LineNumberTable.Line(is.readUnsignedShort(), is.readUnsignedShort());
                    }
                    attribute = new LineNumberTable(lines);
                    break;
                default:
                    Utils.readNBytes(is, attributeLength);
            }

            attributes.attributes[i] = attribute;
        }

        return attributes;
    }

    public static Instruction[] readByteCode(byte[] byteCode, ConstantPool constantPool)
            throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        try (DataInputStream stm = new DataInputStream(new ByteArrayInputStream(byteCode))) {
            while (stm.available() > 0) {
                int opCode = stm.readUnsignedByte();
                try {
                    Instruction inst = InstructionReader.read(opCode, stm);
                    if (inst == null) {
                        System.out.println(Integer.toHexString(opCode) + " is missing");
                        break;
                    }
                    instructions.add(inst);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Instruction[] ret = new Instruction[instructions.size()];
        instructions.toArray(ret);
        return ret;
    }
}
