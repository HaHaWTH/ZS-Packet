package io.wdsj.zspacket.mixin.network;

import io.wdsj.zspacket.ZSPacket;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(
            method = "getNetworkCompressionThreshold",
            at = @At("HEAD"),
            cancellable = true

    )
    public void getNetworkCompressionThreshold(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ZSPacket.IDENTIFIER);
    }
}
