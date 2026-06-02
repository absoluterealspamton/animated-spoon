package com.urceus.ucg.client.gui.screen;

import com.mojang.blaze3d.platform.GLFW;
import com.mojang.blaze3d.vertex.PoseStack;
import com.urceus.ucg.client.gui.widget.EasterEggWidget;
import com.urceus.ucg.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Font;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class UCGScreen extends Screen {
    private static final Component TITLE = Component.literal("[Urceus] Command Generator").withStyle(net.minecraft.ChatFormatting.GOLD);
    
    private int selectedTab = 0;
    private float vaseRotation = 0.0f;
    
    protected UCGScreen() {
        super(Component.literal("UCG"));
    }
    
    @Override
    protected void init() {
        super.init();
        Config.init();
        Config.loadClicks();
        Config.loadHistory();
        Config.loadSavedCommands();
        Config.loadColorPresets();
        Config.loadRecentItems();
        Config.loadTemplates();
        
        int y = 20;
        this.addRenderableWidget(Button.builder(Component.literal("Command Generator"), (b) -> selectedTab = 0).position(10, y).size(120, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("QoL"), (b) -> selectedTab = 1).position(140, y).size(120, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Settings"), (b) -> selectedTab = 2).position(270, y).size(120, 20).build());
        
        y += 50;
        this.addRenderableWidget(new EasterEggWidget(this.width / 2 - 10, y, 20, 20, () -> {}));
    }
    
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pDeltaTick) {
        this.renderBackground(pPoseStack, pDeltaTick);
        super.render(pPoseStack, pMouseX, pMouseY, pDeltaTick);
        
        Font font = this.font;
        
        drawCenteredText(pPoseStack, font, TITLE, this.width / 2, 8, 0xFFFFFF);
        
        vaseRotation += pDeltaTick * 20.0f;
        renderVase(pPoseStack, pDeltaTick);
    }
    
    private void renderVase(PoseStack pPoseStack, float pDeltaTick) {
        int x = this.width / 2 - 10;
        int y = 50;
        int size = 20;
        
        pPoseStack.pushPose();
        pPoseStack.translate(x + size / 2.0f, y + size / 2.0f, 0);
        
        // Apply rotation
        pPoseStack.rotate((float) Math.toRadians(vaseRotation), 0, 1, 0);
        
        // Draw the decorated_pot texture
        try {
            ResourceLocation texture = ResourceLocation.parse("minecraft:block/decorated_pot");
            this.minecraft.getTextureManager().bind(texture);
            fillRoundRect(pPoseStack, -size/2, -size/2, size, size, 0xFFFFFFFF, 255);
        } catch (Exception e) {
            // Fallback: draw a simple rectangle
            fillRoundRect(pPoseStack, -size/2, -size/2, size, size, 0xFFD4AF37, 255);
        }
        
        pPoseStack.popPose();
    }
    
    private void fillRoundRect(PoseStack pPoseStack, int pX, int pY, int pWidth, int pHeight, int pColor, int pAlpha) {
        fill(pPoseStack, pX, pY, pX + pWidth, pY + pHeight, pColor | (pAlpha << 24));
    }
    
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.setScreen(null);
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    
    @Override
    public void onClose() {
        super.onClose();
        Config.save();
        Config.saveClicks();
        Config.saveHistory();
        Config.saveSavedCommands();
        Config.saveColorPresets();
        Config.saveRecentItems();
        Config.saveTemplates();
    }
}