package fr.MaGiikAl.OneInTheChamber.Sign;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.MaGiikAl.OneInTheChamber.Arena.Arena;
import fr.MaGiikAl.OneInTheChamber.Arena.ArenaManager;
import fr.MaGiikAl.OneInTheChamber.Arena.Status;
import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.LocationManager;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;

public class SignManager {

	public static HashMap<Arena, ArrayList<Sign>> signs = new HashMap<Arena, ArrayList<Sign>>();

	public static void loadSigns(){

		File fichier_signs = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Signs.yml");
		YamlConfiguration signsFile = YamlConfiguration.loadConfiguration(fichier_signs);

		if(ArenaManager.getArenaManager().getArenas() != null){
			for(Arena arena : ArenaManager.getArenaManager().getArenas()){
				if(signsFile.contains("Arenas." + arena.getName() + ".Signs")){
					List<String> locsSigns = signsFile.getStringList("Arenas." + arena.getName() + ".Signs");
					ArrayList<Sign> signsList = new ArrayList<Sign>();
					for(String locSign : locsSigns){
						Location loc = LocationManager.stringToLoc(locSign);
						if(loc.getBlock().getState() instanceof Sign){
							signsList.add((Sign)loc.getBlock().getState());
						}
					}
					signs.put(arena, signsList);
				}
			}
		}
	}

	public static void addSign(Arena arena, Sign sign){
		if(sign != null){
			ArrayList<Sign> arenaSigns = new ArrayList<Sign>();
			if(signs.get(arena) != null){
				arenaSigns = signs.get(arena);
				if(signs.get(arena).contains(sign)) return;
			}
			arenaSigns.add(sign);
			signs.put(arena, arenaSigns);

			File fichier_signs = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Signs.yml");
			YamlConfiguration signsFile = YamlConfiguration.loadConfiguration(fichier_signs);

			List<String> locsArenaSign = new ArrayList<String>();  

			for(Sign arenaSign : signs.get(arena)){
				String locArenaSign = LocationManager.locToString(arenaSign.getLocation());
				locsArenaSign.add(locArenaSign);
			}

			signsFile.set("Arenas." + arena.getName() + ".Signs", locsArenaSign);

			try {
				signsFile.save(fichier_signs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static ArrayList<Sign> getArenaSigns(Arena arena){
		if(signs.get(arena) == null) return null;
		if(signs.get(arena).isEmpty()) return null;
		return signs.get(arena);
	}

	public static void removeSign(Sign sign){
		if(sign != null){
			for(Entry<Arena, ArrayList<Sign>> entry : signs.entrySet()){
				if(entry.getValue().contains(sign)){
					ArrayList<Sign> arenaSigns = entry.getValue();
					arenaSigns.remove(sign);
					signs.put(entry.getKey(), arenaSigns);

					File fichier_signs = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Signs.yml");
					YamlConfiguration signsFile = YamlConfiguration.loadConfiguration(fichier_signs);

					List<String> locsArenaSign = new ArrayList<String>();  

					for(Sign arenaSign : signs.get(entry.getKey())){
						String locArenaSign = LocationManager.locToString(arenaSign.getLocation());
						locsArenaSign.add(locArenaSign);
					}

					signsFile.set("Arenas." + entry.getKey().getName() + ".Signs", locsArenaSign);

					try {
						signsFile.save(fichier_signs);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void updateSigns(Arena arena){

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);	

		String name = UtilChatColor.colorizeString(Language.getString("Language.Signs.Name"));
		String joinable = UtilChatColor.colorizeString(Language.getString("Language.Signs.Joinable"));
		String ingame = UtilChatColor.colorizeString(Language.getString("Language.Signs.InGame"));
		String starting = UtilChatColor.colorizeString(Language.getString("Language.Signs.Starting"));
		String notJoinable = UtilChatColor.colorizeString(Language.getString("Language.Signs.Not_joinable"));
		String arenaString = UtilChatColor.colorizeString(Language.getString("Language.Signs.Arena")).replaceAll("%arena", arena.getDisplayName());
		String numberinarena = UtilChatColor.colorizeString(Language.getString("Language.Signs.Number_in_arena")).replaceAll("%number", arena.getPlayers().size() + "").replaceAll("%maxplayers", arena.getMaxPlayers() + "");

		if(getArenaSigns(arena) != null){
			for(Sign arenaSign : getArenaSigns(arena)){
				arenaSign.setLine(0, name);
				arenaSign.setLine(1, arenaString);

				if(arena.getStatus() == Status.JOINABLE){
					arenaSign.setLine(2, joinable);
				}
				if(arena.getStatus() == Status.INGAME){
					arenaSign.setLine(2, ingame);
				}
				if(arena.getStatus() == Status.STARTING){
					arenaSign.setLine(2, starting);
				}
				if(arena.getStatus() == Status.NOTJOINABLE){
					arenaSign.setLine(2, notJoinable);
				}
				arenaSign.setLine(3, numberinarena);

				arenaSign.update();
			}
		}
	}
}
