package me.firebreath15.quicksand.Listener;

import me.firebreath15.quicksand.Arena;
import me.firebreath15.quicksand.QuicksandPlugin;
import me.firebreath15.quicksand.Events.PlayerJoinArenaEvent;
import me.firebreath15.quicksand.Helper.Metadata;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinArenaListener implements Listener
{
	private final QuicksandPlugin plugin;

	public PlayerJoinArenaListener(QuicksandPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoinArena(PlayerJoinArenaEvent event)
	{
		Player player = event.getPlayer();
		Arena arena = event.getArena();

		if(arena != null){
			if (!arena.getSettingsApi().isArenaReady()) {
				arena.announce(ChatColor.RED + "Arena " + arena.getTitle() + "hasn't been set up yet! You need a lobby, spawn, and end!", player);
				return;
			}
		}

		if (player.getGameMode() == GameMode.CREATIVE) {
			arena.announce(ChatColor.RED + "You cannot join while in creative mode!", player);
			return;
		}

		if (arena.getMatchApi().isRunning()) {
			arena.announce(ChatColor.RED + "A match is already running!", player);
			return;
		}

		if (arena.getMatchApi().isContestant(player)) {
			arena.announce(ChatColor.RED + "You are already queued to play!", player);
			return;
		}

		if (Metadata.isset(player, "arena") && this.plugin.getArena(Metadata.asString(player, "arena")).getMatchApi().isContestant(player)) {
			arena.announce(ChatColor.RED + "You are already queued in another arena!", player);
			return;
		}

		arena.getMatchApi().addContestant(player);
		arena.announce(player.getName() + " joined the game!");

		int numPlayers = arena.getMatchApi().countContestants();
		int minPlayers = arena.getSettingsApi().getMinPlayers();

		if (numPlayers < minPlayers) {
			arena.announce(ChatColor.YELLOW + String.valueOf(minPlayers - numPlayers) + " more player(s) until the match starts.");
			return;
		}

		if (numPlayers == minPlayers) {
			arena.startMatch();
		}

		arena.getPlayerApi().teleport(player, "spawn");
	}
}
