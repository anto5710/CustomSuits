package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;

import javax.annotation.Nonnull;

import static org.bukkit.Bukkit.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import static org.bukkit.enchantments.Enchantment.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;
import gmail.anto5710.mcp.customsuits.Utils.items.Enchant;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.*;
import static org.bukkit.ChatColor.*;

public class Inventories {
	public static final String maininventory_name = "[Settings]",
							   armorinventory_name = "[Armor]",
							   chestinventory_name = "[Chestplate]",
							   bootsinventory_name = "[Boots]",
							   mainIteminventory_name = "[MainHand Item]",
							   offIteminventory_name = "[OffHand Item]",
							   leggingsinventory_name = "[Leggings]",
							   helmetinventory_name = "[Helmet]",
							   type_inventory_name = "[EntityType]",
							   vehicle_inventory_name = "[Vehicle_EntityType]",
							   
							   HelmetColorInventory_name = "[HelmetColor]",
							   ChestPlateColorInventory_name = "[ChestplateColor]",
							   LeggingsColorInventory_name = "[LeggingsColor]",
							   BootsColorInventory_name = "[BootsColor]",
							   commandinventory_name = "[Command]",
							   list_name = "[Online Players]";
	
	public static Inventory main = createInventory(null, 27, maininventory_name), 
							commandCenter = createInventory(null, 27, commandinventory_name),
							armorinventory = createInventory(null, 54, armorinventory_name),
	
							helmetinventory = createInventory(null, 27, helmetinventory_name),
							chestinventory = createInventory(null, 27, chestinventory_name),
							leggingsinventory = createInventory(null, 27, leggingsinventory_name),
							bootsinventory = createInventory(null, 27, bootsinventory_name),
							mainIteminventory = createInventory(null, 27, bootsinventory_name),
							offIteminventory = createInventory(null, 27, bootsinventory_name),

							type_inventory = createInventory(null, 27, type_inventory_name),
							vehicle_inventory = createInventory(null, 27, vehicle_inventory_name),
	
							HelmetColorInventory = createInventory(null, 45, HelmetColorInventory_name),
							ChestplateColorInventory = createInventory(null, 45, ChestPlateColorInventory_name),
							LeggingsColorInventory = createInventory(null, 45, LeggingsColorInventory_name),
							BootsColorInventory = createInventory(null, 45, BootsColorInventory_name);
	
	
	public static void init(){
		ItemStack levelitem = createWithName(Material.EXPERIENCE_BOTTLE, GREEN + "[Level]");
		ItemStack armorset = createWithName(Material.DIAMOND_CHESTPLATE, GOLD + "[Armor]");
		ItemStack command = createWithName(Material.COMPARATOR, RED + "[Command]");
		
		main.setItem(0, new ItemStack(command));
		main.setItem(4, new ItemStack(armorset));
		main.setItem(8, new ItemStack(levelitem));

		ItemStack ArmorIcon = new ItemStack(Material.ARMOR_STAND);
		ItemStack EnchantIcon = new ItemStack(Material.ENCHANTING_TABLE);
		ItemStack ColorIcon = new ItemStack(Material.SUNFLOWER);
		
		Enchant.englow(ArmorIcon);
		Enchant.englow(EnchantIcon);
		Enchant.englow(ColorIcon);

		ItemStack helmetEnchantIcon = createWithName(Material.ENCHANTED_BOOK, AQUA + "[Helmet]");
		ItemStack chestplateEnchantIcon = createWithName(Material.ENCHANTED_BOOK, AQUA + "[ChestPlate]");
		ItemStack leggingsEnchantIcon = createWithName(Material.ENCHANTED_BOOK, AQUA + "[Leggings]");
		ItemStack bootsEnchantIcon = createWithName(Material.ENCHANTED_BOOK, AQUA + "[Boots]");

		Enchant.englow(helmetEnchantIcon);
		Enchant.englow(chestplateEnchantIcon);
		Enchant.englow(leggingsEnchantIcon);
		Enchant.englow(bootsEnchantIcon);

		armorinventory.setItem(19, new ItemStack(Material.GOLDEN_HELMET));
		armorinventory.setItem(28, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(29, new ItemStack(Material.IRON_HORSE_ARMOR));
		armorinventory.setItem(37, new ItemStack(Material.GOLDEN_LEGGINGS));
		armorinventory.setItem(46, new ItemStack(Material.LEATHER_BOOTS));

		name(ArmorIcon, YELLOW + "[Set Armor]");
		name(EnchantIcon, AQUA + "[Enchant]");
		name(ColorIcon, GOLD + "[Set Color]");

		armorinventory.setItem(1, ArmorIcon);
		armorinventory.setItem(4, EnchantIcon);
		armorinventory.setItem(7, ColorIcon);

		armorinventory.setItem(22, helmetEnchantIcon);
		armorinventory.setItem(31, chestplateEnchantIcon);
		armorinventory.setItem(40, leggingsEnchantIcon);
		armorinventory.setItem(49, bootsEnchantIcon);
		
		librarize(leggingsinventory, PROTECTION_ENVIRONMENTAL, DURABILITY, THORNS, WATER_WORKER);
		librarize(chestinventory, PROTECTION_ENVIRONMENTAL, DURABILITY, THORNS);
		librarize(bootsinventory, PROTECTION_FIRE, DURABILITY, THORNS, PROTECTION_FALL);
		librarize(helmetinventory, PROTECTION_EXPLOSIONS, DURABILITY, THORNS, WATER_WORKER, OXYGEN);
		librarize(mainIteminventory, DAMAGE_ALL, DAMAGE_ARTHROPODS, DAMAGE_UNDEAD, DURABILITY, MENDING);
		librarize(offIteminventory, DURABILITY);
		
		ItemStack PartyPIcon = createWithName(Material.PLAYER_HEAD, AQUA + "[Party Protocol]");
		ItemStack SuitSummonIcon = createWithName(Material.DIAMOND_HELMET, AQUA + "[Suit Summon]");
		ItemStack TargetIcon = createWithName(Material.SKELETON_SKULL, BLUE + "[Target]");
		ItemStack FireworkIcon = createWithName(Material.FIREWORK_ROCKET, DARK_RED + "[Fireworks]");
		
		ItemStack HelmetColorIcon = createWithName(Material.LEATHER_HELMET, GOLD + "[Helmet Color]");
		ItemStack ChestColorIcon = createWithName(Material.LEATHER_CHESTPLATE, GOLD + "[Chestplate Color]");
		ItemStack LeggingsColorIcon = createWithName(Material.LEATHER_LEGGINGS, GOLD + "[Leggings Color]");		
		ItemStack BootsColorIcon = createWithName(Material.LEATHER_BOOTS, GOLD + "[Boots Color]");
		
		Enchant.englow(HelmetColorIcon);
		Enchant.englow(ChestColorIcon);
		Enchant.englow(LeggingsColorIcon);
		Enchant.englow(BootsColorIcon);

		armorinventory.setItem(25, HelmetColorIcon);
		armorinventory.setItem(34, ChestColorIcon);
		armorinventory.setItem(43, LeggingsColorIcon);
		armorinventory.setItem(52, BootsColorIcon);

		commandCenter.setItem(10, SuitSummonIcon);
		commandCenter.setItem(12, TargetIcon);
		commandCenter.setItem(14, PartyPIcon);
		commandCenter.setItem(16, FireworkIcon);
		
		HelmetColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_HELMET)));
		ChestplateColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_CHESTPLATE)));
		LeggingsColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_LEGGINGS)));
		BootsColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_BOOTS)));
		
		initTypeMap();
	}
	
	private static void librarize(@Nonnull Inventory inven, Enchantment ... ments){
		if (ments != null) {
			for (Enchantment ment : ments) {
				inven.addItem(Enchant.manuscribe(ment));
			}
		}
	}
	
	private static void initTypeMap() {
		// key 에 대응하는 Entity 종류를 맵에 담아둡니다.
		int eslot = 0, vslot = 0;
		
		for(CustomEntities e : CustomEntities.values()){
			ItemStack icon = e.getIcon();
			if(e!=CustomEntities.NONE){
				type_inventory.setItem(eslot, icon);
				eslot++;
			}
			vehicle_inventory.setItem(vslot, icon);
			vslot++;
		}
	}
}
