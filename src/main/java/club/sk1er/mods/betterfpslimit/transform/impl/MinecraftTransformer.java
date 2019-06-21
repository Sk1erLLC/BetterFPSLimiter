package club.sk1er.mods.betterfpslimit.transform.impl;

import club.sk1er.mods.betterfpslimit.transform.FramesTransformer;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public final class MinecraftTransformer implements FramesTransformer {

    @Override
    public String[] getClassNames() {
        return new String[]{"net.minecraft.client.Minecraft"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {

        for (MethodNode method : classNode.methods) {
            String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, method.name, method.desc);

            if (methodName.equals("isFramerateLimitBelowMax") || methodName.equalsIgnoreCase("func_147107_h")) {
                method.instructions.insertBefore(method.instructions.getFirst(), this.getMod());
            }
        }
    }

    private InsnList getMod() {
        InsnList insnList = new InsnList();
        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "club/sk1er/mods/betterfpslimit/BetterFPSLimiter", "enabled", "Z"));
        LabelNode labelNode = new LabelNode();
        insnList.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));
        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "club/sk1er/mods/betterfpslimit/BetterFPSLimiter", "unlimited", "Z"));
        LabelNode labelNode1 = new LabelNode();
        insnList.add(new JumpInsnNode(Opcodes.IFEQ, labelNode1));
        insnList.add(new InsnNode(Opcodes.ICONST_0));
        insnList.add(new InsnNode(Opcodes.IRETURN));
        insnList.add(labelNode1);
        insnList.add(new InsnNode(Opcodes.ICONST_1));
        insnList.add(new InsnNode(Opcodes.IRETURN));
        insnList.add(labelNode);
        return insnList;
    }


}
