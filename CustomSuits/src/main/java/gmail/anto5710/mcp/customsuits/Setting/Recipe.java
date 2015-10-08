package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Man.Man;

public class Recipe {
	CustomSuitPlugin plugin;
	public Recipe(CustomSuitPlugin plugin){
		this.plugin = plugin;
		
	}
	public static void addRecipe(Server server) {
		ShapedRecipe hammerRecipe = new ShapedRecipe(CustomSuitPlugin.hammer);
		hammerRecipe.shape("###","%%%","_|_");
		hammerRecipe.setIngredient('#', Material.IRON_INGOT);
		hammerRecipe.setIngredient('%', Material.IRON_BLOCK);
		hammerRecipe.setIngredient('|', Material.STICK);
		
		server.addRecipe(hammerRecipe);
		
		ShapedRecipe suitrecipe = new ShapedRecipe(CustomSuitPlugin.suitremote);

		suitrecipe.shape("*%*", "_!_", "*%*");
		suitrecipe.setIngredient('*', Material.GOLD_INGOT);
		
		suitrecipe.setIngredient('%', Material.REDSTONE_COMPARATOR);
		suitrecipe.setIngredient('_', Material.DIODE);
		suitrecipe.setIngredient('!', Material.COMPASS);
		

		server.addRecipe(suitrecipe);
		ShapedRecipe Smoke_recipe = new ShapedRecipe(CustomSuitPlugin.Smoke);
		Smoke_recipe.shape("^^^"  ,"^*^" , "^^^");
		Smoke_recipe.setIngredient('^',Material.SULPHUR);
		Smoke_recipe.setIngredient('*',Material.FIREWORK_CHARGE);
		
		server.addRecipe(Smoke_recipe);
		
		ShapedRecipe Bomb_recipe = new ShapedRecipe(CustomSuitPlugin.Bomb);
		Bomb_recipe.shape("^*^"  ,"*&*" , "^*^");
		Bomb_recipe.setIngredient('^',Material.SULPHUR);
		Bomb_recipe.setIngredient('*',Material.SAND);
		Bomb_recipe.setIngredient('&',Material.FIREWORK_CHARGE);
		
		server.addRecipe(Bomb_recipe);
		
		ShapedRecipe gunrecipe = new ShapedRecipe(CustomSuitPlugin.gunitem);

		gunrecipe.shape("&&*", "^$!", "&&*");
		gunrecipe.setIngredient('&', Material.IRON_INGOT);
		gunrecipe.setIngredient('*', Material.SULPHUR);
		gunrecipe.setIngredient('!', Material.IRON_BARDING);
		gunrecipe.setIngredient('$', Material.REDSTONE);
		gunrecipe.setIngredient('^', Material.TNT);

		server.addRecipe(gunrecipe);
		
		ShapedRecipe launcherrecipe = new ShapedRecipe(CustomSuitPlugin.missileLauncher);

		launcherrecipe.shape("^@@", "$!%", "^@@");
		
		launcherrecipe.setIngredient('@', Material.IRON_INGOT);
		launcherrecipe.setIngredient('$', Material.GOLD_BARDING);
		launcherrecipe.setIngredient('%', Material.TNT);
		launcherrecipe.setIngredient('!', Material.PISTON_BASE);
		launcherrecipe.setIngredient('^', Material.SULPHUR);
		

		server.addRecipe(launcherrecipe);
		
		
		
		
		ShapedRecipe Man_Chestplate_recipe = new ShapedRecipe(CustomSuitPlugin.Chestplate_Man);
		Man_Chestplate_recipe.shape("*#*","&C&","*#*");
		Man_Chestplate_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Chestplate_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Chestplate_recipe.setIngredient('&',Material.STRING);
		Man_Chestplate_recipe.setIngredient('C', Material.LEATHER_CHESTPLATE);
		
		server.addRecipe(Man_Chestplate_recipe);
		
		ShapedRecipe Man_Leggings_recipe = new ShapedRecipe(CustomSuitPlugin.Leggings_Man);
		Man_Leggings_recipe.shape("*#*","&C&","*#*");
		Man_Leggings_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Leggings_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Leggings_recipe.setIngredient('&',Material.STRING);
		Man_Leggings_recipe.setIngredient('C', Material.LEATHER_LEGGINGS);
		
		server.addRecipe(Man_Leggings_recipe);
		
		ShapedRecipe Man_Boots_recipe = new ShapedRecipe(CustomSuitPlugin.Boots_Man);
		Man_Boots_recipe.shape("*#*","&C&","*#*");
		Man_Boots_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Boots_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Boots_recipe.setIngredient('&',Material.STRING);
		Man_Boots_recipe.setIngredient('C', Material.LEATHER_BOOTS);
		
		server.addRecipe(Man_Boots_recipe);
		
		
		

		ShapedRecipe Man_Sword_recipe = new ShapedRecipe(CustomSuitPlugin.Sword_Man);
		Man_Sword_recipe.shape("***","*C*","#|#");
		Man_Sword_recipe.setIngredient('#', Material.IRON_INGOT);
		Man_Sword_recipe.setIngredient('*', Material.GOLD_INGOT);
		Man_Sword_recipe.setIngredient('|', Material.STICK);
		
		Man_Sword_recipe.setIngredient('C', Material.GOLD_SWORD);
		
		server.addRecipe(Man_Sword_recipe);
	}
	
	
	
}
