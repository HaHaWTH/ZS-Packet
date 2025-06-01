package io.wdsj.zspacket.mixin.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.wdsj.zspacket.duck.PacketCompressionAccessor;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.server.network.NetHandlerLoginServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = NetHandlerLoginServer.class, priority = 999)
public abstract class NetHandlerLoginServerMixin {
    @Redirect(
            method = "tryAcceptPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V"
            ),
            allow = 1
    )
    public void tryAcceptPlayer(NetworkManager instance, Packet<?> packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>[] listeners) {
        instance.sendPacket(packetIn, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                instance.setCompressionThreshold(((PacketCompressionAccessor)  packetIn).getZSCompressionThreshold());
            }
        });
    }
}
