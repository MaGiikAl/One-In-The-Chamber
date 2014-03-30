package fr.MaGiikAl.OneInTheChamber.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;

public class InventoryClick implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerClickInventory(InventoryClickEvent e){

		HumanEntity he = e.getWhoClicked();

		if(he.getType() == EntityType.PLAYER){
			final Player p = (Player) e.getWhoClicked();
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

}
