package com.erwan.miner.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.erwan.miner.Main;
import com.erwan.miner.GUI.spinGUI;
import com.erwan.miner.managers.MySQLManager;

import net.md_5.bungee.api.ChatColor;

public class CrystalListener implements Listener {

	spinGUI spin = new spinGUI();
	Main main = Main.getInstance();
	MySQLManager sql = new MySQLManager();

	public static int getRandomInt(int max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onApply(InventoryClickEvent e) {
		
		if (e.getRawSlot() != e.getSlot()) {
			
		

		if (e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.PLACE_ALL
				|| e.getAction() == InventoryAction.PLACE_ONE)

			Bukkit.getConsoleSender().sendMessage("");

		if (e.getCursor().getType() == Material.PRISMARINE_SHARD && e.getCursor().getItemMeta() != null
				&& e.getCurrentItem().getType() != Material.AIR) {

			if (e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Common Crystal")
					|| e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Rare Crystal")
					|| e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Epic Crystal")
					|| e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Legendary Crystal")) {

				if (e.getCurrentItem().getType().equals(Material.WOOD_SPADE)
						|| e.getCurrentItem().getType().equals(Material.STONE_SPADE)
						|| e.getCurrentItem().getType().equals(Material.IRON_SPADE)
						|| e.getCurrentItem().getType().equals(Material.GOLD_SPADE)
						|| e.getCurrentItem().getType().equals(Material.DIAMOND_SPADE)
						|| e.getCurrentItem().getType().equals(Material.WOOD_AXE)
						|| e.getCurrentItem().getType().equals(Material.STONE_AXE)
						|| e.getCurrentItem().getType().equals(Material.IRON_AXE)
						|| e.getCurrentItem().getType().equals(Material.GOLD_AXE)
						|| e.getCurrentItem().getType().equals(Material.DIAMOND_AXE)
						|| e.getCurrentItem().getType().equals(Material.WOOD_PICKAXE)
						|| e.getCurrentItem().getType().equals(Material.STONE_PICKAXE)
						|| e.getCurrentItem().getType().equals(Material.IRON_PICKAXE)
						|| e.getCurrentItem().getType().equals(Material.GOLD_PICKAXE)
						|| e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {

					if (e.getWhoClicked() instanceof Player) {

						Player player = (Player) e.getWhoClicked();
						ItemStack tool = e.getCurrentItem();
						ItemStack crystal = e.getCursor();
						List<String> crystallore = crystal.getItemMeta().getLore();
						short durability = e.getCurrentItem().getDurability();

						int percent = Integer.parseInt(ChatColor
								.stripColor(crystallore.get(1).replace("Success Rate: ", "").replace("%", "")));
						if (tool.hasItemMeta() == true && tool.getItemMeta().hasLore() == true) {
							List<String> toollore = tool.getItemMeta().getLore();

							if (toollore.size() <= sql.getEnchant(player.getUniqueId())) {

								if (percent >= getRandomInt(100)) {


									if (e.getCursor().getItemMeta().getDisplayName()
											.equals(ChatColor.WHITE + "Common Crystal")) {
										e.setCursor(new ItemStack(Material.AIR));
										e.setCurrentItem(new ItemStack(Material.AIR));
										spin.getSpin("common", tool, durability, player);
									} else if (e.getCursor().getItemMeta().getDisplayName()
											.equals(ChatColor.AQUA + "Rare Crystal")) {
										e.setCursor(new ItemStack(Material.AIR));
										e.setCurrentItem(new ItemStack(Material.AIR));
										spin.getSpin("rare", tool, durability, player);
									} else if (e.getCursor().getItemMeta().getDisplayName()
											.equals(ChatColor.DARK_PURPLE + "Epic Crystal")) {
										e.setCursor(new ItemStack(Material.AIR));
										e.setCurrentItem(new ItemStack(Material.AIR));
										spin.getSpin("epic", tool, durability, player);
									} else if (e.getCursor().getItemMeta().getDisplayName()
											.equals(ChatColor.GOLD + "Legendary Crystal")) {
										e.setCursor(new ItemStack(Material.AIR));
										e.setCurrentItem(new ItemStack(Material.AIR));
										spin.getSpin("legendary", tool, durability, player);
									}


								} else {
									player.sendMessage(ChatColor.RED + "The crystal was not successful");
									e.setCancelled(true);
									e.setCursor(new ItemStack(Material.AIR));
									
								}

							} else {
								player.sendMessage(
										ChatColor.RED + "You do not have enough enchant slots to apply this crystal!");
							}
						} else {
							if (percent >= getRandomInt(100)) {
								


								if (e.getCursor().getItemMeta().getDisplayName()
										.equals(ChatColor.WHITE + "Common Crystal")) {
									e.setCursor(new ItemStack(Material.AIR));
									e.setCurrentItem(new ItemStack(Material.AIR));
									spin.getSpin("common", tool, durability, player);
								} else if (e.getCursor().getItemMeta().getDisplayName()
										.equals(ChatColor.AQUA + "Rare Crystal")) {
									e.setCursor(new ItemStack(Material.AIR));
									e.setCurrentItem(new ItemStack(Material.AIR));
									spin.getSpin("rare", tool, durability, player);
								} else if (e.getCursor().getItemMeta().getDisplayName()
										.equals(ChatColor.DARK_PURPLE + "Epic Crystal")) {
									e.setCursor(new ItemStack(Material.AIR));
									e.setCurrentItem(new ItemStack(Material.AIR));
									spin.getSpin("epic", tool, durability, player);
								} else if (e.getCursor().getItemMeta().getDisplayName()
										.equals(ChatColor.GOLD + "Legendary Crystal")) {
									e.setCursor(new ItemStack(Material.AIR));
									e.setCurrentItem(new ItemStack(Material.AIR));
									spin.getSpin("legendary", tool, durability, player);
								}


							} else {
								player.sendMessage(ChatColor.RED + "The crystal was not successful");
								e.setCancelled(true);
								e.setCursor(new ItemStack(Material.AIR));
							}
						}

					}

				} else {
					e.getWhoClicked().sendMessage(ChatColor.RED + "The crystal can not be applied to this type of item.");
					e.setCancelled(true);
				}
			}

		}

	}
	}

}
