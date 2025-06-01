package io.wdsj.zspacket.mixin.network;

import net.minecraft.server.network.NetHandlerLoginServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = NetHandlerLoginServer.class, priority = 999)
public abstract class NetHandlerLoginServerMixin {
    @ModifyConstant(
            method = "tryAcceptPlayer",
            constant = @Constant(expandZeroConditions = Constant.Condition.GREATER_THAN_OR_EQUAL_TO_ZERO),
            require = 1
    )
    public int tryAcceptPlayer(int original) {
        return Integer.MIN_VALUE;
    }
}
