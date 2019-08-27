package com.erwan.miner.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.erwan.miner.GUI.statGUI;
import com.erwan.miner.managers.MySQLManager;

public class StatCommand implements CommandExecutor {
	
	statGUI gui = new statGUI();
	MySQLManager sql = new MySQLManager();	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if (Label.equalsIgnoreCase("level") && sender instanceof Player) {
			Player player = (Player) sender;
			gui.getInventory(player);
			
			if (player.isOp() == true && sql.getLevel(player.getUniqueId()) != 35) {
				sql.setLevel(player.getUniqueId());
			}

		}
		
		return false;
	}

}
