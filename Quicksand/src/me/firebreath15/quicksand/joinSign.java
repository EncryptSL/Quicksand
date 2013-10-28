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
					if(plugin.getConfig().getBoolean("isinsession")==false){
						plugin.api.storePlayerArmor(p.getName());
						plugin.api.storePlayerInventory(p.getName());
						int x = plugin.getConfig().getInt("lobby.x");
						int y = plugin.getConfig().getInt("lobby.y");
						int z = plugin.getConfig().getInt("lobby.z");
						String w = plugin.getConfig().getString("lobby.world");
						World wo = plugin.getServer().getWorld(w);
						Location l = new Location(wo,x,y,z);
						p.teleport(l);
						int pn = plugin.getConfig().getInt("playernum");
						plugin.getConfig().set("playernum", pn+1);
						plugin.getConfig().createSection("players."+p.getName());
						plugin.saveConfig();
						
						BukkitTask prepare = new prepareGame(plugin,p).runTaskLater(plugin, 20);
						
						plugin.getServer().getPluginManager().registerEvents(new blockLogger(plugin), plugin);
						
					}else{
						p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.RED+"The game is already in session!");
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
