package me.firebreath15.quicksand.api;

import java.util.ArrayList;
import java.util.List;

import me.firebreath15.quicksand.Arena;
import me.firebreath15.quicksand.QuicksandPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class SettingsApi
{
	private final Arena           arena;
	private ConfigurationSection  configSection;
	private final QuicksandPlugin plugin;

	public SettingsApi(QuicksandPlugin plugin, Arena arena)
	{
		this.plugin = plugin;
		this.arena = arena;
		this.configSection = this.plugin.getConfig().getConfigurationSection("arenas." + this.arena.getName().toLowerCase());

		if (this.configSection == null) {
			this.configSection = this.plugin.getConfig().createSection("arenas." + this.arena.getName().toLowerCase());
			this.plugin.saveConfig();
		}

		this.configSection.addDefault("countdown", this.plugin.getConfig().getInt("defaults.countdown"));
		this.configSection.addDefault("minPlayers", this.plugin.getConfig().getInt("defaults.minPlayers"));
		this.configSection.addDefault("floorMaterials", this.plugin.getConfig().getStringList("defaults.floorMaterials"));
	}

	public int getCountdown()
	{
		return this.configSection.getInt("countdown");
	}

	public Location getLocation(String destination)
	{
		World world = this.plugin.getServer().getWorld(this.configSection.getString(destination + ".world"));
		double x = this.configSection.getDouble(destination + ".x");
		double y = this.configSection.getDouble(destination + ".y");
		double z = this.configSection.getDouble(destination + ".z");
		float yaw = (float) this.configSection.getDouble(destination + ".yaw");
		float pitch = (float) this.configSection.getDouble(destination + ".pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public List<Material> getMaterials()
	{
		List<Material> materials = new ArrayList<Material>();

		for (String materialName : this.configSection.getStringList("floorMaterials")) {
			materials.add(Material.valueOf(materialName.toUpperCase()));
		}

		return materials;
	}

	public int getMinPlayers()
	{
		return this.configSection.getInt("minPlayers");
	}

	public boolean isArenaReady()
	{
		return this.configSection.contains("lobby") && this.configSection.contains("spawn") && this.configSection.contains("end");
	}

	public void setCountdown(int seconds)
	{
		this.configSection.set("countdown", Math.max(seconds, 0));
		this.plugin.saveConfig();
	}

	public void setLocation(String destination, Location location)
	{
		this.configSection.set(destination + ".world", location.getWorld().getName());
		this.configSection.set(destination + ".x", location.getX());
		this.configSection.set(destination + ".y", location.getY());
		this.configSection.set(destination + ".z", location.getZ());
		this.configSection.set(destination + ".yaw", location.getYaw());
		this.configSection.set(destination + ".pitch", location.getPitch());
		this.plugin.saveConfig();
	}

	public void setMinPlayers(int amount)
	{
		this.configSection.set("minPlayers", Math.max(amount, 1));
		this.plugin.saveConfig();
	}
}
