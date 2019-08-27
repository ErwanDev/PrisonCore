package com.erwan.miner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.erwan.miner.commands.AbilityCommand;
import com.erwan.miner.commands.CrystalCommand;
import com.erwan.miner.commands.StatCommand;
import com.erwan.miner.database.MySQL;
import com.erwan.miner.listeners.AbilityListener;
import com.erwan.miner.listeners.CrystalListener;
import com.erwan.miner.listeners.EnchantListener;
import com.erwan.miner.listeners.GUIListener;
import com.erwan.miner.listeners.PlayerListener;
import com.erwan.miner.listeners.ScrollListener;
import com.erwan.miner.listeners.XPListener;
import com.erwan.miner.managers.MySQLManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin {
	private static Main instance;
	public static Connection c = null;
	private static Economy econ = null;
	private static final Logger log = Logger.getLogger("Minecraft");
	MySQLManager sql = new MySQLManager();

	@Override
	public void onEnable() {
		instance = this;
		
		 if (!setupEconomy() ) {
	            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
	            getServer().getPluginManager().disablePlugin(this);
	            return;
	        }
		
		registerCommands();
		registerListeners();
		loadConfig();
		reloadConfig();
		MySQL();
		getWorldGuard();
		

		
	}

	@Override
	public void onDisable() {
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			sql.removeCooldown(player.getUniqueId(), "nuke");
			sql.removeCooldown(player.getUniqueId(), "light");
			sql.removeCooldown(player.getUniqueId(), "rain");
			sql.removeCooldown(player.getUniqueId(), "dragon");
		}
		
	

	}
	



	
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	    

		
	 
	    return (WorldGuardPlugin) plugin;
	}
	
	
	
	 private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }
	 
	    public static Economy getEconomy() {
	        return econ;
	    }

	public void MySQL() {

		try {


			
			String HOSTNAME = "sql.pebblehost.com";
			String PORT = "3306";
			String DATABASE = "customer_79019";
			String USERNAME = "customer_79019";
			String PASSWORD = "40e4113e5a";
			MySQL getConnection = new MySQL(HOSTNAME, PORT, DATABASE, USERNAME, PASSWORD);

			try {

				c = getConnection.openConnection();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(
						"[MinerPlus Configuration] Please input your MySQL Information in the config.yml and restart the server or plugin.");
			}

			try {
				Statement s = Main.c.createStatement();
				s.executeUpdate("create table if not exists minerdata ( " + "  uuid VARCHAR(50), name VARCHAR(50), level INT, exp DOUBLE, enchant INT, defense INT, health INT, movement INT, speed INT, damage INT, nuke BOOLEAN, rain BOOLEAN, light BOOLEAN, "
						+ "  dragon BOOLEAN )");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage(" There is an error creating the table in your database. Please check your MySQL Information in the config.yml to see if it is correct.");
			}

		} catch (NullPointerException e) {

		}

	}

	public static Main getInstance() {
		return instance;
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();

	}

	public void registerCommands() {
		getCommand("level").setExecutor(new StatCommand());
		getCommand("abilities").setExecutor(new AbilityCommand());
		getCommand("crystal").setExecutor(new CrystalCommand());

	}

	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new XPListener(), this);
		Bukkit.getPluginManager().registerEvents(new CrystalListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
		Bukkit.getPluginManager().registerEvents(new AbilityListener(), this);
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new ScrollListener(), this);
	}
	
	

}
