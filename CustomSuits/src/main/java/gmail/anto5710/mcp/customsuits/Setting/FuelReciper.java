package gmail.anto5710.mcp.customsuits.Setting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

public class FuelReciper implements Listener{
	
	//sources to fuels
	private static Set<FuelRecipe> fuelrecipes = new HashSet<>();
	private static Map<Block, ItemStack> latest_fuels = new HashMap<>();
	
	public static ItemStack THE_FAILURE = ItemUtil.createWithName(Material.GUNPOWDER, "Failure");
	
	public static void addFuelRecipe(@Nonnull CookingRecipe<?> recipe, ItemStack fuel, ItemStack failure) {
		Bukkit.addRecipe(recipe);
		fuelrecipes.add(new FuelRecipe(recipe.getInput(), fuel, failure));
	}
	
	public static void addFuelRecipe(@Nonnull CookingRecipe<?> recipe, Material fuel) {
		Bukkit.addRecipe(recipe);
		fuelrecipes.add(new FuelRecipe(recipe.getInput(), fuel, THE_FAILURE));
	}
	
	@EventHandler
	public void onPutFuel(FurnaceBurnEvent e) {
		latest_fuels.put(e.getBlock(), e.getFuel());
	}
		
	@EventHandler
	public void onFurnaceBreak(BlockBreakEvent e) {
		if(e.getBlock().getType().data==Furnace.class) {			
			latest_fuels.remove(e.getBlock());
		}
	}
	
	/**
	 * @param source the source item for furnace recipe
	 * @return the required fuel material
	 */
	private static FuelRecipe getFuelRecipe(ItemStack source) {
		for(FuelRecipe recipe : fuelrecipes) {
			if(ItemUtil.compare(recipe.getSource(), source)) return recipe;
		}
		return null;
	}
	
	private static ItemStack getLatestBurntFuel(Block furnace) {
		return latest_fuels.containsKey(furnace) ? latest_fuels.get(furnace) : getFurnace(furnace).getInventory().getFuel(); 
	}
	
	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {
		FuelRecipe key_fuel = getFuelRecipe(e.getSource());
		ItemStack latest_fuel = getLatestBurntFuel(e.getBlock());
		
		if(!SuitUtils.anyNull(key_fuel, latest_fuel) && !key_fuel.check(latest_fuel)) {
			if(key_fuel.getFailure() != null) {
				e.setResult(key_fuel.getFailure());
			}else e.setCancelled(true);
		}
	}
	
	/** Converts a block to its furnace blockstate
	 * @param b Block to consider
	 * @return furnace (blockstate) or null if not a furnace
	 */
	private static Furnace getFurnace(@Nonnull Block b) {
		BlockState state = b.getState();
		return state instanceof Furnace ? (Furnace) state : null;
	}
}
