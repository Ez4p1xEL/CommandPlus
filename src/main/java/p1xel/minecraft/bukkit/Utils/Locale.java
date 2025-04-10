package p1xel.minecraft.bukkit.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.bukkit.CommandPlus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Locale {

    public static File file;
    public static FileConfiguration yaml;



    public static void createLocaleFile() {

        List<String> lang = Arrays.asList("en","zh_CN","zh_TW");
        for (String l : lang) {
            File file = new File(CommandPlus.getInstance().getDataFolder() + "/lang", l + ".yml");
            YamlConfiguration exist_file = YamlConfiguration.loadConfiguration(file);
            if (!file.exists()) {
                CommandPlus.getInstance().saveResource("lang/" + l + ".yml", false);
            } else {
            InputStreamReader newFile = new InputStreamReader(CommandPlus.getInstance().getResource("lang/"+ l +".yml"), StandardCharsets.UTF_8);
                YamlConfiguration latest_file = YamlConfiguration.loadConfiguration(newFile);

                // Gets all the keys inside the internal file and iterates through all of it's key pairs
                for (String string : latest_file.getKeys(true)) {
                    // Checks if the external file contains the key already.
                    if (!exist_file.contains(string)) {
                        // If it doesn't contain the key, we set the key based off what was found inside the plugin jar
                        exist_file.set(string, latest_file.get(string));
                    }
                }

                try {
                    exist_file.save(file);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }

        upload(new File(CommandPlus.getInstance().getDataFolder() + "/lang", CommandPlus.getInstance().getConfig().getString("language") + ".yml"));
    }

    public static void upload(File locale) {
        file = locale;
        yaml = YamlConfiguration.loadConfiguration(locale);
    }

    public static void set(String path, Object value) {
        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static String getMessage(String path) {
        try {
            return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replaceAll("%prefix%", yaml.getString("plugin-name")));
        } catch (NullPointerException event){
            CommandPlus.getInstance().getLogger().warning("Your language file is not updated to the latest. Please delete it and let it to be re-generated.");
            return "Please update the language file.";
        }
    }

    public static String getCmdMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replaceAll("%prefix%", yaml.getString("commands-plugin-name")));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%prefix%", yaml.getString("plugin-name")));
    }


}
