package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;

public class EntityDamageByEntity implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){

		if(e.getEntity() instanceof Player){

			Player p = (Player) e.getEntity();

			for(Arena a : Arena.arenaObjects){

				if(a.getPlayers().contains(p.getName())){

					if(a.getStatus() == Status.STARTING){

						e.setCancelled(true);

					}
					if(a.getStatus() == Status.INGAME){
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
}
