package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;

public class PlayerMove implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent e){
		
		Player player = e.getPlayer();
		
		if(!ArenaManager.getArenaManager().isInArena(player)) return;
		
		if(player.getLocation().getY() <= 0){
			Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(player);
			if(arena.getStatus() == Status.INGAME){
				Random rand = new Random();
				int nbAlea = rand.nextInt(arena.getSpawnsLocations().size());

				player.teleport(arena.getSpawnsLocations().get(nbAlea));
			}
			else{
				player.teleport(arena.getStartLocation());
			}
		}
		
	}
	
}
