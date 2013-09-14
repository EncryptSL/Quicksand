package me.firebreath15.quicksand;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Teleport {
	main plugin;
	Teleport(main c){
		plugin=c;
	}
	
	public void teleportAllToGameEnd(){
		Player[] players = plugin.getServer().getOnlinePlayers();
		
		for (int i=0; i<players.length; i++){
			Player p = players[i];
			if(plugin.getConfig().contains("players."+p.getName()) || plugin.getConfig().contains("dead."+p.getName())){
				//now teleport all quicksand players to the end point.
				double x = plugin.getConfig().getDouble("end.x");
				double y = plugin.getConfig().getDouble("end.y");
				double z = plugin.getConfig().getDouble("end.z");
				String wn = plugin.getConfig().getString("end.world");
				World w = plugin.getServer().getWorld(wn);
				Location l = new Location(w,x,y,z);
				p.teleport(l);
				p.setFlying(false);
				p.setAllowFlight(false);
				this.makePlayerVisible(p);
				plugin.saveConfig();
			}
		}
	}
	
	public void makePlayerInvisible(Player p){
		Player[] players = plugin.getServer().getOnlinePlayers();
		
		for(int i=0; i<players.length; i++){
			players[i].hidePlayer(p);
		}
	}
	
	public void makePlayerVisible(Player p){
		Player[] players = plugin.getServer().getOnlinePlayers();
		
		for(int i=0; i<players.length; i++){
			players[i].showPlayer(p);
		}
	}
}
