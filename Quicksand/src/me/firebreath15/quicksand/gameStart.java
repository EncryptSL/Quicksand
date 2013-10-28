package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class gameStart extends BukkitRunnable{
	main plugin;
	gameStart(main c){
		plugin=c;
	}
	
	
	public void run(){
		plugin.getConfig().set("gamestarted", true);
		plugin.getConfig().set("isinsession", true);
		plugin.saveConfig();
		
		messages m = new messages(plugin);
		m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"The game has started! Good luck!");
	}
}
