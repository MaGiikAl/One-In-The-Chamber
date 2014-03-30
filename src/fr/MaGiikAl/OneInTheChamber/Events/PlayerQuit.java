package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;

public class PlayerQuit implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent e){

		Player p = e.getPlayer();

		for(Arena a : Arena.arenaObjects){
			if(a.getPlayers().contains(p.getName())){

				e.setQuitMessage("");
				ArenaManager.getArenaManager().removePlayer(p);
				
			}
		}
	}

}
