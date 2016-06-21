package me.firebreath15.quicksand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.firebreath15.quicksand.Listener.MovementTrackerTask;
import me.firebreath15.quicksand.api.MatchApi;
import me.firebreath15.quicksand.api.PlayerApi;
import me.firebreath15.quicksand.api.SettingsApi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Arena
{
	private final PlayerApi                   playerApi;
	private final SettingsApi                 settingsApi;
	private final MatchApi                    matchApi;
	private final String                      name;
	private final QuicksandPlugin             plugin;
	private final HashMap<Location, Material> floors  = new HashMap<Location, Material>();
	private final List<Material>              floorMaterials;
	private BukkitTask                        tracker;
	private final List<String>                runners = new ArrayList<String>();

	Arena(QuicksandPlugin plugin, String name)
	{
		this.plugin = plugin;
		this.name = name;
		this.playerApi = new PlayerApi(this);
		this.matchApi = new MatchApi(this);
		this.settingsApi = new SettingsApi(plugin, this);
		this.floorMaterials = this.settingsApi.getMaterials();
	}

	public void addMovingPlayer(Player player)
	{
		this.runners.add(player.getName());
	}

	public void announce(String msg)
	{
		this.announce(msg, this.getMatchApi().getContestants());
	}

	public void announce(String msg, Player... players)
	{
		this.plugin.getChatApi().announce(this.getTitle() + msg, players);
	}

	public void announceTheWinner()
	{
		for (Player player : this.getMatchApi().getContestants()) {
			if (this.getMatchApi().isActiveContestant(player)) {
				this.broadcast(ChatColor.GREEN + player.getName() + " WON the game!");
			}
		}

		this.broadcast("The match lasted " + this.getMatchApi().getFormattedDuration() + "!");
	}

	public void breakBlock(Block topBlock)
	{
		if (this.floorMaterials.contains(topBlock.getType())) {
			this.floors.put(topBlock.getLocation(), topBlock.getType());
			topBlock.setType(Material.AIR);
			topBlock.getRelative(BlockFace.DOWN).setType(Material.AIR);
		}
	}

	public void broadcast(String msg)
	{
		this.plugin.getChatApi().broadcast(this.getTitle() + msg);
	}

	public MatchApi getMatchApi()
	{
		return this.matchApi;
	}

	public String getName()
	{
		return this.name;
	}

	public PlayerApi getPlayerApi()
	{
		return this.playerApi;
	}

	public SettingsApi getSettingsApi()
	{
		return this.settingsApi;
	}

	public String getTitle()
	{
		return ChatColor.DARK_RED + ".:" + this.name + ":. " + ChatColor.RESET;
	}

	public boolean isReady()
	{
		return this.settingsApi.isArenaReady();
	}

	public void reset()
	{
		this.matchApi.reset();
		
		if(tracker != null){
			this.tracker.cancel();
		}

		for (Entry<Location, Material> field : this.floors.entrySet()) {
			Block topBlock = field.getKey().getBlock();
			topBlock.setType(field.getValue());
			topBlock.getRelative(BlockFace.DOWN).setType(Material.TNT);
		}
	}

	public void setup(String location, Player player, String permission, String message)
	{
		if (player.hasPermission(permission)) {
			this.getSettingsApi().setLocation(location, player.getLocation());
			this.announce(message, player);
		} else {
			this.announce(ChatColor.RED + "You don't have permission!", player);
		}
	}

	public void shoveCampers()
	{
		for (Player player : this.getMatchApi().getContestants()) {
			if (this.getMatchApi().isActiveContestant(player) && !this.runners.contains(player.getName())) {
				this.getPlayerApi().shove(player);
				this.breakBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN));
			}
		}

		this.runners.clear();
	}

	public void startMatch()
	{
		if (this.getMatchApi().isRunning())
			return;

		for (Player contestant : this.getMatchApi().getContestants()) {
			this.getPlayerApi().teleport(contestant, "spawn");
		}

		int countdown = this.getSettingsApi().getCountdown();
		this.broadcast(ChatColor.YELLOW + "The match starts in " + ChatColor.RED + countdown + " seconds!");
		this.tracker = (new MovementTrackerTask(this)).runTaskTimer(this.plugin, 20 * (countdown + 1), 20);
		(new GameStartTask(this, countdown)).runTaskTimer(this.plugin, 0, 20);
	}
}
