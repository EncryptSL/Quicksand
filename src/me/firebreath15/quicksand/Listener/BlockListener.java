package me.firebreath15.quicksand.Listener;

import me.firebreath15.quicksand.Arena;
import me.firebreath15.quicksand.QuicksandPlugin;
import me.firebreath15.quicksand.Helper.Metadata;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockListener implements Listener
{
	private final QuicksandPlugin plugin;

	public BlockListener(QuicksandPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerRun(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();

		if (!Metadata.isset(player, "arena"))
			return;

		Arena arena = this.plugin.getArena(Metadata.asString(player, "arena"));

		if (!arena.getMatchApi().isRunning() || !arena.getMatchApi().isActiveContestant(player))
			return;

		Block playerBlock = player.getLocation().getBlock();

		if ((this.hasDelta(event.getFrom().getBlockX(), event.getTo().getBlockX()) || this.hasDelta(event.getFrom().getBlockZ(), event.getTo().getBlockZ())) && (event.getFrom().getBlockY() == event.getTo().getBlockY())) {
			player.setFoodLevel(20);
			arena.addMovingPlayer(player);
			arena.breakBlock(event.getFrom().getBlock().getRelative(BlockFace.DOWN));
		}

		if (playerBlock.isLiquid()) {
			arena.announce(ChatColor.GREEN + player.getName() + " is out!");
			arena.getMatchApi().changeSpectatorMode(player, true);

			if (arena.getMatchApi().countActiveContestants() <= 1) {
				arena.announceTheWinner();
				arena.reset();
			} else {
				arena.announce(ChatColor.RED + "You lost! You are now a spectator!", player);
				arena.getPlayerApi().teleport(player, "spawn");
			}
		}
	}

	private boolean hasDelta(int pos1, int pos2)
	{
		return Math.abs(pos1 - pos2) == 1;
	}
}
