package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

public class Recipe {
	CustomSuitPlugin plugin;
	public Recipe(CustomSuitPlugin plugin){
		this.plugin = plugin;
		
	}
	public static void addRecipe(Server server) {
		ShapedRecipe suitrecipe = new ShapedRecipe(CustomSuitPlugin.suitremote);

		suitrecipe.shape("*%*", "_!_", "*%*");
		suitrecipe.setIngredient('*', Material.GOLD_INGOT);
		
		suitrecipe.setIngredient('%', Material.REDSTONE_COMPARATOR);
		suitrecipe.setIngredient('_', Material.DIODE);
		suitrecipe.setIngredient('!', Material.COMPASS);
		

		server.addRecipe(suitrecipe);

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
		
	}
	
	
	
}
