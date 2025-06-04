package io.wdsj.zspacket.util;

import dev.tonimatas.packetfixer.util.Config;
import net.minecraftforge.fml.common.Loader;

public class ModCompatUtils {
    private static final boolean isPacketFixerInstalled = Loader.isModLoaded("packetfixer");

    public static int getDecoderMaxSize() {
        if (isPacketFixerInstalled) {
            return Config.getDecoderSize();
        }
        return 2097152;
    }
}
