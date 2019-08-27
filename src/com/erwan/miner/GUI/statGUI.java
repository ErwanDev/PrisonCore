package com.erwan.miner.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.erwan.miner.listeners.XPListener;
import com.erwan.miner.managers.MySQLManager;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class statGUI {

	MySQLManager sql = new MySQLManager();
	XPListener xp = new XPListener();
	
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
		

		

		Inventory stats = Bukkit.createInventory(null, 27, ChatColor.GRAY + player.getName() + " - Level: "
				+ ChatColor.DARK_AQUA + Integer.toString(sql.getLevel(player.getUniqueId())));

		ItemStack playerstats = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta playerstatsmeta = (SkullMeta) playerstats.getItemMeta();
		playerstatsmeta.setOwner(player.getName());
		playerstatsmeta.setDisplayName(ChatColor.GREEN + "Player Information");
		ArrayList<String> playerstatslore = new ArrayList<String>();
		playerstatslore.add(ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + Integer.toString(sql.getLevel(player.getUniqueId())));
		playerstatslore.add(ChatColor.GRAY + "Current XP: " + ChatColor.YELLOW + Double.toString(sql.getEXP(player.getUniqueId())));
		playerstatslore.add(ChatColor.GRAY + "Modifiers: ");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Defense: " + ChatColor.YELLOW + "+" + ChatColor.YELLOW + sql.getModifiers(player.getUniqueId(), "defense") + ChatColor.YELLOW + ".00%");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Health: " + ChatColor.YELLOW + "+" + ChatColor.YELLOW + sql.getModifiers(player.getUniqueId(), "health") + ChatColor.YELLOW + ".00%");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Movement: " + ChatColor.YELLOW + "+" + ChatColor.YELLOW + sql.getModifiers(player.getUniqueId(), "movement") + ChatColor.YELLOW + ".00%");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Attack Speed: " + ChatColor.YELLOW + "+" + ChatColor.YELLOW + sql.getModifiers(player.getUniqueId(), "speed") + ChatColor.YELLOW + ".00%");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Attack Damage: " + ChatColor.YELLOW + "+" + ChatColor.YELLOW + sql.getModifiers(player.getUniqueId(), "damage") + ChatColor.YELLOW + ".00%");
		playerstatslore.add(ChatColor.GRAY + "Enchants: ");
		playerstatslore.add(ChatColor.DARK_GRAY + "  + Total: " + ChatColor.YELLOW + sql.getEnchant(player.getUniqueId()));
		if (sql.getEnchant(player.getUniqueId()) != 7 && sql.getEnchant(player.getUniqueId()) > 2) {
			playerstatslore.add(ChatColor.DARK_GRAY + "  + Next: " + ChatColor.YELLOW + Integer.toString(sql.getEnchant(player.getUniqueId()) + 1));
		} else if (sql.getEnchant(player.getUniqueId()) == 7) {
			playerstatslore.add(ChatColor.DARK_GRAY + "  + Next: " + ChatColor.YELLOW + "7");
		} else {
			playerstatslore.add(ChatColor.DARK_GRAY + "  + Next: " + ChatColor.YELLOW + "3");
		}
		playerstatsmeta.setLore(playerstatslore);
		playerstats.setItemMeta(playerstatsmeta);
		stats.setItem(10, playerstats);
		
		final char PROGRESS_ICON = '|';
		double nextrank = sql.xpNeeded(sql.getLevel(player.getUniqueId()) + 1);
		double xpneeded = sql.getEXP(player.getUniqueId()) - sql.xpNeeded(sql.getLevel(player.getUniqueId()));
		double xptotal = sql.xpNeeded(sql.getLevel(player.getUniqueId()) + 1) - sql.xpNeeded(sql.getLevel(player.getUniqueId()));

		int numberOfBars = 40;
		double percent =  (int) ((xpneeded / xptotal) * 100);
	    int progress = (int) ((xpneeded / xptotal) * 40);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(ChatColor.GREEN.toString());
		for (int greenBar = 1; greenBar <= progress; greenBar++) {
		    stringBuffer.append(PROGRESS_ICON);
		}

		int yellowBars = numberOfBars - progress; 
		stringBuffer.append(ChatColor.DARK_GRAY.toString());
		for (int yellowBar = 1; yellowBar <= yellowBars; yellowBar++) {
		    stringBuffer.append(PROGRESS_ICON);
		}
		String resultingProgressBar = stringBuffer.toString();

		ItemStack level = new ItemStack(Material.PAPER);
		ItemMeta levelmeta = level.getItemMeta();
		levelmeta.setDisplayName(ChatColor.GREEN + "Leveling");
		ArrayList<String> levellore = new ArrayList<String>();
		levellore.add(ChatColor.GRAY + "Mining ores and completing challenges will");
		levellore.add(ChatColor.GRAY + "earn you " + ChatColor.AQUA + "EXP" + ChatColor.GRAY + ", which is required to level up");
		levellore.add(ChatColor.GRAY + "add and unlock new abilities and modifiers.");
		levellore.add(ChatColor.GRAY + "");
		levellore.add(ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + sql.getLevel(player.getUniqueId()) + ChatColor.DARK_GRAY + " [" + resultingProgressBar + ChatColor.DARK_GRAY + "] " + ChatColor.AQUA + percent +  ChatColor.AQUA + "%");
		levellore.add(ChatColor.GRAY + "Current XP: " + ChatColor.YELLOW + sql.getEXP(player.getUniqueId()) + ChatColor.DARK_GRAY + " / " + ChatColor.GOLD + "2000000");
		levellore.add(ChatColor.GRAY + "XP Until Next Level: " + ChatColor.YELLOW + (nextrank - xpneeded));
		levelmeta.setLore(levellore);
		level.setItemMeta(levelmeta);
		stats.setItem(12, addGlow(level));
		
		ItemStack ability = new ItemStack(Material.NETHER_STAR);
		ItemMeta abilitymeta = ability.getItemMeta();
		abilitymeta.setDisplayName(ChatColor.GREEN + "Abilities");
		ArrayList<String> abilitylore = new ArrayList<String>();
		abilitylore.add(ChatColor.GRAY + "Unlock unique abilies by leveling up.");
		abilitylore.add(ChatColor.GRAY + "Abilities can be activated within a mine.");
		abilitylore.add("");
		abilitylore.add(ChatColor.GRAY + "Unlocked Abilities");
		abilitylore.add(ChatColor.DARK_GRAY + "  + Nuke: " + ChatColor.GREEN + "Level 1");
		abilitylore.add(ChatColor.DARK_GRAY + "  + Acid Rain: " + ChatColor.GREEN + "Level 1");
		abilitylore.add(ChatColor.DARK_GRAY + "  + Lightning Strike: " + ChatColor.GREEN + "Level 1");
		abilitylore.add(ChatColor.DARK_GRAY + "  + Dragon's Storm: " + ChatColor.GREEN + "Level 1");
		abilitymeta.setLore(abilitylore);
		ability.setItemMeta(abilitymeta);
		stats.setItem(14, addGlow(ability));
		
		ItemStack enchant = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta enchantmeta = enchant.getItemMeta();
		enchantmeta.setDisplayName(ChatColor.GREEN + "Enchants");
		ArrayList<String> enchantlore = new ArrayList<String>();
		enchantlore.add(ChatColor.GRAY + "You unlock the ability to place more");
		enchantlore.add(ChatColor.GRAY + "enchants on a tool as you level up.");
		enchantlore.add("");
		enchantlore.add(ChatColor.GRAY + "Total Enchants: " + ChatColor.YELLOW + sql.getEnchant(player.getUniqueId()));
		if (sql.getEnchant(player.getUniqueId()) != 7 && sql.getEnchant(player.getUniqueId()) > 2) {
			enchantlore.add(ChatColor.GRAY + "Next Total Enchant: " + ChatColor.YELLOW + Integer.toString(sql.getEnchant(player.getUniqueId()) + 1));
		} else if (sql.getEnchant(player.getUniqueId()) == 7) {
			enchantlore.add(ChatColor.GRAY + "Next Total Enchant: " + ChatColor.YELLOW + "7");
		} else {
			enchantlore.add(ChatColor.GRAY + "Next Total Enchant: " + ChatColor.YELLOW + "3");
		}
		enchantlore.add("");
		enchantlore.add(ChatColor.GRAY + "Level 5: " + ChatColor.YELLOW + "3 Enchants");
		enchantlore.add(ChatColor.GRAY + "Level 10: " + ChatColor.YELLOW + "4 Enchants");
		enchantlore.add(ChatColor.GRAY + "Level 15: " + ChatColor.YELLOW + "5 Enchants");
		enchantlore.add(ChatColor.GRAY + "Level 20: " + ChatColor.YELLOW + "6 Enchants");
		enchantlore.add(ChatColor.GRAY + "Level 25: " + ChatColor.YELLOW + "7 Enchants");
		enchantmeta.setLore(enchantlore);
		enchant.setItemMeta(enchantmeta);
		stats.setItem(16, addGlow(enchant));
		
		while (stats.firstEmpty() != -1) {

			ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			stats.setItem(stats.firstEmpty(), addGlow(filler));
		}
		
		player.openInventory(stats);

	}

}
