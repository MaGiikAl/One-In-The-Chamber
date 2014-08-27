package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class PlayerCommandPreprocess implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		String[] args = e.getMessage().split(" ");
		if(e.getMessage().equalsIgnoreCase("/oitc")) return;
		if(e.getMessage().equalsIgnoreCase("/oitc help")) return;
		if(e.getMessage().equalsIgnoreCase("/oitc leave")) return;
		if(e.getMessage().equalsIgnoreCase("/oitc reload")) return;
		if(args[0].equalsIgnoreCase("/oitc") && args[1].equalsIgnoreCase("stop")) return;

		if(ArenaManager.getArenaManager().isInArena(player)){
			e.setCancelled(true);
			File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
			FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);
			String noCommands = Language.getString("Language.Error.No_commands");
			UtilSendMessage.sendMessage(player, noCommands);
		}
	}
}
