package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class findWinner {
	main plugin;
	findWinner(main c){
		plugin=c;
	}
	
	public void findAWinner(){
		// This should be called when a player has fallen into the void and is teleported back to the lobby.
		// Because one player died, we now have to see if theyre is another player left. If there is, then we
		// see if he is the LAST player left. If so then he wins.
		Player[] ps = plugin.getServer().getOnlinePlayers();
		int psn = ps.length;
		for(int i=0; i<psn; i++){
			Player p = ps[i];
			int plays = plugin.getConfig().getInt("playernum");
			if(plays==1){
				//If there is one player left
				if(plugin.getConfig().contains("players."+p.getName())){
					//If we found the last player alive					
					
					//teleport players to the end
					Teleport t = new Teleport(plugin);
					t.teleportAllToGameEnd();
					
					plugin.getConfig().set("players."+p.getName(), null);
					plugin.getConfig().set("dead", null);
					plugin.getConfig().set("playernum", 0);
					plugin.getConfig().set("Gracep", true);
					plugin.getConfig().set(p.getName(), null);
					plugin.saveConfig();
					plugin.getServer().broadcastMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName().toUpperCase()+" WON"+ChatColor.GREEN+"!!!");
					
					//regenerate arena
					regenerate r = new regenerate(plugin);
					r.restoreArena();
				}
			}
		}
	}
}
