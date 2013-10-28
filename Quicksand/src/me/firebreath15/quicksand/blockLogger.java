package me.firebreath15.quicksand;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class blockLogger implements Listener{
	
	main plugin;
	blockLogger(main c){
		plugin=c;
	}
	
	
	private HashMap<Integer, Location> toph = new HashMap<Integer, Location>();
	private HashMap<Integer, Location> tnth = new HashMap<Integer, Location>();
	private HashMap<Integer, String> type = new HashMap<Integer, String>();
	
	@EventHandler
	public void onPlayerRun(PlayerMoveEvent e){
		if(plugin.getConfig().getBoolean("isinsession")==true){
			if(plugin.getConfig().getBoolean("gamestarted")==true){
				if(plugin.getConfig().contains("players."+e.getPlayer().getName())){
					Delta d = new Delta();
					Player p = e.getPlayer();
					if((d.ifChangeWasOne(e.getFrom().getBlockX(), e.getTo().getBlockX()) || d.ifChangeWasOne(e.getFrom().getBlockZ(), e.getTo().getBlockZ())) && (d.ifChangeWasZero(e.getFrom().getBlockY(), e.getTo().getBlockY()))){
						Location top = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ());
						Location tnt = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()-2, p.getLocation().getBlockZ());
						
						if(top.getBlock().getType()==Material.SAND || top.getBlock().getType()==Material.GRAVEL){
							
							toph.put(toph.size()+1, top);
							tnth.put(tnth.size()+1, tnt);
							type.put(type.size()+1, top.getBlock().getType().toString());
							
							top.getBlock().setType(Material.AIR);
							tnt.getBlock().setType(Material.AIR);
						}
						
						if(p.getLocation().getBlock().getType()==Material.WATER || p.getLocation().getBlock().getType()==Material.STATIONARY_WATER){
							messages m = new messages(plugin);
							m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has lost!");
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You lost! You are now a spectator!");
							
							plugin.getConfig().set("players."+p.getName(), null);
							plugin.getConfig().set(p.getName(), null);
							plugin.getConfig().createSection("dead."+p.getName());
							int pn = plugin.getConfig().getInt("playernum");
							plugin.getConfig().set("playernum", pn-1);
							plugin.saveConfig();
							
							int x = plugin.getConfig().getInt("spawn.x");
							int y = plugin.getConfig().getInt("spawn.y");
							int z = plugin.getConfig().getInt("spawn.z");
							String w = plugin.getConfig().getString("spawn.world");
							World wo = plugin.getServer().getWorld(w);
							Location l = new Location(wo,x,y,z);
							p.teleport(l);
							
							if(plugin.getConfig().getInt("playernum") >= 2){
								Player[] op = Bukkit.getServer().getOnlinePlayers();
								for(int i=0; i<op.length; i++){
									if(plugin.getConfig().contains("players."+op[i].getName())){
										op[i].hidePlayer(p);
										op[i].setAllowFlight(true);
										op[i].setFlying(true);
									}
								}
							}else{
								this.findawinner();
							}
							
						}
					}else{
						//if they didn't move one block, check to see if they died. If they did, we'll do the same as we did above
						if(p.getLocation().getBlock().getType()==Material.WATER || p.getLocation().getBlock().getType()==Material.STATIONARY_WATER){
							messages m = new messages(plugin);
							m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has lost!");
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You lost! You are now a spectator!");
							
							plugin.getConfig().set("players."+p.getName(), null);
							plugin.getConfig().set(p.getName(), null);
							plugin.getConfig().createSection("dead."+p.getName());
							int pn = plugin.getConfig().getInt("playernum");
							plugin.getConfig().set("playernum", pn-1);
							plugin.saveConfig();
							
							int x = plugin.getConfig().getInt("spawn.x");
							int y = plugin.getConfig().getInt("spawn.y");
							int z = plugin.getConfig().getInt("spawn.z");
							String w = plugin.getConfig().getString("spawn.world");
							World wo = plugin.getServer().getWorld(w);
							Location l = new Location(wo,x,y,z);
							p.teleport(l);
							
							if(plugin.getConfig().getInt("playernum") >= 2){
								Player[] op = Bukkit.getServer().getOnlinePlayers();
								for(int i=0; i<op.length; i++){
									if(plugin.getConfig().contains("players."+op[i].getName())){
										op[i].hidePlayer(p);
										op[i].setAllowFlight(true);
										op[i].setFlying(true);
									}
								}
							}else{
								this.findawinner();
							}
							
						}
					}
				}
			}
		}
	}
	
	private void findawinner(){
		//this is being called because there is only 1 player left in the game.
		plugin.getConfig().set("isinsession", false);
		plugin.getConfig().set("gamestarted", false);
		plugin.saveConfig();
		Player[] op = Bukkit.getServer().getOnlinePlayers();
		for(int i=0; i<op.length; i++){
			if(plugin.getConfig().contains("players."+op[i].getName())){
				int x = plugin.getConfig().getInt("end.x");
				int y = plugin.getConfig().getInt("end.y");
				int z = plugin.getConfig().getInt("end.z");
				String w = plugin.getConfig().getString("end.world");
				World wo = plugin.getServer().getWorld(w);
				Location l = new Location(wo,x,y,z);
				op[i].teleport(l);
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+op[i].getName()+" WON the game!");
				plugin.getConfig().set("players."+op[i].getName(), null);
				plugin.getConfig().set(op[i].getName(), null);
				plugin.saveConfig();
			}
			
			if(plugin.getConfig().contains("dead."+op[i].getName())){
				plugin.getConfig().set("dead."+op[i].getName(), null);
				plugin.saveConfig();
				
				int x = plugin.getConfig().getInt("end.x");
				int y = plugin.getConfig().getInt("end.y");
				int z = plugin.getConfig().getInt("end.z");
				String w = plugin.getConfig().getString("end.world");
				World wo = plugin.getServer().getWorld(w);
				Location l = new Location(wo,x,y,z);
				op[i].teleport(l);
				
				op[i].setFlying(false);
				op[i].setAllowFlight(false);
				
				Player[] oop = Bukkit.getServer().getOnlinePlayers();
				for(int u=0; u<oop.length; u++){
					oop[u].showPlayer(op[i]); //we want to show them to the others again, don't we?
				}				
			}
		}
		
		this.restoreGame(); //we call this after everything else is all said-and-done
	}
	
	private void restoreGame(){
		
		//restore TNT
		for(int i=0; i<tnth.size()+1; i++){
			if(tnth.containsKey(i)){
				Location l = tnth.get(i);
				l.getBlock().setType(Material.TNT);
			}
		}
		
		//restore sand or gravel
		for(int i=0; i<type.size()+1; i++){
			
			if(type.containsKey(i)){
			
				if(type.get(i).equalsIgnoreCase("SAND")){
					Location l = toph.get(i);
					l.getBlock().setType(Material.SAND);
				}
			
				if(type.get(i).equalsIgnoreCase("GRAVEL")){
					Location l = toph.get(i);
					l.getBlock().setType(Material.GRAVEL);
				}
				
			}
		}
	}
}
