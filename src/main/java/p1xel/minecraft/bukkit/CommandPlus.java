package p1xel.minecraft.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import p1xel.minecraft.bukkit.Commands.Cmd;
import p1xel.minecraft.bukkit.Utils.GroupManager;
import p1xel.minecraft.bukkit.Utils.Locale;

public class CommandPlus extends JavaPlugin {

    private static CommandPlus instance;
    public static CommandPlus getInstance() { return instance; }

    private static GroupManager groupmanager;
    public static GroupManager getGroupManager() { return groupmanager;}


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        groupmanager = new GroupManager();
        Locale.createLocaleFile();
        groupmanager.initialization();

        getServer().getPluginCommand("CommandPlus").setExecutor(new Cmd());

        int pluginId = 25427;
        new Metrics(this, pluginId);

        // Text from https://tools.miku.ac/taag/ (Font: Slant)
        getLogger().info("   ______                                          ______  __     ");
        getLogger().info("  / ____/___  ____ ___  ____ ___  ____ _____  ____/ / __ \\/ /_  _______");
        getLogger().info(" / /   / __ \\/ __ `__ \\/ __ `__ \\/ __ `/ __ \\/ __  / /_/ / / / / / ___/");
        getLogger().info("/ /___/ /_/ / / / / / / / / / / / /_/ / / / / /_/ / ____/ / /_/ (__  ) ");
        getLogger().info("\\____/\\____/_/ /_/ /_/_/ /_/ /_/\\__,_/_/ /_/\\__,_/_/   /_/\\__,_/____/  ");

    }


}
