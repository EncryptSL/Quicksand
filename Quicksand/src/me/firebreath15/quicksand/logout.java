package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class logout implements Listener{
	main plugin;
	logout(main c){
		plugin=c;
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(plugin.getConfig().contains("players."+p.getName()) || plugin.getConfig().contains("dead."+p.getName())){
			plugin.getConfig().set("dead."+p.getName(), null);
			plugin.getConfig().set("players."+p.getName(), null);
			int pn = plugin.getConfig().getInt("playernum");
			plugin.getConfig().set("playernum", pn-1);
			plugin.getConfig().set(p.getName(), null);
			plugin.saveConfig();
			messages m = new messages(plugin);
			m.sendMessageToQuicksandPlayers(ChatColor.GREEN+p.getName()+" has left "+ChatColor.RED+"QUICKSAND"+ChatColor.GREEN+".");
			findWinner fw = new findWinner(plugin);
			fw.findAWinner();
			Teleport t = new Teleport(plugin);
			t.makePlayerVisible(p);
			double x = plugin.getConfig().getDouble("end.x");
			double y = plugin.getConfig().getDouble("end.y");
			double z = plugin.getConfig().getDouble("end.z");
			String wn = plugin.getConfig().getString("end.world");
			World w = plugin.getServer().getWorld(wn);
			Location l = new Location(w,x,y,z);
			p.teleport(l);
			p.setFlying(false);
			p.setAllowFlight(false);
			t.makePlayerVisible(p);
		}
	}
	
	@EventHandler
	public void onLogout(PlayerKickEvent e){
		Player p = e.getPlayer();
		if(plugin.getConfig().contains("players."+p.getName()) || plugin.getConfig().contains("dead."+p.getName())){
			plugin.getConfig().set("dead."+p.getName(), null);
			plugin.getConfig().set("players."+p.getName(), null);
			int pn = plugin.getConfig().getInt("playernum");
			plugin.getConfig().set("playernum", pn-1);
			plugin.getConfig().set(p.getName(), null);
			plugin.saveConfig();
			messages m = new messages(plugin);
			m.sendMessageToQuicksandPlayers(ChatColor.GREEN+p.getName()+" has left "+ChatColor.RED+"QUICKSAND"+ChatColor.GREEN+".");
			findWinner fw = new findWinner(plugin);
			fw.findAWinner();
			Teleport t = new Teleport(plugin);
			t.makePlayerVisible(p);
			double x = plugin.getConfig().getDouble("end.x");
			double y = plugin.getConfig().getDouble("end.y");
			double z = plugin.getConfig().getDouble("end.z");
			String wn = plugin.getConfig().getString("end.world");
			World w = plugin.getServer().getWorld(wn);
			Location l = new Location(w,x,y,z);
			p.teleport(l);
			p.setFlying(false);
			p.setAllowFlight(false);
			t.makePlayerVisible(p);
		}
	}
	
	@EventHandler
	public void stopCommands(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String n = p.getName();
		if((plugin.getConfig().contains("players."+n) || plugin.getConfig().contains("dead."+n)) && (!e.getMessage().contains("qs"))){
			e.setCancelled(true);
			p.sendMessage(ChatColor.GREEN+"To do that you must first leave "+ChatColor.RED+"QUICKSAND");
		}
	}
}
