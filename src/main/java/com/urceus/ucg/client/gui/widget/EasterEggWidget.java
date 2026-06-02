package com.urceus.ucg.client.gui.widget;

import com.urceus.ucg.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class EasterEggWidget extends Button {
    private boolean isHovered = false;
    
    public EasterEggWidget(int pX, int pY, int pWidth, int pHeight, Runnable pOnClick) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnClick, DEFAULT_TOOLTIP_APPENDER);
    }
    
    @Override
    protected void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pDeltaTick) {
        super.renderWidget(pPoseStack, pMouseX, pMouseY, pDeltaTick);
        isHovered = pMouseX >= this.x && pMouseX <= this.x + this.width && pMouseY >= this.y && pMouseY <= this.y + this.height;
    }
    
    @Override
    public void onClick() {
        handleVaseClick();
    }
    
    private void handleVaseClick() {
        Config.loadClicks();
        
        // Check if banned
        if (Config.clicks.banExpiry > 0) {
            long currentTime = Minecraft.getInstance().level != null ? 
                Minecraft.getInstance().level.dayTime() : System.currentTimeMillis();
            if (currentTime < Config.clicks.banExpiry) {
                return; // Still banned
            }
        }
        
        Config.clicks.clickCount++;
        Config.saveClicks();
        
        // Check for 100 clicks
        if (Config.clicks.clickCount >= 100) {
            triggerWrath();
            return;
        }
        
        // Show random phrase
        String phrase = getRandomPhrase();
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(phrase));
    }
    
    private void triggerWrath() {
        Config.clicks.banExpiry = (Minecraft.getInstance().level != null ? 
            Minecraft.getInstance().level.dayTime() : System.currentTimeMillis()) + 24000L * 1; // 1 game day
        Config.saveClicks();
        
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Urcei non amant molestos homines."));
        Minecraft.getInstance().setScreen(null);
    }
    
    private String getRandomPhrase() {
        String[] englishPhrases = {
            "The Jugs are watching.",
            "The Jugs remember.",
            "Do not anger the vessels.",
            "Every sip echoes.",
            "Clay remembers your touch.",
            "The Jugs dream of water.",
            "The cracks tell stories.",
            "You are being weighed.",
            "The Jugs know your name.",
            "Patience, mortal.",
            "The kiln has not cooled.",
            "Your thirst is noted.",
            "The Jugs wait.",
            "They speak in drips.",
            "Do not tip the balance.",
            "The handles are warm.",
            "The Jugs have been watching since before you were born.",
            "Shadows inside swirl.",
            "A single drop can break silence.",
            "The Jugs collect moments, not water."
        };
        
        int index = (int) (Math.random() * englishPhrases.length);
        return englishPhrases[index];
    }
}