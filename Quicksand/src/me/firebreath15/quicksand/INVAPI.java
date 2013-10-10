package me.firebreath15.quicksand;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/* Created by Firebreath15
 * .
 * This class is for my private use in plugins, to speed up the process a little
 * .
 * With code borrowed from TNTRUN by Shevchikden
 */

public class INVAPI {
	private HashMap<String, ItemStack[]> plinv = new HashMap<String, ItemStack[]>();
	private HashMap<String, ItemStack[]> plarmor = new HashMap<String, ItemStack[]>();
	
	public void storePlayerInventory(String name){
		PlayerInventory pinv = Bukkit.getPlayerExact(name).getInventory();
		plinv.put(name, pinv.getContents());
		pinv.clear();
	}
	
	public void storePlayerArmor(String name){
		PlayerInventory pinv = Bukkit.getPlayerExact(name).getInventory();
		plarmor.put(name, pinv.getArmorContents());
		pinv.setArmorContents(null);
	}
	
	public void restorePlayerInventory(String name){
		if(plinv.containsKey(name)){
			Bukkit.getPlayerExact(name).getInventory().setContents(plinv.get(name));
			plinv.remove(name);
		}		
	}
	
	public void restorePlayerArmor(String name){
		if(plarmor.containsKey(name)){
			Bukkit.getPlayerExact(name).getInventory().setArmorContents(plarmor.get(name));
			plarmor.remove(name);
		}
	}
}
