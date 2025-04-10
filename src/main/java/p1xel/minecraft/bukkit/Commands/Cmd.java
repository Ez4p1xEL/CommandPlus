package p1xel.minecraft.bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p1xel.minecraft.bukkit.CommandPlus;
import p1xel.minecraft.bukkit.Utils.Locale;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

public class Cmd implements CommandExecutor {


    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("commandplus.use")) {
            sender.sendMessage(Locale.getMessage("no-perm"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Locale.getMessage("commands.help"));
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {
                for (String path : Locale.yaml.getConfigurationSection("commands").getKeys(false)) {
                    sender.sendMessage(Locale.getMessage("commands." + path));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                CommandPlus.getInstance().reloadConfig();
                CommandPlus.getGroupManager().initialization();
                Locale.createLocaleFile();
                sender.sendMessage(Locale.getMessage("reload"));
                return true;
            }
        }

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("group") && args[2].equalsIgnoreCase("list")) {

                String group = args[1];

                if (!CommandPlus.getGroupManager().getGroups().contains(group)) {
                    sender.sendMessage(Locale.getMessage("group-not-exist").replaceAll("%group%", group));
                    return true;
                }

                sender.sendMessage(Locale.getMessage("list").replaceAll("%group%", group).replaceAll("%players%", CommandPlus.getGroupManager().getGroupMembers(group).toString()));
                return true;

            }

        }

        if (args.length == 4) {

            if (args[0].equalsIgnoreCase("group") && args[2].equalsIgnoreCase("add")) {

                String group = args[1];

                if (!CommandPlus.getGroupManager().getGroups().contains(group)) {
                    sender.sendMessage(Locale.getMessage("group-not-exist").replaceAll("%group%", group));
                    return true;
                }

                String name = args[3];

                OfflinePlayer player = Bukkit.getOfflinePlayer(name);
                if (!player.hasPlayedBefore()) {
                    sender.sendMessage(Locale.getMessage("player-not-online").replaceAll("%player%", name));
                    return true;
                }


                if (CommandPlus.getGroupManager().getGroupMembers(group).contains(name)) {
                    sender.sendMessage(Locale.getMessage("player-already-exist").replaceAll("%group%", group).replaceAll("%player%", name));
                    return true;
                }

                CommandPlus.getGroupManager().addMember(group, name);
                sender.sendMessage(Locale.getMessage("add").replaceAll("%player%", name).replaceAll("%group%", group));
                return true;

            }

            if (args[0].equalsIgnoreCase("group") && args[2].equalsIgnoreCase("remove")) {

                String group = args[1];

                if (!CommandPlus.getGroupManager().getGroups().contains(group)) {
                    sender.sendMessage(Locale.getMessage("group-not-exist").replaceAll("%group%", group));
                    return true;
                }

                String name = args[3];

                OfflinePlayer player = Bukkit.getOfflinePlayer(name);
                if (!player.hasPlayedBefore()) {
                    sender.sendMessage(Locale.getMessage("player-not-online").replaceAll("%player%", name));
                    return true;
                }

                if (CommandPlus.getGroupManager().getGroupMembers(group).contains(name)) {
                    sender.sendMessage(Locale.getMessage("player-not-exist").replaceAll("%group%", group).replaceAll("%player%", name));
                    return true;
                }

                CommandPlus.getGroupManager().removeMember(group, name);
                sender.sendMessage(Locale.getMessage("remove").replaceAll("%player%", name).replaceAll("%group%", group));
                return true;

            }

        }


        if (args.length >= 3) {

            if (args[0].equalsIgnoreCase("send")) {
                if (args[1].equalsIgnoreCase("all")) {

                    // /commandplus send all <command>
                    String raw_cmd = Arrays.toString(Arrays.copyOfRange(args, 2, args.length));
                    raw_cmd = raw_cmd.substring(1, raw_cmd.length() - 1).replaceAll(",", "");

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        String name = player.getName();
                        String cmd_cvt = raw_cmd.replaceAll("%player%", name);
                        System.out.println(cmd_cvt);
                        Bukkit.getServer().dispatchCommand(CommandPlus.getInstance().getServer().getConsoleSender(), cmd_cvt);

                    }

                    sender.sendMessage(Locale.getMessage("all"));
                    return true;
                }
            }

            if (args.length >= 4) {

                if (args[0].equalsIgnoreCase("send")) {
                    if (args[1].equalsIgnoreCase("group")) {

                        String group = args[2];

                        if (!CommandPlus.getGroupManager().getGroups().contains(group)) {
                            sender.sendMessage(Locale.getMessage("group-not-exist"));
                            return true;
                        }
                        // /commandplus send group <group> <command>
                        String raw_cmd = Arrays.toString(Arrays.copyOfRange(args, 3, args.length));
                        raw_cmd = raw_cmd.substring(1, raw_cmd.length() - 1).replaceAll(",", "");

                        for (String name : CommandPlus.getGroupManager().getGroupMembers(group)) {

                            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
                            if (!player.hasPlayedBefore()) {
                                continue;
                            }
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), raw_cmd.replaceAll("%player%", name));

                        }

                        sender.sendMessage(Locale.getMessage("group").replaceAll("%group%", group));
                        return true;
                    }
                }

            }

        }












        sender.sendMessage(Locale.getMessage("wrong-arg"));
        return true;
    }

}
