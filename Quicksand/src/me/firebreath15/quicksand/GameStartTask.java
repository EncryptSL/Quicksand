package me.firebreath15.quicksand;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTask extends BukkitRunnable
{
	private final Arena arena;
	private int         countdown;

	public GameStartTask(Arena arena, int countdown)
	{
		this.arena = arena;
		this.countdown = countdown;
	}

	@Override
	public void run()
	{
		if (this.countdown > 0) {
			if (this.countdown % 10 == 0 || this.countdown < 10) {
				this.arena.getSettingsApi().getLocation("spawn").getWorld().playSound(this.arena.getSettingsApi().getLocation("spawn"), Sound.NOTE_PLING, 2, 2);
				this.arena.announce(String.valueOf(this.countdown));
			}
			this.countdown--;
		} else {
			this.arena.getMatchApi().start();
			this.arena.broadcast("The match has started. " + ChatColor.GREEN + "Good luck!");
			this.cancel();
		}
	}
}
