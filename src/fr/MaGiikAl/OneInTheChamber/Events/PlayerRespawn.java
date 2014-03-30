package fr.MaGiikAl.OneInTheChamber.Events;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class PlayerRespawn implements Listener{

	int compteur = 0;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent e){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		final FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		final String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		final String PartiePerdue = UtilChatColor.colorizeString(Language.getString("Language.Arena.Player_lost"));
		final String Gagnant = UtilChatColor.colorizeString(Language.getString("Language.Arena.Win"));

		final Player player = e.getPlayer();

		for(final Arena a : Arena.arenaObjects){

			if(a.getPlayers().contains(player.getName())){

				if(a.getStatus() == Status.INGAME){

					Random rand = new Random();
					final int nbAlea = rand.nextInt(a.getSpawnsLocations().size());

					new BukkitRunnable() {
						@Override
						public void run() {

							ArenaManager.getArenaManager();
							if(ArenaManager.lives.get(player.getName()) == 0){

								FileConfiguration config = OneInTheChamberMain.instance.getConfig();
								Location lobby = new Location(Bukkit.getWorld(config.getString("Lobby.World")), config.getDouble("Lobby.X"), config.getDouble("Lobby.Y"), config.getDouble("Lobby.Z"), (float)config.getDouble("Lobby.Yaw"), (float)config.getDouble("Lobby.Pitch"));

								player.teleport(lobby);

								player.getInventory().setArmorContents(null);
								player.getInventory().clear();
								player.getInventory().setContents(ArenaManager.inv.get(player.getName()));
								player.getInventory().setArmorContents(ArenaManager.armour.get(player.getName()));

								ScoreboardManager ScMan = OneInTheChamberMain.instance.getServer().getScoreboardManager();

								player.setScoreboard(ScMan.getNewScoreboard());

								a.getPlayers().remove(player.getName());

								player.sendMessage(Prefixe + " " + PartiePerdue);

								for(String players : a.getPlayers()){

									String JoueurAPerdu = UtilChatColor.colorizeString(Language.getString("Language.Arena.Broadcast_player_lost")).replaceAll("%player", player.getName());

									Player p = Bukkit.getPlayer(players);
									p.sendMessage(Prefixe + " " + JoueurAPerdu);
								}

								player.setHealth(ArenaManager.health.get(player.getName()));
								player.setFireTicks(0);
								player.setExp(0);
								player.setLevel(ArenaManager.level.get(player.getName()));
								player.setGameMode(GameMode.valueOf(ArenaManager.gamemode.get(player.getName())));

								if(a.getPlayers().size() <= 1 && a.getStatus() == Status.INGAME){
									for(String players : a.getPlayers()){

										Player p = Bukkit.getPlayer(players);

										File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
										FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

										Players.set("Players." + p.getName() + ".Coins", Players.getInt("Players." + p.getName() + ".Coins") +20);
										Players.set("Players." + p.getName() + ".Wins", Players.getInt("Players." + p.getName() + ".Wins") +1);

										try {
											Players.save(fichier_players);
										} catch (IOException e1) {
											e1.printStackTrace();
										}

										p.sendMessage(Prefixe + " " + Gagnant);

									}
									ArenaManager.getArenaManager().endArena(a.getName());
								}
								this.cancel();

							}else{

								player.teleport(a.getSpawnsLocations().get(nbAlea));
								ArenaManager.getArenaManager().loadGame(player);
								this.cancel();
							}



						}
					}.runTaskTimer(OneInTheChamberMain.instance, 1L, 1L);

				}
				if(a.getStatus() == Status.STARTING || a.getStatus() == Status.STARTING){

					new BukkitRunnable() {

						@Override
						public void run() {

							Location start = a.getStartLocation();

							player.teleport(start);
							this.cancel();

						}

					}.runTaskTimer(OneInTheChamberMain.instance, 1L, 1L);

				}
			}
		}
	}
}