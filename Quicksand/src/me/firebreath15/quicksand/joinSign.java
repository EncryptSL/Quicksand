package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;


public class joinSign implements Listener{
	main plugin;
	joinSign(main c){
		plugin=c;
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onSignClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType()==Material.SIGN_POST || e.getClickedBlock().getType()==Material.WALL_SIGN || e.getClickedBlock().getType()==Material.SIGN)){
			Sign s = (Sign)e.getClickedBlock().getState();
			if(s.getLine(0).equalsIgnoreCase("[Quicksand]")){
				if(plugin.getConfig().contains("lobby.x") && plugin.getConfig().contains("spawn.x") && plugin.getConfig().contains("end.x")){
						if(plugin.getConfig().getBoolean("Gracep")==true){
							if((plugin.getConfig().getBoolean("maxPlayers.active")==true && plugin.getConfig().getInt("maxPlayers.max") <= plugin.getConfig().getInt("playernum")) || (plugin.getConfig().getBoolean("maxPlayers.active")==false)){
								if(!(plugin.getConfig().contains("players."+p.getName()))){
									if(!(plugin.getConfig().contains("dead."+p.getName()))){
										double x = plugin.getConfig().getDouble("lobby.x");
										double y = plugin.getConfig().getDouble("lobby.y");
										double z = plugin.getConfig().getDouble("lobby.z");
										String wn = plugin.getConfig().getString("lobby.world");
										World w = plugin.getServer().getWorld(wn);
										Location l = new Location(w,x,y,z);
										p.teleport(l);
										plugin.api.storePlayerInventory(p.getName());
										plugin.api.storePlayerArmor(p.getName());
										plugin.getConfig().createSection("players."+p.getName());
										plugin.getConfig().createSection(p.getName());
										int pn = plugin.getConfig().getInt("playernum");
										plugin.getConfig().set("playernum", pn+1);
										plugin.saveConfig();
										messages m = new messages(plugin);
										
										if(pn+1 == 1){
											plugin.getServer().broadcastMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has joined "+ChatColor.YELLOW+"QUICKSAND"+ChatColor.GREEN+"!");
										}else{
											m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" has joined "+ChatColor.YELLOW+"QUICKSAND"+ChatColor.GREEN+"!");
										}										
										BukkitTask task = new task1(plugin).runTaskLater(plugin, 60);
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
					p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.YELLOW+"QUICKSAND "+ChatColor.RED+"hasn't been set up yet! You need a lobby, spawn, and end!");
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e){
		Player p = e.getPlayer();
		if(e.getLine(0).equalsIgnoreCase("[Quicksand]")){
			if(p.hasPermission("quicksand.sign")){
				e.setLine(0, "[Quicksand]");
				e.setLine(1, ChatColor.DARK_BLUE+"Click to play!");
				p.sendMessage(ChatColor.GREEN+"Sign created! Right-click it to play!");
			}else{
				e.setCancelled(true);
			}
		}
	}
}
