package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class task2 extends BukkitRunnable{
	main plugin;
	task2(main c){
		plugin=c;
	}

	public void run(){
		messages m = new messages(plugin);
		m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand]"+ChatColor.GREEN+"Grace Period is over!");
		plugin.getConfig().set("Gracep", false);
		plugin.getConfig().set("t1done", false);
		plugin.saveConfig();
		BukkitTask task = new antiCamp(plugin).runTaskLater(plugin, 200);
	}
}
