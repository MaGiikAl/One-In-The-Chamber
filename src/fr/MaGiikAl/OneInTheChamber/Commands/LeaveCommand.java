package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class LeaveCommand implements BasicCommand{

	public boolean onCommand(Player player, String[] args) {
		
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);
		
		if(player.hasPermission(getPermission())){
			if(ArenaManager.getArenaManager().isInArena(player)){
				
				String pourJoueur = Language.getString("Language.Arena.Leave");
				String pourJoueursArene = Language.getString("Language.Arena.Broadcast_leave").replaceAll("%player", player.getName());

				Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(player);
				arena.removePlayer(player, pourJoueur, pourJoueursArene);
			}else{
				String notInGame = Language.getString("Language.Arena.Not_in_game");
				UtilSendMessage.sendMessage(player, notInGame);
			}
		}else{
			String notPerm = Language.getString("Language.Error.Not_permission");
			UtilSendMessage.sendMessage(player, notPerm);
		}
		return true;
	}

	public String help(Player player) {

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String help = Language.getString("Language.Help.Leave");

		if(player.hasPermission(getPermission())){
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.leave";
	}

}
