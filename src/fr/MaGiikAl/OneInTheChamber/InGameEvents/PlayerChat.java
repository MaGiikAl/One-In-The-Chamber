package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;

public class PlayerChat implements Listener{
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		
		Player player = e.getPlayer();
		
		if(!ArenaManager.getArenaManager().isInArena(player)) return;
		
		Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(player);
		if(!arena.isPrivateChat()) return;
		
		e.setCancelled(true);
		
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);
		
		String chatFormat = Language.getString("Language.Arena.Chat_format").replaceAll("%player", player.getName()).replaceAll("%message", e.getMessage());
		
		arena.chat(chatFormat);
		
	}


}
