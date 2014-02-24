package me.firebreath15.quicksand.api;

import me.firebreath15.quicksand.QuicksandPlugin;

import org.bukkit.entity.Player;

public class ChatApi
{
	private final QuicksandPlugin plugin;
	private final String          prefix;

	public ChatApi(QuicksandPlugin plugin, String prefix)
	{
		this.plugin = plugin;
		this.prefix = prefix;
	}

	public void announce(String msg, Player... players)
	{
		for (Player player : players) {
			this.tell(player, msg);
		}
	}

	public void broadcast(String msg)
	{
		this.plugin.getServer().broadcastMessage(this.prefix + msg);
	}

	public void tell(Player player, String msg)
	{
		player.sendMessage(this.prefix + msg);
	}
}
