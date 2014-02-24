package me.firebreath15.quicksand.Helper;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

public class Metadata
{
	private static Plugin plugin;

	public static Boolean asBoolean(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.asBoolean();
	}

	public static Double asDouble(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.asDouble();
	}

	public static Integer asInt(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.asInt();
	}

	public static Long asLong(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.asLong();
	}

	public static String asString(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.asString();
	}

	@SuppressWarnings("unchecked")
	public static List<String> asStringList(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? new ArrayList<String>() : (List<String>) value;
	}

	public static Object get(Metadatable entity, String key)
	{
		MetadataValue value = Metadata.getValue(entity, key);
		return value == null ? null : value.value();
	}

	public static boolean isset(Metadatable entity, String key)
	{
		return entity.hasMetadata(key);
	}

	public static void remove(Metadatable entity, String key)
	{
		entity.removeMetadata(key, Metadata.plugin);
	}

	public static void set(Metadatable entity, String key, Object value)
	{
		entity.setMetadata(key, new FixedMetadataValue(Metadata.plugin, value));
	}

	public static void setPlugin(Plugin plugin)
	{
		Metadata.plugin = plugin;
	}

	private static MetadataValue getValue(Metadatable entity, String key)
	{
		if (!entity.hasMetadata(key))
			return null;

		String pluginName = Metadata.plugin.getName();

		for (MetadataValue value : entity.getMetadata(key)) {
			if (value.getOwningPlugin().getName().equals(pluginName))
				return value;
		}

		return null;
	}
}