package com.urceus.ucg;

import com.urceus.ucg.client.GuiEventHandler;
import com.urceus.ucg.client.config.ClientConfigManager;
import com.urceus.ucg.client.gui.screen.UCGScreen;
import com.urceus.ucg.client.keybind.KeybindHandler;
import com.urceus.ucg.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod("ucg")
public class UCG {
    
    public static final String MOD_ID = "ucg";
    public static final ResourceLocation VASE_TEXTURE = ResourceLocation.parse("minecraft:block/decorated_pot");
    
    public UCG() {
        NeoForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        NetworkManager.init();
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeybindHandler.KEY_U);
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onScreenEvent(ScreenEvent.Open event) {
        if (event.getScreen() instanceof UCGScreen) {
            GuiEventHandler.onScreenOpen((UCGScreen) event.getScreen());
        }
    }
}