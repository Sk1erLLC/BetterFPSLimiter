package club.sk1er.mods.betterfpslimit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

@Mod(modid = BetterFPSLimiter.MOD_ID, name = BetterFPSLimiter.MOD_NAME, version = BetterFPSLimiter.MOD_VERSION)
public class BetterFPSLimiter {

    public static final String MOD_ID = "better_fps_limiter";
    public static final String MOD_VERSION = "1.0";
    public static final String MOD_NAME = "Better FPS Limiter";
    public static boolean enabled = true;
    public static boolean unlimited = true;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new BetterFPSLimiterCommand());

        try {
            if (event.getSuggestedConfigurationFile().exists()) {
                JsonObject asJsonObject = new JsonParser().parse(FileUtils.readFileToString(event.getSuggestedConfigurationFile())).getAsJsonObject();
                enabled = asJsonObject.has("enabled") && asJsonObject.get("enabled").getAsBoolean();
                unlimited = asJsonObject.has("unlimited") && asJsonObject.get("unlimited").getAsBoolean();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("enabled", enabled);
            jsonObject.addProperty("unlimited", unlimited);
            try {
                FileUtils.write(event.getSuggestedConfigurationFile(), jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
