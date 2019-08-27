package com.erwan.miner.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.erwan.miner.Main;

public class spinGUI {

	private static Main main = Main.getInstance();
	HashMap<UUID, Integer> timing = new HashMap<UUID, Integer>();
	HashMap<String, Integer> max = new HashMap<String, Integer>();

	public static int getRandomInt(int max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

	public static ItemStack addGlow(ItemStack item){ 

		ItemMeta meta = item.getItemMeta();
        meta.addEnchant( Enchantment.LURE, 1, false );
        meta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        item.setItemMeta( meta );
        
        return item;
		}
	
	public String colorTranslate(String message) {
	    return ChatColor.translateAlternateColorCodes('&', message);

	}
	
	public int getValue(String name) {
		
		if (name.equals("I")) {
			return 1;
		} else if (name.equals("II")) {
			return 2;
		} else if (name.equals("III")) {
			return 3;
		} else if (name.equals("IV")) {
			return 4;
		} else if (name.equals("V")) {
			return 5;
		} else if (name.equals("VI")) {
			return 6;
		}
		return 0;
	}

	public String getTier(String name) {

		if (name.contains("Sil")) {
			return "common";
		} else if (name.contains("Fre")) {
			return "common";
		} else if (name.contains("Alc")) {
			return "common";
		} else if (name.contains("Mon")) {
			return "rare";
		} else if (name.contains("Reg")) {
			return "rare";
		} else if (name.contains("Rep")) {
			return "epic";
		} else if (name.contains("Str")) {
			return "epic";
		} else if (name.contains("Ore")) {
			return "epic";
		} else if (name.contains("Has")) {
			return "epic";
		} else if (name.contains("Luc")) {
			return "epic";
		} else if (name.contains("Sup")) {
			return "legendary";
		} else if (name.contains("Eff")) {
			return "legendary";
		} else if (name.contains("Unb")) {
			return "legendary";
		} else if (name.contains("Vei")) {
			return "legendary";
		}

		return "no match";

	}

	public int getMax(String enchantname) {
		return max.get(enchantname);
	}

	public ChatColor getColor(String type) {
		if (type.equals("common")) {
			return ChatColor.WHITE;
		} else if (type.equals("rare")) {
			return ChatColor.AQUA;
		} else if (type.equals("epic")) {
			return ChatColor.DARK_PURPLE;
		} else if (type.equals("legendary")) {
			return ChatColor.GOLD;
		}
		return null;
	}

	public static String RomanNumerals(int Int) {
		LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
		roman_numerals.put("VI", 6);
		roman_numerals.put("V", 5);
		roman_numerals.put("IV", 4);
		roman_numerals.put("III", 3);
		roman_numerals.put("II", 2);
		roman_numerals.put("I", 1);
		String res = "";
		for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
			int matches = Int / entry.getValue();
			res += repeat(entry.getKey(), matches);
			Int = Int % entry.getValue();
		}
		return res;
	}

	public static String repeat(String s, int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public void getSpin(String type, ItemStack tool, short durability, Player player) {
		tool.setDurability(durability);
		ArrayList<ItemStack> common = new ArrayList<ItemStack>();
		ArrayList<ItemStack> rare = new ArrayList<ItemStack>();
		ArrayList<ItemStack> epic = new ArrayList<ItemStack>();
		ArrayList<ItemStack> legendary = new ArrayList<ItemStack>();

		ItemStack freeze = new ItemStack(Material.PACKED_ICE);
		ItemMeta freezemeta = freeze.getItemMeta();
		freezemeta.setDisplayName(ChatColor.DARK_PURPLE + "Freeze");
		ArrayList<String> freezelore = new ArrayList<String>();
		freezelore.add(ChatColor.GRAY + "Rarity: " + ChatColor.WHITE + "Common");
		freezelore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "3");
		freezelore.add("");
		freezelore.add(ChatColor.GRAY + "A chance to freeze over all blocks in a radius.");
		freezemeta.setLore(freezelore);
		freeze.setItemMeta(freezemeta);
		common.add(addGlow(freeze));
		max.put("Freeze", 3);

		ItemStack alchemy = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta alchemymeta = alchemy.getItemMeta();
		alchemymeta.setDisplayName(ChatColor.DARK_PURPLE + "Alchemy");
		ArrayList<String> alchemylore = new ArrayList<String>();
		alchemylore.add(ChatColor.GRAY + "Rarity: " + ChatColor.WHITE + "Common");
		alchemylore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "4");
		alchemylore.add("");
		alchemylore.add(ChatColor.GRAY + "A chance to upgrade the block mined into a ");
		alchemylore.add(ChatColor.GRAY + "higher tier block.");
		alchemymeta.setLore(alchemylore);
		alchemy.setItemMeta(alchemymeta);
		common.add(addGlow(alchemy));
		max.put("Alchemy", 4);

		ItemStack selectorup = new ItemStack(Material.EMERALD);
		ItemMeta selectorupmeta = selectorup.getItemMeta();
		selectorupmeta.setDisplayName(ChatColor.GRAY + "↓");
		selectorup.setItemMeta(selectorupmeta);

		ItemStack selectordown = new ItemStack(Material.EMERALD);
		ItemMeta selectordownmeta = selectordown.getItemMeta();
		selectordownmeta.setDisplayName(ChatColor.GRAY + "↑");
		selectordown.setItemMeta(selectordownmeta);

		ItemStack silk = new ItemStack(Material.DIAMOND_ORE);
		ItemMeta silkmeta = silk.getItemMeta();
		silkmeta.setDisplayName(ChatColor.DARK_PURPLE + "Silk Touch");
		ArrayList<String> silklore = new ArrayList<String>();
		silklore.add(ChatColor.GRAY + "Rarity: " + ChatColor.WHITE + "Common");
		silklore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "1");
		silklore.add("");
		silklore.add(ChatColor.GRAY + "Mined blocks drop themselves instead of the usual items.");
		silkmeta.setLore(silklore);
		silk.setItemMeta(silkmeta);
		common.add(addGlow(silk));
		max.put("Silk Touch", 1);

		ItemStack regen = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta regenmeta = regen.getItemMeta();
		regenmeta.setDisplayName(ChatColor.DARK_PURPLE + "Regenerate");
		ArrayList<String> regenlore = new ArrayList<String>();
		regenlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.AQUA + "Rare");
		regenlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "4");
		regenlore.add("");
		regenlore.add(ChatColor.GRAY + "A chance to instantly regenerate the ore");
		regenlore.add(ChatColor.GRAY + "you previously mined.");
		regenmeta.setLore(regenlore);
		regen.setItemMeta(regenmeta);
		rare.add(addGlow(regen));
		max.put("Regenerate", 4);

		ItemStack money = new ItemStack(Material.PAPER);
		ItemMeta moneymeta = money.getItemMeta();
		moneymeta.setDisplayName(ChatColor.DARK_PURPLE + "Money Bag");
		ArrayList<String> moneylore = new ArrayList<String>();
		moneylore.add(ChatColor.GRAY + "Rarity: " + ChatColor.AQUA + "Rare");
		moneylore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "5");
		moneylore.add("");
		moneylore.add(ChatColor.GRAY + "A chance to find money within a block.");
		moneymeta.setLore(moneylore);
		money.setItemMeta(moneymeta);
		rare.add(addGlow(money));
		max.put("Money Bag", 5);

		ItemStack oreexplosion = new ItemStack(Material.STONE);
		ItemMeta oreexplosionmeta = oreexplosion.getItemMeta();
		oreexplosionmeta.setDisplayName(ChatColor.DARK_PURPLE + "Ore Explosion");
		ArrayList<String> oreexplosionlore = new ArrayList<String>();
		oreexplosionlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
		oreexplosionlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "4");
		oreexplosionlore.add("");
		oreexplosionlore.add(ChatColor.GRAY + "A chance to replace an area of stone with ores.");
		oreexplosionmeta.setLore(oreexplosionlore);
		oreexplosion.setItemMeta(oreexplosionmeta);
		epic.add(addGlow(oreexplosion));
		max.put("Ore Explosion", 4);

		ItemStack luckychest = new ItemStack(Material.STONE);
		ItemMeta luckychestmeta = luckychest.getItemMeta();
		luckychestmeta.setDisplayName(ChatColor.DARK_PURPLE + "Lucky Chest");
		ArrayList<String> luckychestlore = new ArrayList<String>();
		luckychestlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
		luckychestlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "3");
		luckychestlore.add("");
		luckychestlore.add(ChatColor.GRAY + "A chance to open a lucky chest with loot inside.");
		luckychestmeta.setLore(luckychestlore);
		luckychest.setItemMeta(luckychestmeta);
		epic.add(addGlow(luckychest));
		max.put("Lucky Chest", 3);

		ItemStack drill = new ItemStack(Material.HOPPER);
		ItemMeta drillmeta = drill.getItemMeta();
		drillmeta.setDisplayName(ChatColor.DARK_PURPLE + "Straight Drill");
		ArrayList<String> drilllore = new ArrayList<String>();
		drilllore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
		drilllore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "3");
		drilllore.add("");
		drilllore.add(ChatColor.GRAY + "A chance to break blocks in a row.");
		
		drillmeta.setLore(drilllore);
		drill.setItemMeta(drillmeta);
		epic.add(addGlow(drill));
		max.put("Straight Drill", 3);

		ItemStack repair = new ItemStack(Material.ANVIL);
		ItemMeta repairmeta = repair.getItemMeta();
		repairmeta.setDisplayName(ChatColor.DARK_PURPLE + "Repair");
		ArrayList<String> repairlore = new ArrayList<String>();
		repairlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
		repairlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "5");
		repairlore.add("");
		repairlore.add(ChatColor.GRAY + "A to repair the item this enchant is ");
		repairlore.add(ChatColor.GRAY + "applied to.");
		repairmeta.setLore(repairlore);
		repair.setItemMeta(repairmeta);
		epic.add(addGlow(repair));
		max.put("Repair", 5);

		ItemStack haste = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta hastemeta = haste.getItemMeta();
		hastemeta.setDisplayName(ChatColor.DARK_PURPLE + "Haste");
		ArrayList<String> hastelore = new ArrayList<String>();
		hastelore.add(ChatColor.GRAY + "Rarity: " + ChatColor.DARK_PURPLE + "Epic");
		hastelore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "3");
		hastelore.add("");
		hastelore.add(ChatColor.GRAY + "A chance to activate haste for a short");
		hastelore.add(ChatColor.GRAY + "period.");
		hastemeta.setLore(hastelore);
		haste.setItemMeta(hastemeta);
		epic.add(addGlow(haste));
		max.put("Haste", 3);

		ItemStack breaker = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta breakermeta = breaker.getItemMeta();
		breakermeta.setDisplayName(ChatColor.DARK_PURPLE + "Super Breaker");
		ArrayList<String> breakerlore = new ArrayList<String>();
		breakerlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
		breakerlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "4");
		breakerlore.add("");
		breakerlore.add(ChatColor.GRAY + "allow you to break blocks almost instantly for a ");
		breakerlore.add(ChatColor.GRAY + "short period.");
		breakermeta.setLore(breakerlore);
		breaker.setItemMeta(breakermeta);
		legendary.add(addGlow(breaker));
		max.put("Super Breaker", 4);

		ItemStack efficiency = new ItemStack(Material.GOLD_SPADE);
		ItemMeta efficiencymeta = efficiency.getItemMeta();
		efficiencymeta.setDisplayName(ChatColor.DARK_PURPLE + "Efficiency");
		ArrayList<String> efficiencylore = new ArrayList<String>();
		efficiencylore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
		efficiencylore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "5");
		efficiencylore.add("");
		efficiencylore.add(ChatColor.GRAY + "Increases mining speed.");
		efficiencymeta.setLore(efficiencylore);
		efficiency.setItemMeta(efficiencymeta);
		legendary.add(addGlow(efficiency));
		max.put("Efficiency", 5);

		ItemStack unbreaking = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta unbreakingmeta = unbreaking.getItemMeta();
		unbreakingmeta.setDisplayName(ChatColor.DARK_PURPLE + "Unbreaking");
		ArrayList<String> unbreakinglore = new ArrayList<String>();
		unbreakinglore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
		unbreakinglore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "3");
		unbreakinglore.add("");
		unbreakinglore.add(ChatColor.GRAY + "Increases effective durability.");
		unbreakingmeta.setLore(unbreakinglore);
		unbreaking.setItemMeta(unbreakingmeta);
		legendary.add(addGlow(unbreaking));
		max.put("Unbreaking", 3);

		ItemStack vein = new ItemStack(Material.VINE);
		ItemMeta veinmeta = vein.getItemMeta();
		veinmeta.setDisplayName(ChatColor.DARK_PURPLE + "Vein Miner");
		ArrayList<String> veinlore = new ArrayList<String>();
		veinlore.add(ChatColor.GRAY + "Rarity: " + ChatColor.GOLD + "Legendary");
		veinlore.add(ChatColor.GRAY + "Max Level: " + ChatColor.YELLOW + "4");
		veinlore.add("");
		veinlore.add(ChatColor.GRAY + "A chance to automatically break al similiar ");
		veinlore.add(ChatColor.GRAY + "blocks in a given radius.");
		veinmeta.setLore(veinlore);
		vein.setItemMeta(veinmeta);
		legendary.add(addGlow(vein));
		max.put("Vein Miner", 4);

		Inventory spin = Bukkit.createInventory(null, 27, ChatColor.GRAY + "Enchanting");
		spin.setItem(4, selectorup);
		spin.setItem(22, selectordown);
		player.openInventory(spin);
		timing.put(player.getUniqueId(), 0);
		new BukkitRunnable() {

			@Override
			public void run() {

				Bukkit.getConsoleSender().sendMessage("");

				timing.put(player.getUniqueId(), timing.get(player.getUniqueId()) + 1);

				for (int i = 0; i < 4; i++) {

					int random = getRandomInt(15);
					ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) random);
					spin.setItem(i, addGlow(filler));

				}
				for (int i = 5; i < 9; i++) {

					int random = getRandomInt(15);
					ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) random);
					spin.setItem(i, addGlow(filler));

				}

				for (int r = 9; r < 18; r++) {
					if (type.equalsIgnoreCase("common")) {
						int random = getRandomInt(common.size());
						spin.setItem(r, common.get(random));
					} else if (type.equalsIgnoreCase("rare")) {
						int random = getRandomInt(rare.size());
						spin.setItem(r, rare.get(random));
					} else if (type.equalsIgnoreCase("epic")) {
						int random = getRandomInt(epic.size());
						spin.setItem(r, epic.get(random));
					} else if (type.equalsIgnoreCase("legendary")) {
						int random = getRandomInt(legendary.size());
						spin.setItem(r, legendary.get(random));
					}

				}

				for (int i = 18; i < 22; i++) {

					int random = getRandomInt(15);
					ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) random);
					spin.setItem(i, addGlow(filler));

				}

				for (int i = 23; i < 27; i++) {

					int random = getRandomInt(15);
					ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) random);
					spin.setItem(i, addGlow(filler));

				}

				if (player.getOpenInventory().getTitle().equals(ChatColor.GRAY + "Enchanting") == false) {
					Bukkit.getConsoleSender().sendMessage("");
					List<String> toollore;

					String itemname = ChatColor.stripColor(spin.getItem(15).getItemMeta().getDisplayName());
					int random = getRandomInt(max.get(itemname));
					

					if (random == 0) {
						random++;
					}
					
					String tier = RomanNumerals(random);
					ItemMeta toolmeta = tool.getItemMeta();
					if (toolmeta.hasLore() == false) {
						toollore = new ArrayList<String>();
					} else {
						toollore = toolmeta.getLore();
					}
					
					for (int n = 0; n < toollore.size(); n++) {

						if (ChatColor.stripColor(toollore.get(n).toString()).equals(ChatColor.stripColor(itemname + " " + tier))) {
							if (getValue(tier) + 1 > max.get(itemname)) {
								player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
								tool.setDurability(durability);
								player.getInventory().addItem(addGlow(tool));
								player.closeInventory();
								cancel();
								timing.remove(player.getUniqueId());
								return;
								
							} else {
								toollore.remove(n);
								toollore.add(n, getColor(type) + itemname + " " + RomanNumerals(getValue(tier) + 1));
								toolmeta.setLore(toollore);
								tool.setItemMeta(toolmeta);
								tool.setDurability(durability);
								player.getInventory().addItem(addGlow(tool));
								player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + RomanNumerals(getValue(tier) + 1) + ChatColor.GRAY + " from the crystal."));
								player.closeInventory();
								cancel();
								timing.remove(player.getUniqueId());
								return;
								
							}

						} else if (toollore.get(n).toString().contains(itemname)) {;
							String num = toollore.get(n).substring(toollore.get(n).length() - 2, toollore.get(n).length()).replace(" ", "");
							if (getValue(num) < getValue(tier)) {
								toollore.remove(n);
								toollore.add(n, getColor(type) + itemname + " " + tier);
								tool.setItemMeta(toolmeta);
								tool.setDurability(durability);
								player.getInventory().addItem(addGlow(tool));
								player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
								player.closeInventory();
								cancel();
								timing.remove(player.getUniqueId());
								return;
								
							} else {
								player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
								tool.setDurability(durability);
								player.getInventory().addItem(addGlow(tool));
								player.closeInventory();
								cancel();
								timing.remove(player.getUniqueId());
								return;
							}
					}
					
				}
					
					if (timing.get(player.getUniqueId()) != null ){
						toollore.add(getColor(type) + itemname + " " + tier);

						ArrayList<String> newlore = new ArrayList<String>();

						if (toollore.size() <= 1) {

							toolmeta.setLore(toollore);
							tool.setItemMeta(toolmeta);
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;

						}

						for (int i = 0; i < toollore.size(); i++) {

							if (getTier(toollore.get(i).toString()) == "common") {
								newlore.add(0, toollore.get(i).toString());
							} else if (getTier(toollore.get(i).toString()) == "legendary") {
								newlore.add(toollore.get(i).toString());
							} else if (getTier(toollore.get(i).toString()) == "rare") {

								if (getTier(toollore.get(0).toString()) == "common") {

									if (getTier(toollore.get(1).toString()) == "common") {

										if (getTier(toollore.get(2).toString()) == "common") {

											if (getTier(toollore.get(3).toString()) == "common") {

												if (getTier(toollore.get(4).toString()) == "common") {

													if (getTier(toollore.get(5).toString()) == "common") {

														newlore.add(6, toollore.get(i).toString());

													} else {
														newlore.add(5, toollore.get(i).toString());
													}

												} else {
													newlore.add(4, toollore.get(i).toString());
												}

											} else {
												newlore.add(3, toollore.get(i).toString());
											}

										} else {
											newlore.add(2, toollore.get(i).toString());
										}

									} else {
										newlore.add(1, toollore.get(i).toString());
									}

								} else {

									newlore.add(0, toollore.get(i).toString());

								}

							} else if (getTier(toollore.get(i).toString()) == "epic") {

								if (getTier(toollore.get(0).toString()) == "common"
										|| getTier(toollore.get(0).toString()) == "rare") {

									if (getTier(toollore.get(1).toString()) == "common"
											|| getTier(toollore.get(1).toString()) == "rare") {

										if (getTier(toollore.get(2).toString()) == "common"
												|| getTier(toollore.get(2).toString()) == "rare") {

											if (getTier(toollore.get(3).toString()) == "common"
													|| getTier(toollore.get(3).toString()) == "rare") {

												if (getTier(toollore.get(4).toString()) == "common"
														|| getTier(toollore.get(4).toString()) == "rare") {

													if (getTier(toollore.get(5).toString()) == "common"
															|| getTier(toollore.get(5).toString()) == "rare") {

														newlore.add(6, toollore.get(i).toString());

													} else {
														newlore.add(5, toollore.get(i).toString());
													}

												} else {
													newlore.add(4, toollore.get(i).toString());
												}

											} else {
												newlore.add(3, toollore.get(i).toString());
											}

										} else {
											newlore.add(2, toollore.get(i).toString());
										}

									} else {
										newlore.add(1, toollore.get(i).toString());
									}

								} else {

									newlore.add(0, toollore.get(i).toString());

								}

							}

						}

						toolmeta.setLore(newlore);
						tool.setItemMeta(toolmeta);
						tool.setDurability(durability);
						player.getInventory().addItem(addGlow(tool));
						player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
						player.closeInventory();
						cancel();
						timing.remove(player.getUniqueId());
						return;
					}

				}

				if (timing.get(player.getUniqueId()) >= 22) {
					Bukkit.getConsoleSender().sendMessage("");
					List<String> toollore;

					String itemname = ChatColor.stripColor(spin.getItem(15).getItemMeta().getDisplayName());
					int random = getRandomInt(max.get(itemname));
					

					if (random == 0) {
						random++;
					}
					
					String tier = RomanNumerals(random);
					ItemMeta toolmeta = tool.getItemMeta();
					if (toolmeta.hasLore() == false) {
						toollore = new ArrayList<String>();
					} else {
						toollore = toolmeta.getLore();
					}
					
					
					for (int n = 0; n < toollore.size(); n++) {

					if (ChatColor.stripColor(toollore.get(n).toString()).equals(ChatColor.stripColor(itemname + " " + tier))) {
						if (getValue(tier) + 1 > max.get(itemname)) {
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;
							
						} else {
							toollore.remove(n);
							toollore.add(n, getColor(type) + itemname + " " + RomanNumerals(getValue(tier) + 1));
							toolmeta.setLore(toollore);
							tool.setItemMeta(toolmeta);
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + RomanNumerals(getValue(tier) + 1) + ChatColor.GRAY + " from the crystal."));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;
							
						}

					} else if (toollore.get(n).toString().contains(itemname)) {;
						String num = toollore.get(n).substring(toollore.get(n).length() - 2, toollore.get(n).length()).replace(" ", "");
						if (getValue(num) < getValue(tier)) {
							toollore.remove(n);
							toollore.add(n, getColor(type) + itemname + " " + tier);
							tool.setItemMeta(toolmeta);
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;
							
						} else {
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;
						}
					}
					
				}
					
					if (timing.get(player.getUniqueId()) != null ){
						toollore.add(getColor(type) + itemname + " " + tier);

						ArrayList<String> newlore = new ArrayList<String>();

						if (toollore.size() <= 1) {

							toolmeta.setLore(toollore);
							tool.setItemMeta(toolmeta);
							tool.setDurability(durability);
							player.getInventory().addItem(addGlow(tool));
							player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
							player.closeInventory();
							cancel();
							timing.remove(player.getUniqueId());
							return;

						}

						for (int i = 0; i < toollore.size(); i++) {

							if (getTier(toollore.get(i).toString()) == "common") {
								newlore.add(0, toollore.get(i).toString());
							} else if (getTier(toollore.get(i).toString()) == "legendary") {
								newlore.add(toollore.get(i).toString());
							} else if (getTier(toollore.get(i).toString()) == "rare") {

								if (getTier(toollore.get(0).toString()) == "common") {

									if (getTier(toollore.get(1).toString()) == "common") {

										if (getTier(toollore.get(2).toString()) == "common") {

											if (getTier(toollore.get(3).toString()) == "common") {

												if (getTier(toollore.get(4).toString()) == "common") {

													if (getTier(toollore.get(5).toString()) == "common") {

														newlore.add(6, toollore.get(i).toString());

													} else {
														newlore.add(5, toollore.get(i).toString());
													}

												} else {
													newlore.add(4, toollore.get(i).toString());
												}

											} else {
												newlore.add(3, toollore.get(i).toString());
											}

										} else {
											newlore.add(2, toollore.get(i).toString());
										}

									} else {
										newlore.add(1, toollore.get(i).toString());
									}

								} else {

									newlore.add(0, toollore.get(i).toString());

								}

							} else if (getTier(toollore.get(i).toString()) == "epic") {

								if (getTier(toollore.get(0).toString()) == "common"
										|| getTier(toollore.get(0).toString()) == "rare") {

									if (getTier(toollore.get(1).toString()) == "common"
											|| getTier(toollore.get(1).toString()) == "rare") {

										if (getTier(toollore.get(2).toString()) == "common"
												|| getTier(toollore.get(2).toString()) == "rare") {

											if (getTier(toollore.get(3).toString()) == "common"
													|| getTier(toollore.get(3).toString()) == "rare") {

												if (getTier(toollore.get(4).toString()) == "common"
														|| getTier(toollore.get(4).toString()) == "rare") {

													if (getTier(toollore.get(5).toString()) == "common"
															|| getTier(toollore.get(5).toString()) == "rare") {

														newlore.add(6, toollore.get(i).toString());

													} else {
														newlore.add(5, toollore.get(i).toString());
													}

												} else {
													newlore.add(4, toollore.get(i).toString());
												}

											} else {
												newlore.add(3, toollore.get(i).toString());
											}

										} else {
											newlore.add(2, toollore.get(i).toString());
										}

									} else {
										newlore.add(1, toollore.get(i).toString());
									}

								} else {

									newlore.add(0, toollore.get(i).toString());

								}

							}

						}

						toolmeta.setLore(newlore);
						tool.setItemMeta(toolmeta);
						tool.setDurability(durability);
						player.getInventory().addItem(addGlow(tool));
						player.sendMessage(colorTranslate("&a&l&k:&2&l[&a&lMineMc&2&l]&a&l&k:" + ChatColor.GRAY + " You have rolled " + getColor(type) + itemname + " " + tier + ChatColor.GRAY + " from the crystal."));
						player.closeInventory();
						cancel();
						timing.remove(player.getUniqueId());
						return;
					}

				}

			}
		}.runTaskTimer(main, 0, 5);

	}

}