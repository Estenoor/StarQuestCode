package com.ginger_walnut.sqpowertools.objects;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class PowerToolType {

	public String name;
	public String configName;
	
	public Material material;
	
	public boolean hasRecipe;

	public short durability;
	
	public int energy;
	public int energyPerUse;
	
	public int maxMods;
	
	public List<Attribute> attributes;
	public List<Effect> effects;
	public List<Modifier> modifiers;

	public List<String> extraLore;
	
	public Map<Enchantment, Integer> enchants;
	
	public PowerToolType() {
		
	}
	
}
