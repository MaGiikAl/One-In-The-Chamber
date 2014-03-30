package fr.MaGiikAl.OneInTheChamber.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class PlayerDeath implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String arrow = UtilChatColor.colorizeString(Language.getString("Language.Stuff.Arrow"));

		if(e.getEntityType() == EntityType.PLAYER){

			final Player player = e.getEntity();

			for(Arena a : Arena.arenaObjects){

				if(a.getPlayers().contains(player.getName())){
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
					}.runTaskTimer(OneInTheChamberMain.instance, 1L, 1L);

					if(a.getStatus() == Status.INGAME){

						e.setDeathMessage("");
						e.getDrops().removeAll(e.getDrops());
						e.setDroppedExp(0);
						ArenaManager.lives.put(player.getName(), ArenaManager.lives.get(player.getName())-1);

						ItemStack arrows = new ItemStack(Material.ARROW);
						ItemMeta imarrow = arrows.getItemMeta();
						imarrow.setDisplayName(arrow);
						arrows.setItemMeta(imarrow);

						if(a.getPlayers().contains(player.getKiller().getName())){

							if(player.getKiller().getName() != player.getName()){

								ArenaManager.kills.put(player.getKiller().getName(), ArenaManager.kills.get(player.getKiller().getName()) +1);
								player.getKiller().getScoreboard().getObjective("Kills").getScore(player.getKiller()).setScore(ArenaManager.kills.get(player.getKiller().getName()));

								player.getKiller().getInventory().addItem(arrows);

								File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
								FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

								Players.set("Players." + player.getKiller().getName() + ".Kills", Players.getInt("Players." + player.getKiller().getName() + ".Kills") +1);
								Players.set("Players." + player.getKiller().getName() + ".Coins", Players.getInt("Players." + player.getKiller().getName() + ".Coins") +2);

								try {
									Players.save(fichier_players);
								} catch (IOException e1) {
									e1.printStackTrace();
								}

								for(String players : a.getPlayers()){

									Player p = Bukkit.getPlayer(players);
									p.setScoreboard(p.getScoreboard().getObjective("Kills").getScoreboard());

								}
							}

						}

						for(String players : a.getPlayers()){

							String DeathMessage = UtilChatColor.colorizeString(Language.getString("Language.Arena.Death_message")).replaceAll("%killer", player.getKiller().getName()).replaceAll("%player", player.getName());

							Player p = Bukkit.getPlayer(players);
							p.sendMessage(Prefixe + " " + DeathMessage);

						}
					}
				}
			}
		}

	}
}

