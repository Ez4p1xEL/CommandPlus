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

    }


}
