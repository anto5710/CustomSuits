package gmail.anto5710.mcp.customsuits.Setting;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class FuelRecipe implements Listener{
	
	//sources to fuels
	private static Map<ItemStack, Material> fuels = new HashMap<>();
	
	private static ItemStack THE_FAILURE = ItemUtil.createWithName(Material.GUNPOWDER, "Failure");
	
	public static void addFuelRecipe(@Nonnull CookingRecipe<?> recipe, Material fuel) {
		Bukkit.addRecipe(recipe);
		fuels.put(recipe.getInput(), fuel);
	}
		
	private static final String LATEST_FUEL_TYPE = "LATESTO FUEL-TYPED";
	@EventHandler
	public void onPutFuel(FurnaceBurnEvent e) {
		System.out.println("put new Fuel event " +e.getFuel());
		Metadative.imprint(e.getBlock(), LATEST_FUEL_TYPE, e.getFuel().getType().name());
	}
		
	/**
	 * @param source the source item for furnace recipe
	 * @return the required fuel material
	 */
	private static Material getFuelRecipe(ItemStack source) {
		for(ItemStack fuel : fuels.keySet()) {
			if(ItemUtil.checkItem(fuel, source)) return fuels.get(fuel);
		}
		return null;
	}
	
	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {
		Material key_fuel = getFuelRecipe(e.getSource());
		Material latest_fuel = Material.matchMaterial(Metadative.excavatext(e.getBlock(), LATEST_FUEL_TYPE));
		if(key_fuel!=null && latest_fuel!=null && key_fuel != latest_fuel) {
				e.setResult(THE_FAILURE);
		}
	}
//	
//	
//	
//	/** Converts a block to its furnace blockstate
//	 * @param b Block to consider
//	 * @return furnace (blockstate) or null if not a furnace
//	 */
//	private static Furnace getFurnace(@Nonnull Block b) {
//		BlockState state = b.getState();
//		return state instanceof Furnace ? (Furnace) state : null;
//	}
}
