package club.sk1er.mods.betterfpslimit;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class BetterFPSLimiterCommand extends CommandBase {
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "fpslimit";
    }

    private String prefix = EnumChatFormatting.RED + "[" + EnumChatFormatting.AQUA + "Better FPS Limiter" + EnumChatFormatting.RED + "] " + EnumChatFormatting.WHITE;
    private String usage = prefix + "/fpslimit <toggle,unlimited,limit>";

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            sender.addChatMessage(new ChatComponentText(usage));
        } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("toggle")) {
                BetterFPSLimiter.enabled = !BetterFPSLimiter.enabled;
                sender.addChatMessage(new ChatComponentText(prefix + "Toggled " + (BetterFPSLimiter.enabled ? "On" : "Off")));
            } else if (arg.equalsIgnoreCase("unlimited")) {
                BetterFPSLimiter.unlimited = !BetterFPSLimiter.unlimited;
                sender.addChatMessage(new ChatComponentText(prefix + "Set FPS to " + (BetterFPSLimiter.unlimited ? "Unlimited" : "Limited")));
            } else if (NumberUtils.isNumber(arg)) {
                Minecraft.getMinecraft().gameSettings.limitFramerate = Integer.parseInt(arg);
                sender.addChatMessage(new ChatComponentText(prefix + "Set Max FPS to " + arg));
            } else {
                sender.addChatMessage(new ChatComponentText(prefix + "Invalid Number: '" + arg + "'"));
            }
        }
    }
}
