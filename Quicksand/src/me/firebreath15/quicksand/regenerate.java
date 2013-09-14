package me.firebreath15.quicksand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class regenerate {
	main plugin;
	regenerate(main c){
		plugin=c;
	}
	
	public void restoreArena(){
		//after this is called, reset sand and tnt numbers
		int snxt = plugin.getConfig().getInt("sand.next");
		int tnxt = plugin.getConfig().getInt("tnt.next");
		int gnxt = plugin.getConfig().getInt("gravel.next");
		
		if(tnxt > 1){
			for(int i=tnxt; i>0; i--){
				//i equals a Next Number
				String wn = plugin.getConfig().getString("tnt.1.world");
				World w = plugin.getServer().getWorld(wn);
				double x = plugin.getConfig().getDouble("tnt."+i+".x");
				double y = plugin.getConfig().getDouble("tnt."+i+".y");
				double z = plugin.getConfig().getDouble("tnt."+i+".z");
				Location l = new Location(w,x,y,z);
				l.getBlock().setType(Material.TNT);
			}
			
			for(int i=snxt; i>0; i--){
				//i equals a Next Number
				String wn = plugin.getConfig().getString("sand.1.world");
				World w = plugin.getServer().getWorld(wn);
				double x = plugin.getConfig().getDouble("sand."+i+".x");
				double y = plugin.getConfig().getDouble("sand."+i+".y");
				double z = plugin.getConfig().getDouble("sand."+i+".z");
				Location l = new Location(w,x,y,z);
				l.getBlock().setType(Material.SAND);
			}
			
			for(int i=gnxt; i>0; i--){
				//i equals a Next Number
				String wn = plugin.getConfig().getString("gravel.1.world");
				World w = plugin.getServer().getWorld(wn);
				double x = plugin.getConfig().getDouble("gravel."+i+".x");
				double y = plugin.getConfig().getDouble("gravel."+i+".y");
				double z = plugin.getConfig().getDouble("gravel."+i+".z");
				Location l = new Location(w,x,y,z);
				l.getBlock().setType(Material.GRAVEL);
			}
		}
		
		plugin.getConfig().set("sand", null);
		plugin.getConfig().set("sand.next", 1);
		plugin.getConfig().set("tnt", null);
		plugin.getConfig().set("tnt.next", 1);
		plugin.getConfig().set("gravel", null);
		plugin.getConfig().set("gravel.next", 1);
		plugin.saveConfig();
	}
}
