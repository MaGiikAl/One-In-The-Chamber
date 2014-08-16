package fr.MaGiikAl.OneInTheChamber.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.PlayerArena;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class PlayerDeath implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e){
		
		if(e.getEntityType() == EntityType.PLAYER){

			final Player player = e.getEntity();

			if(ArenaManager.getArenaManager().isInArena(player)){
				
				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
							Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
							Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");

							for(Object ob : enumClass.getEnumConstants()){
								if(ob.toString().equals("PERFORM_RESPAWN")){
									packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
								}
							}

							Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
							con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
						}
						catch(Throwable t){
							t.printStackTrace();
						}
					}
				}.runTaskLater(OneInTheChamber.instance, 2L);

				Arena arena = ArenaManager.getArenaManager().getArenaByPlayer(player);
				
				if(arena.getStatus() == Status.INGAME){

					PlayerArena pa = PlayerArena.getPlayerArenaByPlayer(player);
					
					e.setDeathMessage("");
					e.getDrops().removeAll(e.getDrops());
					e.setDroppedExp(0);
					pa.setLives(pa.getLives() -1);

					if(arena.getPlayers().contains(PlayerArena.getPlayerArenaByPlayer(player.getKiller()))){

						if(player.getKiller().getName() != player.getName()){

							PlayerArena killer = PlayerArena.getPlayerArenaByPlayer(player.getKiller());
							
							killer.addArrow();

							File fichier_killer = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Players" + File.separator + killer.getName() + ".yml");
							FileConfiguration Killer = YamlConfiguration.loadConfiguration(fichier_killer);

							Killer.set("Kills", Killer.getInt("Kills") +1);
							Killer.set("Coins", Killer.getInt("Coins") +2);
							
							killer.setScore(killer.getScore() + 1);

							try {
								Killer.save(fichier_killer);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							arena.updateScores();
						}
					}

					File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
					FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);
					
					String DeathMessage = UtilChatColor.colorizeString(Language.getString("Language.Arena.Death_message")).replaceAll("%killer", player.getKiller().getName()).replaceAll("%player", player.getName());
					
					arena.broadcast(DeathMessage);
				}
			}

		}

	}
}

