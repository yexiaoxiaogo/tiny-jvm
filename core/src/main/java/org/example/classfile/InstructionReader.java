package org.example.classfile;

import org.example.classfile.cp.ClassCp;
import org.example.classfile.cp.FloatCp;
import org.example.classfile.cp.IntegerCp;
import org.example.classfile.cp.StringCp;
import org.example.instruction.Instruction;
import org.example.instruction.comparisons.*;
import org.example.instruction.constants.*;
import org.example.instruction.control.AReturnInst;
import org.example.instruction.control.IReturnInst;
import org.example.instruction.control.ReturnInst;
import org.example.instruction.extended.GotoInst;
import org.example.instruction.loads.*;
import org.example.instruction.math.IAddInst;
import org.example.instruction.math.LAddInst;
import org.example.instruction.references.*;
import org.example.instruction.stack.DupInst;
import org.example.instruction.stores.IStore1Inst;
import org.example.instruction.stores.IStore2Inst;
import org.example.instruction.stores.IStore3Inst;
import org.example.instruction.stores.LStore1Inst;
import org.example.util.Utils;

import java.io.IOException;

import static org.example.classfile.ConstantPoolInfoEnum.*;

public abstract class InstructionReader {
    public static Instruction read(int opCode, MyDataInputStream stm, ConstantPool constantPool)
            throws IOException {
        switch (opCode) {
            case 0x3:
                return new IConst0Inst();
            case 0x4:
                return new IConst1Inst();
            case 0x5:
                return new IConst2Inst();
            case 0x9:
                return new Lconst0Inst();
            case 0xa:
                return new Lconst1Inst();

            case 0x10:
                return new BiPushInst(stm.readByte());
            case 0x12:
                int index = stm.readUnsignedByte();
                ConstantInfo info = constantPool.infos[index - 1];
                switch (info.infoEnum) {
                    case CONSTANT_String:
                        int stringIndex = ((StringCp) info).stringIndex;
                        String string = Utils.getString(constantPool, stringIndex);
                        return new LdcInst("Ljava/lang/String", string);
                    case CONSTANT_Integer:
                        return new LdcInst("I", ((IntegerCp) info).val);
                    case CONSTANT_Float:
                        return new LdcInst("F", ((FloatCp) info).val);
                    case CONSTANT_Class:
                        return new LdcInst("L", Utils.getString(constantPool, ((ClassCp) info).nameIndex));
                }
                throw new IllegalStateException();
            case 0x1b:
                return new ILoad1Inst();
            case 0x1c:
                return new ILoad2Inst();
            case 0x1d:
                return new ILoad3Inst();
            case 0x1f:
                return new LLoad1Inst();

            case 0x2a:
                return new ALoad0Inst();
            case 0x2b:
                return new ALoad1Inst();

            case 0x3c:
                return new IStore1Inst();
            case 0x3d:
                return new IStore2Inst();
            case 0x3e:
                return new IStore3Inst();

            case 0x40:
                return new LStore1Inst();

            case 0x59:
                return new DupInst();

            case 0x60:
                return new IAddInst();
            case 0x61:
                return new LAddInst();

            case 0x94:
                return new LCmpInst();
            case 0x9b:
                return new IfLtInst(stm.readShort());
            case 0x9c:
                return new IfGeInst(stm.readShort());
            case 0x9e:
                return new IfLeInst(stm.readShort());

            case 0xa4:
                return new IfICmpLeInst(stm.readShort());
            case 0xa6:
                return new IfACmpNeInst(stm.readShort());
            case 0xa7:
                return new GotoInst(stm.readShort());
            case 0xac:
                return new IReturnInst();

            case 0xb0:
                return new AReturnInst();
            case 0xb1:
                return new ReturnInst();
            case 0xb6:
                int ivIndex = stm.readUnsignedShort();
                return new InvokeVirtualInst(
                        Utils.getClassNameByMethodDefIdx(constantPool, ivIndex),
                        Utils.getMethodNameByMethodDefIdx(constantPool, ivIndex),
                        Utils.getMethodTypeByMethodDefIdx(constantPool, ivIndex)
                );
            case 0xb7:
                int isIndex = stm.readUnsignedShort();
                return new InvokeSpecialInst(
                        Utils.getClassNameByMethodDefIdx(constantPool, isIndex),
                        Utils.getMethodNameByMethodDefIdx(constantPool, isIndex),
                        Utils.getMethodTypeByMethodDefIdx(constantPool, isIndex)
                );
            case 0xb8:
                int mdIdx = stm.readUnsignedShort();
                return new InvokeStaticInst(
                        Utils.getClassNameByMethodDefIdx(constantPool, mdIdx),
                        Utils.getMethodNameByMethodDefIdx(constantPool, mdIdx),
                        Utils.getMethodTypeByMethodDefIdx(constantPool, mdIdx)
                );
            case 0xbb:
                return new NewInst(Utils.getClassName(constantPool, stm.readUnsignedShort()));
            case 0xbf:
                return new AThrowInst();

            case 0xc9:
                // jsr_w, 同 jsr, 忽略
                throw new UnsupportedOperationException();
            default:
                return null;
        }
    }

}
