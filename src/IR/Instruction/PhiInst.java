package IR.Instruction;

import IR.Program.IRBlock;
import IR.Operand.BaseOperand;
import IR.Operand.Register;

import java.util.ArrayList;
import java.util.HashSet;

public class PhiInst extends BaseInst {
    public ArrayList<IRBlock> blocks;
    public ArrayList<BaseOperand> values;

    public PhiInst(IRBlock block, Register register, ArrayList<IRBlock> blocks, ArrayList<BaseOperand> values) {
        super(block, register);
        this.blocks = blocks;
        this.values = values;
    }

    @Override
    public String toString() {
        String str = register.toString() + " = phi " + register.type.toString() + " ";
        for (int i = 0; i < values.size(); ++i) {
            if (i > 0) str += ", ";
            str += "[ " + values.get(i).toString() + ", %" + blocks.get(i).name + " ]";
        }
        return str;
    }

    @Override
    public HashSet<BaseOperand> getUses() {
        HashSet<BaseOperand> uses = new HashSet<>();
        uses.addAll(values);
        return uses;
    }

    public void removeBlock(IRBlock block) {
        for (int i = 0; i < blocks.size(); ++i) {
            if (blocks.get(i) == block) {
                blocks.remove(i);
                values.remove(i);
                break;
            }
        }
    }

    @Override
    public void remove(boolean fromBlock) {
        if (fromBlock) block.removeInst(this);
        values.forEach(value -> value.removeUse(this));
    }
}
