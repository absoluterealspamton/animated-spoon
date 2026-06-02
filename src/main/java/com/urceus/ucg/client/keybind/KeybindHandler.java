package com.urceus.ucg.client.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import com.urceus.ucg.client.gui.screen.UCGScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

public class KeybindHandler {
    public static final RegisterKeyBinding KEY_U = new RegisterKeyBinding(
        "key.ucg.open",
        InputConstants.KEY_U,
        net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent.Register.key(
            InputConstants.KEY_U, 
            InputConstants.KEY_U, 
            false
        )
    );
    
    public static void init() {
        NeoForge.EVENT_BUS.register(KeybindHandler.class);
    }
    
    public static void onKeyInput() {
        if (Minecraft.getInstance().currentScreen == null) {
            Minecraft.getInstance().setScreen(new UCGScreen());
        }
    }
    
    public static class RegisterKeyBinding {
        public final String description;
        public final int keyCode;
        public final net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent.Register register;
        
        public RegisterKeyBinding(String description, int keyCode, net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent.Register register) {
            this.description = description;
            this.keyCode = keyCode;
            this.register = register;
        }
    }
}