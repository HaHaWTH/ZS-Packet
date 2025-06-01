package io.wdsj.zspacket.mixin.network;

import io.wdsj.zspacket.config.Settings;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = NetworkManager.class, priority = 999)
public abstract class NetworkManagerMixin {
}
