package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;

public class PlayerDropItem implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent e){

		final Player p = e.getPlayer();

		for(Arena a : Arena.arenaObjects){

			if(a.getPlayers().contains(p.getName())){

				e.setCancelled(true);

				new BukkitRunnable() {

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
