package com.erwan.miner.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.erwan.miner.GUI.AbilityGUI;

public class AbilityCommand implements CommandExecutor {
	
	AbilityGUI gui = new AbilityGUI();
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if (Label.equalsIgnoreCase("abilities") && sender instanceof Player) {
			Player player = (Player) sender;
			gui.getInventory(player);
			
		}
		
		return false;
	}

}
