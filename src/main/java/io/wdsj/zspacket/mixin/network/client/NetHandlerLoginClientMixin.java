package io.wdsj.zspacket.mixin.network.client;

import io.wdsj.zspacket.duck.PacketCompressionAccessor;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.login.server.SPacketEnableCompression;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetHandlerLoginClient.class)
public class NetHandlerLoginClientMixin {

    @Redirect(
            method = "handleEnableCompression",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/login/server/SPacketEnableCompression;getCompressionThreshold()I"
            )
    )
    public int handleEnableCompression(SPacketEnableCompression instance) {
        PacketCompressionAccessor packet = ((PacketCompressionAccessor) instance);
        return packet.isZSEnableCompressionPacket() ? packet.getZSCompressionThreshold() : instance.getCompressionThreshold();
    }
}
