package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class SetPrivateChatCommand implements BasicCommand {

	public boolean onCommand(Player player, String[] args) {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){
			if(args.length < 2){
				String notEnoughArgs = Language.getString("Language.Error.Not_enough_args");
				UtilSendMessage.sendMessage(player, notEnoughArgs);
				return true;
			}if(args.length > 1){
				String arenaName = args[1];
				if(ArenaManager.getArenaManager().exists(arenaName)){
					Arena arena = ArenaManager.getArenaManager().getArenaByName(arenaName);
					if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")){
						boolean privateChat = Boolean.parseBoolean(args[0]);
						arena.setPrivateChat(privateChat);
						arena.saveConfig();
						String succes = Language.getString("Language.Setup.Private_chat").replaceAll("%value", privateChat + "").replaceAll("%arena", arenaName);
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

		String help = Language.getString("Language.Help.Setup.Private_chat");

		if(player.hasPermission(getPermission())){

			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.setprivatechat";
	}

}
