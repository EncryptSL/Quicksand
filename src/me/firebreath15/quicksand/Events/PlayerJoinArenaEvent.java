package me.firebreath15.quicksand.Events;

import me.firebreath15.quicksand.Arena;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class PlayerJoinArenaEvent extends PlayerEvent
{
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	private final Arena arena;

	public PlayerJoinArenaEvent(Player player, Arena arena)
	{
		super(player);
		this.arena = arena;
	}

	public Arena getArena()
	{
		return this.arena;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
}