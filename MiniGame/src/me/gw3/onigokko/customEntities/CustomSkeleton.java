package me.gw3.onigokko.customEntities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;

public class CustomSkeleton extends EntitySkeleton{

	public CustomSkeleton(World world) {
		super(world);	
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);
		getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
		getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(8.0D);
		this.setCustomName(ChatColor.RED + "Boomber");
	}
	
	@Override
	protected Item getLoot() {
		return Items.DIAMOND_PICKAXE;
	}
	
	
	public void spawn(Location loc) {
		World world = ((CraftWorld)loc.getWorld()).getHandle();
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		world.addEntity(this);
		this.setEquipment(4, CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_HELMET)));
	}
	
	

}
