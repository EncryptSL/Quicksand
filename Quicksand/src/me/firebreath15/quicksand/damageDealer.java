package me.firebreath15.quicksand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
}
