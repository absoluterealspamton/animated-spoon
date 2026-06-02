package com.urceus.ucg.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.urceus.ucg.UCG;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File(System.getProperty("minecraft", "."), "config");
    private static final File UCG_DIR = new File(CONFIG_DIR, "ucg");
    
    public static String defaultTarget = "@p";
    public static boolean autoCreative = false;
    public static boolean wrathEnabled = false;
    public static String theme = "vanilla_dark";
    
    public static void init() {
        UCG_DIR.mkdirs();
        load();
    }
    
    public static void load() {
        File configFile = new File(UCG_DIR, "config.json");
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JsonObject obj = GSON.fromJson(reader, JsonObject.class);
                defaultTarget = obj.has("defaultTarget") ? obj.get("defaultTarget").getAsString() : "@p";
                autoCreative = obj.has("autoCreative") ? obj.get("autoCreative").getAsBoolean() : false;
                wrathEnabled = obj.has("wrathEnabled") ? obj.get("wrathEnabled").getAsBoolean() : false;
                theme = obj.has("theme") ? obj.get("theme").getAsString() : "vanilla_dark";
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load config", e);
            }
        }
    }
    
    public static void save() {
        File configFile = new File(UCG_DIR, "config.json");
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("defaultTarget", defaultTarget);
            obj.addProperty("autoCreative", autoCreative);
            obj.addProperty("wrathEnabled", wrathEnabled);
            obj.addProperty("theme", theme);
            GSON.toJson(obj, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save config", e);
        }
    }
    
    public static class ClicksData {
        public int clickCount = 0;
        public long banExpiry = 0;
        public boolean wrathEnabled = false;
    }
    
    public static ClicksData clicks = new ClicksData();
    
    public static void loadClicks() {
        File file = new File(UCG_DIR, "clicks.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                clicks = GSON.fromJson(reader, ClicksData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load clicks data", e);
            }
        }
    }
    
    public static void saveClicks() {
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "clicks.json"))) {
            GSON.toJson(clicks, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save clicks data", e);
        }
    }
    
    public static class HistoryData {
        public List<String> history = new ArrayList<>();
    }
    
    public static HistoryData history = new HistoryData();
    
    public static void loadHistory() {
        File file = new File(UCG_DIR, "history.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                history = GSON.fromJson(reader, HistoryData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load history", e);
            }
        }
    }
    
    public static void saveHistory() {
        if (history.history.size() > 10) {
            history.history = history.history.subList(history.history.size() - 10, history.history.size());
        }
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "history.json"))) {
            GSON.toJson(history, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save history", e);
        }
    }
    
    public static class SavedCommand {
        public String name;
        public String command;
        public boolean favorite = false;
        
        public SavedCommand(String name, String command) {
            this.name = name;
            this.command = command;
        }
    }
    
    public static class SavedCommandsData {
        public List<SavedCommand> commands = new ArrayList<>();
    }
    
    public static SavedCommandsData savedCommands = new SavedCommandsData();
    
    public static void loadSavedCommands() {
        File file = new File(UCG_DIR, "saved_commands.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                savedCommands = GSON.fromJson(reader, SavedCommandsData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load saved commands", e);
            }
        }
    }
    
    public static void saveSavedCommands() {
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "saved_commands.json"))) {
            GSON.toJson(savedCommands, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save saved commands", e);
        }
    }
    
    public static class ColorPreset {
        public String name;
        public String hex;
        
        public ColorPreset(String name, String hex) {
            this.name = name;
            this.hex = hex;
        }
    }
    
    public static class ColorPresetsData {
        public List<ColorPreset> presets = new ArrayList<>();
    }
    
    public static ColorPresetsData colorPresets = new ColorPresetsData();
    
    public static void loadColorPresets() {
        File file = new File(UCG_DIR, "color_presets.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                colorPresets = GSON.fromJson(reader, ColorPresetsData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load color presets", e);
            }
        }
    }
    
    public static void saveColorPresets() {
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "color_presets.json"))) {
            GSON.toJson(colorPresets, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save color presets", e);
        }
    }
    
    public static class RecentItemsData {
        public List<String> recentItems = new ArrayList<>();
    }
    
    public static RecentItemsData recentItems = new RecentItemsData();
    
    public static void loadRecentItems() {
        File file = new File(UCG_DIR, "recent_items.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                recentItems = GSON.fromJson(reader, RecentItemsData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load recent items", e);
            }
        }
    }
    
    public static void saveRecentItems() {
        if (recentItems.recentItems.size() > 10) {
            recentItems.recentItems = recentItems.recentItems.subList(recentItems.recentItems.size() - 10, recentItems.recentItems.size());
        }
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "recent_items.json"))) {
            GSON.toJson(recentItems, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save recent items", e);
        }
    }
    
    public static class Template {
        public String name;
        public String command;
        public List<String> placeholders;
        
        public Template(String name, String command, List<String> placeholders) {
            this.name = name;
            this.command = command;
            this.placeholders = placeholders;
        }
    }
    
    public static class TemplatesData {
        public List<Template> templates = new ArrayList<>();
    }
    
    public static TemplatesData templates = new TemplatesData();
    
    public static void loadTemplates() {
        File file = new File(UCG_DIR, "templates.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                templates = GSON.fromJson(reader, TemplatesData.class);
            } catch (IOException e) {
                UCG.LOGGER.error("Failed to load templates", e);
            }
        }
    }
    
    public static void saveTemplates() {
        try (FileWriter writer = new FileWriter(new File(UCG_DIR, "templates.json"))) {
            GSON.toJson(templates, writer);
        } catch (IOException e) {
            UCG.LOGGER.error("Failed to save templates", e);
        }
    }
}