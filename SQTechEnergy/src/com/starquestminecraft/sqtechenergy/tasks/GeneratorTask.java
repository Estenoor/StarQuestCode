package com.starquestminecraft.sqtechenergy.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.starquestminecraft.sqtechenergy.Fuel;

import net.md_5.bungee.api.ChatColor;

public class GeneratorTask extends Thread {

	public void run() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(SQTechBase.getPluginMain(), new Runnable() {
			
			@Override
			public void run() {
				
				for (Machine machine : ObjectUtils.getAllMachinesOfType("Basic Generator")) {
					
					if (machine.enabled) {
						
						if (machine.data.containsKey("fuel")) {
							
							if (machine.data.get("fuel") instanceof HashMap<?,?>) {
								
								HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
								
								List<Fuel> fuels = new ArrayList<Fuel>();
								fuels.addAll(currentFuels.keySet());
								
								if (fuels.size() > 0) {
									
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {
										
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 2);
										furnace.setCookTime((short) 0);
										
									}
									
									Fuel fuel = fuels.get(0);
									
									if (machine.getEnergy() < machine.getMachineType().getMaxEnergy()) {
										
										machine.setEnergy(machine.getEnergy() + fuel.energyPerTick);
										currentFuels.replace(fuel, currentFuels.get(fuel) - 1);
											
										if (currentFuels.get(fuel) <= 0) {
												
											currentFuels.remove(fuel);
												
										}
										
										for (Player player : SQTechBase.currentGui.keySet()) {
											
											if (SQTechBase.currentGui.get(player) == machine.getGUI()) {
												
												if (player.getOpenInventory() != null) {
													
													if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Basic Generator")) {
														
														player.getOpenInventory().setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy: " + machine.getEnergy(), new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
														
													}
													
												}
												
											}
											
										}
											
									}
									
								} else {
									
									if (machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.FURNACE) || machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.BURNING_FURNACE)) {
										
										Furnace furnace = (Furnace) machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getState();
										furnace.setBurnTime((short) 0);
										
									}
									
								}

							}
							
						}
						
					}

				}
				
			}
			
		}, 0, 0);
		
	}
	
}