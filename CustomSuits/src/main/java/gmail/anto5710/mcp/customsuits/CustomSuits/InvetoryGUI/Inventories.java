package gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;
import gmail.anto5710.mcp.customsuits.Utils.items.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

public class Inventories {
	public static final String maininventory_name = "[Settings]";
	public static final String armorinventory_name = "[Armor]";
	public static final String chestinventory_name = "[Chestplate]";
	public static final String bootsinventory_name = "[Boots]";
	public static final String mainIteminventory_name = "[MainHand Item]";
	public static final String offIteminventory_name = "[OffHand Item]";
	public static final String leggingsinventory_name = "[Leggings]";
	public static final String helmetinventory_name = "[Helmet]";
	public static final String type_inventory_name = "[EntityType]";
	public static final String vehicle_inventory_name = "[Vehicle_EntityType]";
	
	public static final String HelmetColorInventory_name = "[HelmetColor]";
	public static final String ChestPlateColorInventory_name = "[ChestplateColor]";
	public static final String LeggingsColorInventory_name = "[LeggingsColor]";
	public static final String BootsColorInventory_name = "[BootsColor]";
	public static final String commandinventory_name = "[Command]";
	
	public static final String list_name = "[Online Players]";
	public static Inventory main = Bukkit.createInventory(null, 27, maininventory_name);
	public static Inventory commandCenter = Bukkit.createInventory(null, 27, commandinventory_name);
	public static Inventory armorinventory = Bukkit.createInventory(null, 54, armorinventory_name);
	
	public static Inventory helmetinventory = Bukkit.createInventory(null, 27, helmetinventory_name);
	public static Inventory chestinventory = Bukkit.createInventory(null, 27, chestinventory_name);
	public static Inventory leggingsinventory = Bukkit.createInventory(null, 27, leggingsinventory_name);
	public static Inventory bootsinventory = Bukkit.createInventory(null, 27, bootsinventory_name);
	public static Inventory mainIteminventory = Bukkit.createInventory(null, 27, bootsinventory_name);
	public static Inventory offIteminventory = Bukkit.createInventory(null, 27, bootsinventory_name);

	public static Inventory type_inventory = Bukkit.createInventory(null, 27, type_inventory_name);
	public static Inventory vehicle_inventory = Bukkit.createInventory(null, 27, vehicle_inventory_name);
	
	public static Inventory HelmetColorInventory = Bukkit.createInventory(null, 45, HelmetColorInventory_name);
	public static Inventory ChestplateColorInventory = Bukkit.createInventory(null, 45, ChestPlateColorInventory_name);
	public static Inventory LeggingsColorInventory = Bukkit.createInventory(null, 45, LeggingsColorInventory_name);
	public static Inventory BootsColorInventory = Bukkit.createInventory(null, 45, BootsColorInventory_name);
	
	
	public static void init(){
		ItemStack levelitem = ItemUtil.createWithName(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "[Level]");
		ItemStack armorset =ItemUtil.createWithName(Material.DIAMOND_CHESTPLATE, ChatColor.GOLD + "[Armor]");
		ItemStack command = ItemUtil.createWithName(Material.COMPARATOR, ChatColor.RED + "[Command]");
		
		main.setItem(0, new ItemStack(command));
		main.setItem(4, new ItemStack(armorset));
		main.setItem(8, new ItemStack(levelitem));

		ItemStack ArmorIcon = new ItemStack(Material.ARMOR_STAND);
		ItemStack EnchantIcon = new ItemStack(Material.ENCHANTING_TABLE);
		ItemStack ColorIcon = new ItemStack(Material.SUNFLOWER);
		
		Enchant.englow(ArmorIcon);
		Enchant.englow(EnchantIcon);
		Enchant.englow(ColorIcon);

		ItemStack helmetEnchantIcon = ItemUtil.createWithName(Material.ENCHANTED_BOOK, ChatColor.AQUA + "[Helmet]");
		ItemStack chestplateEnchantIcon = ItemUtil.createWithName(Material.ENCHANTED_BOOK, ChatColor.AQUA + "[ChestPlate]");
		ItemStack leggingsEnchantIcon = ItemUtil.createWithName(Material.ENCHANTED_BOOK, ChatColor.AQUA + "[Leggings]");
		ItemStack bootsEnchantIcon = ItemUtil.createWithName(Material.ENCHANTED_BOOK, ChatColor.AQUA + "[Boots]");

		Enchant.englow(helmetEnchantIcon);
		Enchant.englow(chestplateEnchantIcon);
		Enchant.englow(leggingsEnchantIcon);
		Enchant.englow(bootsEnchantIcon);

		armorinventory.setItem(19, new ItemStack(Material.GOLDEN_HELMET));
		armorinventory.setItem(28, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(29, new ItemStack(Material.IRON_HORSE_ARMOR));
		armorinventory.setItem(37, new ItemStack(Material.GOLDEN_LEGGINGS));
		armorinventory.setItem(46, new ItemStack(Material.LEATHER_BOOTS));

		ItemUtil.name(ArmorIcon, ChatColor.YELLOW + "[Set Armor]");
		ItemUtil.name(EnchantIcon, ChatColor.AQUA + "[Enchant]");
		ItemUtil.name(ColorIcon, ChatColor.GOLD + "[Set Color]");

		armorinventory.setItem(1, ArmorIcon);
		armorinventory.setItem(4, EnchantIcon);
		armorinventory.setItem(7, ColorIcon);

		armorinventory.setItem(22, helmetEnchantIcon);
		armorinventory.setItem(31, chestplateEnchantIcon);
		armorinventory.setItem(40, leggingsEnchantIcon);
		armorinventory.setItem(49, bootsEnchantIcon);
		
		
		librarize(leggingsinventory, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.THORNS, Enchantment.WATER_WORKER);
		librarize(chestinventory, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.THORNS);
		librarize(bootsinventory, Enchantment.PROTECTION_FIRE, Enchantment.DURABILITY, Enchantment.THORNS, Enchantment.PROTECTION_FALL);
		librarize(helmetinventory, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.DURABILITY, Enchantment.THORNS, Enchantment.WATER_WORKER, Enchantment.OXYGEN);
		librarize(mainIteminventory, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.DURABILITY, Enchantment.MENDING);
		librarize(offIteminventory, Enchantment.DURABILITY);
		
		ItemStack PartyPIcon = ItemUtil.createWithName(Material.PLAYER_HEAD, ChatColor.AQUA + "[Party Protocol]");
		ItemStack SuitSummonIcon = ItemUtil.createWithName(Material.DIAMOND_HELMET, ChatColor.AQUA + "[Suit Summon]");
		ItemStack TargetIcon = ItemUtil.createWithName(Material.SKELETON_SKULL, ChatColor.BLUE + "[Target]");
		ItemStack FireworkIcon = ItemUtil.createWithName(Material.FIREWORK_ROCKET, ChatColor.DARK_RED + "[Fireworks]");
		
		ItemStack HelmetColorIcon = ItemUtil.createWithName(Material.LEATHER_HELMET, ChatColor.GOLD + "[Helmet Color]");
		ItemStack ChestColorIcon = ItemUtil.createWithName(Material.LEATHER_CHESTPLATE, ChatColor.GOLD + "[Chestplate Color]");
		ItemStack LeggingsColorIcon = ItemUtil.createWithName(Material.LEATHER_LEGGINGS, ChatColor.GOLD + "[Leggings Color]");		
		ItemStack BootsColorIcon = ItemUtil.createWithName(Material.LEATHER_BOOTS, ChatColor.GOLD + "[Boots Color]");
		
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
		
		HelmetColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_HELMET)));
		ChestplateColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_CHESTPLATE)));
		LeggingsColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_LEGGINGS)));
		BootsColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_BOOTS)));
		
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
