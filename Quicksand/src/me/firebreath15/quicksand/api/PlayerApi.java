package me.firebreath15.quicksand.api;

import java.util.HashMap;

import me.firebreath15.quicksand.Arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class PlayerApi
{
	private final Arena                        arena;
	private final HashMap<String, ItemStack[]> inventories = new HashMap<String, ItemStack[]>();
	private final HashMap<String, ItemStack[]> armors      = new HashMap<String, ItemStack[]>();

	public PlayerApi(Arena arena)
	{
		this.arena = arena;
	}

	@SuppressWarnings("deprecation")
	public void restoreInventory(Player player)
	{
		String name = player.getName();

		if (this.inventories.containsKey(name)) {
			player.getInventory().setContents(this.inventories.get(name));
			this.inventories.remove(name);
		}

		if (this.armors.containsKey(name)) {
			player.getInventory().setArmorContents(this.armors.get(name));
			this.armors.remove(name);
		}

		player.updateInventory();
	}

	public void shove(Player player)
	{
		Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
		location.setYaw(player.getLocation().getYaw());
		location.setPitch(player.getLocation().getPitch());

		player.teleport(location);
		player.setVelocity(new Vector(0, 0.25, 0));
	}

	@SuppressWarnings("deprecation")
	public void storeInventory(Player player)
	{
		PlayerInventory inventory = player.getInventory();

		this.armors.put(player.getName(), inventory.getArmorContents());
		inventory.setArmorContents(null);

		this.inventories.put(player.getName(), inventory.getContents());
		inventory.clear();

		player.updateInventory();
	}

	public void teleport(Player player, String destination)
	{
		player.setFallDistance(0);
		player.setFireTicks(0);
		player.teleport(this.arena.getSettingsApi().getLocation(destination));
	}
}
