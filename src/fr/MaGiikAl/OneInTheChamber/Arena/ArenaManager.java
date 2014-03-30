package fr.MaGiikAl.OneInTheChamber.Arena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArenaManager {

	private static ArenaManager am = new ArenaManager();

	public static Map<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
	public static Map<String, ItemStack[]> armour = new HashMap<String, ItemStack[]>();
	public static Map<String, String> gamemode = new HashMap<String, String>();
	public static Map<String, Integer> level = new HashMap<String, Integer>();
	public static Map<String, Double> health = new HashMap<String, Double>();

	public static Map<String, Integer> lives = new HashMap<String, Integer>();

	public static Map<String, Integer> kills = new HashMap<String, Integer>();

	public static ArenaManager getArenaManager(){
		return am;
	}

	public Arena getArena(String name){
		for(Arena a : Arena.arenaObjects){
			if(a.getName().equals(name)){
				return a;
			}
		}return null;

	}

	public void deleteArena(String arenaName){
		Arena arena = getArena(arenaName);
		Arena.arenaObjects.remove(arena);
		Arena.arenas.remove(arena.getName());

		OneInTheChamberMain.instance.getConfig().set("Arenas." + arenaName, null);
		OneInTheChamberMain.instance.saveConfig();
	}

	public void createArena(String arenaName, Location startLocation, ArrayList<Location> spawnsLocations, int minplayers, int maxplayers){
		new Arena(arenaName, startLocation, spawnsLocations, minplayers, maxplayers);

		FileConfiguration config = OneInTheChamberMain.instance.getConfig();

		config.set("Arenas." + arenaName + ".Lives", 5);

		config.set("Arenas." + arenaName + ".MaxPlayers", maxplayers);
		config.set("Arenas." + arenaName + ".MinPlayers", minplayers);

		config.set("Arenas." + arenaName + ".World", startLocation.getWorld().getName());

		int id = 0;

		for(Location loc : spawnsLocations){
			id++;
			config.set("Arenas." + arenaName + ".Spawns." + id + ".X", loc.getBlockX());
			config.set("Arenas." + arenaName + ".Spawns." + id + ".Y", loc.getY());
			config.set("Arenas." + arenaName + ".Spawns." + id + ".Z", loc.getZ());
			config.set("Arenas." + arenaName + ".Spawns." + id + ".Yaw", loc.getYaw());
			config.set("Arenas." + arenaName + ".Spawns." + id + ".Pitch", loc.getPitch());
		}
		config.set("Arenas." + arenaName + ".Start.X", startLocation.getX());
		config.set("Arenas." + arenaName + ".Start.Y", startLocation.getY());
		config.set("Arenas." + arenaName + ".Start.Z", startLocation.getZ());
		config.set("Arenas." + arenaName + ".Start.Yaw", startLocation.getYaw());
		config.set("Arenas." + arenaName + ".Start.Pitch", startLocation.getPitch());

		OneInTheChamberMain.instance.saveConfig();
	}

	public void startArena(final String arenaName){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		final FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		final String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		final String PasAssezJoueurs = UtilChatColor.colorizeString(Language.getString("Language.Arena.Not_enough_players_to_launch"));

		if(getArena(arenaName) != null){
			final Arena arena = getArena(arenaName);

			if(arena.getStatus() != Status.INGAME){

				arena.setStatus(Status.STARTING);

				new BukkitRunnable() {
					int countdown = OneInTheChamberMain.instance.getConfig().getInt("Time_before_start_arena");

					@Override
					public void run() {
						if(arena.getPlayers().size() > 0){
							if(!(arena.getPlayers().size() < arena.getMinPlayers())){
								if(arena.getStatus() != Status.INGAME){
									if (countdown > 0) {
										for(String p : arena.getPlayers()){
											Player player = Bukkit.getPlayer(p);
											player.setLevel(countdown);
										}
										if(countdown == 1 || countdown == 5 || countdown == 4 || countdown == 3 || countdown == 2){
											for(String p : arena.getPlayers()){
												Player player = Bukkit.getPlayer(p);
												player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10, 10);
											}
										}
										if(countdown == OneInTheChamberMain.instance.getConfig().getInt("Time_before_start_arena")){

											String DemarrageArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.StartsIn")).replaceAll("%time", countdown + "");

											for(String p : arena.getPlayers()){
												//MESSAGE
												Player player = Bukkit.getPlayer(p);
												player.sendMessage(Prefixe + " " + DemarrageArene);
											}
										}
										countdown--;

									}else if (countdown <= 0) {
										for(String p : arena.getPlayers()){
											Player player = Bukkit.getPlayer(p);
											player.setLevel(0);
										}
										this.cancel();
										start(arena);
									}
								}else{
									for(String p : arena.getPlayers()){
										Player player = Bukkit.getPlayer(p);
										player.setLevel(0);
									}
									this.cancel();
								}
								
							}else{
								for(String players : arena.getPlayers()){
									Player p = Bukkit.getPlayer(players);
									p.sendMessage(Prefixe + " " + PasAssezJoueurs);
									p.setLevel(0);
								}
								this.cancel();
							}
						}else{

							this.cancel();
						}
					}
				}.runTaskTimer(OneInTheChamberMain.instance, 0L, 20L);
			}
		}
	}

	public void start(Arena arena) {

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String Demarrage = UtilChatColor.colorizeString(Language.getString("Language.Arena.Start"));

		String NomScoreboard = UtilChatColor.colorizeString(Language.getString("Language.Scoreboard.Name"));


		ScoreboardManager ScMan = OneInTheChamberMain.instance.getServer().getScoreboardManager();
		Scoreboard board = ScMan.getNewScoreboard();

		Objective obj = board.registerNewObjective("Kills", "KillCount");

		obj.setDisplayName(NomScoreboard);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		arena.setStatus(Status.INGAME);
		int i = 0;
		for(String player : arena.getPlayers()){
			Player p = Bukkit.getPlayer(player);
			p.sendMessage(Prefixe + " " + Demarrage);
			p.playSound(p.getLocation(), Sound.WITHER_DEATH, 10, 4);
			lives.put(p.getName(), OneInTheChamberMain.instance.getConfig().getInt("Arenas." + arena.getName() + ".Lives"));
			loadGame(p);
			p.teleport(arena.getSpawnsLocations().get(i));

			File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
			FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);
			
			Players.set("Players." + p.getName() + ".Played", Players.getInt("Players." + p.getName() + ".Played") +1);
			
			try {
				Players.save(fichier_players);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			kills.put(player, 0);
			obj.getScore(p).setScore(0);

			i++;
		}
		for(String player : arena.getPlayers()){

			Player p = Bukkit.getPlayer(player);
			p.setScoreboard(board);

		}
	}

	public void loadGame(Player player) {
		for(Arena arena : Arena.arenaObjects){
			if(arena.getPlayers().contains(player.getName())){

				File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
				FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

				String sword = UtilChatColor.colorizeString(Language.getString("Language.Stuff.Sword"));
				String bow = UtilChatColor.colorizeString(Language.getString("Language.Stuff.Bow"));
				String arrow = UtilChatColor.colorizeString(Language.getString("Language.Stuff.Arrow"));
				String redstone = UtilChatColor.colorizeString(Language.getString("Language.Stuff.Redstone"));

				ItemStack redstone2 = new ItemStack(Material.REDSTONE, lives.get(player.getName()));
				ItemStack sword2 = new ItemStack(Material.STONE_SWORD);
				ItemStack bow2 = new ItemStack(Material.BOW);
				ItemStack arrow2 = new ItemStack(Material.ARROW);

				ItemMeta imredstone = redstone2.getItemMeta();
				ItemMeta imsword = sword2.getItemMeta();
				ItemMeta imbow = bow2.getItemMeta();
				ItemMeta imarrow = arrow2.getItemMeta();

				imredstone.setDisplayName(redstone);
				imsword.setDisplayName(sword);
				imbow.setDisplayName(bow);
				imarrow.setDisplayName(arrow);

				redstone2.setItemMeta(imredstone);
				sword2.setItemMeta(imsword);
				bow2.setItemMeta(imbow);
				arrow2.setItemMeta(imarrow);

				player.getInventory().setItem(0, sword2);
				player.getInventory().setItem(1, bow2);
				player.getInventory().setItem(2, arrow2);
				player.getInventory().setItem(8, redstone2);

			}
		}
	}
	public void endArena(String arenaName){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String FinArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.End"));

		if(getArena(arenaName) != null){
			Arena arena = getArena(arenaName);

			if(arena.getStatus() == Status.INGAME || arena.getStatus() == Status.STARTING){

				if(OneInTheChamberMain.instance.getConfig().getConfigurationSection("Lobby") != null){

					ArrayList<String> players = new ArrayList<String>(arena.getPlayers());
					Iterator<String> playersIterator =  players.iterator();
					while(playersIterator.hasNext()){

						Player p = Bukkit.getPlayer(playersIterator.next());

						FileConfiguration config = OneInTheChamberMain.instance.getConfig();
						Location lobby = new Location(Bukkit.getWorld(config.getString("Lobby.World")), config.getDouble("Lobby.X"), config.getDouble("Lobby.Y"), config.getDouble("Lobby.Z"), (float)config.getDouble("Lobby.Yaw"), (float)config.getDouble("Lobby.Pitch"));

						p.teleport(lobby);

						p.getInventory().setArmorContents(null);
						p.getInventory().clear();
						p.getInventory().setContents(inv.get(p.getName()));
						p.getInventory().setArmorContents(armour.get(p.getName()));
						p.setGameMode(GameMode.valueOf(gamemode.get(p.getName())));
						arena.getPlayers().remove(p.getName());

						ScoreboardManager ScMan = OneInTheChamberMain.instance.getServer().getScoreboardManager();

						p.setScoreboard(ScMan.getNewScoreboard());

						p.sendMessage(Prefixe + " " + FinArene);

						p.setHealth(health.get(p.getName()));
						p.setFireTicks(0);
						p.setExp(0);
						p.setLevel(level.get(p.getName()));

						if(arena.getPlayers().size() == 0){
							arena.getPlayers().clear();
						}
					}
				}				
				arena.setStatus(Status.JOINABLE);

			}
		}
	}
	public void loadArenas(){

		FileConfiguration config = OneInTheChamberMain.instance.getConfig();

		if(config.getConfigurationSection("Arenas") != null){

			for(String keys : config.getConfigurationSection("Arenas").getKeys(false)){

				ArrayList<Location> spawnsLocation = new ArrayList<Location>();
				World world = Bukkit.getWorld(config.getString("Arenas." + keys + ".World"));

				if(config.getConfigurationSection("Arenas." + keys + ".Spawns") != null){

					for(String spawnsLocations : config.getConfigurationSection("Arenas." + keys + ".Spawns").getKeys(false)){

						double spawnX = config.getDouble("Arenas." + keys + ".Spawns." + spawnsLocations + ".X");
						double spawnY = config.getDouble("Arenas." + keys + ".Spawns." + spawnsLocations + ".Y");
						double spawnZ = config.getDouble("Arenas." + keys + ".Spawns." + spawnsLocations + ".Z");
						float spawnYaw = (float) config.getDouble("Arenas." + keys + ".Spawns." + spawnsLocations + ".Yaw");
						float spawnPitch = (float) config.getDouble("Arenas." + keys + ".Spawns." + spawnsLocations + ".Pitch");

						spawnsLocation.add(new Location(world, spawnX, spawnY, spawnZ, spawnYaw, spawnPitch));
					}
				}

				double startX = config.getDouble("Arenas." + keys + ".Start.X");
				double startY = config.getDouble("Arenas." + keys + ".Start.Y");
				double startZ = config.getDouble("Arenas." + keys + ".Start.Z");
				float startYaw = (float) config.getDouble("Arenas." + keys + ".Start.Yaw");
				float startPitch = (float) config.getDouble("Arenas." + keys + ".Start.Pitch");

				Location startloc = new Location(world, startX, startY, startZ, startYaw, startPitch);

				int maxplayers = config.getInt("Arenas." + keys + ".MaxPlayers");
				int minplayers = config.getInt("Arenas." + keys + ".MinPlayers");

				new Arena(keys, startloc, spawnsLocation, minplayers, maxplayers);


			}

		}else{
			return;
		}

	}
	public void addPlayer(Player player, String arenaName){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String DejaDansArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.Already_in_game"));
		String JoueurRejoint = UtilChatColor.colorizeString(Language.getString("Language.Arena.Join")).replaceAll("%number", getArena(arenaName).getPlayers().size() +1 + "").replaceAll("%max", getArena(arenaName).getMaxPlayers() + "").replaceAll("%player", player.getName());
		String EnJeu = UtilChatColor.colorizeString(Language.getString("Language.Arena.In_game"));
		String Complete = UtilChatColor.colorizeString(Language.getString("Language.Arena.Full"));
		String PasAssezSpawns = UtilChatColor.colorizeString(Language.getString("Language.Error.Not_enough_spawns"));
		String PasPrete = UtilChatColor.colorizeString(Language.getString("Language.Error.Not_ready"));

		for(Arena a : Arena.arenaObjects){
			if(a.getPlayers().contains(player.getName())){
				//MESSAGE
				player.sendMessage(Prefixe + " " + DejaDansArene);
				return;
			}
		}
		if(getArena(arenaName) != null){
			Arena arena = getArena(arenaName);
			if(arena.isReady()){
				if(arena.getSpawnsLocations().size() >= arena.getMaxPlayers()){
					if(!arena.getPlayers().contains(player.getName())){
						if(!arena.isFull()){
							if(arena.getStatus() != Status.INGAME){
								arena.getPlayers().add(player.getName());
								inv.put(player.getName(), player.getInventory().getContents());
								armour.put(player.getName(), player.getInventory().getArmorContents());
								gamemode.put(player.getName(), player.getGameMode().toString());
								level.put(player.getName(), player.getLevel());
								health.put(player.getName(), player.getHealth());
								player.setFoodLevel(20);
								arena.addPlayer(player);

								File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
								FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

								if(Players.getConfigurationSection("Players." + player.getName()) == null){

									Players.set("Players." + player.getName() + ".Kills", 0);
									Players.set("Players." + player.getName() + ".Wins", 0);
									Players.set("Players." + player.getName() + ".Coins", 0);
									Players.set("Players." + player.getName() + ".Played", 0);

									try {
										Players.save(fichier_players);
									} catch (IOException e1) {
										e1.printStackTrace();
									}

								}

								if(arena.getPlayers().size() == arena.getMinPlayers()){
									for(String players : arena.getPlayers()){
										Player p = Bukkit.getPlayer(players);
										p.sendMessage(Prefixe + " " + JoueurRejoint);
									}
									startArena(arenaName);
								}else {
									for(String players : arena.getPlayers()){
										Player p = Bukkit.getPlayer(players);
										p.sendMessage(Prefixe + " " + JoueurRejoint);
									}

								}

							}else{
								player.sendMessage(Prefixe + " " + EnJeu);

							}
						}else{
							player.sendMessage(Prefixe + " " + Complete);

						}
					}else{
						player.sendMessage(Prefixe + " " + DejaDansArene);

					}
				}else{

					player.sendMessage(Prefixe + " " + PasAssezSpawns);

				}
			}else{
				player.sendMessage(Prefixe + " " + PasPrete);

			}
		}
	}
	public void removePlayer(Player player){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String QuitteArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.Leave"));
		String JoueurQuitteArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.Broadcast_leave")).replaceAll("%player", player.getName());
		String Gagnant = UtilChatColor.colorizeString(Language.getString("Language.Arena.Win"));
		String PasDansArene = UtilChatColor.colorizeString(Language.getString("Language.Arena.Not_in_game"));

		for(Arena arena : Arena.arenaObjects){
			if(arena.getPlayers().contains(player.getName())){

				FileConfiguration config = OneInTheChamberMain.instance.getConfig();
				Location lobby = new Location(Bukkit.getWorld(config.getString("Lobby.World")), config.getDouble("Lobby.X"), config.getDouble("Lobby.Y"), config.getDouble("Lobby.Z"), (float)config.getDouble("Lobby.Yaw"), (float)config.getDouble("Lobby.Pitch"));

				player.teleport(lobby);

				player.getInventory().setArmorContents(null);
				player.getInventory().clear();
				player.getInventory().setContents(inv.get(player.getName()));
				player.getInventory().setArmorContents(armour.get(player.getName()));
				arena.getPlayers().remove(player.getName());

				ScoreboardManager ScMan = OneInTheChamberMain.instance.getServer().getScoreboardManager();

				player.setScoreboard(ScMan.getNewScoreboard());

				player.sendMessage(Prefixe + " " + QuitteArene);

				for(String players : arena.getPlayers()){

					Player p = Bukkit.getPlayer(players);
					p.sendMessage(Prefixe + " " + JoueurQuitteArene);
				}

				player.setHealth(health.get(player.getName()));
				player.setFireTicks(0);
				player.setExp(0);
				player.setLevel(level.get(player.getName()));
				player.setGameMode(GameMode.valueOf(gamemode.get(player.getName())));

				if(arena.getPlayers().size() <= 1 && arena.getStatus() == Status.INGAME){
					for(String players : arena.getPlayers()){

						Player p = Bukkit.getPlayer(players);
						p.sendMessage(Prefixe + " " + Gagnant);

						File fichier_players = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Players.yml");
						FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);

						Players.set("Players." + p.getName() + ".Coins", Players.getInt("Players." + p.getName() + ".Coins") +20);
						Players.set("Players." + p.getName() + ".Wins", Players.getInt("Players." + p.getName() + ".Wins") +1);

						try {
							Players.save(fichier_players);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					endArena(arena.getName());
				}

			}else{

				player.sendMessage(Prefixe + " " + PasDansArene);  

			}
		}
	}

	public void setLobby(Location loc){

		String world = loc.getWorld().getName();

		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float yaw = loc.getYaw();
		float pitch = loc.getPitch();

		FileConfiguration config = OneInTheChamberMain.instance.getConfig();

		config.set("Lobby.World", world);
		config.set("Lobby.X", x);
		config.set("Lobby.Y", y);
		config.set("Lobby.Z", z);
		config.set("Lobby.Yaw", yaw);
		config.set("Lobby.Pitch", pitch);

		OneInTheChamberMain.instance.saveConfig();

	}

}
