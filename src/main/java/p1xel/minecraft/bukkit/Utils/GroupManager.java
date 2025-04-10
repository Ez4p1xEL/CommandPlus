package p1xel.minecraft.bukkit.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.bukkit.CommandPlus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    private File file;
    private FileConfiguration yaml;
    private List<String> groups;

    public void initialization() {

        File file = new File(CommandPlus.getInstance().getDataFolder(), "groups.yml");

        if (!file.exists()) {
            CommandPlus.getInstance().saveResource("groups.yml", false);

        }
        upload(file, YamlConfiguration.loadConfiguration(file));

        groups = new ArrayList<>();
        groups.addAll(yaml.getKeys(false));

    }

    public void upload(File f, FileConfiguration y) {
        this.file = f;
        this.yaml = y;
    }

    public FileConfiguration get() {
        return yaml;
    }

    public void set(String path, Object value) {
        yaml.set(path, value);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getGroupMembers(String group) {
        return yaml.getStringList(group);
    }

    public void addMember(String group, String name) {
        List<String> list = yaml.getStringList(group);
        list.add(name);
        set(group, list);
    }

    public void removeMember(String group, String name) {
        List<String> list = yaml.getStringList(group);
        list.remove(name);
        set(group,list);
    }





}
