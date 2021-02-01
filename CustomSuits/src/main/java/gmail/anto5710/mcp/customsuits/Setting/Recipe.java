package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import static gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin.*;


public class Recipe {
	
	@SuppressWarnings("deprecation")
	public static void addRecipes(Server server) {
		ShapedRecipe hammerRecipe = new ShapedRecipe(NamespacedKey.minecraft("hammer"), hammer);
		hammerRecipe.shape("###","%%%","_|_");
		hammerRecipe.setIngredient('#', Material.IRON_INGOT);
		hammerRecipe.setIngredient('%', Material.IRON_BLOCK);
		hammerRecipe.setIngredient('|', Material.STICK);
		
		server.addRecipe(hammerRecipe);
		
		ShapedRecipe suitrecipe = new ShapedRecipe(NamespacedKey.minecraft("suit_commander"), suitremote);

		suitrecipe.shape("*%*", "_!_", "*%*");
		suitrecipe.setIngredient('*', Material.GOLD_INGOT);
		
		suitrecipe.setIngredient('%', Material.COMPARATOR);
		suitrecipe.setIngredient('_', Material.REPEATER);
		suitrecipe.setIngredient('!', Material.COMPASS);
		

		server.addRecipe(suitrecipe);
		ShapedRecipe Smoke_recipe = new ShapedRecipe(NamespacedKey.minecraft("smoke_bomb"), Smoke);
		Smoke_recipe.shape("^^^"  ,"^*^" , "^^^");
		Smoke_recipe.setIngredient('^',Material.GUNPOWDER);
		Smoke_recipe.setIngredient('*',Material.FIREWORK_STAR);
		
		server.addRecipe(Smoke_recipe);
		
		ShapedRecipe Bomb_recipe = new ShapedRecipe(NamespacedKey.minecraft("bomb"), Bomb);
		Bomb_recipe.shape("^*^"  ,"*&*" , "^*^");
		Bomb_recipe.setIngredient('^',Material.GUNPOWDER);
		Bomb_recipe.setIngredient('*',Material.SAND);
		Bomb_recipe.setIngredient('&',Material.FIREWORK_STAR);
		
		server.addRecipe(Bomb_recipe);
		
		ShapedRecipe gunrecipe = new ShapedRecipe(NamespacedKey.minecraft("machine_gun"), gunitem);

		gunrecipe.shape("&&*", "^$!", "&&*");
		gunrecipe.setIngredient('&', Material.IRON_INGOT);
		gunrecipe.setIngredient('*', Material.GUNPOWDER);
		gunrecipe.setIngredient('!', Material.IRON_HORSE_ARMOR);
		gunrecipe.setIngredient('$', Material.REDSTONE);
		gunrecipe.setIngredient('^', Material.TNT);

		server.addRecipe(gunrecipe);
		
		ShapedRecipe launcherrecipe = new ShapedRecipe(NamespacedKey.minecraft("missile_launcher"), missileLauncher);

		launcherrecipe.shape("^@@", "$!%", "^@@");
		
		launcherrecipe.setIngredient('@', Material.IRON_INGOT);
		launcherrecipe.setIngredient('$', Material.GOLDEN_HORSE_ARMOR);
		launcherrecipe.setIngredient('%', Material.TNT);
		launcherrecipe.setIngredient('!', Material.PISTON);
		launcherrecipe.setIngredient('^', Material.GUNPOWDER);
		
		server.addRecipe(launcherrecipe);
		
		
		
		
		ShapedRecipe Man_Chestplate_recipe = new ShapedRecipe(NamespacedKey.minecraft("man_chestplate"), Chestplate_Man);
		Man_Chestplate_recipe.shape("*#*","&C&","*#*");
		Man_Chestplate_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Chestplate_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Chestplate_recipe.setIngredient('&',Material.STRING);
		Man_Chestplate_recipe.setIngredient('C', Material.LEATHER_CHESTPLATE);
		
		server.addRecipe(Man_Chestplate_recipe);
		
		ShapedRecipe Man_Leggings_recipe = new ShapedRecipe(NamespacedKey.minecraft("man_leggings"), Leggings_Man);
		Man_Leggings_recipe.shape("*#*","&C&","*#*");
		Man_Leggings_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Leggings_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Leggings_recipe.setIngredient('&',Material.STRING);
		Man_Leggings_recipe.setIngredient('C', Material.LEATHER_LEGGINGS);
		
		server.addRecipe(Man_Leggings_recipe);
		
		ShapedRecipe Man_Boots_recipe = new ShapedRecipe(NamespacedKey.minecraft("man_boots"), Boots_Man);
		Man_Boots_recipe.shape("*#*","&C&","*#*");
		Man_Boots_recipe.setIngredient('*', Material.IRON_INGOT);
		Man_Boots_recipe.setIngredient('#', Material.GOLD_INGOT);
		Man_Boots_recipe.setIngredient('&',Material.STRING);
		Man_Boots_recipe.setIngredient('C', Material.LEATHER_BOOTS);
		
		server.addRecipe(Man_Boots_recipe);
		
	
		ShapedRecipe Man_Sword_recipe = new ShapedRecipe(NamespacedKey.minecraft("man_sword"), Sword_Man);
		Man_Sword_recipe.shape("***","*C*","#|#");
		Man_Sword_recipe.setIngredient('#', Material.IRON_INGOT);
		Man_Sword_recipe.setIngredient('*', Material.GOLD_INGOT);
		Man_Sword_recipe.setIngredient('|', Material.STICK);
		
		Man_Sword_recipe.setIngredient('C', Material.GOLDEN_SWORD);
		
		server.addRecipe(Man_Sword_recipe);
		
		ShapedRecipe Gear_Blade_recipe = new ShapedRecipe(NamespacedKey.minecraft("gear_blade"), mg_trigger);
		// 임시 조합법
		Gear_Blade_recipe.shape("/",
								"/",
								":");
		Gear_Blade_recipe.setIngredient('/', new RecipeChoice.ExactChoice(mg_ultrasteel));		
		Gear_Blade_recipe.setIngredient(':', Material.LEVER);
		server.addRecipe(Gear_Blade_recipe);
		
		
		ItemStack ultrasteel_triplet = mg_ultrasteel.clone();
		ultrasteel_triplet.setAmount(3);
		BlastingRecipe Gear_IronBamboo_recipe = new BlastingRecipe(NamespacedKey.minecraft("iron_bamboo"), 
				ultrasteel_triplet, Material.IRON_BLOCK, 6, 120);
		FuelReciper.addFuelRecipe(Gear_IronBamboo_recipe, Material.BAMBOO);
	}
}
