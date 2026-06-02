package com.urceus.ucg.client.config;

import com.urceus.ucg.common.config.Config;

public class ClientConfig {
    public static String getDefaultTarget() {
        return Config.defaultTarget;
    }
    
    public static void setDefaultTarget(String target) {
        Config.defaultTarget = target;
        Config.save();
    }
    
    public static boolean isAutoCreative() {
        return Config.autoCreative;
    }
    
    public static void setAutoCreative(boolean autoCreative) {
        Config.autoCreative = autoCreative;
        Config.save();
    }
    
    public static boolean isWrathEnabled() {
        return Config.wrathEnabled;
    }
    
    public static void setWrathEnabled(boolean wrathEnabled) {
        Config.wrathEnabled = wrathEnabled;
        Config.save();
        Config.saveClicks();
    }
    
    public static String getTheme() {
        return Config.theme;
    }
    
    public static void setTheme(String theme) {
        Config.theme = theme;
        Config.save();
    }
}