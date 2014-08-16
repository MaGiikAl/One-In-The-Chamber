package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class LobbyCommand implements BasicCommand {

	public boolean onCommand(Player player, String[] args) {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){
			if(!ArenaManager.getArenaManager().isInArena(player)){
				if(ArenaManager.getArenaManager().getLobbyLocation() != null){
					player.teleport(ArenaManager.getArenaManager().getLobbyLocation());
					return true;
				}
				else{
					String lobbyNotSet = Language.getString("Language.Error.Lobby_is_not_set");
					UtilSendMessage.sendMessage(player, lobbyNotSet);
					return true;
				}
			}else{
				String inGame = Language.getString("Language.Error.Already_in_game");
				UtilSendMessage.sendMessage(player, inGame);
				return true;
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

		String help = Language.getString("Language.Help.Lobby");

		if(player.hasPermission(getPermission())){
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.lobby";
	}

}
