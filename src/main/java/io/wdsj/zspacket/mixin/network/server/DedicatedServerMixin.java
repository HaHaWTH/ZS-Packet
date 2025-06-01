package io.wdsj.zspacket.mixin.network.server;

import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public abstract class DedicatedServerMixin {
    @Inject(method = "getNetworkCompressionThreshold", at = @At("HEAD"), cancellable = true)
    public void getNetworkCompressionThreshold(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(-1);
    }
}
