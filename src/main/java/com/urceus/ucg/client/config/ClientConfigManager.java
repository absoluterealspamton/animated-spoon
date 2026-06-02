package com.urceus.ucg.client.config;

import com.urceus.ucg.common.config.Config;

public class ClientConfigManager {
    public static void init() {
        Config.init();
        Config.loadClicks();
        Config.loadHistory();
        Config.loadSavedCommands();
        Config.loadColorPresets();
        Config.loadRecentItems();
        Config.loadTemplates();
    }
}