package fr.MaGiikAl.OneInTheChamber.Arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.LocationManager;

public class ArenaManager {

	static ArenaManager am = new ArenaManager();

	public HashMap<String, Arena> arenas = new HashMap<String, Arena>();

	public static ArenaManager getArenaManager(){
		return am;
	}
	public Arena getArenaByName(String arenaName){
		if(this.arenas.size() < 1) return null;
		return (Arena)this.arenas.get(arenaName);
	}

	public ArrayList<Arena> getArenas(){
		ArrayList<Arena> arenasList = new ArrayList<Arena>();

		if(this.arenas.values().isEmpty()) return null;

		for(Arena arena : this.arenas.values()){
			arenasList.add(arena);
		}
		return arenasList;
	}

	public Arena getArenaByPlayer(Player player){
		for(Arena arena : this.arenas.values()){
			if(arena.getPlayers().contains(PlayerArena.getPlayerArenaByPlayer(player))){
				return arena;
			}
		}return null;
	}

	public static void loadArenas(){

		File dossier_arenas = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Arenas");

		if(dossier_arenas.exists()){
			if(dossier_arenas.listFiles().length != 0){
				File[] arenasFiles = dossier_arenas.listFiles();
				for(File arenaFile : arenasFiles){
					String arenaName = arenaFile.getName().replaceAll(".yml", "");
					getArenaManager().createArena(arenaName);
				}
			}else{
				System.out.println("No arenas found !");
			}
		}
	}

	public void createArena(String arenaName){

		if(arenaName == null) return;

		Arena arena = new Arena(arenaName);

		this.arenas.put(arenaName, arena);

		File fichier_arena = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Arenas" + File.separator + arenaName + ".yml");

		if(fichier_arena.exists()){
			YamlConfiguration arenaFile = YamlConfiguration.loadConfiguration(fichier_arena);

			if(arenaFile.contains("DisplayName")){
				String displayName = arenaFile.getString("DisplayName");
				arena.setDisplayName(displayName);
			}
			if(arenaFile.contains("Type")){
				Type type = Type.valueOf(arenaFile.getString("Type"));
				arena.setType(type);
			}
			if(arenaFile.contains("Active")){
				boolean active = arenaFile.getBoolean("Active");
				arena.setActive(active);
			}
			if(arenaFile.contains("Lives")){
				int lives = arenaFile.getInt("Lives");
				arena.setLives(lives);
			}
			if(arenaFile.contains("Points")){
				int points = arenaFile.getInt("Points");
				arena.setMaxPoints(points);
			}
			if(arenaFile.contains("MaxPlayers")){
				int mp = arenaFile.getInt("MaxPlayers");
				arena.setMaxPlayers(mp);
			}
			if(arenaFile.contains("MinPlayers")){
				int mp = arenaFile.getInt("MinPlayers");
				arena.setMinPlayers(mp);
			}
			if(arenaFile.contains("CountdownBeforeStart")){
				int countdown = arenaFile.getInt("CountdownBeforeStart");
				arena.setCountdownBeforeStart(countdown);
			}
			if(arenaFile.contains("PrivateChat")){
				boolean privateChat = arenaFile.getBoolean("PrivateChat");
				arena.setPrivateChat(privateChat);
			}

			if(arenaFile.contains("StartLocation") && !arenaFile.getString("StartLocation").isEmpty()){
				String startLocation = arenaFile.getString("StartLocation");
				arena.setStartLocation(LocationManager.stringToLoc(startLocation));
			}
			if(arenaFile.contains("Spawns") && !arenaFile.getStringList("Spawns").isEmpty()){
				List<String> spawnsLocations = arenaFile.getStringList("Spawns");

				for(String spawnLocation : spawnsLocations){
					arena.addSpawnLocation(LocationManager.stringToLoc(spawnLocation));
				}
			}
			arena.saveConfig();
		}else{
			arena.saveConfig();
		}
	}

	public void deleteArena(Arena arena){

		File fichier_signs = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Signs.yml");
		YamlConfiguration signsFile = YamlConfiguration.loadConfiguration(fichier_signs);

		signsFile.set("Arenas." + arena.getName(), null);

		try {
			signsFile.save(fichier_signs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.arenas.remove(arena.getName());
		File fichier_arena = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Arenas" + File.separator + arena.getName() + ".yml");
		fichier_arena.delete();

	}

	public void setLobby(Location loc){

		File fichier_lobby = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Lobby.yml");
		FileConfiguration Lobby = YamlConfiguration.loadConfiguration(fichier_lobby);

		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float yaw = loc.getYaw();
		float pitch = loc.getPitch();

		Lobby.set("Lobby.World", world);
		Lobby.set("Lobby.X", x);
		Lobby.set("Lobby.Y", y);
		Lobby.set("Lobby.Z", z);
		Lobby.set("Lobby.Yaw", yaw);
		Lobby.set("Lobby.Pitch", pitch);

		try {
			Lobby.save(fichier_lobby);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Location getLobbyLocation(){
		File fichier_lobby = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Lobby.yml");
		FileConfiguration Lobby = YamlConfiguration.loadConfiguration(fichier_lobby);

		World world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		float yaw = 0;
		float pitch = 0;

		if(isLobbySet()){
			world = Bukkit.getWorld(Lobby.getString("Lobby.World"));
			x = Lobby.getDouble("Lobby.X");
			y = Lobby.getDouble("Lobby.Y");
			z = Lobby.getDouble("Lobby.Z");
			yaw = (float) Lobby.getDouble("Lobby.Yaw");
			pitch = (float) Lobby.getDouble("Lobby.Pitch");
		}else{
			return null;
		}
		return new Location(world, x, y, z, yaw, pitch);
	}

	private boolean isLobbySet() {

		File fichier_lobby = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Lobby.yml");
		FileConfiguration Lobby = YamlConfiguration.loadConfiguration(fichier_lobby);

		if(Lobby.contains("Lobby.World") && Lobby.contains("Lobby.X") && Lobby.contains("Lobby.Y") && Lobby.contains("Lobby.Z") && Lobby.contains("Lobby.Yaw") && Lobby.contains("Lobby.Pitch")){
			if(!Lobby.getString("Lobby.World").isEmpty() && !Lobby.get("Lobby.X").toString().isEmpty() && !Lobby.get("Lobby.Y").toString().isEmpty() && !Lobby.get("Lobby.Z").toString().isEmpty() && !Lobby.get("Lobby.Yaw").toString().isEmpty() && !Lobby.get("Lobby.Pitch").toString().isEmpty()){
				return true;
			}
		}
		return false;
	}

	public boolean isInArena(Player player){
		if(PlayerArena.players.containsKey(player.getName())){
			return true;
		}
		return false;
	}

	public void stopArenas() {
		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String reload = Language.getString("Language.Arena.Server_reload");

		for(Arena a : this.arenas.values()){
			if(a != null){
				a.stop(reload, Status.JOINABLE);
			}
		}

	}
	public boolean exists(String arena){
		return Boolean.valueOf(this.arenas.containsKey(arena));
	}
}
