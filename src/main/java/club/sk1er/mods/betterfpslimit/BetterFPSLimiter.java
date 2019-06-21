package club.sk1er.mods.betterfpslimit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.Collections;

@Mod(modid = BetterFPSLimiter.MOD_ID, name = BetterFPSLimiter.MOD_NAME, version = BetterFPSLimiter.MOD_VERSION, clientSideOnly = true)
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
