package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;

public class Recipe {
	CustomSuitPlugin plugin;
	public Recipe(CustomSuitPlugin plugin){
		this.plugin = plugin;
		
	}
	
	public static void addRecipes(Server server) {
		ShapedRecipe hammerRecipe = new ShapedRecipe(NamespacedKey.minecraft("Hammer"), CustomSuitPlugin.hammer);
		hammerRecipe.shape("###","%%%","_|_");
		hammerRecipe.setIngredient('#', Material.IRON_INGOT);
		hammerRecipe.setIngredient('%', Material.IRON_BLOCK);
		hammerRecipe.setIngredient('|', Material.STICK);
		
		server.addRecipe(hammerRecipe);
		
		ShapedRecipe suitrecipe = new ShapedRecipe(NamespacedKey.minecraft("Suit Commander"), CustomSuitPlugin.suitremote);

		suitrecipe.shape("*%*", "_!_", "*%*");
		suitrecipe.setIngredient('*', Material.GOLD_INGOT);
		
		suitrecipe.setIngredient('%', Material.COMPARATOR);
		suitrecipe.setIngredient('_', Material.REPEATER);
		suitrecipe.setIngredient('!', Material.COMPASS);
		

		server.addRecipe(suitrecipe);
		ShapedRecipe Smoke_recipe = new ShapedRecipe(NamespacedKey.minecraft("Smoke Bomb"), CustomSuitPlugin.Smoke);
		Smoke_recipe.shape("^^^"  ,"^*^" , "^^^");
		Smoke_recipe.setIngredient('^',Material.GUNPOWDER);
		Smoke_recipe.setIngredient('*',Material.FIREWORK_STAR);
		
		server.addRecipe(Smoke_recipe);
		
		ShapedRecipe Bomb_recipe = new ShapedRecipe(NamespacedKey.minecraft("Bomb"), CustomSuitPlugin.Bomb);
		Bomb_recipe.shape("^*^"  ,"*&*" , "^*^");
		Bomb_recipe.setIngredient('^',Material.GUNPOWDER);
		Bomb_recipe.setIngredient('*',Material.SAND);
		Bomb_recipe.setIngredient('&',Material.FIREWORK_STAR);
		
		server.addRecipe(Bomb_recipe);
		
		ShapedRecipe gunrecipe = new ShapedRecipe(NamespacedKey.minecraft("Machine Gun"), CustomSuitPlugin.gunitem);

		gunrecipe.shape("&&*", "^$!", "&&*");
		gunrecipe.setIngredient('&', Material.IRON_INGOT);
		gunrecipe.setIngredient('*', Material.GUNPOWDER);
		gunrecipe.setIngredient('!', Material.IRON_HORSE_ARMOR);
		gunrecipe.setIngredient('$', Material.REDSTONE);
		gunrecipe.setIngredient('^', Material.TNT);

		server.addRecipe(gunrecipe);
		
		ShapedRecipe launcherrecipe = new ShapedRecipe(NamespacedKey.minecraft("Missile Launcher"), CustomSuitPlugin.missileLauncher);

		launcherrecipe.shape("^@@", "$!%", "^@@");
		
		launcherrecipe.setIngredient('@', Material.IRON_INGOT);
		launcherrecipe.setIngredient('$', Material.GOLDEN_HORSE_ARMOR);
		launcherrecipe.setIngredient('%', Material.TNT);
		launcherrecipe.setIngredient('!', Material.PISTON);
		launcherrecipe.setIngredient('^', Material.GUNPOWDER);
		

		server.addRecipe(launcherrecipe);
		
		
		
		
		ShapedRecipe Man_Chestplate_recipe = new ShapedRecipe(NamespacedKey.minecraft("Man Chestplate"), CustomSuitPlugin.Chestplate_Man);
		Man_Chestplate_recipe.shape("*#*","&C&","*#*");
		Man_Chestplate_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Chestplate_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Chestplate_recipe.setIngredient('&',Material.STRING);
		Man_Chestplate_recipe.setIngredient('C', Material.LEATHER_CHESTPLATE);
		
		server.addRecipe(Man_Chestplate_recipe);
		
		ShapedRecipe Man_Leggings_recipe = new ShapedRecipe(NamespacedKey.minecraft("Man Leggings"), CustomSuitPlugin.Leggings_Man);
		Man_Leggings_recipe.shape("*#*","&C&","*#*");
		Man_Leggings_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Leggings_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Leggings_recipe.setIngredient('&',Material.STRING);
		Man_Leggings_recipe.setIngredient('C', Material.LEATHER_LEGGINGS);
		
		server.addRecipe(Man_Leggings_recipe);
		
		ShapedRecipe Man_Boots_recipe = new ShapedRecipe(NamespacedKey.minecraft("Man Boots"), CustomSuitPlugin.Boots_Man);
		Man_Boots_recipe.shape("*#*","&C&","*#*");
		Man_Boots_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Boots_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Boots_recipe.setIngredient('&',Material.STRING);
		Man_Boots_recipe.setIngredient('C', Material.LEATHER_BOOTS);
		
		server.addRecipe(Man_Boots_recipe);
		
	
		ShapedRecipe Man_Sword_recipe = new ShapedRecipe(NamespacedKey.minecraft("Man Sword"), CustomSuitPlugin.Sword_Man);
		Man_Sword_recipe.shape("***","*C*","#|#");
		Man_Sword_recipe.setIngredient('#', Material.IRON_INGOT);
		Man_Sword_recipe.setIngredient('*', Material.GOLD_INGOT);
		Man_Sword_recipe.setIngredient('|', Material.STICK);
		
		Man_Sword_recipe.setIngredient('C', Material.GOLDEN_SWORD);
		
		server.addRecipe(Man_Sword_recipe);
	}
}
