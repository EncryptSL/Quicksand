package me.firebreath15.quicksand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class theRun implements Listener{
	main plugin;
	theRun(main c){
		plugin=c;
	}
	
	@EventHandler
	public void onPlayerRun(PlayerMoveEvent e){
		if(plugin.getConfig().contains("players."+e.getPlayer().getName())){
			if(!plugin.getConfig().getBoolean("Gracep")==true){
				Location to = e.getTo();
				Location nto = new Location(to.getWorld(),to.getX(),to.getY()-1,to.getZ());
				if(nto.getBlock().getType()==Material.SAND){
					Delta d = new Delta();
					if(d.ifChangeWasOne(e.getFrom().getBlockX(), e.getTo().getBlockX()) || d.ifChangeWasOne(e.getFrom().getBlockZ(), e.getTo().getBlockZ())){
						//if the player moved a whole block in the X or Z position
						int snext = plugin.getConfig().getInt("sand.next");
						int tnext = plugin.getConfig().getInt("tnt.next");
						
						int x = e.getFrom().getBlockX();
						int y = e.getFrom().getBlockY();
						int z = e.getFrom().getBlockZ();
						
						Location sand = new Location(e.getPlayer().getWorld(),x,y-1,z);
						sand.getBlock().setType(Material.AIR);
						
						Location tnt = new Location(e.getPlayer().getWorld(),x,y-2,z);
						tnt.getBlock().setType(Material.AIR);
						
						plugin.getConfig().set("sand."+snext+".x", sand.getX());
						plugin.getConfig().set("sand."+snext+".y", sand.getY());
						plugin.getConfig().set("sand."+snext+".z", sand.getZ());
						plugin.getConfig().set("sand."+snext+".world", e.getPlayer().getWorld().getName());
						
						plugin.getConfig().set("tnt."+tnext+".x", tnt.getX());
						plugin.getConfig().set("tnt."+tnext+".y", tnt.getY());
						plugin.getConfig().set("tnt."+tnext+".z", tnt.getZ());
						plugin.getConfig().set("tnt."+tnext+".world", e.getPlayer().getWorld().getName());
						plugin.getConfig().set("sand.next", snext+1);
						plugin.getConfig().set("tnt.next", tnext+1);
						plugin.saveConfig();
						
						e.getPlayer().setFoodLevel(20); //Hey, runners gotta eat!
					}
				}
				
				if(nto.getBlock().getType()==Material.GRAVEL){
					Delta d = new Delta();
					if(d.ifChangeWasOne(e.getFrom().getBlockX(), e.getTo().getBlockX()) || d.ifChangeWasOne(e.getFrom().getBlockZ(), e.getTo().getBlockZ())){
						//if the player moved a whole block in the X or Z position
						int gnext = plugin.getConfig().getInt("gravel.next");
						int tnext = plugin.getConfig().getInt("tnt.next");
						
						int x = e.getTo().getBlockX();
						int y = e.getTo().getBlockY();
						int z = e.getTo().getBlockZ();
						
						Location gravel = new Location(e.getPlayer().getWorld(),x,y-1,z);
						gravel.getBlock().setType(Material.AIR);
						
						Location tnt = new Location(e.getPlayer().getWorld(),x,y-2,z);
						tnt.getBlock().setType(Material.AIR);
						
						plugin.getConfig().set("gravel."+gnext+".x", gravel.getX());
						plugin.getConfig().set("gravel."+gnext+".y", gravel.getY());
						plugin.getConfig().set("gravel."+gnext+".z", gravel.getZ());
						plugin.getConfig().set("gravel."+gnext+".world", e.getPlayer().getWorld().getName());
						
						plugin.getConfig().set("tnt."+tnext+".x", tnt.getX());
						plugin.getConfig().set("tnt."+tnext+".y", tnt.getY());
						plugin.getConfig().set("tnt."+tnext+".z", tnt.getZ());
						plugin.getConfig().set("tnt."+tnext+".world", e.getPlayer().getWorld().getName());
						plugin.getConfig().set("gravel.next", gnext+1);
						plugin.getConfig().set("tnt.next", tnext+1);
						plugin.saveConfig();
						
						e.getPlayer().setFoodLevel(20); //Hey, runners gotta eat!
					}
				}
			}
		}
	}
}
