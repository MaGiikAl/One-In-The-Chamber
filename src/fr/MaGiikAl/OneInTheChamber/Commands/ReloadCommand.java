package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class ReloadCommand implements BasicCommand{
	
	public boolean onCommand(Player player, String[] args) {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);
		
		if(player.hasPermission(getPermission())){
			OneInTheChamber.instance.getServer().getPluginManager().disablePlugin(OneInTheChamber.instance);
			OneInTheChamber.instance.getServer().getPluginManager().enablePlugin(OneInTheChamber.instance);
			String reload = Language.getString("Language.Arena.Reload");
			UtilSendMessage.sendMessage(player, reload);
		}else{
			String notPerm = Language.getString("Language.Error.Not_permission");
			UtilSendMessage.sendMessage(player, notPerm);
		}
		return true;
	}
	
	public String help(Player player) {

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String help = Language.getString("Language.Help.Reload");

		if(player.hasPermission(getPermission())){
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.reload";
	}


	
}
