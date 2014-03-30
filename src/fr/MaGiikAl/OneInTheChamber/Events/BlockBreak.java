package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;

public class BlockBreak implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e){
		
		Player p = e.getPlayer();
		
		for(Arena a : Arena.arenaObjects){
			
			if(a.getPlayers().contains(p.getName())){
				
				e.setCancelled(true);
				
			}

			
		}
		
	}
	
}
