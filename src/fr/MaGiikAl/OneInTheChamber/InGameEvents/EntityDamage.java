package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;

public class EntityDamage implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent e){

		if(e.getEntity() instanceof Player){

			Player p = (Player) e.getEntity();

			if(ArenaManager.getArenaManager().isInArena(p)){
				if(e.getCause() != DamageCause.PROJECTILE && e.getCause() != DamageCause.ENTITY_ATTACK){
					e.setCancelled(true);
				}
			}
		}
	}
}


