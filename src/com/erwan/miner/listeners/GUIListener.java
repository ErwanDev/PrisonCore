package com.erwan.miner.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.erwan.miner.managers.MySQLManager;

public class GUIListener implements Listener {

	MySQLManager sql = new MySQLManager();

	@EventHandler
	public void onClick(InventoryClickEvent event) {

		if ((event.getCurrentItem() != null) && (event.getCurrentItem().getItemMeta() != null)) {
			Inventory inv = event.getInventory();
			Player player = (Player) event.getWhoClicked();
			if ((inv.getTitle().equals(ChatColor.GRAY + player.getName() + " - Level: " + ChatColor.DARK_AQUA
					+ Integer.toString(sql.getLevel(player.getUniqueId()))))) {
				event.setCancelled(true);

			}

		}

		if ((event.getCurrentItem() != null) && (event.getCurrentItem().getItemMeta() != null)) {
			Inventory inv = event.getInventory();
			if ((inv.getTitle().equals(ChatColor.GRAY + "Enchanting"))) {
				event.setCancelled(true);

			}

		}

		if ((event.getCurrentItem() != null) && (event.getCurrentItem().getItemMeta() != null)) {
			Inventory inv = event.getInventory();
			if ((inv.getTitle().equals(ChatColor.GRAY + "Select an Ability"))) {
				
				if (event.getCurrentItem().hasItemMeta() == true && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
					
					if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Nuke")
							|| event.getCurrentItem().getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.RED + "Acid Rain")
							|| event.getCurrentItem().getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.RED + "Lightning")
							|| event.getCurrentItem().getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.RED + "Dragon's Storm")) {

						event.setCancelled(true);
						
					} else {
						event.setCancelled(true);
					}
					
				} else {
					event.setCancelled(true);
				}
				
				

			}

		}

	}
}
