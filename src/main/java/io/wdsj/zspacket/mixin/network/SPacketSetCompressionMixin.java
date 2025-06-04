package io.wdsj.zspacket.mixin.network;

import io.wdsj.zspacket.ZSPacket;
import io.wdsj.zspacket.config.Settings;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.server.SPacketEnableCompression;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SPacketEnableCompression.class, priority = 999)
public abstract class SPacketSetCompressionMixin {
    @Shadow private int compressionThreshold;

    @Inject(
            method = "writePacketData",
            at = @At("TAIL")
    )
    public void writePacketData(PacketBuffer buf, CallbackInfo ci) {
        buf.writeVarInt(Settings.threshold);
    }

    @Inject(
            method = "readPacketData",
            at = @At("TAIL")
    )
    public void readPacketData(PacketBuffer buf, CallbackInfo ci) {
        if (compressionThreshold == ZSPacket.IDENTIFIER) {
            try {
                Settings.threshold = buf.readVarInt();
            } catch (Throwable th) {
                ZSPacket.LOGGER.warn("Error occurred while syncing config from server", th);
            }
            ZSPacket.LOGGER.info("ZStandard compression threshold set to {}", Settings.threshold);
        }
    }
}
