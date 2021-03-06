package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.utils.Location;
import org.shouthost.essentials.utils.Warp;

import java.util.List;

public class TeleportCommands extends CommandListener {
    @Commands(name = "warp",
            permission = "essentials.command.warp",
            syntax = "<name>",
            description = "To teleport to a set warp point",
            commandblocks = false,
            console = false)
    public static void warp(Player player, List<String> args) {
        if (args.isEmpty()) {

        }


        player.warpTo(args.get(0));
    }

    @Commands(name = "setwarp",
            permission = "essentials.command.setwarp",
            syntax = "<name>",
            description = "",
            commandblocks = false,
            console = false)
    public static void setwarp(Player player, List<String> args) {
        if (args.isEmpty())
            return;
        Warp warp = new Warp(args.get(0), player.getLocation());
        warp.save();
        player.sendSuccessMessage("Warp '" + args.get(0) + "' have been created");
    }

    @Commands(name = "teleport",
            permission = "essentials.command.teleport",
            syntax = "<name|target> [target] [x] [y] [z]",
            alias = {"tp"},
            disableInProduction = true)
    public static void teleport(Player player, List<String> args) {
        if (args.isEmpty())
            return;
        if (args.size() == 3) {
            int x = Integer.parseInt(args.get(0));
            int y = Integer.parseInt(args.get(1));
            int z = Integer.parseInt(args.get(2));
            player.teleportTo(player.getWorld(), x, y, z);
        }
        if (args.size() == 4) {
            //teleport player to coords
            Player target = getPlayerFromString(args.get(0));
            if (target == null) return;

            int x = Integer.parseInt(args.get(1));
            int y = Integer.parseInt(args.get(2));
            int z = Integer.parseInt(args.get(3));
            target.teleportTo(new Location(target.getWorld(), x, y, z));
        } else if (args.size() == 2) {
            //teleport player -> player
            Player target1 = getPlayerFromString(args.get(0));
            Player target2 = getPlayerFromString(args.get(1));
            //TODO: add checks for both targets to point out who is online/exist
            if (target1 == null || target2 == null) return;
            target1.teleportTo(target2.getLocation());
        } else if (args.size() == 1) {
            player.teleportTo(getPlayerFromString(args.get(0)).getLocation());
        }
    }

    @Commands(name = "tpa",
            permission = "essentials.command.tpa",
            syntax = "<target>",
            disableInProduction = true)
    public static void tpa(Player player, List<String> args) {

    }

    @Commands(name = "tpaccept",
            permission = "essentials.command.tpaccept",
            disableInProduction = true)
    public static void tpaccept(Player player, List<String> args) {

    }

    @Commands(name = "tpreject",
            permission = "essentials.command.tpreject",
            disableInProduction = true)
    public static void tpreject(Player player, List<String> args) {

    }

    @Commands(name = "tpall",
            permission = "essentials.command.tpall",
            syntax = "force",
            disableInProduction = true)
    public static void tpall(Player player, List<String> args) {

    }

    @Commands(name = "warpall",
            permission = "essentials.command.warpall",
            syntax = "force",
            disableInProduction = true)
    public static void warpall(Player player, List<String> args) {

    }


}
