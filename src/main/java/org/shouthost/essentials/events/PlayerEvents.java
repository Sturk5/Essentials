package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.shouthost.essentials.commands.CommandListener;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.json.players.PowerTools;
import scala.actors.threadpool.Arrays;

import java.util.List;


public class PlayerEvents {
    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onNameFormatEvent(NameFormat event) {
        //This event is used to format the player name. This will get moved to its own module in the future
        Player player = CommandListener.getPlayerFromString(event.username);
        if (player == null) return;
        event.displayname = String.format("%s%s%s", player.getPrefix().replaceAll("&", "§"), player.getNickname().replaceAll("&", "§"), player.getSuffix().replaceAll("&", "§"));
        //event.entityPlayer.refreshDisplayName();
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        Player player = CommandListener.getPlayerFromString(event.username);
        if (player == null) return;
        if (player.isMuted()) {
            player.sendMessage(EnumChatFormatting.RED + "You are muted for: " + EnumChatFormatting.YELLOW + player.getMuteReason());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLogin(PlayerLoggedInEvent event) {
        if (Essentials.playerList.getIfPresent(event.player.getPersistentID()) == null) {
            Essentials.playerList.put(event.player.getPersistentID(), CommandListener.getPlayerFromString(event.player.getDisplayName()));
            event.player.refreshDisplayName();
        }
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (Essentials.playerList.getIfPresent(event.player.getPersistentID()) == null) {
            Player player = Essentials.playerList.getIfPresent(event.player.getPersistentID());
            player.updateCoords(player.getLocation());
            player.save();
            Essentials.playerList.invalidate(event.player.getPersistentID());
            Essentials.playersList.invalidate(event.player.getPersistentID());
        }
    }

    //FIXME: Fix hanging when checking for powertool
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void powerToolsCheck(PlayerInteractEvent event) {
	    if (Essentials.debug && !event.world.isRemote) {
		    EntityPlayerMP pl = (EntityPlayerMP) event.entityPlayer;
            Player player = new Player(pl);
            PlayerInteractEvent.Action action = event.action;
            if ((action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && pl.getCurrentEquippedItem() != null) {
                if (player.doesPowerToolExist(pl.getCurrentEquippedItem())) {
                    PowerTools pt = player.getPowerTool(pl.getCurrentEquippedItem());
                    if (pl.canCommandSenderUseCommand(0, pt.getCommand())) {
                        player.exec(pt.getCommand());
                    }
                }

            }
        }
    }

	@SubscribeEvent()
	public void signShopCheck(PlayerInteractEvent event) {
        if (Essentials.debug && !event.world.isRemote) {
            World world = event.world;
            int x = event.x;
            int y = event.y;
            int z = event.z;
            Block block = world.getBlock(x, y, z);

            if ((block == Blocks.standing_sign || block == Blocks.wall_sign) && block instanceof BlockSign) {
                TileEntitySign sign = (TileEntitySign) world.getTileEntity(x, y, z);
                List<String> text = Arrays.asList(sign.signText);
                if (text.get(0) == "SignShop") {
                    Item item = (Item) Item.itemRegistry.getObject(text.get(1));
                    Player player = CommandListener.getPlayerFromEntity((EntityPlayerMP) event.entityPlayer);
                    int amount = Integer.parseInt(text.get(2));
                    int cost = Integer.parseInt(text.get(3));
                    int sellPrice = Integer.parseInt(text.get(4));

                    //TODO: Rework this check
                    Player seller = CommandListener.getPlayerFromString(text.get(5));//TODO:

                    if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                        if (player.getBalance() >= cost) {
                            //TODO:See if the player has room in inventory for items being bought.
                            player.setBalance(player.getBalance() - cost);
                            player.giveItem(new ItemStack(item, amount));
                            player.sendSuccessMessage("Succesfully bought " + amount + " " + item.getItemStackDisplayName(new ItemStack(item)) + " for " + cost + ".");
                        } else {
                            player.sendErrorMessage("Invalid funds.");
                        }
                    } else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        //TODO:Check to see if the player has what the shops buying.
                        seller.setBalance(seller.getBalance() - sellPrice);
                        player.setBalance(player.getBalance() + sellPrice);
                        player.sendSuccessMessage("Succesfully sold " + amount + " " + item.getItemStackDisplayName(new ItemStack(item)) + " for " + sellPrice + ".");
                    }
                }
            }
        }
    }
}