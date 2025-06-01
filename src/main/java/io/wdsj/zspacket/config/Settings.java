package io.wdsj.zspacket.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.wdsj.zspacket.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public class Settings {

    @Config.Comment("ZStandard compression level")
    @Config.RequiresMcRestart
    public static int compressionLevel = 6;

    @Config.Comment("ZStandard compression threshold")
    @Config.RequiresMcRestart
    public static int threshold = 256;

    static {
        ConfigAnytime.register(Settings.class);
    }
}
