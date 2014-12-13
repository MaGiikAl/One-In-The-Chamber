package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Type;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class SetTypeCommand implements BasicCommand {

	public boolean onCommand(Player player, String[] args) {

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){
			if(args.length < 2){
				String notEnoughArgs = Language.getString("Language.Error.Not_enough_args");
				UtilSendMessage.sendMessage(player, notEnoughArgs);
				return true;
			}
			if(args.length > 1){
				String arenaName = args[1];
				if(ArenaManager.getArenaManager().getArenaByName(arenaName) != null){
					if(args[0].equalsIgnoreCase("lives") || args[0].equalsIgnoreCase("points")){
						Type type = Type.valueOf(args[0].toUpperCase());
						Arena arena = ArenaManager.getArenaManager().getArenaByName(arenaName);
						arena.setType(type);
						arena.saveConfig();
						String succes = Language.getString("Language.Setup.Type").replaceAll("%arena", arenaName);
						UtilSendMessage.sendMessage(player, succes);
						return true;
					}else{
						String badArg = Language.getString("Language.Error.Bad_args");
						UtilSendMessage.sendMessage(player, badArg);
						return true;
					}
				}else{
					String doesntExist = Language.getString("Language.Error.Arena_does_not_exist").replaceAll("%arena", arenaName);
					UtilSendMessage.sendMessage(player, doesntExist);
					return true;
				}
			}
		}else{
			String notPerm = Language.getString("Language.Error.Not_permission");
			UtilSendMessage.sendMessage(player, notPerm);
			return true;
		}
		return true;
	}

	public String help(Player player) {

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){
			String help = Language.getString("Language.Help.Setup.Type");
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.settype";
	}

}
