package me.firebreath15.quicksand;

import me.firebreath15.quicksand.Events.PlayerJoinArenaEvent;
import me.firebreath15.quicksand.Events.PlayerLeaveEvent;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuicksandCommandExecutor implements CommandExecutor
{
	private final QuicksandPlugin plugin;

	public QuicksandCommandExecutor(QuicksandPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!cmd.getName().equalsIgnoreCase("quicksand") && !cmd.getName().equalsIgnoreCase("qs"))
			return false;

		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		Arena arena = args.length > 1 ? this.plugin.getArena(args[1]) : null;

		switch (args.length > 0 ? args[0].toLowerCase() : "help") {
			case "setlobby":
				if (arena == null) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", player);
				} else {
					arena.setup("lobby", player, "quicksand.setlobby", "You set QUICKSAND's lobby! Players will teleport here before the game starts.");
				}
				break;
			case "setspawn":
				if (arena == null) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", player);
				} else {
					arena.setup("spawn", player, "quicksand.setspawn", "You set QUICKSAND's spawnpoint! Players will teleport here when the game starts!");
				}
				break;
			case "setend":
				if (arena == null) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", player);
				} else {
					arena.setup("end", player, "quicksand.setend", "You set QUICKSAND's end point! Players will teleport here when the game is over.");
				}
				break;
			case "configure":
				if (!player.hasPermission("quicksand.config")) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You don't have permission!", player);
					return true;
				}

				if (arena == null) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", player);
					return true;
				}

				if (args.length < 4) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter a settings and its new value!", player);
					return true;
				}

				switch (args[2].toLowerCase()) {
					case "countdown":
						arena.getSettingsApi().setCountdown(Integer.parseInt(args[3]));
						this.plugin.getChatApi().announce("Countdown is set to " + arena.getSettingsApi().getCountdown(), player);
						break;
					case "players":
						arena.getSettingsApi().setMinPlayers(Integer.parseInt(args[3]));
						this.plugin.getChatApi().announce("minPlayers is set to " + arena.getSettingsApi().getMinPlayers(), player);
						break;
					default:
						this.plugin.getChatApi().announce(ChatColor.RED + "There is no setting called \"" + args[3] + "\"!", player);
						return true;
				}

				break;
			case "join":
				if (player.hasPermission("quicksand.join")) {
					this.plugin.getServer().getPluginManager().callEvent(new PlayerJoinArenaEvent(player, arena));
				} else if (arena == null) {
					this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", player);
				} else {
					arena.announce(ChatColor.RED + "You don't have permission!", player);
				}
				break;
			case "leave":
				this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(player));
				break;
			default:
				player.sendMessage(ChatColor.GOLD + "==========[ " + ChatColor.YELLOW + "QUICKSAND " + ChatColor.GOLD + "]==========");
				player.sendMessage(ChatColor.RED + "/quicksand setSpawn <arena> " + ChatColor.RESET + "- sets the spawnpoint for the game.");
				player.sendMessage(ChatColor.RED + "/quicksand setLobby <arena> " + ChatColor.RESET + "- sets the waiting room for the game.");
				player.sendMessage(ChatColor.RED + "/quicksand setEnd <arena> " + ChatColor.RESET + "- sets the place where players go at the end of the game.");
				player.sendMessage(ChatColor.RED + "/quicksand configure <arena> countdown <seconds>" + ChatColor.RESET + "- sets the duration of the countdown.");
				player.sendMessage(ChatColor.RED + "/quicksand configure <arena> players <number>" + ChatColor.RESET + "- sets the minimum amount of players required to start the game.");
				player.sendMessage(ChatColor.RED + "/quicksand join <arena> " + ChatColor.RESET + "- join QUICKSAND!");
				player.sendMessage(ChatColor.RED + "/quicksand leave " + ChatColor.RESET + "- leave QUICKSAND!");
				player.sendMessage(ChatColor.GOLD + "==========[ " + ChatColor.YELLOW + "QUICKSAND " + ChatColor.GOLD + "]==========");
				break;
		}

		return true;
	}
}
