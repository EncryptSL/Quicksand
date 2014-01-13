package me.firebreath15.quicksand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class prepareGame extends BukkitRunnable{
	
	Player newp;
	
	main plugin;
	prepareGame(main c, Player np){
		plugin=c;
		newp=np;
	}
	
	
	@SuppressWarnings("unused")
	public void run(){
		if(plugin.getConfig().getInt("playernum") == 1){
			plugin.getServer().getPluginManager().registerEvents(new blockLogger(plugin), plugin);
		}
		
		if(plugin.getConfig().getInt("playernum") == 2){
			//send players to spawn and start the session so new players cannot join
			Player[] op = Bukkit.getServer().getOnlinePlayers();
			for(int i=0; i<op.length; i++){
				if(plugin.getConfig().contains("players."+op[i].getName())){
					int x = plugin.getConfig().getInt("spawn.x");
					int y = plugin.getConfig().getInt("spawn.y");
					int z = plugin.getConfig().getInt("spawn.z");
					String w = plugin.getConfig().getString("spawn.world");
					World wo = plugin.getServer().getWorld(w);
					Location l = new Location(wo,x,y,z);
					op[i].teleport(l);
					
					messages m = new messages(plugin);
					m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Game starting in "+ChatColor.RED+"20 seconds");
					
					BukkitTask start = new gameStart(plugin).runTaskLater(plugin, 400);
				}
			}
			
		}
		
		if(plugin.getConfig().getInt("playernum") >= 3){
			//not beginning the start cycle, there are others so now we join them personally.
			if(plugin.getConfig().contains("players."+newp.getName())){
				int x = plugin.getConfig().getInt("spawn.x");
				int y = plugin.getConfig().getInt("spawn.y");
				int z = plugin.getConfig().getInt("spawn.z");
				String w = plugin.getConfig().getString("spawn.world");
				World wo = plugin.getServer().getWorld(w);
				Location l = new Location(wo,x,y,z);
				newp.teleport(l);
				
				messages m = new messages(plugin);
				m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+newp.getName()+" joined the game!");
			}
		}
		
	}
}
