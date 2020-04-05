package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

public class FuelRecipe{
	private ItemStack source, fuel, failure;

	private boolean materialed;
	
	public FuelRecipe(ItemStack source, ItemStack fuel, ItemStack failure) {
		materialed = false;
		this.failure = failure;
		this.fuel = fuel;
		this.source = source;
	}
	
	public FuelRecipe(ItemStack source, Material fuel, ItemStack failure) {
		this(source, new ItemStack(fuel), failure);
		materialed = true;
	}

	public boolean check(ItemStack fuel) {
		return materialed ? this.fuel.getType() == fuel.getType() : ItemUtil.compare(this.fuel, fuel);
	}
	
	public ItemStack getFuel() {return fuel;}
	
	public ItemStack getFailure() {return failure;}
	
	public boolean isMaterialed() {return materialed;}
	
	public ItemStack getSource() {return source;}
}
