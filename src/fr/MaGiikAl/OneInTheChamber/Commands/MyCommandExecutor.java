package fr.MaGiikAl.OneInTheChamber.Commands;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.MaGiikAl.OneInTheChamber.Main.OneInTheChamber;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilChatColor;
import fr.MaGiikAl.OneInTheChamber.Utils.UtilSendMessage;

public class MyCommandExecutor implements CommandExecutor{

	private HashMap<String, BasicCommand> commands = new HashMap<String, BasicCommand>();

	public MyCommandExecutor(){
		loadCommands();
	}

	private void loadCommands(){
		this.commands.put("join", new JoinCommand());
		this.commands.put("lobby", new LobbyCommand());
		this.commands.put("leave", new LeaveCommand());
		this.commands.put("reload", new ReloadCommand());
		this.commands.put("stats", new StatsCommand());
		this.commands.put("create", new CreateCommand());
		this.commands.put("remove", new RemoveCommand());
		this.commands.put("delete", new RemoveCommand());
		this.commands.put("setlobby", new SetLobbyCommand());
		this.commands.put("addspawn", new AddSpawnCommand());
		this.commands.put("setminplayers", new SetMinPlayersCommand());
		this.commands.put("setmaxplayers", new SetMaxPlayersCommand());
		this.commands.put("setlives", new SetLivesCommand());
		this.commands.put("setpoints", new SetPointsCommand());
		this.commands.put("settype", new SetTypeCommand());
		this.commands.put("setcountdown", new SetCountdownCommand());
		this.commands.put("setdisplayname", new SetDisplayNameCommand());
		this.commands.put("active", new ActiveCommand());
		this.commands.put("disactive", new DisactiveCommand());
		this.commands.put("stop", new StopCommand());
		this.commands.put("setstart", new SetStartLocationCommand());
		this.commands.put("setprivatechat", new SetPrivateChatCommand());
		this.commands.put("update", new UpdateCommand());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;

		if ((sender instanceof Player)) {
			player = (Player)sender;
		}
		else {
			sender.sendMessage("You need to be a player !");
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("oitc")){
			if(args == null || args.length < 1){
				help(player);
				return true;
			}
			if(args[0].equalsIgnoreCase("help")){
				help(player);
				return true;
			}
			String sub = args[0];

			Vector<String> l = new Vector<String>();
			l.addAll(Arrays.asList(args));
			l.remove(0);
			args = (String[])l.toArray(new String[0]);

			if (!this.commands.containsKey(sub)) {

				File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
				FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

				String wrongCommand = Language.getString("Language.Error.Wrong_command");

				UtilSendMessage.sendMessage(player, wrongCommand);
				return true;
			}

			try {
				((BasicCommand)this.commands.get(sub)).onCommand(player, args);
			} catch (Exception e) {
				e.printStackTrace();
				player.sendMessage(ChatColor.RED + "An error occured while executing the command. Check the console");
				player.sendMessage(ChatColor.BLUE + "Type /oitc help for help");
			}
			return true;

		}
		return true;
	}

	private void help(Player player){

		File fichier_language = new File(OneInTheChamber.instance.getDataFolder() + File.separator + "Language.yml");
		FileConfiguration Language = YamlConfiguration.loadConfiguration(fichier_language);

		String menu = UtilChatColor.colorizeString(Language.getString("Language.Help.Menu"));

		player.sendMessage(menu);

		for(BasicCommand bc : this.commands.values()){
			if(!bc.help(player).isEmpty()){
				player.sendMessage(UtilChatColor.colorizeString(bc.help(player)));
			}
		}
	}

}
