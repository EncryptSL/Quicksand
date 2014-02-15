package me.firebreath15.quicksand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class main extends JavaPlugin{
	
	INVAPI api;
	
	public void onEnable(){
		this.reloadConfig();
		this.getConfig().set("players", null);
		this.getConfig().set("dead", null);
		this.getConfig().set("playernum", 0);
		this.getConfig().set("isinsession", false);
		this.getConfig().set("gamestarted", false);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new damageDealer(this), this);
		this.getServer().getPluginManager().registerEvents(new logout(this), this);
		this.getServer().getPluginManager().registerEvents(new joinSign(this), this);
		
		api = new INVAPI();
	}
	public void onDisable(){
		this.getConfig().set("players", null);
		this.getConfig().set("dead", null);
		this.getConfig().set("playernum", 0);
		this.getConfig().set("isinsession", false);
		this.getConfig().set("gamestarted", false);
		this.saveConfig();
		blockLogger.restoreGame();
	}
	
	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("quicksand") || cmd.getName().equalsIgnoreCase("qs")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(args.length==0){
					p.sendMessage(ChatColor.GOLD+"==========[ "+ChatColor.YELLOW+"QUICKSAND "+ChatColor.GOLD+"]==========");
					p.sendMessage(ChatColor.RED+"/quicksand setspawn "+ChatColor.YELLOW+"- sets the spawnpoint for the game!");
					p.sendMessage(ChatColor.RED+"/quicksand setlobby "+ChatColor.YELLOW+"- sets the waiting room for the game!");
					p.sendMessage(ChatColor.RED+"/quicksand setend "+ChatColor.YELLOW+"- sets the place where players go at the end of the game!");
					p.sendMessage(ChatColor.RED+"/quicksand join "+ChatColor.YELLOW+"- join QUICKSAND!");
					p.sendMessage(ChatColor.RED+"/quicksand leave "+ChatColor.YELLOW+"- leave QUICKSAND!");
					p.sendMessage(ChatColor.GOLD+"==========[ "+ChatColor.YELLOW+"QUICKSAND "+ChatColor.GOLD+"]==========");
				}
				if(args.length==1){
					String a = args[0];
					
					if(a.equalsIgnoreCase("setspawn")){
						if(p.hasPermission("quicksand.setspawn")){
							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							String wn = p.getWorld().getName();
							this.getConfig().set("spawn.x", x);
							this.getConfig().set("spawn.y", y);
							this.getConfig().set("spawn.z", z);
							this.getConfig().set("spawn.world", wn);
							this.saveConfig();
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"You set QUICKSAND's spawnpoint! Players will teleport here when the game starts!");
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.DARK_RED+"You don't have permission!");
						}
					}
					if(a.equalsIgnoreCase("setlobby")){
						if(p.hasPermission("quicksand.setlobby")){
							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							String wn = p.getWorld().getName();
							this.getConfig().set("lobby.x", x);
							this.getConfig().set("lobby.y", y);
							this.getConfig().set("lobby.z", z);
							this.getConfig().set("lobby.world", wn);
							this.saveConfig();
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"You set QUICKSAND's lobby! Players will teleport here before the game starts.");
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.DARK_RED+"You don't have permission!");
						}
					}
					if(a.equalsIgnoreCase("setend")){
						if(p.hasPermission("quicksand.setend")){
							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							String wn = p.getWorld().getName();
							this.getConfig().set("end.x", x);
							this.getConfig().set("end.y", y);
							this.getConfig().set("end.z", z);
							this.getConfig().set("end.world", wn);
							this.saveConfig();
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"You set QUICKSAND's end point! Players will teleport here when the game is over.");
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.DARK_RED+"You don't have permission!");
						}
					}
					if(a.equalsIgnoreCase("join")){
						if(this.getConfig().contains("lobby.x") && this.getConfig().contains("spawn.x") && this.getConfig().contains("end.x")){
							if(p.hasPermission("quicksand.join")){
								if(this.getConfig().getBoolean("isinsession")==false){
									if(!this.getConfig().contains("players."+p.getName())){
										api.storePlayerArmor(p.getName());
										api.storePlayerInventory(p.getName());
										int x = this.getConfig().getInt("lobby.x");
										int y = this.getConfig().getInt("lobby.y");
										int z = this.getConfig().getInt("lobby.z");
										String w = this.getConfig().getString("lobby.world");
										World wo = this.getServer().getWorld(w);
										Location l = new Location(wo,x,y,z);
										p.teleport(l);
										int pn = this.getConfig().getInt("playernum");
										this.getConfig().set("playernum", pn+1);
										this.getConfig().createSection("players."+p.getName());
										this.saveConfig();
										
										BukkitTask prepare = new prepareGame(this, p).runTaskLater(this, 20);
									}else{
										p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You are already queued to play!");
									}
								}else{
									p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"The game is already in session!");
								}
							}else{
								p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You don't have permission!");
							}
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.YELLOW+"QUICKSAND "+ChatColor.RED+"hasn't been set up yet! You need a lobby, spawn, and end!");
						}
					}
					if(a.equalsIgnoreCase("leave")){
						if(this.getConfig().contains("players."+p.getName()) || this.getConfig().contains("dead."+p.getName())){
							if(this.getConfig().contains("players."+p.getName())){
								this.getConfig().set("dead."+p.getName(), null);
								this.getConfig().set("players."+p.getName(), null);
								int pn = this.getConfig().getInt("playernum");
								this.getConfig().set("playernum", pn-1);
								this.getConfig().set(p.getName(), null);
								this.saveConfig();
								double x = this.getConfig().getDouble("end.x");
								double y = this.getConfig().getDouble("end.y");
								double z = this.getConfig().getDouble("end.z");
								String wn = this.getConfig().getString("end.world");
								World w = this.getServer().getWorld(wn);
								Location l = new Location(w,x,y,z);
								p.teleport(l);
								p.setFlying(false);
								p.setAllowFlight(false);
							}
							if(this.getConfig().contains("dead."+p.getName())){
								this.getConfig().set("dead."+p.getName(), null);
								this.saveConfig();
								
								int x = this.getConfig().getInt("end.x");
								int y = this.getConfig().getInt("end.y");
								int z = this.getConfig().getInt("end.z");
								String w = this.getConfig().getString("end.world");
								World wo = this.getServer().getWorld(w);
								Location l = new Location(wo,x,y,z);
								p.teleport(l);
								
								Player[] oop = Bukkit.getServer().getOnlinePlayers();
								for(int u=0; u<oop.length; u++){
									oop[u].showPlayer(p);
								}
								
								p.setFlying(false);
								p.setAllowFlight(false);
								
								p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Thanks for playing!");
							}
							
							if(this.getConfig().getInt("playernum")<=0){
								this.getConfig().set("playernum", 0);
								this.getConfig().set("isinsession", false);
								this.saveConfig();
							}
							
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You aren't playing!");
						}
					}
				}
			}
			return true;
		}else{
			
		}
		return false;
	}
}
