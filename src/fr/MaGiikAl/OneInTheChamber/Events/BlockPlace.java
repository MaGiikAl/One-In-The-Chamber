package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;

public class BlockPlace implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e){
		
		final Player p = e.getPlayer();
		
		for(Arena a : Arena.arenaObjects){
			
			if(a.getPlayers().contains(p.getName())){
				
				e.setCancelled(true);
				
				new BukkitRunnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						p.updateInventory();
						this.cancel();

					}

				}.runTaskTimer(OneInTheChamberMain.instance, 1L, 1L);
				
			}

			
		}
		
	}
	
}
