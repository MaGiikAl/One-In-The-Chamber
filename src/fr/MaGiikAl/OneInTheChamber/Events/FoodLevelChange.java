package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;

public class FoodLevelChange implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFoodLevelChange(FoodLevelChangeEvent e){

		if(e.getEntity().getType() == EntityType.PLAYER){

			Player p = (Player) e.getEntity();

			for(Arena a : Arena.arenaObjects){

				if(a.getPlayers().contains(p.getName())){

					p.setFoodLevel(20);
					e.setCancelled(true);
					
				}

			}

		}


	}

}
