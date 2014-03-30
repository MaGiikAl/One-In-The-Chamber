package fr.MaGiikAl.OneInTheChamber.Arena;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamberMain;

public class Arena {

	public static ArrayList<Arena> arenaObjects = new ArrayList<Arena>();
	public static ArrayList<String> arenas = new ArrayList<String>();

	private String name;

	private int maxplayers;
	private int minplayers;

	private Location startLocation;

	private ArrayList<Location> spawnsLocations;

	private Status status = Status.JOINABLE;

	private ArrayList<String> players = new ArrayList<String>();

	public Arena(String arenaName, Location startLocation, ArrayList<Location> spawnsLocations, int minplayers, int maxplayers){
		this.name = arenaName;
		this.spawnsLocations = spawnsLocations;

		this.startLocation = startLocation;

		this.minplayers = minplayers;
		this.maxplayers = maxplayers;

		arenas.add(arenaName);
		arenaObjects.add(this);
	}

	public String getName(){
		return this.name;
	}


	public ArrayList<Location> getSpawnsLocations(){
		return this.spawnsLocations;
	}

	public int getMaxPlayers(){
		return this.maxplayers;
	}

	public int getMinPlayers(){
		return this.minplayers;
	}
	public Location getStartLocation(){
		return this.startLocation;
	}


	public void setMaxPlayers(int i){
		ArenaManager.getArenaManager().deleteArena(this.name);

		this.maxplayers = i;

		ArenaManager.getArenaManager().createArena(this.name, this.startLocation, this.spawnsLocations, this.minplayers, this.maxplayers);

	}

	public void setMinPlayers(int i){
		ArenaManager.getArenaManager().deleteArena(this.name);

		this.minplayers = i;

		ArenaManager.getArenaManager().createArena(this.name, this.startLocation, this.spawnsLocations, this.minplayers, this.maxplayers);

	}


	public ArrayList<String> getPlayers(){
		return this.players;	
	}

	public void setName(String arenaName){
		ArenaManager.getArenaManager().deleteArena(this.name);

		this.name = arenaName;

		ArenaManager.getArenaManager().createArena(this.name, this.startLocation, this.spawnsLocations, this.minplayers, this.maxplayers);
	}
	public Status getStatus(){
		return this.status;
	}

	public void setStatus(Status status){
		this.status = status;
	}

	public void setStartLocation(Location startLocation){
		this.startLocation = startLocation;
		ArenaManager.getArenaManager().deleteArena(this.name);
		ArenaManager.getArenaManager().createArena(this.name, this.startLocation, this.spawnsLocations, this.minplayers, this.maxplayers);
	}

	public void addSpawnLocation(Location loc){
		spawnsLocations.add(loc);
		ArenaManager.getArenaManager().deleteArena(this.name);
		ArenaManager.getArenaManager().createArena(this.name, this.startLocation, this.spawnsLocations, this.minplayers, this.maxplayers);
	}

	public boolean isFull(){
		if (this.players.size() >= this.maxplayers){
			return true;
		} else {
			return false;
		}
	}

	public boolean isReady(){
		if(OneInTheChamberMain.instance.getConfig().getString("Arenas." + this.name + ".World") != null && OneInTheChamberMain.instance.getConfig().getInt("Arenas." + this.name + ".MaxPlayers") != 0 && OneInTheChamberMain.instance.getConfig().getConfigurationSection("Arenas." + this.name + ".Spawns") != null && OneInTheChamberMain.instance.getConfig().getConfigurationSection("Arenas." + this.name + ".Start") != null && OneInTheChamberMain.instance.getConfig().getString("Lobby.World") != null){
			return true;
		}
		else{
			return false;
		}
	}

	public void addPlayer(Player player) {		           	
		player.getInventory().setArmorContents(null); 
		player.setHealth(player.getMaxHealth());
		player.setFireTicks(0);
		player.setLevel(0);
		player.setExp(0);
		player.setGameMode(GameMode.ADVENTURE);	
		player.getInventory().clear();

		player.teleport(this.getStartLocation());
	}
}
