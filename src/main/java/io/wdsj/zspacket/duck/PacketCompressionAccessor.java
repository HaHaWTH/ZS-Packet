package io.wdsj.zspacket.duck;

public interface PacketCompressionAccessor {
    int getZSCompressionThreshold();
    boolean isZSEnableCompressionPacket();
}
