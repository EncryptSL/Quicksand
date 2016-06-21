package me.firebreath15.quicksand;

import java.util.HashMap;
import java.util.Map;

import me.firebreath15.quicksand.Helper.Metadata;
import me.firebreath15.quicksand.Listener.BlockListener;
import me.firebreath15.quicksand.Listener.CheatPrevention;
import me.firebreath15.quicksand.Listener.LogoutListener;
import me.firebreath15.quicksand.Listener.PlayerJoinArenaListener;
import me.firebreath15.quicksand.Listener.PlayerLeaveListener;
import me.firebreath15.quicksand.Listener.SignListener;
import me.firebreath15.quicksand.api.ChatApi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class QuicksandPlugin extends JavaPlugin
{
	private final Map<String, Arena> arenas = new HashMap<String, Arena>();
	private ChatApi                  chatApi;

	public Arena getArena(String name)
	{
		if (!this.arenas.containsKey(name.toLowerCase())) {
			Arena arena = new Arena(this, name);
			this.arenas.put(name.toLowerCase(), arena);
		}

		return this.arenas.get(name.toLowerCase());
	}

	public ChatApi getChatApi()
	{
		return this.chatApi;
	}

	@Override
	public void onDisable()
	{
		for (Arena arena : this.arenas.values()) {
			arena.reset();
		}
	}

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		this.saveDefaultConfig();

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new SignListener(this), this);
		pm.registerEvents(new PlayerJoinArenaListener(this), this);
		pm.registerEvents(new PlayerLeaveListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new CheatPrevention(), this);
		pm.registerEvents(new LogoutListener(this), this);

		this.getCommand("quicksand").setExecutor(new QuicksandCommandExecutor(this));
		this.getCommand("qs").setExecutor(new QuicksandCommandExecutor(this));
		this.chatApi = new ChatApi(this, ChatColor.YELLOW + "[Quicksand] " + ChatColor.RESET);

		Metadata.setPlugin(this);

		this.getLogger().info("by Firebreath15 loaded in " + (System.currentTimeMillis() - start) / 1000 + " seconds.");
	}
}
