package io.wdsj.zspacket;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@IFMLLoadingPlugin.Name("ZSPacketPlugin")
public class ZSPacketPlugin implements IEarlyMixinLoader, IFMLLoadingPlugin {
    private static final Map<String, Supplier<Boolean>> commonMixinConfigs = ImmutableMap.copyOf(new LinkedHashMap<String, Supplier<Boolean>>()
    {
        {
            put("mixins.zspacket.json", () -> true);
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(commonMixinConfigs.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        Supplier<Boolean> commonSupplier = commonMixinConfigs.get(mixinConfig);
        if (commonSupplier != null) {
            return commonSupplier.get();
        }
        return true;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
