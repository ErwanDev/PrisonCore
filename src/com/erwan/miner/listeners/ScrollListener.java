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
import org.bukkit.inventory.meta.ItemMeta;

import com.erwan.miner.Main;
import com.erwan.miner.GUI.spinGUI;
import com.erwan.miner.managers.MySQLManager;

import net.md_5.bungee.api.ChatColor;

public class ScrollListener implements Listener {

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

			if (e.getCursor().getType() == Material.INK_SACK && e.getCursor().getItemMeta() != null
					&& e.getCurrentItem().getType() != Material.AIR) {

				if (e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.RED + "Black Crystal")) {

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
							ItemMeta toolmeta = tool.getItemMeta();

							if (tool.hasItemMeta() == true && tool.getItemMeta().hasLore() == true) {

								List<String> toollore = tool.getItemMeta().getLore();

								if (toollore.size() > 1) {

									int lorenumber = getRandomInt(toollore.size() - 1);
									player.getInventory().remove(tool);
									toollore.remove(lorenumber);
									toolmeta.setLore(toollore);
									tool.setItemMeta(toolmeta);
									player.getInventory().addItem(tool);
									e.setCursor(null);
									e.setCancelled(true);

								} else {

									if (toollore.size() == 1) {

										player.getInventory().remove(tool);
										toollore.remove(0);
										toolmeta.setLore(toollore);
										tool.setItemMeta(toolmeta);
										player.getInventory().addItem(tool);
										e.setCursor(null);
										e.setCancelled(true);

									} else {

										player.sendMessage(
												ChatColor.RED + "You do not have any enchants to remove on this tool!");
										e.setCancelled(true);

									}

								}
							} else {

								player.sendMessage(
										ChatColor.RED + "You do not have any enchants to remove on this tool!");
								e.setCancelled(true);
							}

						}

					} else {
						e.getWhoClicked()
								.sendMessage(ChatColor.RED + "The crystal can not be applied to this type of item.");
						e.setCancelled(true);
						e.setCursor(null);
					}
				}

			}

		}
	}

}
