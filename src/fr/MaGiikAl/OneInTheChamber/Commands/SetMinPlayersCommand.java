package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class SetMinPlayersCommand implements BasicCommand{

	public boolean onCommand(Player player, String[] args) {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){
			if(args.length > 1){
				try{
					Integer.parseInt(args[0]);
				}catch(NumberFormatException e){
					String badArg = Language.getString("Language.Error.Bad_args");
					UtilSendMessage.sendMessage(player, badArg);
					return true;
				}
				int minPlayers = Integer.parseInt(args[0]);
				String arenaName = args[1];
				if(ArenaManager.getArenaManager().getArenaByName(arenaName) != null){
					if(ArenaManager.getArenaManager().getArenaByName(arenaName).getMaxPlayers() >= minPlayers){
						ArenaManager.getArenaManager().getArenaByName(arenaName).setMinPlayers(minPlayers);
						ArenaManager.getArenaManager().getArenaByName(arenaName).saveConfig();
						String succes = Language.getString("Language.Setup.Min_players_succesfully_set").replaceAll("%arena", arenaName);
						UtilSendMessage.sendMessage(player, succes);
						return true;
					}else{
						String tooBig = Language.getString("Language.Setup.Min_players_too_big");
						UtilSendMessage.sendMessage(player, tooBig);
						return true;
					}
				}else{
					String doesntExist = Language.getString("Language.Error.Arena_does_not_exist").replaceAll("%arena", arenaName);
					UtilSendMessage.sendMessage(player, doesntExist);
				}
			}else{
				String notEnoughArgs = Language.getString("Language.Error.Not_enough_args");
				UtilSendMessage.sendMessage(player, notEnoughArgs);
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

		String help = Language.getString("Language.Help.Setup.Min_players");

		if(player.hasPermission(getPermission())){

			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.setminplayers";
	}

}
