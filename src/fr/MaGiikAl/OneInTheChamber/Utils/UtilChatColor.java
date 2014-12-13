package fr.MaGiikAl.OneInTheChamber.Utils;

import org.bukkit.ChatColor;

public class UtilChatColor {

	public static String colorizeString(String string){

		if(string == null) return "";
		return ChatColor.translateAlternateColorCodes('&', string).replace("<3", "❤").replace("[*]", "★").replace("[**]", "✹").replace("[p]", "●").replace("[v]", "✔").replace("[+]", "♦").replace("[++]", "✦");

	}

}
