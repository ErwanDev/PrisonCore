package com.erwan.miner.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.erwan.miner.Main;


public class MySQLManager {

	Main plugin = Main.getInstance();

	static String table = "minerdata";

	public static void checkPlayer(UUID uuid, Player player) {
		try {

			Statement s = Main.c.createStatement();
			ResultSet results = s.executeQuery("SELECT * FROM " + table + " WHERE UUID='" + uuid + "'");
			if (!(results.next())) {
				PreparedStatement statement = Main.c.prepareStatement("INSERT INTO " + table
						+ " (uuid,name,level,exp,enchant,defense,health,movement,speed,damage,nuke,rain,light,dragon) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, player.getName());
				statement.setInt(3, 1);
				statement.setDouble(4, 0.0);
				statement.setInt(5, 3);
				statement.setInt(6, 0);
				statement.setInt(7, 0);
				statement.setInt(8, 0);
				statement.setInt(9, 0);
				statement.setInt(10, 0);
				statement.setBoolean(11, false);
				statement.setBoolean(12, false);
				statement.setBoolean(13, false);
				statement.setBoolean(14, false);
				statement.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void levelUp(UUID uuid) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET level=?, defense=?, health=?, movement=?, damage=?  WHERE UUID=?");
				statement2.setInt(1, results.getInt("level") + 1);
				statement2.setInt(2, results.getInt("defense") + 3);
				statement2.setInt(3, results.getInt("health") + 2);
				statement2.setInt(4, results.getInt("movement") + 1);
				statement2.setInt(5, results.getInt("damage") + 1);
				statement2.setString(6, uuid.toString());
				statement2.execute();
				
				if (results.getInt("level") + 1 == 10) {
					giveEnchants(uuid, 1);
				} else if (results.getInt("level") + 1 == 15) {
					giveEnchants(uuid, 1);
				} else if (results.getInt("level") + 1 == 20) {
					giveEnchants(uuid, 1);
				} else if (results.getInt("level") + 1 == 25) {
					giveEnchants(uuid, 1);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void giveEnchants(UUID uuid, int amount) {
		
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET enchant=?  WHERE UUID=?");
				statement2.setInt(1, results.getInt("enchant") + amount);
				statement2.setString(2, uuid.toString());
				statement2.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setLevel(UUID uuid) {
		
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET level=?, enchant=?  WHERE UUID=?");
				statement2.setInt(1, 35);
				statement2.setInt(2, 7);
				statement2.setString(3, uuid.toString());
				statement2.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public double xpNeeded(int level) {
		List<Integer> rankxp = Arrays.asList(0, 300, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 7500, 8350, 9200,
				11000, 13600, 15000, 19000, 21000, 23000, 35000, 45000, 50000, 60000, 70000, 80000, 90000, 100000,
				110000, 120000, 200000, 220000, 240000, 260000, 280000, 300000, 2000000);
		return rankxp.get(level);

	}

	public int getLevel(UUID uuid) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getInt("level");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean getCooldown(UUID uuid, String name) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
					return results.getBoolean(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setCooldown(UUID uuid, String name) {
		
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET nuke=?, rain=?, light=?, dragon=?  WHERE UUID=?");
				
				if (name.equalsIgnoreCase("nuke")) {
					statement2.setBoolean(1, true);
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("rain")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, true);
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("light")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, true);
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("dragon")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, true);
				}
				

				statement2.setString(5, uuid.toString());
				statement2.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeCooldown(UUID uuid, String name) {
		
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET nuke=?, rain=?, light=?, dragon=?  WHERE UUID=?");
				
				if (name.equalsIgnoreCase("nuke")) {
					statement2.setBoolean(1, false);
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("rain")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, false);
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("light")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, false);
					statement2.setBoolean(4, results.getBoolean("dragon"));
				} else if (name.equalsIgnoreCase("dragon")) {
					statement2.setBoolean(1, results.getBoolean("nuke"));
					statement2.setBoolean(2, results.getBoolean("rain"));
					statement2.setBoolean(3, results.getBoolean("light"));
					statement2.setBoolean(4, false);
				}
				

				statement2.setString(5, uuid.toString());
				statement2.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	public int getEnchant(UUID uuid) {
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getInt("enchant");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getModifiers(UUID uuid, String modifier) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getInt(modifier);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double getEXP(UUID uuid) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getDouble("exp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void updateEXP(UUID uuid, double exp) {
		
		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c
						.prepareStatement("UPDATE " + table + " SET exp=?  WHERE UUID=?");
				statement2.setDouble(1, results.getDouble("exp") + exp);
				statement2.setString(2, uuid.toString());
				statement2.execute();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	public void enchantMax(UUID uuid) {

		try {
			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {

				if (results.getInt("level") == 5) {

					PreparedStatement statement2 = Main.c
							.prepareStatement("UPDATE " + table + " SET level=?  WHERE UUID=?");
					statement2.setInt(1, 3);
					statement2.setString(2, uuid.toString());
					statement2.execute();

				} else {

					if (results.getInt("level") < 26) {

						PreparedStatement statement2 = Main.c
								.prepareStatement("UPDATE " + table + " SET level=?  WHERE UUID=?");
						statement2.setInt(1, results.getInt("enchant") + 1);
						statement2.setString(2, uuid.toString());
						statement2.execute();

					}

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void statReset(Player player, UUID uuid) {
		try {

			PreparedStatement statement = Main.c.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				PreparedStatement statement2 = Main.c.prepareStatement("UPDATE " + table
						+ " SET level=?, exp=?, enchant=?, defense=?, health=?, movement=?, speed=?, damage=?  WHERE UUID=?");
				statement2.setInt(1, 0);
				statement2.setDouble(2, 0.0);
				statement2.setInt(3, 0);
				statement2.setInt(4, 0);
				statement2.setInt(5, 3);
				statement2.setInt(6, 0);
				statement2.setInt(7, 0);
				statement2.setInt(8, 0);
				statement2.setString(9, uuid.toString());
				statement2.execute();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
