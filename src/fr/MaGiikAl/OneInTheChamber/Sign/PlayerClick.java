package fr.MaGiikAl.OneInTheChamber.Sign;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class PlayerClick implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){

		Player player = e.getPlayer();

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String name1 = UtilChatColor.colorizeString(Language.getString("Language.Signs.Name_lives"));
		String name2 = UtilChatColor.colorizeString(Language.getString("Language.Signs.Name_points"));

		if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		if(!(e.getClickedBlock().getState() instanceof Sign)){
			return;
		}

		Sign sign = (Sign)e.getClickedBlock().getState();

		if(sign.getLine(0).equals(name1) || sign.getLine(0).equals(name2)){
			if(!sign.getLine(1).isEmpty()){
				String dislayName = ChatColor.stripColor(sign.getLine(1));
				if(player.hasPermission("oitc.join.signs")){
					for(Arena arena : ArenaManager.getArenaManager().getArenas()){
						if(dislayName.contains(arena.getDisplayName())){
							
							arena.addPlayer(player);
							e.setCancelled(true);
						}
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
