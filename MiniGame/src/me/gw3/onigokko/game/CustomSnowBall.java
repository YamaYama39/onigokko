package me.gw3.onigokko.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomSnowBall {
	
	public static ItemStack create() {
		ItemStack itemStack = new ItemStack(Material.SNOW_BALL, 1);
		List<String> lore = new ArrayList<String>();
		lore.add("Oni tobasi");
		itemStack.getItemMeta().setLore(lore);
		return itemStack;
	}

}
