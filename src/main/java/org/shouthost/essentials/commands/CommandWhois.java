package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandWhois extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.whois";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return true;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/whois <player>";
    }

    @Override
    public void processCommand(Player player, List<String> args) {

    }

    @Override
    public String getCommandName() {
        return "whois";
    }
}
