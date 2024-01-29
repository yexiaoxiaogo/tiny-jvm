package org.example.classfile;

import org.example.instruction.EmptyInstruction;
import org.example.instruction.Instruction;
import org.example.instruction.constants.BiPushInst;
import org.example.instruction.control.ReturnInst;
import org.example.instruction.loads.ALoad0Inst;
import org.example.instruction.loads.ILoad1Inst;
import org.example.instruction.loads.ILoad2Inst;
import org.example.instruction.math.IAddInst;
import org.example.instruction.stores.IStore1Inst;
import org.example.instruction.stores.IStore2Inst;
import org.example.instruction.stores.IStore3Inst;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class InstructionReader {
    public static Instruction read(int opCode, DataInputStream stm, ConstantPool constantPool)
            throws IOException {
        switch (opCode) {
            case 0x10:
                return new BiPushInst(stm.readByte());

            case 0x1b:
                return new ILoad1Inst();
            case 0x1c:
                return new ILoad2Inst();

            case 0x2a:
                return new ALoad0Inst();

            case 0x3c:
                return new IStore1Inst();
            case 0x3d:
                return new IStore2Inst();
            case 0x3e:
                return new IStore3Inst();

            case 0x60:
                return new IAddInst();


            case 0xb1:
                return new ReturnInst();

            case 0xb7:
                // classfile中b7读走消费
                stm.readUnsignedShort();
                return new EmptyInstruction();
            default:
                return null;
        }
    }

}
