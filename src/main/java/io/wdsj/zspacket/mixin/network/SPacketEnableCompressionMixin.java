package io.wdsj.zspacket.mixin.network;

import io.wdsj.zspacket.ZSPacket;
import io.wdsj.zspacket.config.Settings;
import io.wdsj.zspacket.duck.PacketCompressionAccessor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.server.SPacketEnableCompression;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(value = SPacketEnableCompression.class, priority = 999)
public abstract class SPacketEnableCompressionMixin implements PacketCompressionAccessor {
    @Shadow private int compressionThreshold;
    @Unique
    private int zsThreshold = Settings.threshold;
    @Unique
    private boolean zsEnableCompressionPacket;

    @Inject(
            method = "readPacketData",
            at = @At("TAIL")
    )
    public void readPacketData(PacketBuffer buf, CallbackInfo ci) {
        try {
            this.zsThreshold = buf.readVarInt();
        } catch (Throwable ignored) {
            this.zsEnableCompressionPacket = false;
            this.zsThreshold = compressionThreshold;
            ZSPacket.LOGGER.warn("ZStandard packet compression is not supported by remote");
        }
        this.zsEnableCompressionPacket = true;
        ZSPacket.LOGGER.info("Remote server supports ZStandard packet compression");
    }

    @Inject(
            method = "writePacketData",
            at = @At("TAIL")
    )
    public void writePacketData(PacketBuffer buf, CallbackInfo ci) {
        buf.writeVarInt(Settings.threshold);
    }

    @Override
    public int getZSCompressionThreshold() {
        return zsThreshold;
    }

    @Override
    public boolean isZSEnableCompressionPacket() {
        return zsEnableCompressionPacket;
    }
}
