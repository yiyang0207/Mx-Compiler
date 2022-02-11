package Asm.Instruction;

import Asm.Operand.Imm;
import Asm.Operand.Register;
import Asm.Program.AsmBlock;

import java.util.HashSet;

public class LdInst extends BaseInst {
    public Register address;
    public Imm offset;
    public int size;

    public LdInst(AsmBlock block, Register register, Register address, Imm offset, int size) {
        super(block, register);
        this.address = address;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public HashSet<Register> uses() {
        HashSet<Register> uses = new HashSet<>();
        uses.add(address);
        return uses;
    }

    @Override
    public String toString() {
        String str;
        if (size == 1) str = "lb ";
        else if (size == 2) str = "lh ";
        else str = "lw ";
        return str + register + ", " + offset + "(" + address + ")";
    }
}
