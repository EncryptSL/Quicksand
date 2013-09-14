package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class main extends JavaPlugin{
	public void onEnable(){
		this.reloadConfig();
		this.getConfig().set("players", null);
		this.getConfig().set("dead", null);
		this.getConfig().set("playernum", 0);
		this.getConfig().set("sand", null);
		this.getConfig().set("gravel", null);
		this.getConfig().set("cc", null);
		this.getConfig().set("sand.next", 1);
		this.getConfig().set("gravel.next", 1);
		this.getConfig().set("tnt", null);
		this.getConfig().set("tnt.next", 1);
		this.getConfig().set("t1done", false);
		this.getConfig().set("Gracep", true);
		this.getConfig().set("maxPlayers.max", 10);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new damageDealer(this), this);
		this.getServer().getPluginManager().registerEvents(new theRun(this), this);
		this.getServer().getPluginManager().registerEvents(new logout(this), this);
		this.getServer().getPluginManager().registerEvents(new joinSign(this), this);
		
		if(!this.getConfig().contains("maxPlayers")){
			this.getConfig().set("maxPlayers.active", false);
			this.saveConfig();
		}
	}
	public void onDisable(){
		this.getConfig().set("players", null);
		this.getConfig().set("dead", null);
		this.getConfig().set("playernum", 0);
		this.getConfig().set("sand", null);
		this.getConfig().set("sand.next", 1);
		this.getConfig().set("tnt", null);
		this.getConfig().set("tnt.next", 1);
		this.getConfig().set("gravel", null);
		this.getConfig().set("gravel.next", 1);
		this.saveConfig();
		
		regenerate r = new regenerate(this);
		r.restoreArena();
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
					p.sendMessage(ChatColor.RED+"/quicksand togglemax"+ChatColor.YELLOW+"- toggle the 10-player-max.");
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
								if(this.getConfig().getBoolean("Gracep")==true){
									if((this.getConfig().getBoolean("maxPlayers.active")==true && this.getConfig().getInt("maxPlayers.max") <= this.getConfig().getInt("playernum")) || (this.getConfig().getBoolean("maxPlayers.active")==false)){
										if(!(this.getConfig().contains("players."+p.getName()))){
											if(!(this.getConfig().contains("dead."+p.getName()))){
												double x = this.getConfig().getDouble("lobby.x");
												double y = this.getConfig().getDouble("lobby.y");
												double z = this.getConfig().getDouble("lobby.z");
												String wn = this.getConfig().getString("lobby.world");
												World w = this.getServer().getWorld(wn);
												Location l = new Location(w,x,y,z);
												p.teleport(l);
												p.getInventory().clear();
												this.getConfig().createSection("players."+p.getName());
												this.getConfig().createSection(p.getName());
												int pn = this.getConfig().getInt("playernum");
												this.getConfig().set("playernum", pn+1);
												this.saveConfig();
												messages m = new messages(this);
												
												if(pn+1 == 1){
													this.getServer().broadcastMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has joined "+ChatColor.YELLOW+"QUICKSAND"+ChatColor.GREEN+"!");
												}else{
													m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has joined "+ChatColor.YELLOW+"QUICKSAND"+ChatColor.GREEN+"!");
												}										
												BukkitTask task = new task1(this).runTaskLater(this, 60);
											}else{
												p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You're dead! You can't rejoin yet!");
											}
										}else{
											p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You're already playing "+ChatColor.YELLOW+"QUICKSAND "+ChatColor.RED+"!");
										}
									}else{
										p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"The game has reached the max player count. Sorry!");
									}
								}else{
									p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"The game has already begun! Please wait for it to be over.");
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
							double x = this.getConfig().getDouble("end.x");
							double y = this.getConfig().getDouble("end.y");
							double z = this.getConfig().getDouble("end.z");
							String wn = this.getConfig().getString("end.world");
							World w = this.getServer().getWorld(wn);
							Location l = new Location(w,x,y,z);
							this.getConfig().set("players."+p.getName(), null);
							int pn = this.getConfig().getInt("playernum");
							this.getConfig().set("playernum", pn-1);
							this.getConfig().set("dead."+p.getName(), null);
							this.saveConfig();
							p.teleport(l);
							Teleport t = new Teleport(this);
							t.makePlayerVisible(p);
							messages m = new messages(this);
							m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has left "+ChatColor.YELLOW+"QUICKSAND"+ChatColor.GREEN+".");
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Thanks for playing!");
							findWinner fw = new findWinner(this);
							fw.findAWinner();
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"You aren't playing!");
						}
					}
					if(args[0].equalsIgnoreCase("togglemax")){
						if(p.hasPermission("quicksand.togglemax")){
							if(this.getConfig().getBoolean("maxPlayers.active")==true){
								this.getConfig().set("maxPlayers.active", false);
								this.saveConfig();
								p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Toggled 10-player-max "+ChatColor.RED+"OFF");
							}else{
								this.getConfig().set("maxPlayers.active", true);
								this.saveConfig();								
								p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"Toggled 10-player-max "+ChatColor.RED+"ON");
							}
						}else{
							p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.DARK_RED+"You don't have permission!");
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
