package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class StatsCommand implements BasicCommand{

	int kills;
	int coins;
	int wins;
	int played;

	public boolean onCommand(Player player, String[] args) {

		File fichier_player = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Players" + File.separator + player.getName() + ".yml");
		FileConfiguration playerFile = YamlConfiguration.loadConfiguration(fichier_player);

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String start = UtilChatColor.colorizeString(Language.getString("Language.Stats.Start").replaceAll("%player", player.getName()));

		if(player.hasPermission(getPermission())){
			if(args.length == 0){
				if(fichier_player.exists()){
					if(playerFile.contains("Kills")){
						this.kills = playerFile.getInt("Kills");
					}
					if(playerFile.contains("Coins")){
						this.coins = playerFile.getInt("Coins");
					}
					if(playerFile.contains("Wins")){
						this.wins = playerFile.getInt("Wins");
					}
					if(playerFile.contains("Played")){
						this.played = playerFile.getInt("Played");
					}

					String Kills = UtilChatColor.colorizeString(Language.getString("Language.Stats.Kills")).replaceAll("%kills", this.kills + "");
					String Wins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Wins")).replaceAll("%wins", this.wins + "");
					String Coins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Coins")).replaceAll("%coins", this.coins + "");
					String Jouees = UtilChatColor.colorizeString(Language.getString("Language.Stats.Played")).replaceAll("%played", this.played + "");

					player.sendMessage(start);
					player.sendMessage(" ");
					player.sendMessage(Kills);
					player.sendMessage(" ");
					player.sendMessage(Wins);
					player.sendMessage(" ");
					player.sendMessage(Coins);
					player.sendMessage(" ");
					player.sendMessage(Jouees);

					return true;

				}else{
					String neverPlay = Language.getString("Language.Stats.Never_play");
					UtilSendMessage.sendMessage(player, neverPlay);
					return true;
				}
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

		String help = Language.getString("Language.Help.Stats");

		if(player.hasPermission(getPermission())){
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.stats";
	}
	public String getPermission2(){
		return "oitc.stats.other";
	}
}
