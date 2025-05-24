package io.wdsj.zspacket;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = ZSPacket.DEPENDENCY)
public class ZSPacket {
    public static final String DEPENDENCY = "required-after:mixinbooter@[10.1,);required-after:configanytime;";
}
