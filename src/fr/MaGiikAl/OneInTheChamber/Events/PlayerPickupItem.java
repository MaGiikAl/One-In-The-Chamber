package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;

public class PlayerPickupItem implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUpItem(PlayerPickupItemEvent e){

		Player p = e.getPlayer();

		for(Arena a : Arena.arenaObjects){

			if(a.getPlayers().contains(p.getName())){

				e.setCancelled(true);

			}

		}

	}

}
