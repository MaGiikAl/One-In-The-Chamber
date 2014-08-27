package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.Updater;
import fr.MaGiikAl.OneInTheChamber.Utils.Updater.UpdateResult;
import fr.MaGiikAl.OneInTheChamber.Utils.Updater.UpdateType;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class UpdateCommand implements BasicCommand {


	public boolean onCommand(Player player, String[] args) {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		if(player.hasPermission(getPermission())){

			Updater updater = OneInTheChamber.getInstance().update(UpdateType.DEFAULT, false);

			if(updater.getResult() == UpdateResult.SUCCESS){
				String succes = Language.getString("Language.Updater.Succesfully_update");
				UtilSendMessage.sendMessage(player, succes);
			}else if(updater.getResult() == UpdateResult.NO_UPDATE){
				String noUpdate = Language.getString("Language.Updater.No_update_available");
				UtilSendMessage.sendMessage(player, noUpdate);
			}else{
				String error = Language.getString("Language.Updater.Error");
				UtilSendMessage.sendMessage(player, error);
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

		String help = Language.getString("Language.Help.Update");

		if(player.hasPermission(getPermission())){
			return help;
		}
		return "";
	}

	public String getPermission() {
		return "oitc.update";
	}

}
