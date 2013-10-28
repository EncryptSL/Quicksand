package me.firebreath15.quicksand;

import org.bukkit.entity.Player;

public class messages {
	main plugin;
	messages(main c){
		plugin=c;
	}
	
	
	public void sendMessageToQuicksandPlayers(String msg){
		Player[] players = plugin.getServer().getOnlinePlayers();
		int pn = players.length;
		for(int i=0; i<pn; i++){
			Player p = players[i];
			if(plugin.getConfig().contains("players."+p.getName()) || plugin.getConfig().contains("dead."+p.getName())){
				p.sendMessage(msg);
				//Use this so we arent broadcasting messages to the whole server.
			}
		}
	}
}
