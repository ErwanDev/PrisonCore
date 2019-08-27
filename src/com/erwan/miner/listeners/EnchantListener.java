package com.erwan.miner.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.erwan.miner.Main;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class EnchantListener implements Listener {

	private Economy economy = Main.getEconomy();
	Main main = Main.getInstance();
	
	public String colorTranslate(String message) {
	    return ChatColor.translateAlternateColorCodes('&', message);

	}

	public static ItemStack addGlow(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
        meta.addEnchant( Enchantment.LURE, 1, false );
        meta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        item.setItemMeta( meta );
        
        return item;
			
		
	}

	public static ItemStack addCrystal(ItemStack item) {

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

	public static BlockFace getDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 22.5) {
			return BlockFace.NORTH;
		} else if (22.5 <= rotation && rotation < 67.5) {
			return BlockFace.NORTH_EAST;
		} else if (67.5 <= rotation && rotation < 112.5) {
			return BlockFace.EAST;
		} else if (112.5 <= rotation && rotation < 157.5) {
			return BlockFace.SOUTH_EAST;
		} else if (157.5 <= rotation && rotation < 202.5) {
			return BlockFace.SOUTH;
		} else if (202.5 <= rotation && rotation < 247.5) {
			return BlockFace.SOUTH_WEST;
		} else if (247.5 <= rotation && rotation < 292.5) {
			return BlockFace.WEST;
		} else if (292.5 <= rotation && rotation < 337.5) {
			return BlockFace.NORTH_WEST;
		} else if (337.5 <= rotation && rotation < 360.0) {
			return BlockFace.NORTH;
		} else {
			return null;
		}
	}

	public static int getRandomInt(int max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

	public List<Block> getBlocks(Block start, int radius) {
		if (radius < 0) {
			return new ArrayList<Block>(0);
		}
		int iterations = (radius * 2) + 1;
		List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					blocks.add(start.getRelative(x, y, z));
				}
			}
		}
		return blocks;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		ApplicableRegionSet set = null;
		set = WGBukkit.getRegionManager(e.getPlayer().getWorld()).getApplicableRegions(e.getBlock().getLocation());

		if (set.allows(DefaultFlag.BLOCK_BREAK) == true && set.allows(DefaultFlag.SLEEP) == false) {

			short durability = e.getPlayer().getItemInHand().getDurability();

			int chance = getRandomInt(4000);
			if (chance == 2) {
				ItemStack black = new ItemStack(Material.INK_SACK);
				ItemMeta blackmeta = black.getItemMeta();
				blackmeta.setDisplayName(ChatColor.RED + "Black Crystal");
				ArrayList<String> blacklore = new ArrayList<String>();
				blacklore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
						
				blackmeta.setLore(blacklore);
				black.setItemMeta(blackmeta);
				e.getPlayer().getInventory().addItem(addCrystal(black));
			} else if (chance == 1) {

				ItemStack legendary = new ItemStack(Material.PRISMARINE_SHARD);
				ItemMeta legendarymeta = legendary.getItemMeta();
				legendarymeta.setDisplayName(ChatColor.GOLD + "Legendary Crystal");
				ArrayList<String> legendarylore = new ArrayList<String>();
				legendarylore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
				legendarylore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
				legendarymeta.setLore(legendarylore);
				legendary.setItemMeta(legendarymeta);
				e.getPlayer().getInventory().addItem(addCrystal(legendary));

			} else if (chance <= 3) {

				ItemStack epic = new ItemStack(Material.PRISMARINE_SHARD);
				ItemMeta epicmeta = epic.getItemMeta();
				epicmeta.setDisplayName(ChatColor.DARK_PURPLE + "Epic Crystal");
				ArrayList<String> epiclore = new ArrayList<String>();
				epiclore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
				epiclore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
				epicmeta.setLore(epiclore);
				epic.setItemMeta(epicmeta);
				e.getPlayer().getInventory().addItem(addCrystal(epic));

			} else if (chance <= 4) {

				ItemStack rare = new ItemStack(Material.PRISMARINE_SHARD);
				ItemMeta raremeta = rare.getItemMeta();
				raremeta.setDisplayName(ChatColor.AQUA + "Rare Crystal");
				ArrayList<String> rarelore = new ArrayList<String>();
				rarelore.add(ChatColor.GRAY + "Rarity: " + ChatColor.AQUA + "Rare");
				rarelore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
				raremeta.setLore(rarelore);
				rare.setItemMeta(raremeta);
				e.getPlayer().getInventory().addItem(addCrystal(rare));

			} else if (chance <= 5) {
				ItemStack common = new ItemStack(Material.PRISMARINE_SHARD);
				ItemMeta commonmeta = common.getItemMeta();
				commonmeta.setDisplayName(ChatColor.WHITE + "Common Crystal");
				ArrayList<String> commonlore = new ArrayList<String>();
				commonlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.WHITE + "Common");
				commonlore.add(ChatColor.GRAY + "Success Rate: " + ChatColor.YELLOW + getRandomInt(100) + "%");
				commonmeta.setLore(commonlore);
				common.setItemMeta(commonmeta);
				e.getPlayer().getInventory().addItem(addCrystal(common));
			}

			if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR
					&& e.getPlayer().getItemInHand().getItemMeta() != null) {

				Block block = e.getBlock();
				Location blocklocation = e.getBlock().getLocation();
				ItemStack tool = e.getPlayer().getItemInHand();
				ItemMeta toolmeta = tool.getItemMeta();
				List<String> toollore = toolmeta.getLore();

				if (toollore != null && toollore.toString().contains("test") == false) {

					if (toollore.toString().contains("Efficiency")) {

		

							if (toollore.toString().contains("Efficiency V")) {
								if  (tool.getEnchantmentLevel(Enchantment.DIG_SPEED) <= 4) {
								tool.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							} else if (toollore.toString().contains("Efficiency IV")) {
								if  (tool.getEnchantmentLevel(Enchantment.DIG_SPEED) <= 3) {
								tool.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							} else if (toollore.toString().contains("Efficiency III")) {
								if  (tool.getEnchantmentLevel(Enchantment.DIG_SPEED) <= 2) {
								tool.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}

							} else if (toollore.toString().contains("Efficiency II")) {
								if  (tool.getEnchantmentLevel(Enchantment.DIG_SPEED) <= 1) {
								tool.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							} else if (toollore.toString().contains("Efficiency I")) {
								if  (tool.getEnchantmentLevel(Enchantment.DIG_SPEED) <= 0) {
								tool.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							}

						

					}

					if (toollore.toString().contains("Unbreaking")) {


							if (toollore.toString().contains("Unbreaking III")) {
								if  (tool.getEnchantmentLevel(Enchantment.DURABILITY) <= 2) {
								tool.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							} else if (toollore.toString().contains("Unbreaking II")) {
								if  (tool.getEnchantmentLevel(Enchantment.DURABILITY) <= 1) {
								tool.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							} else if (toollore.toString().contains("Unbreaking I")) {
								if  (tool.getEnchantmentLevel(Enchantment.DURABILITY) <= 0) {
								tool.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().getItemInHand().setDurability(durability);
								}
							}

						

					}

					if (toollore.toString().contains("Silk Touch")) {


						if  (tool.getEnchantmentLevel(Enchantment.DURABILITY) <= 0) {
							tool.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
							e.getPlayer().setItemInHand(addGlow(tool));
							e.getPlayer().getItemInHand().setDurability(durability);
						}
						

					}

					if (toollore.toString().contains("Repair")) {

						if (toollore.toString().contains("Repair V")) {
							if (getRandomInt(100) <= 9) {
								tool.setDurability((short) (tool.getDurability() - 15));
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your tool has been repaired."));
							}
						} else if (toollore.toString().contains("Repair IV")) {
							if (getRandomInt(100) <= 8) {
								tool.setDurability((short) (tool.getDurability() - 10));
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your tool has been repaired."));
							}
						} else if (toollore.toString().contains("Repair III")) {
							if (getRandomInt(100) <= 7) {
								tool.setDurability((short) (tool.getDurability() - 7));
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your tool has been repaired."));
							}
						} else if (toollore.toString().contains("Repair II")) {
							if (getRandomInt(100) <= 6) {
								tool.setDurability((short) (tool.getDurability() - 5));
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your tool has been repaired."));
							}
						} else if (toollore.toString().contains("Repair I")) {
							if (getRandomInt(100) <= 5) {
								tool.setDurability((short) (tool.getDurability() - 5));
								e.getPlayer().setItemInHand(addGlow(tool));
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your tool has been repaired."));
							}
						}
					}

					if (toollore.toString().contains("Regenerate")) {

						if (toollore.toString().contains("Regenerate IV")) {
							if (getRandomInt(100) <= 20) {
								blocklocation.getBlock().setType(block.getType());
							}

						} else if (toollore.toString().contains("Regenerate III")) {
							if (getRandomInt(100) <= 15) {
								blocklocation.getBlock().setType(block.getType());
							}

						} else if (toollore.toString().contains("Regenerate II")) {

							if (getRandomInt(100) <= 10) {
								blocklocation.getBlock().setType(block.getType());
							}
							
						} else if (toollore.toString().contains("Regenerate I")) {
							if (getRandomInt(100) <= 5) {
								blocklocation.getBlock().setType(block.getType());
							}
						}
					}

					if (toollore.toString().contains("Vein Miner")) {

						if (e.getBlock().getType().equals(Material.IRON_ORE)
								|| e.getBlock().getType().equals(Material.COAL_ORE)
								|| e.getBlock().getType().equals(Material.GOLD_ORE)
								|| e.getBlock().getType().equals(Material.DIAMOND_ORE)
								|| e.getBlock().getType().equals(Material.EMERALD_ORE)) {

							if (toollore.toString().contains("Vein Miner IV")) {

								if (getRandomInt(100) <= 12) {
									List<Block> radius = getBlocks(e.getBlock(), 6);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {

											if (e.getBlock().getType().equals(Material.COAL_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.COAL));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.IRON_INGOT));
											} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.GOLD_INGOT));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.DIAMOND));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.EMERALD));
											}
											selected.setType(Material.AIR);

										}
									}
								}
								
							} else if (toollore.toString().contains("Vein Miner III")) {
								if (getRandomInt(100) <= 9) {
									List<Block> radius = getBlocks(e.getBlock(), 5);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {

											if (e.getBlock().getType().equals(Material.COAL_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.COAL));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.IRON_INGOT));
											} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.GOLD_INGOT));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.DIAMOND));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.EMERALD));
											}
											selected.setType(Material.AIR);

										}
									}
								}
								
							} else if (toollore.toString().contains("Vein Miner II")) {
								if (getRandomInt(100) <= 7) {
									List<Block> radius = getBlocks(e.getBlock(), 4);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {

											if (e.getBlock().getType().equals(Material.COAL_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.COAL));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.IRON_INGOT));
											} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.GOLD_INGOT));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.DIAMOND));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.EMERALD));
											}
											selected.setType(Material.AIR);

										}
									}
								}
							} else if (toollore.toString().contains("Vein Miner I")) {
								if (getRandomInt(100) <= 5) {
									List<Block> radius = getBlocks(e.getBlock(), 3);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {

											if (e.getBlock().getType().equals(Material.COAL_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.COAL));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.IRON_INGOT));
											} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.GOLD_INGOT));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.DIAMOND));
											} else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
												selected.getWorld().dropItemNaturally(selected.getLocation(),
														new ItemStack(Material.EMERALD));
											}
											selected.setType(Material.AIR);

										}
									}

								}
							}

						}

					}

					if (toollore.toString().contains("Straight Drill")) {

						if (toollore.toString().contains("Straight Drill III")) {
							if (getRandomInt(100) <= 11) {
								Block one = e.getBlock().getRelative(getDirection(e.getPlayer()));
								Block two = one.getRelative(getDirection(e.getPlayer()));
								Block three = two.getRelative(getDirection(e.getPlayer()));
								Block four = three.getRelative(getDirection(e.getPlayer()));
								if (one.getType() != Material.AIR && two.getType() != Material.AIR
										&& three.getType() != Material.AIR
										&& four.getType() != Material.AIR && one.getType() != Material.BEDROCK
												&& two.getType() != Material.BEDROCK
												&& three.getType() != Material.BEDROCK
												&& four.getType() != Material.BEDROCK) {
									four.getWorld().dropItemNaturally(four.getLocation(),
											new ItemStack(four.getType()));
									one.getWorld().dropItemNaturally(one.getLocation(), new ItemStack(one.getType()));
									two.getWorld().dropItemNaturally(two.getLocation(), new ItemStack(two.getType()));
									three.getWorld().dropItemNaturally(three.getLocation(),
											new ItemStack(three.getType()));
									two.setType(Material.AIR);
									one.setType(Material.AIR);
									three.setType(Material.AIR);
									four.setType(Material.AIR);
								}
							}

						} else if (toollore.toString().contains("Straight Drill II")) {
							if (getRandomInt(100) <= 8) {
								Block one = e.getBlock().getRelative(getDirection(e.getPlayer()));
								Block two = one.getRelative(getDirection(e.getPlayer()));
								Block three = two.getRelative(getDirection(e.getPlayer()));
								if (one.getType() != Material.AIR && two.getType() != Material.AIR
										&& three.getType() != Material.AIR && one.getType() != Material.BEDROCK
												&& two.getType() != Material.BEDROCK
												&& three.getType() != Material.BEDROCK) {
									three.getWorld().dropItemNaturally(three.getLocation(),
											new ItemStack(three.getType()));
									one.getWorld().dropItemNaturally(one.getLocation(), new ItemStack(one.getType()));
									three.getWorld().dropItemNaturally(three.getLocation(),
											new ItemStack(three.getType()));
									one.setType(Material.AIR);
									two.setType(Material.AIR);
									three.setType(Material.AIR);
								}
							}
						} else if (toollore.toString().contains("Straight Drill I")) {

							if (getRandomInt(100) <= 5) {

								Block one = e.getBlock().getRelative(getDirection(e.getPlayer()));
								Block two = one.getRelative(getDirection(e.getPlayer()));
								if (one.getType() != Material.AIR && two.getType() != Material.AIR
										&& one.getType() != Material.BEDROCK && two.getType() != Material.BEDROCK) {
									one.getWorld().dropItemNaturally(one.getLocation(), new ItemStack(one.getType()));
									two.getWorld().dropItemNaturally(two.getLocation(), new ItemStack(two.getType()));
									one.setType(Material.AIR);
									two.setType(Material.AIR);
								}

							}
							
						}

					}

					if (toollore.toString().contains("Ore Explosion")) {

						if (e.getBlock().getType().equals(Material.STONE)
								|| e.getBlock().getType().equals(Material.COBBLESTONE)
								|| e.getBlock().getType().equals(Material.GRAVEL)) {

							if (toollore.toString().contains("OreExplosion IV")) {
								if (getRandomInt(100) <= 11) {
									List<Block> radius = getBlocks(e.getBlock(), 3);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {
											selected.setType(Material.GOLD_ORE);
										}
									}

								}
							} else if (toollore.toString().contains("OreExplosion III")) {
								if (getRandomInt(100) <= 9) {
									List<Block> radius = getBlocks(e.getBlock(), 3);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {
											selected.setType(Material.GOLD_ORE);
										}
									}
								}
							} else if (toollore.toString().contains("OreExplosion II")) {
								if (getRandomInt(100) <= 7) {
									List<Block> radius = getBlocks(e.getBlock(), 3);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())) {
											selected.setType(Material.GOLD_ORE);

										}
									}
								}
							} else if (toollore.toString().contains("OreExplosion I")) {
								if (getRandomInt(100) <= 5) {
									List<Block> radius = getBlocks(e.getBlock(), 3);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										selected.setType(Material.GOLD_ORE);
									}
								}
							}

						}

					}

					if (toollore.toString().contains("Freeze")) {


							if (toollore.toString().contains("Freeze III")) {
								if (getRandomInt(100) <= 11) {
									List<Block> radius = getBlocks(e.getBlock(), 2);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())
												&& selected.getType().equals(Material.BEDROCK) == false) {
											selected.setType(Material.PACKED_ICE);
										}
									}

								}
							} else if (toollore.toString().contains("Freeze II")) {
								if (getRandomInt(100) <= 8) {
									List<Block> radius = getBlocks(e.getBlock(), 2);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())
												&& selected.getType().equals(Material.BEDROCK) == false) {
											selected.setType(Material.PACKED_ICE);
										}
									}
								}
							} else if (toollore.toString().contains("Freeze I")) {
								if (getRandomInt(100) <= 5) {
									List<Block> radius = getBlocks(e.getBlock(), 2);
									for (int i = 0; i < radius.size(); i++) {
										Block selected = radius.get(i);
										if (selected.getType().equals(e.getBlock().getType())
												&& selected.getType().equals(Material.BEDROCK) == false) {
											selected.setType(Material.PACKED_ICE);

										}
									}
								}
							}

						

					}

					if (toollore.toString().contains("Lucky Chest")) {

						if (toollore.toString().contains("Lucky Chest III")) {
							if (getRandomInt(100) <= 9) {
								new BukkitRunnable() {
									public void run() {
										if (e.getBlock().getType().equals(Material.BEDROCK) == false) {

											e.getBlock().getLocation().getBlock().setType(Material.CHEST);
											Chest chest = (Chest) e.getBlock().getLocation().getBlock().getState();
											chest.getBlockInventory().addItem(new ItemStack(Material.IRON_BLOCK, 5));
											chest.getBlockInventory().addItem(new ItemStack(Material.GOLD_BLOCK, 5));
											chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK, 5));

										}
									}
								}.runTask(main);

							}
							
						} else if (toollore.toString().contains("Lucky Chest II")) {
							if (getRandomInt(100) <= 7) {
								new BukkitRunnable() {
									public void run() {
										if (e.getBlock().getType().equals(Material.BEDROCK) == false) {
											e.getBlock().getLocation().getBlock().setType(Material.CHEST);
											Chest chest = (Chest) e.getBlock().getLocation().getBlock().getState();
											chest.getBlockInventory().addItem(new ItemStack(Material.IRON_BLOCK, 3));
											chest.getBlockInventory().addItem(new ItemStack(Material.GOLD_BLOCK, 2));
											chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK, 1));
										}
									}
								}.runTask(main);

							}
						} else if (toollore.toString().contains("Lucky Chest I")) {
							if (getRandomInt(100) <= 5) {
								new BukkitRunnable() {
									public void run() {
										if (e.getBlock().getType().equals(Material.BEDROCK) == false) {
											e.getBlock().getLocation().getBlock().setType(Material.CHEST);
											Chest chest = (Chest) e.getBlock().getLocation().getBlock().getState();
											chest.getBlockInventory().addItem(new ItemStack(Material.IRON_BLOCK, 2));
											chest.getBlockInventory().addItem(new ItemStack(Material.GOLD_BLOCK));
											chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK));
										}
									}
								}.runTask(main);

							}

						}
					}

					if (toollore.toString().contains("Haste")) {

						if (toollore.toString().contains("Haste III")) {
							if (getRandomInt(100) <= 11) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 3));

							}
							if (getRandomInt(100) <= 5) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 3));
							}
						} else if (toollore.toString().contains("Haste II")) {
							if (getRandomInt(100) <= 8) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 3));
							}
						} else if (toollore.toString().contains("Haste I")) {
							if (getRandomInt(100) <= 5) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 3));
							}

						}
					}

					if (toollore.toString().contains("Alchemy")) {

						if (e.getBlock().getType().equals(Material.IRON_ORE)
								|| e.getBlock().getType().equals(Material.COAL_ORE)
								|| e.getBlock().getType().equals(Material.GOLD_ORE)
								|| e.getBlock().getType().equals(Material.DIAMOND_ORE)
								|| e.getBlock().getType().equals(Material.EMERALD_ORE)
								|| e.getBlock().getType().equals(Material.IRON_BLOCK)
								|| e.getBlock().getType().equals(Material.GOLD_BLOCK)
								|| e.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {

						}

						if (toollore.toString().contains("Alchemy IV")) {
							if (getRandomInt(100) <= 14) {
								if (e.getBlock().getType().equals(Material.IRON_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_INGOT, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.IRON_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								}

							}
						} else if (toollore.toString().contains("Alchemy III")) {
							if (getRandomInt(100) <= 11) {
								if (e.getBlock().getType().equals(Material.IRON_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_INGOT, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.IRON_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								}
							}
						} else if (toollore.toString().contains("Alchemy II")) {
							if (getRandomInt(100) <= 8) {
								if (e.getBlock().getType().equals(Material.IRON_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_INGOT, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.IRON_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								}

							}

						} else if (toollore.toString().contains("Alchemy I")) {
							if (getRandomInt(100) <= 5) {
								if (e.getBlock().getType().equals(Material.IRON_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_INGOT, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.IRON_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.GOLD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.GOLD_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								} else if (e.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {

									e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, 1));
									e.getBlock().setType(Material.AIR);
								}

							}

						}
					}

					if (toollore.toString().contains("Money Bag")) {

						if (toollore.toString().contains("Money Bag V")) {
							if (getRandomInt(100) <= 18) {
								economy.depositPlayer(e.getPlayer(), 500);
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " Your found $500 dollars in this block."));

							}
						} else if (toollore.toString().contains("Money Bag IV")) {
							if (getRandomInt(100) <= 15) {
								economy.depositPlayer(e.getPlayer(), 200);
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You found $200 dollars in this block."));

							}

						} else if (toollore.toString().contains("Money Bag III")) {
							if (getRandomInt(100) <= 12) {
								economy.depositPlayer(e.getPlayer(), 100);
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You found $100 dollars in this block."));

							}

						} else if (toollore.toString().contains("Money Bag II")) {
							if (getRandomInt(100) <= 10) {
								economy.depositPlayer(e.getPlayer(), 50);
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You found $50 dollars in this block."));
							}

						} else if (toollore.toString().contains("Money Bag I")) {
							if (getRandomInt(100) <= 7) {
								economy.depositPlayer(e.getPlayer(), 25);
								e.getPlayer().sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You found $25 dollars in this block."));
							}

						}
					}

					if (toollore.toString().contains("Super Breaker")) {

						if (toollore.toString().contains("Super Breaker IV")) {
							if (getRandomInt(100) <= 12) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 117));

							}
						} else if (toollore.toString().contains("Super Breaker III")) {
							if (getRandomInt(100) <= 9) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 117));

							}
						} else if (toollore.toString().contains("Super Breaker II")) {
							if (getRandomInt(100) <= 6) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 117));
							}

						} else if (toollore.toString().contains("Super Breaker I")) {
							if (getRandomInt(100) <= 3) {
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 117));
							}

						}
					}

				}

			}
		} else {
			e.getPlayer().sendMessage(ChatColor.RED + "You can't mine in this region!");
		}

	}
}
