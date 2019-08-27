package com.erwan.miner.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.erwan.miner.Main;
import com.erwan.miner.GUI.AbilityGUI;
import com.erwan.miner.managers.MySQLManager;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class AbilityListener implements Listener {

	AbilityGUI gui = new AbilityGUI();
	MySQLManager sql = new MySQLManager();
	Main main = Main.getInstance();

	HashMap<UUID, Boolean> nukeactivated = new HashMap<UUID, Boolean>();
	HashMap<UUID, Boolean> rainactivated = new HashMap<UUID, Boolean>();
	HashMap<UUID, Boolean> lightactivated = new HashMap<UUID, Boolean>();
	HashMap<UUID, Boolean> dragonactivated = new HashMap<UUID, Boolean>();
	HashMap<UUID, Integer> blockcount = new HashMap<UUID, Integer>();
	HashMap<UUID, Integer> dragoncount = new HashMap<UUID, Integer>();

	public void startCooldown(UUID uuid, String name) {

		if (name.equals("nuke")) {
			sql.setCooldown(uuid, "nuke");

			Bukkit.getScheduler().runTaskLater(main, new Runnable() {

				public void run() {
					sql.removeCooldown(uuid, "nuke");
				}
			}, 18000L);
		} else if (name.equals("rain")) {
			sql.setCooldown(uuid, "rain");

			Bukkit.getScheduler().runTaskLater(main, new Runnable() {

				public void run() {
					sql.removeCooldown(uuid, "rain");
				}
			}, 36000L);
		} else if (name.equals("light")) {
			sql.setCooldown(uuid, "light");

			Bukkit.getScheduler().runTaskLater(main, new Runnable() {

				public void run() {
					sql.removeCooldown(uuid, "light");
				}
			}, 72000L);
		} else if (name.equals("dragon")) {
			sql.setCooldown(uuid, "dragon");

			Bukkit.getScheduler().runTaskLater(main, new Runnable() {

				public void run() {
					sql.removeCooldown(uuid, "dragon");
				}
			}, 144000L);
		}

	}

	public List<Block> getBlocks(Block start, int radius) {
		if (radius < 0) {
			return new ArrayList<Block>(0);
		}
		int iterations = (radius * 2) + 1;
		List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius * 2; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					blocks.add(start.getRelative(x, y, z));
				}
			}
		}
		return blocks;
	}

	public List<Block> getLightning(Block start, int radius) {
		if (radius < 0) {
			return new ArrayList<Block>(0);
		}

		int iterations = (radius * 2) + 1;
		List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
		for (int x = -radius; x <= radius; x++) {
			for (int y = radius; y >= -start.getY(); y--) {
				for (int z = -radius; z <= radius; z++) {
					blocks.add(start.getRelative(x, y, z));
				}
			}
		}
		return blocks;
	}

	@EventHandler
	public void onFireballDamage(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player && e.getDamager() instanceof Fireball) {
			e.setDamage(0);

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFireballHit(EntityExplodeEvent e) {

		if (e.getEntity() instanceof Fireball) {
			Fireball fireball = (Fireball) e.getEntity();

			if (fireball.getShooter() instanceof Player) {

				Player player = (Player) fireball.getShooter();
				player.setVelocity(new Vector());
				List<Block> blocks = e.blockList();
				for (int i = 0; i < blocks.size(); i++) {
					ApplicableRegionSet set = null;
					set = WGBukkit.getRegionManager(player.getWorld())
							.getApplicableRegions(blocks.get(i).getLocation());

					if (set.allows(DefaultFlag.BLOCK_BREAK) == true && set.allows(DefaultFlag.SLEEP) == false
							&& blocks.get(i).getType() != Material.BEDROCK) {

						player.getInventory().addItem(new ItemStack(blocks.get(i).getType()));
						blocks.get(i).setType(Material.AIR);

					}

				}

			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {

		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {

			if (nukeactivated.get(e.getPlayer().getUniqueId()) != null
					&& nukeactivated.get(e.getPlayer().getUniqueId()) == true) {

				Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
				fireball.setIsIncendiary(false);
				fireball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.5));
				fireball.setYield(7.0f);
				nukeactivated.remove(e.getPlayer().getUniqueId());
				if (e.getPlayer().isOp() == false) {
					startCooldown(e.getPlayer().getUniqueId(), "nuke");
				}

			}

			if (rainactivated.get(e.getPlayer().getUniqueId()) != null
					&& rainactivated.get(e.getPlayer().getUniqueId()) == true) {

				Block crosshair = e.getPlayer().getTargetBlock((Set<Material>) null, 10);
				List<Block> blocks = getBlocks(crosshair, 2);
				new BukkitRunnable() {
					@Override
					public void run() {

						if (blockcount.get(e.getPlayer().getUniqueId()) == null) {
							blockcount.put(e.getPlayer().getUniqueId(), 0);
						}

						Block selected = blocks.get(blockcount.get(e.getPlayer().getUniqueId()));
						ApplicableRegionSet set = null;

						set = WGBukkit.getRegionManager(e.getPlayer().getWorld())
								.getApplicableRegions(selected.getLocation());

						if (set.allows(DefaultFlag.BLOCK_BREAK) == true && set.allows(DefaultFlag.SLEEP) == false
								&& selected.getType() != Material.BEDROCK) {

							e.getPlayer().getInventory().addItem(new ItemStack(selected.getType()));
							selected.setType(Material.AIR);

						}

						blockcount.replace(e.getPlayer().getUniqueId(),
								blockcount.get(e.getPlayer().getUniqueId()) + 1);

						if (blockcount.get(e.getPlayer().getUniqueId()) >= blocks.size()) {
							cancel();
							e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
						}

					}
				}.runTaskTimer(main, 0, 1);

				rainactivated.remove(e.getPlayer().getUniqueId());
				blockcount.remove(e.getPlayer().getUniqueId());
				if (e.getPlayer().isOp() == false) {
					startCooldown(e.getPlayer().getUniqueId(), "rain");
				}

			}

			if (lightactivated.get(e.getPlayer().getUniqueId()) != null
					&& lightactivated.get(e.getPlayer().getUniqueId()) == true) {

				Block crosshair = e.getPlayer().getTargetBlock((Set<Material>) null, 10);
				List<Block> blocks = getLightning(crosshair, 1);

				for (int i = 0; i < blocks.size(); i++) {

					ApplicableRegionSet set = null;
					Block block = blocks.get(i);
					set = WGBukkit.getRegionManager(e.getPlayer().getWorld()).getApplicableRegions(block.getLocation());

					if (set.allows(DefaultFlag.BLOCK_BREAK) == true && set.allows(DefaultFlag.SLEEP) == false
							&& block.getType() != Material.BEDROCK) {

						e.getPlayer().getWorld().strikeLightningEffect(block.getLocation());
						e.getPlayer().getInventory().addItem(new ItemStack(block.getType()));
						block.setType(Material.AIR);
					}

				}

				e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
				lightactivated.remove(e.getPlayer().getUniqueId());
				blockcount.remove(e.getPlayer().getUniqueId());
				if (e.getPlayer().isOp() == false) {
					startCooldown(e.getPlayer().getUniqueId(), "light");
				}

			}

		}

	}

	void noAI(Entity bukkitEntity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		nmsEntity.c(tag);
		tag.setInt("NoAI", 1);
		nmsEntity.f(tag);
	}

	@EventHandler
	public void onEquip(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();

		if ((event.getCurrentItem() != null) && (event.getCurrentItem().getItemMeta() != null)) {
			if ((inv.getTitle().equals(ChatColor.GRAY + "Select an Ability"))) {

				if (event.getCurrentItem().hasItemMeta() == true
						|| event.getCurrentItem().getItemMeta().getDisplayName() != null) {

					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Nuke")) {

						if (sql.getCooldown(event.getWhoClicked().getUniqueId(), "nuke") == false
								&& sql.getLevel(event.getWhoClicked().getUniqueId()) >= 5) {
							event.setCancelled(true);
							p.closeInventory();
							nukeactivated.put(event.getWhoClicked().getUniqueId(), true);

						}

					}

					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Acid Rain")) {

						if (sql.getCooldown(event.getWhoClicked().getUniqueId(), "rain") == false
								&& sql.getLevel(event.getWhoClicked().getUniqueId()) >= 15) {
							event.setCancelled(true);
							p.closeInventory();
							rainactivated.put(event.getWhoClicked().getUniqueId(), true);
							p.setPlayerWeather(WeatherType.DOWNFALL);

						}

					}

					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Lightning")) {

						if (sql.getCooldown(event.getWhoClicked().getUniqueId(), "light") == false
								&& sql.getLevel(event.getWhoClicked().getUniqueId()) >= 30) {
							event.setCancelled(true);
							p.closeInventory();
							lightactivated.put(event.getWhoClicked().getUniqueId(), true);

						}

					}

					if (event.getCurrentItem().getItemMeta().getDisplayName()
							.equals(ChatColor.RED + "Dragon's Storm")) {

						if (sql.getCooldown(event.getWhoClicked().getUniqueId(), "dragon") == false
								&& sql.getLevel(event.getWhoClicked().getUniqueId()) >= 35) {
							event.setCancelled(true);
							p.closeInventory();
							dragonactivated.put(event.getWhoClicked().getUniqueId(), true);

							Location dragonloc = new Location(p.getWorld(), p.getLocation().getX(),
									p.getLocation().getBlockY() + 7, p.getLocation().getZ());
							Location playerblock = p.getPlayer().getLocation();
							EnderDragon dragon = (EnderDragon) p.getWorld().spawnEntity(dragonloc,
									EntityType.ENDER_DRAGON);
							noAI(dragon);

							new BukkitRunnable() {
								@Override
								public void run() {

									if (dragoncount.get(p.getPlayer().getUniqueId()) == null) {
										dragoncount.put(p.getUniqueId(), 0);
									}

									if (dragoncount.get(p.getUniqueId()) >= 10) {
										cancel();
										dragoncount.remove(p.getUniqueId());
										dragon.remove();
										return;
									}

									Location first_location = dragon.getEyeLocation();
									Location second_location = playerblock;
									Vector vector = second_location.toVector().subtract(first_location.toVector());
									Fireball fireball = dragon.launchProjectile(Fireball.class);
									fireball.setShooter(p);
									fireball.setVelocity(vector.multiply(0.2));
									fireball.setIsIncendiary(false);
									fireball.setYield(4.0f);
									dragoncount.replace(p.getUniqueId(), dragoncount.get(p.getUniqueId()) + 1);

								}
							}.runTaskTimer(main, 0, 20);

							dragonactivated.remove(p.getUniqueId());
							if (p.isOp() == false) {
								startCooldown(p.getUniqueId(), "dragon");
							}

						}

					}

				}

			}
		}

	}

}
