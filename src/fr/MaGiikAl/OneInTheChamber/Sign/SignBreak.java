package fr.MaGiikAl.OneInTheChamber.Sign;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class SignBreak implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignBreak(BlockBreakEvent e){

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String name = UtilChatColor.colorizeString(Language.getString("Language.Signs.Name"));

		Player player = e.getPlayer();

		if(e.getBlock().getState() instanceof Sign){
			Sign sign = (Sign) e.getBlock().getState();
			if(sign.getLine(0).equals(name)){
				if(player.hasPermission("oitc.signs")){
					if(!sign.getLine(1).isEmpty()){
						if(player.isSneaking()){
							SignManager.removeSign(sign);
							String succes = Language.getString("Language.Signs.Break");
							UtilSendMessage.sendMessage(player, succes);
						}else{
							e.setCancelled(true);
							String mustSneak = Language.getString("Language.Signs.Must_to_be_sneak");
							UtilSendMessage.sendMessage(player, mustSneak);
						}
					}
				}else{
					e.setCancelled(true);
					String notPerm = Language.getString("Language.Error.Not_permission");
					UtilSendMessage.sendMessage(player, notPerm);
				}
			}
		}if(e.getBlock().getLocation().subtract(1, 0, 0).getBlock().getType() == Material.WALL_SIGN && !(e.getBlock().getState() instanceof Sign)){
			Block block = e.getBlock().getLocation().subtract(1, 0, 0).getBlock();
			if(block.getFace(e.getBlock()).getOppositeFace() == BlockFace.WEST){
				Sign sign = (Sign) block.getState();
				if(sign.getLine(0).equals(name)){
					if(player.hasPermission("oitc.signs")){
						if(!sign.getLine(1).isEmpty()){
							SignManager.removeSign(sign);
							block.breakNaturally();
							String succes = Language.getString("Language.Signs.Break");
							UtilSendMessage.sendMessage(player, succes);
						}
					}else{
						e.setCancelled(true);
						String notPerm = Language.getString("Language.Error.Not_permission");
						UtilSendMessage.sendMessage(player, notPerm);
					}
				}
			}
		}if(e.getBlock().getLocation().add(1, 0, 0).getBlock().getType() == Material.WALL_SIGN && !(e.getBlock().getState() instanceof Sign)){
			Block block = e.getBlock().getLocation().add(1, 0, 0).getBlock();
			if(block.getFace(e.getBlock()).getOppositeFace() == BlockFace.EAST){
				Sign sign = (Sign) block.getState();
				if(sign.getLine(0).equals(name)){
					if(player.hasPermission("oitc.signs")){
						if(!sign.getLine(1).isEmpty()){
							SignManager.removeSign(sign);
							block.breakNaturally();
							String succes = Language.getString("Language.Signs.Break");
							UtilSendMessage.sendMessage(player, succes);
						}
					}else{
						e.setCancelled(true);
						String notPerm = Language.getString("Language.Error.Not_permission");
						UtilSendMessage.sendMessage(player, notPerm);
					}
				}
			}
		}if(e.getBlock().getLocation().subtract(0, 0, 1).getBlock().getType() == Material.WALL_SIGN && !(e.getBlock().getState() instanceof Sign)){
			Block block = e.getBlock().getLocation().subtract(0, 0, 1).getBlock();
			if(block.getFace(e.getBlock()).getOppositeFace() == BlockFace.NORTH){
				Sign sign = (Sign) block.getState();
				if(sign.getLine(0).equals(name)){
					if(player.hasPermission("oitc.signs")){
						if(!sign.getLine(1).isEmpty()){
							SignManager.removeSign(sign);
							block.breakNaturally();
							String succes = Language.getString("Language.Signs.Break");
							UtilSendMessage.sendMessage(player, succes);
						}
					}else{
						e.setCancelled(true);
						String notPerm = Language.getString("Language.Error.Not_permission");
						UtilSendMessage.sendMessage(player, notPerm);
					}
				}
			}
		}
		if(e.getBlock().getLocation().add(0, 0, 1).getBlock().getType() == Material.WALL_SIGN && !(e.getBlock().getState() instanceof Sign)){
			Block block = e.getBlock().getLocation().add(0, 0, 1).getBlock();
			if(block.getFace(e.getBlock()).getOppositeFace() == BlockFace.SOUTH){
				Sign sign = (Sign) block.getState();
				if(sign.getLine(0).equals(name)){
					if(player.hasPermission("oitc.signs")){
						if(!sign.getLine(1).isEmpty()){
							SignManager.removeSign(sign);
							block.breakNaturally();
							String succes = Language.getString("Language.Signs.Break");
							UtilSendMessage.sendMessage(player, succes);
						}
					}else{
						e.setCancelled(true);
						String notPerm = Language.getString("Language.Error.Not_permission");
						UtilSendMessage.sendMessage(player, notPerm);
					}
				}
			}
		}
		if(e.getBlock().getLocation().add(0, 1, 0).getBlock().getType() == Material.SIGN_POST && !(e.getBlock().getState() instanceof Sign)){
			Block block = e.getBlock().getLocation().add(0, 1, 0).getBlock();
			Sign sign = (Sign) block.getState();
			if(sign.getLine(0).equals(name)){
				if(player.hasPermission("oitc.signs")){
					if(!sign.getLine(1).isEmpty()){
						SignManager.removeSign(sign);
						block.breakNaturally();
						String succes = Language.getString("Language.Signs.Break");
						UtilSendMessage.sendMessage(player, succes);
					}
				}else{
					e.setCancelled(true);
					String notPerm = Language.getString("Language.Error.Not_permission");
					UtilSendMessage.sendMessage(player, notPerm);
				}
			}
		}
	}
}