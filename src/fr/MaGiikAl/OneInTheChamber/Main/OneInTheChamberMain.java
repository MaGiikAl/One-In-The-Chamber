package fr.MaGiikAl.OneInTheChamber.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.MaGiikAl.OneInTheChamber.Arena.*;
import fr.MaGiikAl.OneInTheChamber.Commands.*;
import fr.MaGiikAl.OneInTheChamber.Events.*;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class OneInTheChamberMain extends JavaPlugin{

	public static JavaPlugin instance;

	public void onEnable(){

		instance = this;

		System.out.println("[OneInTheChamber] Plugin by MaGiikAl (Visit my youtube channel !)");

		loadConfiguration();
		saveDefaultConfig();

		registerEvents();

		ArenaManager.getArenaManager().loadArenas();

	}

	public void onDisable(){

		updatePlayers();
		System.out.println("[OneInTheChamber] Plugin disable");

	}

	public void loadConfiguration(){

		File fichier_language = new File(getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);	

		if(!fichier_language.exists()){

			Language.addDefault("Language.Prefix", "&4&l[&6&lOneInTheChamber&4&l]");

			Language.addDefault("Language.Scoreboard.Name", "&4&l>> &6&lLeaderboard &4&l<<");

			Language.addDefault("Language.Stuff.Sword", "&6&lKNIFE");
			Language.addDefault("Language.Stuff.Bow", "&3&lSNIPER");
			Language.addDefault("Language.Stuff.Arrow", "&3&lAN AMMO");
			Language.addDefault("Language.Stuff.Redstone", "&4&lLIVES");

			Language.addDefault("Language.Stats.Start", "&2&l>>>>>> &4&l[&6&l%player's Stats&4&l] &2&l<<<<<<");
			Language.addDefault("Language.Stats.Kills", "  &4>>>>>  &9Kills: &b%kills  &4<<<<<");
			Language.addDefault("Language.Stats.Wins", "  &4>>>>>  &9Wins: &b%wins  &4<<<<<");
			Language.addDefault("Language.Stats.Coins", "  &4>>>>>  &9Coins: &b%coins  &4<<<<<");
			Language.addDefault("Language.Stats.Played", "  &4>>>>>  &9Played: &b%played  &4<<<<<");
			Language.addDefault("Language.Stats.Not_found", "&cPlayer not found !");
			
			Language.addDefault("Language.Help.Start", "&2&l>>>>>> &4&l[&6&lOne In The Chamber's Help&4&l] &2&l<<<<<<");
			Language.addDefault("Language.Help.Players.Help1", "  &4>>>>>  &9&l/oitc join <Arena> &bto join an arena");
			Language.addDefault("Language.Help.Players.Help2", "  &4>>>>>  &9&l/oitc leave &bto leave an arena");
			Language.addDefault("Language.Help.Players.Help3", "  &4>>>>>  &9&l/oitc stats &bto look your skills");
			Language.addDefault("Language.Help.Players.Help4", "  &4>>>>>  &9&l/oitc stats <player> &bto look the player's skills");
						
			Language.addDefault("Language.Help.Admin.Menu.Help1", "  &4>>>>>  &9&l/oitc help player &bto see the player's help");
			Language.addDefault("Language.Help.Admin.Menu.Help2", "  &4>>>>>  &9&l/oitc help admin &bto see the admin's help");
			
			Language.addDefault("Language.Help.Admin.Help1", "  &4>>>>>  &9&l/oitc setup lobby");
			Language.addDefault("Language.Help.Admin.Help2", "  &4>>>>>  &9&l/oitc create <arena>");
			Language.addDefault("Language.Help.Admin.Help3", "  &4>>>>>  &9&l/oitc delete|remove <arena>");
			Language.addDefault("Language.Help.Admin.Help4", "  &4>>>>>  &9&l/oitc setup maxplayers <number> <arena>");
			Language.addDefault("Language.Help.Admin.Help5", "  &4>>>>>  &9&l/oitc setup minplayers <number> <arena>");
			Language.addDefault("Language.Help.Admin.Help6", "  &4>>>>>  &9&l/oitc setup addspawn <arena>");
			Language.addDefault("Language.Help.Admin.Help7", "  &4>>>>>  &9&l/oitc setup start <arena>");
			Language.addDefault("Language.Help.Admin.Help8", "  &4>>>>>  &9&l/oitc start <arena>");
			Language.addDefault("Language.Help.Admin.Help9", "  &4>>>>>  &9&l/oitc stop <arena>");

			Language.addDefault("Language.Arena.Full", "&3The arena is full ! You can't join...");
			Language.addDefault("Language.Arena.In_game", "&3The arena is in game ! You can't join...");
			Language.addDefault("Language.Arena.Not_in_game", "&3You're not in game ! You can't leave...");
			Language.addDefault("Language.Arena.Already_in_game", "&3You are already in game !");
			Language.addDefault("Language.Arena.Join", "&e&l%player &3joined the arena ! &6%number/%max");
			Language.addDefault("Language.Arena.StartsIn", "&3The game starts in &l%time &3seconds.");
			Language.addDefault("Language.Arena.Not_enough_players_to_launch", "&3There is not enough players to launch the game !");
			Language.addDefault("Language.Arena.Start", "&3The game started ! Good luck...");
			Language.addDefault("Language.Arena.Broadcast_leave", "&e&l%player &3left the arena !");
			Language.addDefault("Language.Arena.Leave", "&3You leave the arena !");
			Language.addDefault("Language.Arena.Win", "&3&lYou win the game !");
			Language.addDefault("Language.Arena.Broadcast_player_lost", "&e&l%player &3lost the game !");
			Language.addDefault("Language.Arena.Player_lost", "&3You lost the game !");
			Language.addDefault("Language.Arena.End", "&3End of the game !");
			Language.addDefault("Language.Arena.Server_reload", "&3Server reload ! You leave the arena...");
			Language.addDefault("Language.Arena.Death_message", "&a&l%killer &3smashed &a&l%player &3!");

			Language.addDefault("Language.Error.Arena_does_not_exist", "&cThe arena &l%arena &cdoesn't exist !");
			Language.addDefault("Language.Error.Not_enough_spawns", "&cNot enough spawns defined to join !");
			Language.addDefault("Language.Error.Not_ready", "&cThe arena isn't ready to play !");
			Language.addDefault("Language.Error.Not_permission", "&cYou don't have the permission !");
			Language.addDefault("Language.Error.Not_permission", "&cYou don't have the permission !");
			Language.addDefault("Language.Error.No_commands", "&3You can't use commands in a game ! ==> &l/oitc leave &3to leave the arena...");
			Language.addDefault("Language.Error.Wrong_command", "&cWrong command ! Type /oitc to see all the commands !");

			Language.addDefault("Language.Setup.Arena_succesfully_created", "&cThe arena &l%arena &chas been succesfully created !");
			Language.addDefault("Language.Setup.Arena_succesfully_deleted", "&cThe arena &l%arena &chas been succesfully deleted !");
			Language.addDefault("Language.Setup.Arena_already_exists", "&cThis arena already exists !");
			Language.addDefault("Language.Setup.Lobby_succesfully_set", "&cThe lobby has been succesfully set !");
			Language.addDefault("Language.Setup.Spawn_succesfully_added", "&cSpawn succesfully added for the arena &l%arena &c!");
			Language.addDefault("Language.Setup.Start_set", "&cThe start of the arena &l%arena &chas been succesfully set !");
			Language.addDefault("Language.Setup.Max_players_succesfully_set", "&cMax players set for the arena &l%arena &c!");
			Language.addDefault("Language.Setup.Min_players_succesfully_set", "&cMinimum players set for the arena &l%arena &c!");
			
			Language.addDefault("Language.Force.Start", "&cYou started the arene %arena !");
			Language.addDefault("Language.Force.Stop", "&cYou started the arene %arena !");
			Language.addDefault("Language.Force.Can_not_start", "&cYou can't start the arena %arena ! It is in game...");
			Language.addDefault("Language.Force.Can_not_stop", "&cYou can't stop the arena %arena ! It isn't in game...");

			Language.options().copyDefaults(true);

			try {
				Language.save(fichier_language);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File fichier_players = new File(getDataFolder() + File.separator + "Players.yml");
		FileConfiguration Players = YamlConfiguration.loadConfiguration(fichier_players);	

		if(!fichier_players.exists()){

			ArrayList<String> list = new ArrayList<String>();
			Players.addDefault("Players", list);

			Players.options().copyDefaults(true);

			try {
				Players.save(fichier_players);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerEvents(){

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerCommand(), this);	
		pm.registerEvents(new PlayerDeath(), this);		
		pm.registerEvents(new PlayerQuit(), this);		
		pm.registerEvents(new PlayerRespawn(), this);		
		pm.registerEvents(new EntityDamage(), this);		
		pm.registerEvents(new EntityDamageByEntity(), this);		
		pm.registerEvents(new PlayerPickupItem(), this);		
		pm.registerEvents(new PlayerDropItem(), this);		
		pm.registerEvents(new InventoryClick(), this);		
		pm.registerEvents(new BlockBreak(), this);		
		pm.registerEvents(new BlockPlace(), this);		
		pm.registerEvents(new FoodLevelChange(), this);		

	}

	private void updatePlayers(){

		File fichier_language = new File(OneInTheChamberMain.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String Prefixe = UtilChatColor.colorizeString(Language.getString("Language.Prefix"));
		String ServeurReload = UtilChatColor.colorizeString(Language.getString("Language.Arena.Server_reload"));

		if(Bukkit.getOnlinePlayers() != null){

			for(Arena arena : Arena.arenaObjects){
				ArrayList<String> players = new ArrayList<String>(arena.getPlayers());         
				Iterator<String> playersIterator =  players.iterator();
				while(playersIterator.hasNext()){

					Player player = Bukkit.getPlayer(playersIterator.next());

					player.getInventory().setArmorContents(null);
					player.getInventory().clear();
					player.getInventory().setContents(ArenaManager.inv.get(player.getName()));
					player.getInventory().setArmorContents(ArenaManager.armour.get(player.getName()));
					player.setLevel(ArenaManager.level.get(player.getName()));

					ScoreboardManager ScMan = OneInTheChamberMain.instance.getServer().getScoreboardManager();

					player.setScoreboard(ScMan.getNewScoreboard());

					player.setGameMode(GameMode.valueOf(ArenaManager.gamemode.get(player.getName())));

					player.setHealth(player.getMaxHealth());
					player.setFireTicks(0);

					FileConfiguration config = OneInTheChamberMain.instance.getConfig();
					Location lobby = new Location(Bukkit.getWorld(config.getString("Lobby.World")), config.getDouble("Lobby.X"), config.getDouble("Lobby.Y"), config.getDouble("Lobby.Z"), (float)config.getDouble("Lobby.Yaw"), (float)config.getDouble("Lobby.Pitch"));

					player.teleport(lobby);

					player.sendMessage(Prefixe + " " + ServeurReload);

					playersIterator.remove();
				}
			}
		}
	}
}
