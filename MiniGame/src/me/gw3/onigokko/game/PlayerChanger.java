package me.gw3.onigokko.game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;

public class PlayerChanger {

    public static void becomeOni(Player player) {
        clearInventory(player);

        PlayerInventory inventory = player.getInventory();

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        ItemStack arrow = new ItemStack(Material.ARROW);

        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);

        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE, 10);

        ItemStack enchantedApple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);

        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        inventory.addItem(sword);
        inventory.addItem(bow);
        inventory.addItem(arrow);
        inventory.addItem(steak);
        inventory.addItem(goldenApple);
        inventory.addItem(enchantedApple);
        inventory.addItem(helmet);
        inventory.addItem(chestplate);
        inventory.addItem(leggings);
        inventory.addItem(boots);
    }

    public static void becomeRunner(Player player) {
        clearInventory(player);

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        ItemStack speedPotion = new ItemStack(Material.POTION, 2);
        Potion speedPot = new Potion(2);
        speedPot.setType(PotionType.SPEED);
        speedPot.apply(speedPotion);

        ItemStack slowPotion = new ItemStack(Material.POTION, 2);
        Potion slowPot = new Potion(2);
        slowPot.setType(PotionType.SLOWNESS);
        slowPot.setSplash(true);
        slowPot.apply(slowPotion);

        ItemStack web = new ItemStack(Material.WEB, 5);

        ItemStack arrow = new ItemStack(Material.ARROW);

        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);

        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE, 3);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        player.getInventory().addItem(bow);
        player.getInventory().addItem(arrow);
        player.getInventory().addItem(speedPotion);
        player.getInventory().addItem(slowPotion);
        player.getInventory().addItem(steak);
        player.getInventory().addItem(goldenApple);
        player.getInventory().addItem(web);
        player.getInventory().addItem(helmet);
        player.getInventory().addItem(chestplate);
        player.getInventory().addItem(leggings);
        player.getInventory().addItem(boots);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
    }

}
