package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;

public class PlayerPickupItem implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUpItem(PlayerPickupItemEvent e){

		Player p = e.getPlayer();

		if(ArenaManager.getArenaManager().isInArena(p)){
			e.setCancelled(true);
		}
	}
}
