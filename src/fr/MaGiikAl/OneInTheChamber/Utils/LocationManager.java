package fr.MaGiikAl.OneInTheChamber.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationManager {

	public static String locToString(Location loc){
		return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
	}
	public static Location stringToLoc(String str){
		String[] args = str.split(", ");
		return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
	}
}
