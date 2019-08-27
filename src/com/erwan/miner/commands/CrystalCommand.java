package com.erwan.miner.commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class CrystalCommand implements CommandExecutor {

	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null)
			tag = nmsStack.getTag();
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	public static int getRandomInt(int max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {

		if (Label.equalsIgnoreCase("crystal")) {

			if (args.length == 3) {

				if (args[0].equalsIgnoreCase("give")) {

					if (Bukkit.getPlayer(args[1]) != null) {

						if (args[2].equalsIgnoreCase("common") || args[2].equalsIgnoreCase("rare")
								|| args[2].equalsIgnoreCase("epic") || args[2].equalsIgnoreCase("legendary")
								|| args[2].equalsIgnoreCase("black")) {

							Player player = Bukkit.getPlayer(args[1]);

							if (args[2].equalsIgnoreCase("common")) {

								ItemStack common = new ItemStack(Material.PRISMARINE_SHARD);
								ItemMeta commonmeta = common.getItemMeta();
								commonmeta.setDisplayName(ChatColor.WHITE + "Common Crystal");
								ArrayList<String> commonlore = new ArrayList<String>();
								commonlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.WHITE + "Common");
								commonlore.add(
										ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
								commonmeta.setLore(commonlore);
								common.setItemMeta(commonmeta);
								player.getInventory().addItem(addGlow(common));

							} else if (args[2].equalsIgnoreCase("rare")) {
								
								ItemStack rare = new ItemStack(Material.PRISMARINE_SHARD);
								ItemMeta raremeta = rare.getItemMeta();
								raremeta.setDisplayName(ChatColor.AQUA + "Rare Crystal");
								ArrayList<String> rarelore = new ArrayList<String>();
								rarelore.add(ChatColor.GRAY + "Rarity: " + ChatColor.AQUA + "Rare");
								rarelore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
								raremeta.setLore(rarelore);
								rare.setItemMeta(raremeta);
								player.getInventory().addItem(addGlow(rare));
								
							} else if (args[2].equalsIgnoreCase("epic")) { 
								
								ItemStack epic = new ItemStack(Material.PRISMARINE_SHARD);
								ItemMeta epicmeta = epic.getItemMeta();
								epicmeta.setDisplayName(ChatColor.DARK_PURPLE + "Epic Crystal");
								ArrayList<String> epiclore = new ArrayList<String>();
								epiclore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
								epiclore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
								epicmeta.setLore(epiclore);
								epic.setItemMeta(epicmeta);
								player.getInventory().addItem(addGlow(epic));
								
							}  else if (args[2].equalsIgnoreCase("legendary")) { 
								
								ItemStack legendary = new ItemStack(Material.PRISMARINE_SHARD);
								ItemMeta legendarymeta = legendary.getItemMeta();
								legendarymeta.setDisplayName(ChatColor.GOLD + "Legendary Crystal");
								ArrayList<String> legendarylore = new ArrayList<String>();
								legendarylore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
								legendarylore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
								legendarymeta.setLore(legendarylore);
								legendary.setItemMeta(legendarymeta);
								player.getInventory().addItem(addGlow(legendary));
								
							} else if (args[2].equalsIgnoreCase("black")) {
								
								ItemStack black = new ItemStack(Material.INK_SACK);
								ItemMeta blackmeta = black.getItemMeta();
								blackmeta.setDisplayName(ChatColor.RED + "Black Crystal");
								ArrayList<String> blacklore = new ArrayList<String>();
								blacklore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
										
								blackmeta.setLore(blacklore);
								black.setItemMeta(blackmeta);
								player.getInventory().addItem(addGlow(black));
								
							}
								

						}

					}

				}

			}

		}

		return false;
	}

}
