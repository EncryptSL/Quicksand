package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class task1 extends BukkitRunnable{
	main plugin;
	task1(main c){
		plugin=c;
	}
	
	public void run(){
		if(plugin.getConfig().getInt("playernum")==2){
			Player[] players = plugin.getServer().getOnlinePlayers();
			int pn = players.length;
			if(plugin.getConfig().contains("t1done") && plugin.getConfig().getBoolean("t1done")==false){
				/*
				 * t1done is a variable to say if we've done this before. If 2 players join at the same time,
				 * this will cause a loop and thus a chat spam of messages. We'll turn t1done to true here and
				 * back to false in task2, which is called IF t1done is false.
				 */
				plugin.getConfig().set("t1done", true);
				for(int i=0; i<pn; i++){
					if(plugin.getConfig().contains("players."+players[i].getName())){
						double x = plugin.getConfig().getDouble("spawn.x");
						double y = plugin.getConfig().getDouble("spawn.y");
						double z = plugin.getConfig().getDouble("spawn.z");
						String wn = plugin.getConfig().getString("spawn.world");
						World w = plugin.getServer().getWorld(wn);
						Location l = new Location(w,x,y,z);
						plugin.getConfig().set(players[i].getName(), null);
						plugin.saveConfig();
						players[i].setGameMode(GameMode.SURVIVAL);
						players[i].teleport(l);
						messages m = new messages(plugin);
						m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Game starting in "+ChatColor.RED+"20 seconds");
						plugin.getConfig().set("Gracep", true);
						@SuppressWarnings("unused")
						BukkitTask task = new task2(plugin).runTaskLater(plugin, 400);
					}
				}
				plugin.saveConfig();
			}
		}
		if(plugin.getConfig().getInt("playernum")>=3){
			Player[] players = plugin.getServer().getOnlinePlayers();
			int pn = players.length;
			for(int i=0; i<pn; i++){
				if(plugin.getConfig().contains(players[i].getName())){
					if(plugin.getConfig().contains("t1done") && plugin.getConfig().getBoolean("t1done")==false){
						plugin.getConfig().set("t1done", true);
							if(plugin.getConfig().contains("players."+players[i].getName())){
								double x = plugin.getConfig().getDouble("spawn.x");
								double y = plugin.getConfig().getDouble("spawn.y");
								double z = plugin.getConfig().getDouble("spawn.z");
								String wn = plugin.getConfig().getString("spawn.world");
								World w = plugin.getServer().getWorld(wn);
								Location l = new Location(w,x,y,z);
								plugin.getConfig().set(players[i].getName(), null);
								plugin.saveConfig();
								players[i].setGameMode(GameMode.SURVIVAL);
								players[i].teleport(l);
								plugin.getServer().broadcastMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Game starting in "+ChatColor.RED+"20 seconds");
								plugin.getConfig().set("Gracep", true);
								@SuppressWarnings("unused")
								BukkitTask task = new task2(plugin).runTaskLater(plugin, 400);
							}
						plugin.saveConfig();
					}else{
						double x = plugin.getConfig().getDouble("spawn.x");
						double y = plugin.getConfig().getDouble("spawn.y");
						double z = plugin.getConfig().getDouble("spawn.z");
						String wn = plugin.getConfig().getString("spawn.world");
						World w = plugin.getServer().getWorld(wn);
						Location l = new Location(w,x,y,z);
						plugin.getConfig().set(players[i].getName(), null);
						plugin.saveConfig();
						players[i].setGameMode(GameMode.SURVIVAL);
						players[i].teleport(l);
					}
				}
			}
		}
	}
}
