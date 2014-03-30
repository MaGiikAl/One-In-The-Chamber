package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class PlayerCommand implements Listener{


	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String PasPermission = UtilChatColor.colorizeString(Language.getString("Language.Error.Not_permission"));
		String ExisteDeja = UtilChatColor.colorizeString(Language.getString("Language.Setup.Arena_already_exists"));
		String LobbyCree = UtilChatColor.colorizeString(Language.getString("Language.Setup.Lobby_succesfully_set"));
		String PasCommandes = UtilChatColor.colorizeString(Language.getString("Language.Error.No_commands"));
		String MauvaiseCommande = UtilChatColor.colorizeString(Language.getString("Language.Error.Wrong_command"));

		Player p = e.getPlayer();

		String cmd = e.getMessage();
		String[] args = cmd.split(" ");

		if(args[0].equalsIgnoreCase("/oitc")){
			e.setCancelled(true);
			if(args.length == 1){
				//MESSAGE
				if(!p.hasPermission("oitc.help.admin") && !p.hasPermission("oitc.admin") && p.hasPermission("oitc.help.players")){

					String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
					String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help1"));
					String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help2"));
					String Help3 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help3"));
					String Help4 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help4"));

					p.sendMessage(Start);
					p.sendMessage(" ");
					p.sendMessage(Help1);
					p.sendMessage(" ");
					p.sendMessage(Help2);
					p.sendMessage(" ");
					p.sendMessage(Help3);
					p.sendMessage(" ");
					p.sendMessage(Help4);
				}
				else if(p.hasPermission("oitc.help.admin") || p.hasPermission("oitc.admin")){

					String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
					String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Menu.Help1"));
					String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Menu.Help2"));

					p.sendMessage(" ");
					p.sendMessage(Start);
					p.sendMessage(" ");
					p.sendMessage(Help1);
					p.sendMessage(" ");
					p.sendMessage(Help2);
					p.sendMessage(" ");
				}
				else{

					p.sendMessage(Prefixe + " " + PasPermission);

				}

			}
			else if(args.length == 2){

				if(args[1].equalsIgnoreCase("help")){

					if(!p.hasPermission("oitc.help.admin") && !p.hasPermission("oitc.admin") && p.hasPermission("oitc.help.players")){

						String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
						String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help1"));
						String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help2"));
						String Help3 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help3"));
						String Help4 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help4"));

						p.sendMessage(Start);
						p.sendMessage(" ");
						p.sendMessage(Help1);
						p.sendMessage(" ");
						p.sendMessage(Help2);
						p.sendMessage(" ");
						p.sendMessage(Help3);
						p.sendMessage(" ");
						p.sendMessage(Help4);
					}
					else if(p.hasPermission("oitc.help.admin") || p.hasPermission("oitc.admin")){

						String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
						String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Menu.Help1"));
						String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Menu.Help2"));

						p.sendMessage(" ");
						p.sendMessage(Start);
						p.sendMessage(" ");
						p.sendMessage(Help1);
						p.sendMessage(" ");
						p.sendMessage(Help2);
						p.sendMessage(" ");
					}
					else{

						p.sendMessage(Prefixe + " " + PasPermission);

					}

				}
				else if(args[1].equalsIgnoreCase("leave")){

					if(p.hasPermission("oitc.leave")){

						ArenaManager.getArenaManager().removePlayer(p);
					}

				}else if(args[1].equalsIgnoreCase("stats")){

					if(p.hasPermission("oitc.stats") || p.hasPermission("oitc.admin")){
						File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
						FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

						String StartStats = UtilChatColor.colorizeString(Language.getString("Language.Stats.Start")).replaceAll("%player", p.getName());
						String Kills = UtilChatColor.colorizeString(Language.getString("Language.Stats.Kills")).replaceAll("%kills", Players.getInt("Players." + p.getName() + ".Kills") + "");
						String Wins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Wins")).replaceAll("%wins", Players.getInt("Players." + p.getName() + ".Wins") + "");
						String Coins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Coins")).replaceAll("%coins", Players.getInt("Players." + p.getName() + ".Coins") + "");

						p.sendMessage(" ");
						p.sendMessage(StartStats);
						p.sendMessage(" ");
						p.sendMessage(Kills);
						p.sendMessage(" ");
						p.sendMessage(Wins);
						p.sendMessage(" ");
						p.sendMessage(Coins);
						p.sendMessage(" ");
					}else{
						p.sendMessage(Prefixe + " " + PasPermission);
					}
				}
			}else if(args.length == 3){

				if(args[1].equalsIgnoreCase("help")){

					if(args[2].equalsIgnoreCase("player")){

						if(p.hasPermission("oitc.help.admin") || p.hasPermission("oitc.admin") || p.hasPermission("oitc.help.players")){

							String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
							String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help1"));
							String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help2"));
							String Help3 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help3"));
							String Help4 = UtilChatColor.colorizeString(Language.getString("Language.Help.Players.Help4"));

							p.sendMessage(Start);
							p.sendMessage(" ");
							p.sendMessage(Help1);
							p.sendMessage(" ");
							p.sendMessage(Help2);
							p.sendMessage(" ");
							p.sendMessage(Help3);
							p.sendMessage(" ");
							p.sendMessage(Help4);

						}else{

							p.sendMessage(Prefixe + " " + PasPermission);

						}
					}else if(args[2].equalsIgnoreCase("admin")){
						
						if(p.hasPermission("oitc.help.admin") || p.hasPermission("oitc.admin")){
							
							String Start = UtilChatColor.colorizeString(Language.getString("Language.Help.Start"));
							String Help1 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help1"));
							String Help2 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help2"));
							String Help3 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help3"));
							String Help4 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help4"));
							String Help5 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help5"));
							String Help6 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help6"));
							String Help7 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help7"));
							String Help8 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help8"));
							String Help9 = UtilChatColor.colorizeString(Language.getString("Language.Help.Admin.Help9"));

							p.sendMessage(Start);
							p.sendMessage(Help1);
							p.sendMessage(Help2);
							p.sendMessage(Help3);
							p.sendMessage(Help4);
							p.sendMessage(Help5);
							p.sendMessage(Help6);
							p.sendMessage(Help7);
							p.sendMessage(Help8);
							p.sendMessage(Help9);
						}
					}
				}
				else if(args[1].equalsIgnoreCase("join")){
					if(Arena.arenas.contains(args[2])){
						if(p.hasPermission("oitc.join") || p.hasPermission("oitc.join." + args[2]) || p.hasPermission("oitc.admin")){
							ArenaManager.getArenaManager().addPlayer(p, args[2]);
						}else{

							p.sendMessage(Prefixe + " " + PasPermission);
						}
					}else{
						String AreneExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[2] + "");

						p.sendMessage(Prefixe + " " + AreneExistePas);
					}
				}else if(args[1].equalsIgnoreCase("create")){
					if(p.hasPermission("oitc.create") || p.hasPermission("oitc.admin")){
						if(!Arena.arenas.contains(args[2])){
							ArrayList<Location> spawnsLocations = new ArrayList<Location>();
							ArenaManager.getArenaManager().createArena(args[2], p.getLocation(), spawnsLocations, 3, 8);

							String BienCreee = UtilChatColor.colorizeString(Language.getString("Language.Setup.Arena_succesfully_created")).replaceAll("%arena", args[2] + "");

							p.sendMessage(Prefixe + " " + BienCreee);
						}
						else{
							p.sendMessage(Prefixe + " " + ExisteDeja);
						}
					}else{
						p.sendMessage(Prefixe + " " + PasPermission);
					}
				}else if(args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")){
					if(p.hasPermission("oitc.delete") || p.hasPermission("oitc.admin")){
						if(Arena.arenas.contains(args[2])){
							ArenaManager.getArenaManager().deleteArena(args[2]);
							String SupprArene = UtilChatColor.colorizeString(Language.getString("Language.Setup.Arena_succesfully_deleted")).replaceAll("%arena", args[2] + "");
							p.sendMessage(Prefixe + " " + SupprArene);

						}
						else{
							String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[2] + "");

							p.sendMessage(Prefixe + " " + ExistePas);
						}
					}else{
						p.sendMessage(Prefixe + " " + PasPermission);
					}
				}else if(args[1].equalsIgnoreCase("setup")){
					if(p.hasPermission("oitc.setup.lobby") || p.hasPermission("oitc.admin")){
						if(args[2].equalsIgnoreCase("lobby")){
							ArenaManager.getArenaManager().setLobby(p.getLocation());
							p.sendMessage(Prefixe + " " + LobbyCree);
						}
					}
					else{
						p.sendMessage(Prefixe + " " + PasPermission);
					}
				}else if(args[1].equalsIgnoreCase("stats")){
					if(p.hasPermission("oitc.stats.other") || p.hasPermission("oitc.admin")){
						File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
						FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

						String StartStats = UtilChatColor.colorizeString(Language.getString("Language.Stats.Start")).replaceAll("%player", args[2]);
						String Kills = UtilChatColor.colorizeString(Language.getString("Language.Stats.Kills")).replaceAll("%kills", Players.getInt("Players." + args[2] + ".Kills") + "");
						String Wins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Wins")).replaceAll("%wins", Players.getInt("Players." + args[2] + ".Wins") + "");
						String Coins = UtilChatColor.colorizeString(Language.getString("Language.Stats.Coins")).replaceAll("%coins", Players.getInt("Players." + args[2] + ".Coins") + "");
						String Jouees = UtilChatColor.colorizeString(Language.getString("Language.Stats.Played")).replaceAll("%played", Players.getInt("Players." + args[2] + ".Played") + "");

						p.sendMessage(StartStats);
						p.sendMessage(" ");
						p.sendMessage(Kills);
						p.sendMessage(" ");
						p.sendMessage(Wins);
						p.sendMessage(" ");
						p.sendMessage(Coins);
						p.sendMessage(" ");
						p.sendMessage(Jouees);

					}else{
						p.sendMessage(Prefixe + " " + PasPermission);
					}
				}else if(args[1].equalsIgnoreCase("start")){

					if(p.hasPermission("oitc.force.start") || p.hasPermission("oitc.admin")){
						if(Arena.arenas.contains(args[2])){
							if(ArenaManager.getArenaManager().getArena(args[2]).getStatus() != Status.INGAME){
								String Start = UtilChatColor.colorizeString(Language.getString("Language.Force.Start")).replaceAll("%arena", args[2] + "");

								ArenaManager.getArenaManager().start(ArenaManager.getArenaManager().getArena(args[2]));

								p.sendMessage(Prefixe + " " + Start);
							}
							else{
								String PasStart = UtilChatColor.colorizeString(Language.getString("Language.Force.Can_not_start")).replaceAll("%arena", args[2] + "");

								p.sendMessage(Prefixe + " " + PasStart);
							}
						}else{
							String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[3] + "");
							p.sendMessage(Prefixe + " " + ExistePas);
						}
					}else{
						p.sendMessage(Prefixe + " " + PasPermission); 

					}
				}else if(args[1].equalsIgnoreCase("stop")){
					if(p.hasPermission("oitc.force.stop") || p.hasPermission("oitc.admin")){
						if(Arena.arenas.contains(args[2])){

							if(ArenaManager.getArenaManager().getArena(args[2]).getStatus() == Status.INGAME){
								String Stop = UtilChatColor.colorizeString(Language.getString("Language.Force.Stop")).replaceAll("%arena", args[2] + "");

								ArenaManager.getArenaManager().endArena(args[2]);

								p.sendMessage(Prefixe + " " + Stop);

							}else{
								String PasStop = UtilChatColor.colorizeString(Language.getString("Language.Force.Can_not_stop")).replaceAll("%arena", args[2] + "");

								p.sendMessage(Prefixe + " " + PasStop);
							}
						}else{
							String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[3] + "");
							p.sendMessage(Prefixe + " " + ExistePas);

						}
					}else{

						p.sendMessage(Prefixe + " " + PasPermission); 

					}
				}
			}else if(args.length == 4){

				if(args[1].equalsIgnoreCase("setup")){

					if(args[2].equalsIgnoreCase("addspawn")){

						if(p.hasPermission("oitc.setup.addspawn") || p.hasPermission("oitc.admin")){

							if(Arena.arenas.contains(args[3])){

								String SpawnAjoute = UtilChatColor.colorizeString(Language.getString("Language.Setup.Spawn_succesfully_added")).replaceAll("%arena", args[3] + "");

								ArenaManager.getArenaManager().getArena(args[3]).addSpawnLocation(new Location(p.getLocation().getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
								p.sendMessage(Prefixe + " " + SpawnAjoute);
							}

							else{
								String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[3] + "");
								p.sendMessage(Prefixe + " " + ExistePas);
							}
						}
						else{
							p.sendMessage(Prefixe + " " + PasPermission);
						}
					}
					if(args[2].equalsIgnoreCase("start")){
						if(p.hasPermission("oitc.setup.start") || p.hasPermission("oitc.admin")){
							if(Arena.arenas.contains(args[3])){
								ArenaManager.getArenaManager().getArena(args[3]).setStartLocation(new Location(p.getLocation().getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));

								String StartSet = UtilChatColor.colorizeString(Language.getString("Language.Setup.Start_set")).replaceAll("%arena", args[3] + "");

								p.sendMessage(Prefixe + " " + StartSet);
							}
							else{
								String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[3] + "");

								p.sendMessage(Prefixe + " " + ExistePas);
							}
						}
						else{
							p.sendMessage(Prefixe + " " + PasPermission);
						}
					}
				}
			}else if(args.length == 5){
				if(args[1].equalsIgnoreCase("setup")){
					if(args[2].equalsIgnoreCase("maxplayers")){
						if(p.hasPermission("oitc.setup.maxplayers") || p.hasPermission("oitc.admin")){
							if(Arena.arenas.contains(args[4])){
								String MaxJoueursSet = UtilChatColor.colorizeString(Language.getString("Language.Setup.Max_players_succesfully_set")).replaceAll("%arena", args[4] + "");

								ArenaManager.getArenaManager().getArena(args[4]).setMaxPlayers(Integer.parseInt(args[3]));
								p.sendMessage(Prefixe + " " + MaxJoueursSet);
							}
							else{
								String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[4] + "");

								p.sendMessage(Prefixe + " " + ExistePas);
							}
						}
						else{
							p.sendMessage(Prefixe + " " + PasPermission);
						}
					}
					if(args[2].equalsIgnoreCase("minplayers")){
						if(p.hasPermission("oitc.setup.minplayers") || p.hasPermission("oitc.admin")){
							if(Arena.arenas.contains(args[4])){
								String MinJoueursSet = UtilChatColor.colorizeString(Language.getString("Language.Setup.Min_players_succesfully_set")).replaceAll("%arena", args[4] + "");

								ArenaManager.getArenaManager().getArena(args[4]).setMinPlayers(Integer.parseInt(args[3]));
								p.sendMessage(Prefixe + " " + MinJoueursSet);
							}
							else{
								String ExistePas = UtilChatColor.colorizeString(Language.getString("Language.Error.Arena_does_not_exist")).replaceAll("%arena", args[4] + "");

								p.sendMessage(Prefixe + " " + ExistePas);
							}
						}
						else{
							p.sendMessage(Prefixe + " " + PasPermission);
						}
					}
				}
			}
			else{

				p.sendMessage(Prefixe + " " + MauvaiseCommande) ;
				
			}
		}else{
			for(Arena a : Arena.arenaObjects){
				if(a.getPlayers().contains(p.getName())){
					e.setCancelled(true);
					p.sendMessage(Prefixe + " " + PasCommandes);
				}
				else{
					return;
				}
			}
		}
	}
}
