package fr.MaGiikAl.OneInTheChamber.InGameEvents;

import java.io.File;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.PlayerArena;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class PlayerRespawn implements Listener{

	int compteur = 0;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent e){

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		final FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		final String PartiePerdue = UtilChatColor.colorizeString(Language.getString("Language.Arena.Player_lost"));

		final Player player = e.getPlayer();

		if(ArenaManager.getArenaManager().isInArena(player)){

			final Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(player);

			if(arena.getStatus() == Status.INGAME){

				final PlayerArena pa = PlayerArena.getPlayerArenaByPlayer(player);

				new BukkitRunnable() {
					@Override
					public void run() {

						if(pa.getLives() < 1){

							String JoueurAPerdu = UtilChatColor.colorizeString(Language.getString("Language.Arena.Broadcast_player_lost")).replaceAll("%player", player.getName());

							arena.removePlayer(player, PartiePerdue, JoueurAPerdu);

						}else{
							Random rand = new Random();
							int nbAlea = rand.nextInt(arena.getSpawnsLocations().size());

							pa.getPlayer().teleport(arena.getSpawnsLocations().get(nbAlea));
							pa.loadGameInventory();
							pa.getArena().updateScores();
						}

					}
				}.runTaskLater(OneInTheChamber.instance, 1L);

			}else if(arena.getStatus() == Status.STARTING || arena.getStatus() == Status.JOINABLE){

				new BukkitRunnable() {
					@Override
					public void run() {
						player.teleport(arena.getStartLocation());
					}
				}.runTaskLater(OneInTheChamber.instance, 1L);

			}
		}
	}
}
