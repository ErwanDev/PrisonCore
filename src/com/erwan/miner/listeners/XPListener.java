package com.erwan.miner.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.erwan.miner.managers.MySQLManager;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import net.md_5.bungee.api.ChatColor;

public class XPListener implements Listener {

	private HashMap<UUID, Double> xpStorage = new HashMap<UUID, Double>();
	MySQLManager sql = new MySQLManager();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		Block broken = e.getBlock();

		if (broken.getType() != null) {

			ApplicableRegionSet set = null;
			set = WGBukkit.getRegionManager(e.getPlayer().getWorld()).getApplicableRegions(e.getBlock().getLocation());

			if (set.allows(DefaultFlag.BLOCK_BREAK) == true && set.allows(DefaultFlag.SLEEP) == false) {

				e.setExpToDrop(0);

				if (broken.getType() == Material.DIAMOND_ORE) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 3.0);
				} else if (broken.getType() == Material.DIAMOND_BLOCK) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 15.0);
				} else if (broken.getType() == Material.IRON_ORE) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 1.0);
				} else if (broken.getType() == Material.IRON_BLOCK) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 5.0);
				} else if (broken.getType() == Material.GOLD_ORE) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 2.0);
				} else if (broken.getType() == Material.GOLD_BLOCK) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 10.0);
				} else if (broken.getType() == Material.EMERALD_ORE) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 5.0);
				} else if (broken.getType() == Material.EMERALD_BLOCK) {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 30.0);
				} else {
					xpStorage.replace(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()) + 0.5);
				}

				if (xpStorage.get(e.getPlayer().getUniqueId()) >= 10.0) {
					sql.updateEXP(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()));
					xpStorage.replace(e.getPlayer().getUniqueId(), 0.0);
					double xpneeded = sql.getEXP(e.getPlayer().getUniqueId())
							- sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()));
					double xptotal = sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()) + 1)
							- sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()));
					float xp = (float) (xpneeded / xptotal);
					e.getPlayer().setExp(xp);
				}

				if (xpStorage.get(e.getPlayer().getUniqueId()) + sql.getEXP(e.getPlayer().getUniqueId()) >= sql
						.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()) + 1)) {
					e.getPlayer().sendMessage(ChatColor.RED + "You have ranked up!");
					sql.updateEXP(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()));
					xpStorage.replace(e.getPlayer().getUniqueId(), 0.0);
					sql.levelUp(e.getPlayer().getUniqueId());
					e.getPlayer().setLevel(sql.getLevel(e.getPlayer().getUniqueId()));
					e.getPlayer().setExp(0);
				}

			}
		}

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setKeepLevel(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		xpStorage.put(e.getPlayer().getUniqueId(), 0.0);
		double xpneeded = sql.getEXP(e.getPlayer().getUniqueId())
				- sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()));
		double xptotal = sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()) + 1)
				- sql.xpNeeded(sql.getLevel(e.getPlayer().getUniqueId()));
		float xp = (float) (xpneeded / xptotal);
		e.getPlayer().setExp(xp);
		e.getPlayer().setLevel(sql.getLevel(e.getPlayer().getUniqueId()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		sql.updateEXP(e.getPlayer().getUniqueId(), xpStorage.get(e.getPlayer().getUniqueId()));
		xpStorage.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e) {
		e.setAmount(0);
	}

	public HashMap<UUID, Double> getStorage() {
		return xpStorage;
	}

}
