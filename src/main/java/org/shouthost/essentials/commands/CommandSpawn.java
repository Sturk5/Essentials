package org.shouthost.essentials.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSpawn extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.spawn";
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
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		if(args.isEmpty()){
			if(!(commandSender instanceof EntityPlayer)){
				commandSender.addChatMessage(new ChatComponentText("You can perform this action"));
				throw new WrongUsageException(getCommandUsage(commandSender));
			}
			Player player = new Player((EntityPlayer) commandSender);

		}
	}

	@Override
	public String getCommandName() {
		return "spawn";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/spawn [<player>]";
	}
}