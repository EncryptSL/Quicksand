package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class damageDealer implements Listener{
	main plugin;
	damageDealer(main c){
		plugin=c;
	}
	
	@EventHandler
	public void onPlayerPvp(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(plugin.getConfig().contains("players."+p.getName()) || plugin.getConfig().contains("dead."+p.getName())){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(plugin.getConfig().contains("players."+e.getPlayer().getName()) || plugin.getConfig().contains("dead."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockPlaceEvent e){
		if(plugin.getConfig().contains("players."+e.getPlayer().getName()) || plugin.getConfig().contains("dead."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerFallInVoid(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(plugin.getConfig().contains("players."+p.getName())){
			int yy = p.getLocation().getBlockY();
			if(yy <= 0){
				//THEYRE UNDER BEDROCK!
				double x = plugin.getConfig().getDouble("spawn.x");
				double y = plugin.getConfig().getDouble("spawn.y");
				double z = plugin.getConfig().getDouble("spawn.z");
				String wn = plugin.getConfig().getString("spawn.world");
				World w = plugin.getServer().getWorld(wn);
				Location l = new Location(w,x,y,z);
				p.teleport(l);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"You died while playing! You are now a spectator.");
				
				Teleport t = new Teleport(plugin);
				t.makePlayerInvisible(p);
				
				plugin.getConfig().set("players."+p.getName(), null);
				plugin.getConfig().createSection("dead."+p.getName());
				int pn = plugin.getConfig().getInt("playernum");
				plugin.getConfig().set("playernum", pn-1);
				plugin.getConfig().set(p.getName(), null);
				plugin.saveConfig();
				messages m = new messages(plugin);
				m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" died while playing!");
				
				findWinner fw = new findWinner(plugin);
				fw.findAWinner();
			}
		}
	}
	
	@EventHandler
	public void onPlayerFallInWater(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(plugin.getConfig().contains("players."+p.getName())){
			Location yy = p.getLocation();
			if(yy.getBlock().isLiquid()){
				//THEYRE IN WATER!
				double x = plugin.getConfig().getDouble("spawn.x");
				double y = plugin.getConfig().getDouble("spawn.y");
				double z = plugin.getConfig().getDouble("spawn.z");
				String wn = plugin.getConfig().getString("spawn.world");
				World w = plugin.getServer().getWorld(wn);
				Location l = new Location(w,x,y,z);
				p.teleport(l);
				p.setAllowFlight(true);
				p.setFlying(true);
				
				Teleport t = new Teleport(plugin);
				t.makePlayerInvisible(p);
				
				p.sendMessage(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+"You died while playing!");
				
				plugin.getConfig().set("players."+p.getName(), null);
				plugin.getConfig().createSection("dead."+p.getName());
				int pn = plugin.getConfig().getInt("playernum");
				plugin.getConfig().set("playernum", pn-1);
				plugin.getConfig().set(p.getName(), null);
				plugin.saveConfig();
				messages m = new messages(plugin);
				m.sendMessageToQuicksandPlayers(ChatColor.YELLOW+"[Quicksand] "+ChatColor.GREEN+p.getName()+" died while playing!");
				
				findWinner fw = new findWinner(plugin);
				fw.findAWinner();
			}
		}
	}
}
