package io.wdsj.zspacket;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = ZSPacket.DEPENDENCY)
public class ZSPacket {
    public static final String DEPENDENCY = "required-after:mixinbooter@[10.1,);required-after:configanytime;";
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID);
    public static final int IDENTIFIER = -1;
}
