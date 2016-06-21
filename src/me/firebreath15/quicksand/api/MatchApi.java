package me.firebreath15.quicksand.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.firebreath15.quicksand.Arena;
import me.firebreath15.quicksand.Helper.Metadata;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MatchApi
{
	private final HashMap<String, Boolean> contestants = new HashMap<String, Boolean>();
	private boolean                        sessionRunning;
	private final Arena                    arena;
	private long                           sessionStart;

	public MatchApi(Arena arena)
	{
		this.arena = arena;
	}

	public void addContestant(Player player)
	{
		if (this.isActiveContestant(player))
			return;

		this.arena.getPlayerApi().storeInventory(player);
		this.arena.getPlayerApi().teleport(player, "lobby");
		this.contestants.put(player.getName(), true);

		Metadata.set(player, "arena", this.arena.getName());
	}

	public void changeSpectatorMode(Player contestant, boolean enabled)
	{
		this.contestants.put(contestant.getName(), !enabled);

		contestant.setAllowFlight(enabled);
		contestant.setFlying(enabled);

		if (enabled) {
			for (Player player : this.getContestants()) {
				player.hidePlayer(contestant);
			}
		} else {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.showPlayer(contestant);
			}
		}
	}

	public int countActiveContestants()
	{
		if (!this.contestants.containsValue(true))
			return 0;

		int counter = 0;

		for (Entry<String, Boolean> contestant : this.contestants.entrySet()) {
			if (contestant.getValue()) {
				counter++;
			}
		}

		return counter;
	}

	public int countContestants()
	{
		return this.contestants.size();
	}

	public Player[] getContestants()
	{
		List<Player> contestants = new ArrayList<Player>();

		for (Entry<String, Boolean> contestant : this.contestants.entrySet()) {
			Player player = Bukkit.getServer().getPlayer(contestant.getKey());
			if (player.isOnline()) {
				contestants.add(player);
			} else {
				this.contestants.remove(contestant.getKey());
			}
		}

		return contestants.toArray(new Player[contestants.size()]);
	}

	public long getDuration()
	{
		return (System.currentTimeMillis() - this.sessionStart) / 1000;
	}

	public String getFormattedDuration()
	{
		long seconds = this.getDuration();
		return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
	}

	public boolean isActiveContestant(Player player)
	{
		return this.isContestant(player) && this.contestants.get(player.getName()) == true;
	}

	public boolean isContestant(Player player)
	{
		return this.contestants.containsKey(player.getName());
	}

	public boolean isRunning()
	{
		if (this.countContestants() == 0) {
			this.sessionRunning = false;
		}

		return this.sessionRunning;
	}

	public boolean isSpectator(Player player)
	{
		return this.isContestant(player) && this.contestants.get(player.getName()) == false;
	}

	public void removeContestant(Player player)
	{
		if (!this.isContestant(player))
			return;

		this.arena.getPlayerApi().restoreInventory(player);
		this.arena.getPlayerApi().teleport(player, "end");
		this.changeSpectatorMode(player, false);
		this.contestants.remove(player.getName());

		Metadata.remove(player, "arena");
	}

	public void reset()
	{
		for (Player player : this.getContestants()) {
			this.removeContestant(player);
		}

		this.contestants.clear();
	}

	public void start()
	{
		this.sessionRunning = true;
		this.sessionStart = System.currentTimeMillis();
	}
}
