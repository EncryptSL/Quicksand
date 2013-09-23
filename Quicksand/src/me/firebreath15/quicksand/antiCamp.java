package me.firebreath15.quicksand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class antiCamp extends BukkitRunnable{
	main plugin;
	antiCamp(main c){
		plugin=c;
	}
	
	@SuppressWarnings("unused")
	public void run(){
		if(plugin.getConfig().getInt("playernum") >= 1){
			Player[] ops = plugin.getServer().getOnlinePlayers();
			
			for(int i=0; i<ops.length; i++){
				Player p = ops[i];
				if(plugin.getConfig().contains("players."+p.getName())){
					Location l = p.getLocation();
					Location floor = new Location(l.getWorld(),l.getX(),l.getY()-1,l.getZ());
					if(floor.getBlock().getType()==Material.SAND || floor.getBlock().getType()==Material.GRAVEL){
						if(floor.getBlock().getType()==Material.SAND){
							int snext = plugin.getConfig().getInt("sand.next");
							int tnext = plugin.getConfig().getInt("tnt.next");
							
							int x = l.getBlockX();
							int y = l.getBlockY();
							int z = l.getBlockZ();
							
							Location sand = new Location(p.getWorld(),x,y-1,z);
							sand.getBlock().setType(Material.AIR);
							
							Location tnt = new Location(p.getWorld(),x,y-2,z);
							tnt.getBlock().setType(Material.AIR);
							
							plugin.getConfig().set("sand."+snext+".x", sand.getX());
							plugin.getConfig().set("sand."+snext+".y", sand.getY());
							plugin.getConfig().set("sand."+snext+".z", sand.getZ());
							plugin.getConfig().set("sand."+snext+".world", p.getWorld().getName());
							
							plugin.getConfig().set("tnt."+tnext+".x", tnt.getX());
							plugin.getConfig().set("tnt."+tnext+".y", tnt.getY());
							plugin.getConfig().set("tnt."+tnext+".z", tnt.getZ());
							plugin.getConfig().set("tnt."+tnext+".world", p.getWorld().getName());
							plugin.getConfig().set("sand.next", snext+1);
							plugin.getConfig().set("tnt.next", tnext+1);
							plugin.saveConfig();
						}
						if(floor.getBlock().getType()==Material.GRAVEL){
							int gnext = plugin.getConfig().getInt("gravel.next");
							int tnext = plugin.getConfig().getInt("tnt.next");
							
							int x = l.getBlockX();
							int y = l.getBlockY();
							int z = l.getBlockZ();
							
							Location gravel = new Location(p.getWorld(),x,y-1,z);
							gravel.getBlock().setType(Material.AIR);
							
							Location tnt = new Location(p.getWorld(),x,y-2,z);
							tnt.getBlock().setType(Material.AIR);
							
							plugin.getConfig().set("gravel."+gnext+".x", gravel.getX());
							plugin.getConfig().set("gravel."+gnext+".y", gravel.getY());
							plugin.getConfig().set("gravel."+gnext+".z", gravel.getZ());
							plugin.getConfig().set("gravel."+gnext+".world", p.getWorld().getName());
							
							plugin.getConfig().set("tnt."+tnext+".x", tnt.getX());
							plugin.getConfig().set("tnt."+tnext+".y", tnt.getY());
							plugin.getConfig().set("tnt."+tnext+".z", tnt.getZ());
							plugin.getConfig().set("tnt."+tnext+".world", p.getWorld().getName());
							plugin.getConfig().set("gravel.next", gnext+1);
							plugin.getConfig().set("tnt.next", tnext+1);
							plugin.saveConfig();
						}
						p.setFoodLevel(20);
					}
				}
			}
			BukkitTask task = new antiCamp(plugin).runTaskLater(plugin, 150);
		}
	}
}
