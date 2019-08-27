package com.erwan.miner.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.erwan.miner.Main;
import com.erwan.miner.managers.MySQLManager;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class AbilityGUI implements Listener {

	Main main = Main.getInstance();
	MySQLManager sql = new MySQLManager();
	
	public static ItemStack addGlow(ItemStack item){ 
		  net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		  NBTTagCompound tag = null;
		  if (!nmsStack.hasTag()) {
		      tag = new NBTTagCompound();
		      nmsStack.setTag(tag);
		  }
		  if (tag == null) tag = nmsStack.getTag();
		  NBTTagList ench = new NBTTagList();
		  tag.set("ench", ench);
		  nmsStack.setTag(tag);
		  return CraftItemStack.asCraftMirror(nmsStack);
		}

	public void getInventory(Player player) {
		Inventory ability = Bukkit.createInventory(null, 45, ChatColor.GRAY + "Select an Ability");

		if (sql.getCooldown(player.getUniqueId(), "nuke") == false && sql.getLevel(player.getUniqueId()) >= 5) {

			ItemStack nuke = new ItemStack(Material.TNT);
			ItemMeta nukemeta = nuke.getItemMeta();
			nukemeta.setDisplayName(ChatColor.RED + "Nuke");
			ArrayList<String> nukelore = new ArrayList<String>();
			nukelore.add(ChatColor.GRAY + "Blows up a huge hole in the mine");
			nukelore.add(ChatColor.GRAY + "when activated.");
			nukelore.add("");
			nukelore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.GREEN + "Ready");
			nukelore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "5");
			nukemeta.setLore(nukelore);
			nuke.setItemMeta(nukemeta);
			ability.setItem(11, addGlow(nuke));

		} else {

			ItemStack nuke = new ItemStack(Material.IRON_FENCE);
			ItemMeta nukemeta = nuke.getItemMeta();
			nukemeta.setDisplayName(ChatColor.RED + "Nuke");
			ArrayList<String> nukelore = new ArrayList<String>();
			nukelore.add(ChatColor.GRAY + "Blows up a huge hole in the mine");
			nukelore.add(ChatColor.GRAY + "when activated.");
			nukelore.add("");
			nukelore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.RED + "Not Ready");
			nukelore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "5");
			nukemeta.setLore(nukelore);
			nuke.setItemMeta(nukemeta);
			ability.setItem(11, addGlow(nuke));

		}

		if (sql.getCooldown(player.getUniqueId(), "rain") == false && sql.getLevel(player.getUniqueId()) >= 15) {

			ItemStack rain = new ItemStack(Material.GHAST_TEAR);
			ItemMeta rainmeta = rain.getItemMeta();
			rainmeta.setDisplayName(ChatColor.RED + "Acid Rain");
			ArrayList<String> rainlore = new ArrayList<String>();
			rainlore.add(ChatColor.GRAY + "Summons the acid rain to melt the blocks around you");
			rainlore.add(ChatColor.GRAY + "when activated.");
			rainlore.add("");
			rainlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.GREEN + "Ready");
			rainlore.add(ChatColor.GRAY + "Duration: " + ChatColor.YELLOW + "10s");
			rainlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "15");
			rainmeta.setLore(rainlore);
			rain.setItemMeta(rainmeta);
			ability.setItem(15, addGlow(rain));

		} else {

			ItemStack rain = new ItemStack(Material.IRON_FENCE);
			ItemMeta rainmeta = rain.getItemMeta();
			rainmeta.setDisplayName(ChatColor.RED + "Acid Rain");
			ArrayList<String> rainlore = new ArrayList<String>();
			rainlore.add(ChatColor.GRAY + "Summons the acid rain to melt the blocks around you");
			rainlore.add(ChatColor.GRAY + "when activated.");
			rainlore.add("");
			rainlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.RED + "Not Ready");
			rainlore.add(ChatColor.GRAY + "Duration: " + ChatColor.YELLOW + "10s");
			rainlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "15");
			rainmeta.setLore(rainlore);
			rain.setItemMeta(rainmeta);
			ability.setItem(15, addGlow(rain));

		}

		if (sql.getCooldown(player.getUniqueId(), "light") == false && sql.getLevel(player.getUniqueId()) >= 30) {

			ItemStack light = new ItemStack(Material.GLOWSTONE);
			ItemMeta lightmeta = light.getItemMeta();
			lightmeta.setDisplayName(ChatColor.RED + "Lightning");
			ArrayList<String> lightlore = new ArrayList<String>();
			lightlore.add(ChatColor.GRAY + "Summons a bolt of lightning that will destroy anything in its path.");
			lightlore.add("");
			lightlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.GREEN + "Ready");
			lightlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "30");
			lightmeta.setLore(lightlore);
			light.setItemMeta(lightmeta);
			ability.setItem(29, addGlow(light));

		} else {

			ItemStack light = new ItemStack(Material.IRON_FENCE);
			ItemMeta lightmeta = light.getItemMeta();
			lightmeta.setDisplayName(ChatColor.RED + "Lightning");
			ArrayList<String> lightlore = new ArrayList<String>();
			lightlore.add(ChatColor.GRAY + "Summons a bolt of lightning that will destroy anything in its path.");
			lightlore.add("");
			lightlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.RED + "Not Ready");
			lightlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "30");
			lightmeta.setLore(lightlore);
			light.setItemMeta(lightmeta);
			ability.setItem(29, addGlow(light));

		}

		if (sql.getCooldown(player.getUniqueId(), "dragon") == false && sql.getLevel(player.getUniqueId()) >= 35) {

			ItemStack dragon = new ItemStack(Material.DRAGON_EGG);
			ItemMeta dragonmeta = dragon.getItemMeta();
			dragonmeta.setDisplayName(ChatColor.RED + "Dragon's Storm");
			ArrayList<String> dragonlore = new ArrayList<String>();
			dragonlore.add(ChatColor.GRAY + "Spawn in a dragon that will fly ");
			dragonlore.add(ChatColor.GRAY + "around and attack the mine for you.");
			dragonlore.add("");
			dragonlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.GREEN + "Ready");
			dragonlore.add(ChatColor.GRAY + "Duration: " + ChatColor.YELLOW + "10s");
			dragonlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "35");
			dragonmeta.setLore(dragonlore);
			dragon.setItemMeta(dragonmeta);
			ability.setItem(33, addGlow(dragon));

		} else {

			ItemStack dragon = new ItemStack(Material.IRON_FENCE);
			ItemMeta dragonmeta = dragon.getItemMeta();
			dragonmeta.setDisplayName(ChatColor.RED + "Dragon's Storm");
			ArrayList<String> dragonlore = new ArrayList<String>();
			dragonlore.add(ChatColor.GRAY + "Spawn in a dragon that will fly ");
			dragonlore.add(ChatColor.GRAY + "around and attack the mine for you.");
			dragonlore.add("");
			dragonlore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.RED + "Not Ready");
			dragonlore.add(ChatColor.GRAY + "Duration: " + ChatColor.YELLOW + "10s");
			dragonlore.add(ChatColor.GRAY + "Level Required: " + ChatColor.YELLOW + "35");
			dragonmeta.setLore(dragonlore);
			dragon.setItemMeta(dragonmeta);
			ability.setItem(33, addGlow(dragon));

		}

		while (ability.firstEmpty() != -1) {

			ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ability.setItem(ability.firstEmpty(), addGlow(filler));
		}

		player.openInventory(ability);

	}

}
