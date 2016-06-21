package me.firebreath15.quicksand.Listener;

import me.firebreath15.quicksand.QuicksandPlugin;
import me.firebreath15.quicksand.Events.PlayerLeaveEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogoutListener implements Listener
{
	private final QuicksandPlugin plugin;

	public LogoutListener(QuicksandPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e)
	{
		this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(e.getPlayer()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(e.getPlayer()));
	}
}
