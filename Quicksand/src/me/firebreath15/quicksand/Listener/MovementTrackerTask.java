package me.firebreath15.quicksand.Listener;

import me.firebreath15.quicksand.Arena;

import org.bukkit.scheduler.BukkitRunnable;

public class MovementTrackerTask extends BukkitRunnable
{
	private final Arena arena;

	public MovementTrackerTask(Arena arena)
	{
		this.arena = arena;
	}

	@Override
	public void run()
	{
		this.arena.shoveCampers();
	}
}
