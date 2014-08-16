package fr.MaGiikAl.OneInTheChamber.Sign;

import java.io.File;

import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class SignChange implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent e){
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		Player player = e.getPlayer();

		if(e.getBlock().getState() instanceof Sign){
			if(!e.getLine(0).equalsIgnoreCase("[OITC]")) return;
			if(player.hasPermission("oitc.signs")){
				if(!e.getLine(1).isEmpty()){
					final String arenaName = e.getLine(1);
					if(ArenaManager.getArenaManager().exists(arenaName)){
						Sign sign = (Sign) e.getBlock().getState();
						sign.update();
						SignManager.addSign(ArenaManager.getArenaManager().getArenaByName(arenaName), sign);
						String SignSet = UtilChatColor.colorizeString(Language.getString("Language.Signs.Succesfully_set"));
						UtilSendMessage.sendMessage(player, SignSet);

						new BukkitRunnable() {
							@Override
							public void run() {
								SignManager.updateSigns(ArenaManager.getArenaManager().getArenaByName(arenaName));
							}
						}.runTaskLater(OneInTheChamber.instance, 2L);
					}else{
						String doesntExist = Language.getString("Language.Error.Arena_does_not_exist").replaceAll("%arena", arenaName);
						UtilSendMessage.sendMessage(player, doesntExist);
					}
				}else{
					String missingArena = Language.getString("Language.Signs.Missing_arena");
					UtilSendMessage.sendMessage(player, missingArena);
				}
			}else{
				String notPerm = Language.getString("Language.Error.Not_permission");
				UtilSendMessage.sendMessage(player, notPerm);
			}
		}
	}
}
