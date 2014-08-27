package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;

public class EntityDamageByEntity implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){

		if(e.getEntity() instanceof Player){

			Player p = (Player) e.getEntity();

			if(ArenaManager.getArenaManager().isInArena(p)){

				Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(p);
				
				if(arena.getStatus() == Status.STARTING || arena.getStatus() == Status.JOINABLE){
					e.setCancelled(true);
				}
				if(arena.getStatus() == Status.INGAME){
					if(e.getDamager() instanceof Arrow && e.getDamager() instanceof Player){
						e.setCancelled(true);
					}
					if(e.getDamager() instanceof Arrow){
						e.setDamage(200);
					}
				}
			}
		}
	}
}
