package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;

import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.CancelAirClick;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.InventoryNames;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.SuitInventoryGUI;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Recipe;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import gmail.anto5710.mcp.customsuits._Thor.CreeperDicer;
import gmail.anto5710.mcp.customsuits._Thor.ForceLightning;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;
import gmail.anto5710.mcp.customsuits._Thor.HammerWeapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.util.concurrent.Monitor.Guard;

/**
 * Hello world!
 *
 */
public class CustomSuitPlugin extends JavaPlugin implements Listener {

	SuitUtils suitl = new SuitUtils(this);
	WeaponUtils wuitl = new WeaponUtils();

	public static Logger logger;
	static JavaPlugin plugin;
	Target targetting;
	HungerScheduler hscheduler;
	static boolean isPlayed = false;
	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */
	static Map<String, Class<? extends Entity>> entityMap = new HashMap<>();
	static Map<String, Color> colorMap = new HashMap<>();

	public static ItemStack Bomb = new ItemStack(Material.FIREWORK_STAR);
	public static ItemStack Smoke = new ItemStack(Material.CLAY_BALL);

	public static HashMap<Player, Inventory> command_equipment = new HashMap<>();

	public static ItemStack suitremote = new ItemStack(Material.COMPARATOR);

	public static ItemStack gunitem = new ItemStack(Material.IRON_HORSE_ARMOR);

	static ItemStack AmmoForSniper = new ItemStack(Material.GOLD_NUGGET);
	static ItemStack AmmoForMachineGun = new ItemStack(Material.FLINT);

	static Inventory inventory = Bukkit.createInventory(null, 27, InventoryNames.inventory_name);

	static Inventory armorinventory = Bukkit.createInventory(null, 63, InventoryNames.armorinventory_name);
	static Inventory chestinventory = Bukkit.createInventory(null, 27, InventoryNames.chestinventory_name);
	static Inventory bootsinventory = Bukkit.createInventory(null, 27, InventoryNames.bootsinventory_name);
	static Inventory helmetinventory = Bukkit.createInventory(null, 27, InventoryNames.helmetinventory_name);
	static Inventory leggingsinventory = Bukkit.createInventory(null, 27, InventoryNames.leggingsinventory_name);

	public static Inventory type_inventory = Bukkit.createInventory(null, 27, InventoryNames.type_inventory_name);

	public static HashMap<Player, String> Type_Map = new HashMap<>();
	public static HashMap<Player, List<LivingEntity>> target = new HashMap<>();

	static HashMap<Player, String> color = new HashMap<>();
	static HashMap<Player, Integer> amount = new HashMap<>();

	public static Inventory vehicle_inventory = Bukkit.createInventory(null, 27, InventoryNames.vehicle_inventory_name);

	public static SpawningDao dao;

	public static HashMap<Player, Inventory> handequipment = new HashMap<>(), chestequipment = new HashMap<>(),
			helmetequipment = new HashMap<>(), leggingsequipment = new HashMap<>(), bootsequipment = new HashMap<>();

	public static HashMap<Player, Inventory> equipment = new HashMap<>();
	public static HashMap<Player, Inventory> armorequipment = new HashMap<>();

	public static HashMap<Player, Color> HelmetColorMap = new HashMap<>(), ChestPlatecolorMap = new HashMap<>(),
			LeggingsColorMap = new HashMap<>(), BootsColorMap = new HashMap<>();

	public static Inventory HelmetColorInventory = Bukkit.createInventory(null, 45,
			InventoryNames.HelmetColorInventory_name);
	public static Inventory ChestplateColorInventory = Bukkit.createInventory(null, 45,
			InventoryNames.ChestPlateColorInventory_name);
	public static Inventory LeggingsColorInventory = Bukkit.createInventory(null, 45,
			InventoryNames.LeggingsColorInventory_name);
	public static Inventory BootsColorInventory = Bukkit.createInventory(null, 45,
			InventoryNames.BootsColorInventory_name);
	public static Inventory commandInventory = Bukkit.createInventory(null, 27, InventoryNames.CommandInventory_name);

	public static ItemStack missileLauncher = new ItemStack(Material.GOLDEN_HORSE_ARMOR);

	public static ItemStack hammer = new ItemStack(Material.IRON_AXE, 1, (short) Values.HammerDamage);

	public static ItemStack Helemt_Thor = new ItemStack(Material.IRON_HELMET);
	public static ItemStack Chestplate_Thor = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Thor = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Thor = new ItemStack(Material.IRON_BOOTS);

	public static ItemStack Chestplate_Man = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Man = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Man = new ItemStack(Material.IRON_BOOTS);
	public static ItemStack Sword_Man = new ItemStack(Material.GOLDEN_SWORD, 1, (short) Values.ManDeafultDamage);

	public static HashMap<String, ItemStack> entity_Icon_Map = new HashMap<>(), vehicle_Icon_Map = new HashMap<>();
	public static HashMap<Player, String> vehicle_map = new HashMap<>();

	@Override
	public void onEnable() {
		plugin = this;
		Glow.registerGlow();

		SetDisplayName(ChatColor.YELLOW + "[Bomb]", Bomb);

		SetDisplayName(ChatColor.GRAY + "[Smoke]", Smoke);
		ItemMeta meta = Smoke.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Smoke for " + Values.ManSmoke_Time + " Seconds");
		meta.setLore(lore);
		Smoke.setItemMeta(meta);

		Enchant.enchantBooks();

		ItemStack levelitem = new ItemStack(Material.EXPERIENCE_BOTTLE);
		SetDisplayName(ChatColor.GREEN + "[Level]", levelitem);

		ItemStack armorset = new ItemStack(Material.DIAMOND_CHESTPLATE);
		SetDisplayName(ChatColor.GOLD + "[Armor]", armorset);

		ItemStack command = new ItemStack(Material.COMPARATOR);
		SetDisplayName(ChatColor.RED + "[Command]", command);

		Enchant.enchantment(hammer, Enchantment.DAMAGE_ALL, 5, true);
		Enchant.enchantment(hammer, Enchantment.DURABILITY, 10, true);
		Enchant.enchantment(hammer, Enchantment.FIRE_ASPECT, 8, true);
		Enchant.enchantment(hammer, Enchantment.LOOT_BONUS_MOBS, 5, true);
		Enchant.enchantment(hammer, Enchantment.KNOCKBACK, 8, true);

		SetDisplayName(ChatColor.GOLD + "Thor_Helmet", Helemt_Thor);
		SetDisplayName(ChatColor.GOLD + "Thor_ChestPlate", Chestplate_Thor);
		SetDisplayName(ChatColor.GOLD + "Thor_Leggings", Leggings_Thor);
		SetDisplayName(ChatColor.GOLD + "Thor_Boots", Boots_Thor);

		Enchant.enchantment(Helemt_Thor, Enchantment.PROTECTION_FIRE, 15, true);
		Enchant.enchantment(Helemt_Thor, Enchantment.DURABILITY, 50, true);
		Enchant.enchantment(Helemt_Thor, Enchantment.OXYGEN, 2, true);
		Enchant.enchantment(Helemt_Thor, Enchantment.THORNS, 15, true);

		Enchant.enchantment(Chestplate_Thor, Enchantment.PROTECTION_ENVIRONMENTAL, 15, true);
		Enchant.enchantment(Chestplate_Thor, Enchantment.THORNS, 15, true);
		Enchant.enchantment(Chestplate_Thor, Enchantment.DURABILITY, 50, true);

		Enchant.enchantment(Leggings_Thor, Enchantment.THORNS, 15, true);
		Enchant.enchantment(Leggings_Thor, Enchantment.DURABILITY, 50, true);
		Enchant.enchantment(Leggings_Thor, Enchantment.PROTECTION_EXPLOSIONS, 15, true);

		Enchant.enchantment(Boots_Thor, Enchantment.THORNS, 15, true);
		Enchant.enchantment(Boots_Thor, Enchantment.DURABILITY, 50, true);
		Enchant.enchantment(Boots_Thor, Enchantment.PROTECTION_FALL, 15, true);

		Enchant.enchantment(Chestplate_Man, Enchantment.THORNS, 8, true);
		Enchant.enchantment(Chestplate_Man, Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
		Enchant.enchantment(Chestplate_Man, Enchantment.DURABILITY, 30, true);
		Enchant.enchantment(Leggings_Man, Enchantment.THORNS, 8, true);
		Enchant.enchantment(Leggings_Man, Enchantment.DURABILITY, 30, true);
		Enchant.enchantment(Leggings_Man, Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
		Enchant.enchantment(Boots_Man, Enchantment.THORNS, 8, true);
		Enchant.enchantment(Boots_Man, Enchantment.DURABILITY, 30, true);
		Enchant.enchantment(Boots_Man, Enchantment.PROTECTION_FALL, 8, true);

		Enchant.enchantment(Sword_Man, Enchantment.DAMAGE_ALL, 10, true);

		Enchant.enchantment(Sword_Man, Enchantment.FIRE_ASPECT, 10, true);

		Enchant.enchantment(Sword_Man, Enchantment.LOOT_BONUS_MOBS, 10, true);

		Enchant.enchantment(Sword_Man, Enchantment.KNOCKBACK, 10, true);

		Enchant.enchantment(Sword_Man, Enchantment.DURABILITY, 20, true);
		SetDisplayName(ChatColor.YELLOW + "Sword Of Killer", Sword_Man);

		Color Chestplate_Man_Color = Color.fromRGB(217, 206, 206);
		Color Leggings_Man_Color = Color.fromRGB(31, 28, 28);

		leathercolor(Chestplate_Man, Chestplate_Man_Color);
		leathercolor(Leggings_Man, Leggings_Man_Color);

		leathercolor(Chestplate_Thor, Color.fromRGB(35, 35, 35));
		leathercolor(Leggings_Thor, Color.fromRGB(0, 0, 65));

		inventory.setItem(0, new ItemStack(command));

		inventory.setItem(4, new ItemStack(armorset));

		inventory.setItem(8, new ItemStack(levelitem));

		ItemStack ArmorIcon = new ItemStack(Material.ARMOR_STAND);
		ItemStack enchantIcon = new ItemStack(Material.ENCHANTING_TABLE);
		ItemStack ColorIcon = new ItemStack(Material.SUNFLOWER);
		Enchant.enchantment(ArmorIcon, new Glow(), 1, true);
		Enchant.enchantment(enchantIcon, new Glow(), 1, true);
		Enchant.enchantment(ColorIcon, new Glow(), 1, true);

		ItemStack helmet = new ItemStack(Material.ENCHANTED_BOOK);
		ItemStack chestplate = new ItemStack(Material.ENCHANTED_BOOK);
		ItemStack leggings = new ItemStack(Material.ENCHANTED_BOOK);
		ItemStack boots = new ItemStack(Material.ENCHANTED_BOOK);

		Enchant.enchantBook(helmet, new Glow(), 1, true);
		Enchant.enchantBook(chestplate, new Glow(), 1, true);
		Enchant.enchantBook(leggings, new Glow(), 1, true);
		Enchant.enchantBook(boots, new Glow(), 1, true);

		SetDisplayName(ChatColor.AQUA + "[Helmet]", helmet);
		SetDisplayName(ChatColor.AQUA + "[ChestPlate]", chestplate);
		SetDisplayName(ChatColor.AQUA + "[Leggings]", leggings);
		SetDisplayName(ChatColor.AQUA + "[Boots]", boots);

		armorinventory.setItem(19, new ItemStack(Material.GOLDEN_HELMET));
		armorinventory.setItem(28, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(29, new ItemStack(Material.IRON_HORSE_ARMOR));
		armorinventory.setItem(37, new ItemStack(Material.GOLDEN_LEGGINGS));
		armorinventory.setItem(46, new ItemStack(Material.LEATHER_BOOTS));

		SetDisplayName(ChatColor.YELLOW + "[Set Armor]", ArmorIcon);

		SetDisplayName(ChatColor.AQUA + "[Enchant]", enchantIcon);

		SetDisplayName(ChatColor.GOLD + "[Set Color]", ColorIcon);

		armorinventory.setItem(1, ArmorIcon);
		armorinventory.setItem(4, enchantIcon);
		armorinventory.setItem(7, ColorIcon);

		armorinventory.setItem(22, helmet);
		armorinventory.setItem(31, chestplate);
		armorinventory.setItem(40, leggings);
		armorinventory.setItem(49, boots);

		leggingsinventory.setItem(0, Enchant.Protection);
		leggingsinventory.setItem(1, Enchant.Unbreaking);
		leggingsinventory.setItem(2, Enchant.Thorns);

		chestinventory.setItem(0, Enchant.Protection);
		chestinventory.setItem(1, Enchant.Unbreaking);
		chestinventory.setItem(2, Enchant.Thorns);

		bootsinventory.setItem(0, Enchant.Fire_Protection);
		bootsinventory.setItem(1, Enchant.Unbreaking);
		bootsinventory.setItem(2, Enchant.Thorns);

		bootsinventory.setItem(4, Enchant.Feather_Falling);

		helmetinventory.setItem(0, Enchant.Respiration);
		helmetinventory.setItem(1, Enchant.Blast_Protection);
		helmetinventory.setItem(2, Enchant.Thorns);
		helmetinventory.setItem(3, Enchant.Aqua_Affinity);
		helmetinventory.setItem(4, Enchant.Unbreaking);

		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SetDisplayName(ChatColor.AQUA + "[Party Protocol]", skull);

		ItemStack helmetM = new ItemStack(Material.DIAMOND_HELMET);
		SetDisplayName(ChatColor.AQUA + "[Suit Summon]", helmetM);

		ItemStack Targetitem = new ItemStack(Material.SKELETON_SKULL);
		SetDisplayName(ChatColor.BLUE + "[Target]", Targetitem);

		ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET);
		SetDisplayName(ChatColor.DARK_RED + "[Fireworks]", firework);

		SetDisplayName(ChatColor.GOLD + "Mjöllnir", hammer);

		SetDisplayName(ChatColor.GRAY + "Leggings", Leggings_Man);
		SetDisplayName(ChatColor.GRAY + "ChestPlate", Chestplate_Man);
		SetDisplayName(ChatColor.GRAY + "Boots", Boots_Man);

		ItemStack ColorHelmet = new ItemStack(Material.LEATHER_HELMET);
		Enchant.enchantment(ColorHelmet, new Glow(), 1, true);
		SetDisplayName(ChatColor.GOLD + "[Helmet Color]", ColorHelmet);
		ItemStack ColorChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		Enchant.enchantment(ColorChestplate, new Glow(), 1, true);
		SetDisplayName(ChatColor.GOLD + "[Chestplate Color]", ColorChestplate);
		ItemStack ColorLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Enchant.enchantment(ColorLeggings, new Glow(), 1, true);
		SetDisplayName(ChatColor.GOLD + "[Leggings Color]", ColorLeggings);
		ItemStack ColorBoots = new ItemStack(Material.LEATHER_BOOTS);
		Enchant.enchantment(ColorBoots, new Glow(), 1, true);
		SetDisplayName(ChatColor.GOLD + "[Boots Color]", ColorBoots);

		armorinventory.setItem(25, ColorHelmet);
		armorinventory.setItem(34, ColorChestplate);
		armorinventory.setItem(43, ColorLeggings);
		armorinventory.setItem(52, ColorBoots);

		commandInventory.setItem(10, helmetM);
		commandInventory.setItem(12, Targetitem);
		commandInventory.setItem(14, skull);
		commandInventory.setItem(16, firework);

		// key 에 대응하는 Entity 종류를 맵에 담아둡니다.
		logger = getLogger();

		SetDisplayName(ChatColor.RED + "[Suit Commander]", suitremote);

		SetDisplayName(ChatColor.YELLOW + "Knif-1220 " + "«" + WeaponListner.maxformachine + Values.regex
				+ WeaponListner.maxforsniper + "»", gunitem);

		List<String> GunLore = new ArrayList<>();
		GunLore.add(ChatColor.WHITE + "Machine Gun Ammo: " + ChatColor.YELLOW + WeaponListner.maxformachine);
		GunLore.add(ChatColor.WHITE + "Sniper Ammo: " + ChatColor.YELLOW + WeaponListner.maxforsniper);
		GunLore.add(ChatColor.WHITE + "Machine Gun Bullet Spread: " + ChatColor.YELLOW + 0.3 + ChatColor.WHITE + " | "
				+ ChatColor.YELLOW + 0.05 + "(Zoom)");
		GunLore.add(ChatColor.WHITE + "Sniper Bullet Spread: " + ChatColor.YELLOW + 5.0 + ChatColor.WHITE + " | "
				+ ChatColor.YELLOW + 0 + "(Zoom)");
		GunLore.add(ChatColor.WHITE + "Machine Gun Damage: " + ChatColor.YELLOW + Values.MachineGunDamage);
		GunLore.add(ChatColor.WHITE + "Sniper Damage: " + ChatColor.YELLOW + Values.SniperDamage);
		GunLore.add(ChatColor.WHITE + "Machine Gun Bullet Velocity: " + ChatColor.YELLOW + 50.0 + " Block/Second");
		GunLore.add(ChatColor.WHITE + "Sniper Bullet Velocity: " + ChatColor.YELLOW + 119 + " Block/Second");

		ItemMeta gunMeta = gunitem.getItemMeta();
		gunMeta.setLore(GunLore);
		gunitem.setItemMeta(gunMeta);
		SetDisplayName(ChatColor.DARK_RED + "[Launcher]", missileLauncher);

		entityMap.put("archer", Skeleton.class);
		putItemWithName(ChatColor.WHITE + "archer", Material.BOW, "archer");
		entityMap.put("golem", IronGolem.class);
		putItemWithName(ChatColor.WHITE + "golem", Material.ROSE_RED, "golem");
		entityMap.put("spider", CaveSpider.class);
		putItemWithName(ChatColor.RED + "spider", Material.SPIDER_EYE, "spider");
		entityMap.put("bomb", Creeper.class);
		putItemWithName(ChatColor.GRAY + "bomb", Material.GUNPOWDER, "bomb");
		entityMap.put("giant", Giant.class);
		putItemWithName(ChatColor.DARK_GREEN + "giant", Material.ZOMBIE_HEAD, "giant");
		entityMap.put("enderman", Enderman.class);
		putItemWithName(ChatColor.DARK_PURPLE + "enderman", Material.ENDER_PEARL, "enderman");
		entityMap.put("ghast", Ghast.class);
		putItemWithName(ChatColor.WHITE + "ghast", Material.GHAST_TEAR, "ghast");
		entityMap.put("snowman", Snowman.class);
		putItemWithName(ChatColor.YELLOW + "snowman", Material.PUMPKIN, "snowman");
		entityMap.put("blaze", Blaze.class);
		putItemWithName(ChatColor.DARK_RED + "blaze", Material.BLAZE_POWDER, "blaze");
		entityMap.put("wolf", Wolf.class);
		putItemWithName(ChatColor.WHITE + "wolf", Material.BONE, "wolf");

		entityMap.put("zombie", Zombie.class);
		putItemWithName(ChatColor.DARK_GREEN + "zombie", Material.ZOMBIE_HEAD, "zombie");
		entityMap.put("silverfish", Silverfish.class);
		putItemWithName(ChatColor.WHITE + "silverfish", Material.STONE, "silverfish");
		entityMap.put("horse", Horse.class);
		putItemWithName(ChatColor.GRAY + "horse", Material.IRON_HORSE_ARMOR, "horse");
		entityMap.put("guardian", org.bukkit.entity.Guardian.class);
		putItemWithName(ChatColor.AQUA + "guardian", Material.PRISMARINE_SHARD, "guardian");

		entityMap.put("warrior", PigZombie.class);
		putItemWithName(ChatColor.GOLD + "warrior", Material.GOLDEN_SWORD, "warrior");

		entityMap.put("warrior", WitherSkeleton.class);
		putItemWithName(ChatColor.BLACK + "buster", Material.GOLDEN_SWORD, "buster");

		colorMap.put("red", Color.RED);
		colorMap.put("blue", Color.BLUE);
		colorMap.put("aqua", Color.AQUA);
		colorMap.put("black", Color.BLACK);
		colorMap.put("yellow", Color.YELLOW);
		colorMap.put("green", Color.GREEN);
		colorMap.put("lime", Color.LIME);
		colorMap.put("orange", Color.ORANGE);
		colorMap.put("olive", Color.OLIVE);
		colorMap.put("gray", Color.GRAY);
		colorMap.put("purple", Color.PURPLE);
		colorMap.put("white", Color.WHITE);
		colorMap.put("silver", Color.SILVER);
		colorMap.put("navy", Color.NAVY);
		colorMap.put("maroon", Color.MAROON);
		colorMap.put("fuchsia", Color.FUCHSIA);
		colorMap.put("teal", Color.TEAL);

		PluginManager manager = getServer().getPluginManager();

		this.targetting = new Target(this);
		this.targetting.start();
		this.hscheduler = new HungerScheduler(this);
		CustomSuitPlugin.logger.info("starting Hunger Thread");

		manager.registerEvents(new PlayerEffect(this), this);
		manager.registerEvents(new WeaponListner(this), this);
		manager.registerEvents(new SuitInventoryGUI(this), this);
		manager.registerEvents(new HammerWeapons(this), this);
		manager.registerEvents(new CancelAirClick(this), this);
		manager.registerEvents(new CreeperDicer(this), this);
		manager.registerEvents(new Hammer(this), this);
		manager.registerEvents(new ForceLightning(this), this);
		
		Recipe.addRecipe(getServer());

		dao = new SpawningDao(this);
		inventory.setItem(18, entity_Icon_Map.get("warrior"));
		inventory.setItem(22, entity_Icon_Map.get("spider"));
		dao.init();

		new PlayEffect(this);

		resetTypeMap();

		HelmetColorInventory.setContents(Color_Items(new ItemStack(Material.LEATHER_HELMET)));
		ChestplateColorInventory.setContents(Color_Items(new ItemStack(Material.LEATHER_CHESTPLATE)));
		LeggingsColorInventory.setContents(Color_Items(new ItemStack(Material.LEATHER_LEGGINGS)));
		BootsColorInventory.setContents(Color_Items(new ItemStack(Material.LEATHER_BOOTS)));
	}

	public HungerScheduler getHungerScheduler() {
		return this.hscheduler;
	}

	public CustomSuitPlugin getPlugin() {
		return this;
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Server server = getServer();
		Player spnSender = server.getPlayer(sender.getName());
		if (command.getName().equals("clist")) {
			if (args.length == 0) {
				SuitUtils.wrongCommand(spnSender, command);
			} else {
				if (args[0].equals("entity")) {
					for (String key : entityMap.keySet()) {

						spnSender.sendMessage(ChatColor.BLUE + "[Input]: " + ChatColor.AQUA + key + ChatColor.BLUE
								+ "    [Entity]: " + ChatColor.AQUA + entityMap.get(key).getSimpleName());
					}
				} else if (args[0].equals("color")) {
					for (String key : colorMap.keySet()) {

						spnSender.sendMessage(ChatColor.BLUE + "[Input]: " + ChatColor.WHITE + key);
					}
				} else {
					SuitUtils.wrongCommand(spnSender, command);
				}

			}
		}
		if (command.getName().equals("csuit")) {

			Player mp = getServer().getPlayer(sender.getName());
			mp.sendMessage("");
			spawnSuit(mp);
		}
		if (command.getName().equals("get")) {
			Player player = getServer().getPlayer(sender.getName());
			if (args.length == 0) {
				SuitUtils.wrongCommand(spnSender, command);
			} else {
				String option = args[0];

				if (option.endsWith("commander")) {

					player.getInventory().addItem(suitremote);
				} else if (option.endsWith("gun")) {
					player.getInventory().addItem(gunitem);
				} else if (option.endsWith("launcher")) {
					player.getInventory().addItem(missileLauncher);
				} else if (option.endsWith("hammer")) {
					player.getInventory().addItem(hammer);
					// }else if(option.endsWith("man")){
					// player.getInventory().addItem(Chestplate_Man);
					// player.getInventory().addItem(Leggings_Man);
					// player.getInventory().addItem(Boots_Man);
					// player.getInventory().addItem(Sword_Man);
				} else if (option.endsWith("smoke")) {
					player.getInventory().addItem(Smoke);
				} else if (option.endsWith("bomb")) {
					player.getInventory().addItem(Bomb);
				} else {
					SuitUtils.wrongCommand(spnSender, command);
				}

			}
			player.updateInventory();
		}
		if (command.getName().equals("command")) {
			Player player = getServer().getPlayer(sender.getName());

			if (args.length == 0) {

			} else {

				if (args[0].equals("party")) {
					spawnall(player);
				} else {
					if (command.getName().equals("target")) {
						putTarget(spnSender, player);
					} else if (args[0].equals("firework")) {
						PlayerEffect.spawnfireworks(player);
					} else {
						SuitUtils.wrongCommand(spnSender, command);
					}
				}
			}
		}
		if (command.getName().equals("head")) {
			if (args.length == 0) {
				ItemStack head = Head(spnSender.getName());
				spnSender.getInventory().addItem(head);
				spnSender.updateInventory();
			} else {

				if (args[0] != null) {
					ItemStack head = Head(args[0]);
					spnSender.getInventory().addItem(head);
					spnSender.updateInventory();
				} else if (args[0] == null) {
					SuitUtils.warn(spnSender, "Can't Find That Player");

				}
			}
		}
		if (command.getName().equals("setcolor")) {
			if (args.length == 0) {
				SuitUtils.wrongCommand(spnSender, command);
			} else {
				String armor = args[0].toUpperCase();
				boolean isWrong = false;
				if (!Arrays.asList("HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS").contains(armor)) {
					SuitUtils.warn(spnSender, "Can't Find That ArmorType : " + armor);
					isWrong = true;
				}
				String color = args[1].toLowerCase();
				if (!colorMap.containsKey(color)) {
					SuitUtils.warn(spnSender, "Can't Find That Color : " + (color + "").toUpperCase());
					isWrong = true;
				}
				color.toLowerCase();
				if (!isWrong) {
					Color Color = colorMap.get(color);
					ItemStack item = new ItemStack(Material.LEATHER_HELMET);
					Material material = Material.LEATHER_HELMET;
					leathercolor(item, Color);
					if (armor.equals("HELMET")) {
						HelmetColorMap.put(spnSender, Color);

					} else if (armor.equals("CHESTPLATE")) {
						ChestPlatecolorMap.put(spnSender, Color);
						material = Material.LEATHER_CHESTPLATE;
					} else if (armor.equals("LEGGINGS")) {
						LeggingsColorMap.put(spnSender, Color);
						material = Material.LEATHER_LEGGINGS;
					} else if (armor.equals("BOOTS")) {
						BootsColorMap.put(spnSender, Color);
						material = Material.LEATHER_BOOTS;
					}

					item.setType(material);

					spnSender.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Changed " + armor
							+ "'s Color to " + color.toUpperCase());
					resetColorIcon(item, spnSender);
				}
			}
		}
		if (command.getName().equals("setvehicle")) {
			if (args.length >= 1) {
				String entityName = args[0].toLowerCase();
				if (!entityMap.containsKey(entityName)) {
					SuitUtils.wrongCommand(spnSender, command);
					spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
				} else {
					vehicle_map.put(spnSender, entityName);
				}
			} else {
				SuitUtils.wrongCommand(spnSender, command);
				spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
			}
		}

		if (command.getName().equals("cspn")) {
			if (SuitUtils.checkItem(suitremote, SuitUtils.getHoldingItem(spnSender))) {
				if (args.length < 2) {
					SuitUtils.wrongCommand(spnSender, command);
					spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);

				} else if (args.length >= 2) {
					int creatureCnt = 0;
					try {
						creatureCnt = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						SuitUtils.wrongCommand(spnSender, command);
						spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
					}
					LivingEntity target = null;
					if (CustomSuitPlugin.target.containsKey(spnSender)) {
						target = getTarget(spnSender);
					}
					String vehicle = null;
					String type = args[0];

					if (vehicle_map.containsKey(spnSender)) {
						vehicle = vehicle_map.get(spnSender);
					}
					if (entityMap.containsKey(type)) {
						Type_Map.put(spnSender, type);
					}
					spawnEntity(type, vehicle, creatureCnt, target, spnSender, spnSender.getLocation());
					Inventory inventory = CustomSuitPlugin.inventory;

					spnSender.openInventory(inventory);
				}
			} else {
				SuitUtils.warn(spnSender, "Please Hold Your " + suitremote.getItemMeta().getDisplayName());
			}
		}
		return true;
	}

	public static boolean isCreatedBy(Entity entity, Player player) {
		return dao.isCreatedBy(entity, player);
	}

	public static ItemStack Head(String name) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setOwner(name);
		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static void targetPlayer(Player player, LivingEntity entity, boolean sendMessage) {
		isPlayed = false;
		Collection<Creature> list = player.getWorld().getEntitiesByClass(Creature.class);

		String name = "";
		boolean setAnger = entity != null;
		if (entity != null) {
			if (entity instanceof Player == false) {
				name = entity.getType().getEntityClass().getSimpleName();
			} else {
				name = ((Player) entity).getName();
			}
		}
		for (Entity e : list) {
			if (dao.isCreatedBy(e, player)) {
				((Creature) e).setTarget(entity);
				if (e instanceof PigZombie) {
					PigZombie pg = (PigZombie) e; 
					
					if (setAnger) {
						pg.setAnger(100000);
						pg.setAngry(true);
						pg.setAnger(100000);
					} else {
						pg.setAnger(0);
						pg.setAngry(false);
						pg.setAnger(0);
					}
				}
				isPlayed = true;
			}
		}
		if (sendMessage) {
			if (!isPlayed) {

				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "No such entity");
				player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
			} else {
				putTarget(player, entity);
				player.sendMessage(
						ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Target : " + ChatColor.DARK_AQUA + name);
			}
		}
	}

	public static void putTarget(Player player, LivingEntity entity) {
		List<LivingEntity> list = target.get(player);
		if (list == null || list.isEmpty()) {
			target.put(player, new ArrayList<>(Arrays.asList(entity)));
			return;
		} else {
			ArrayList<LivingEntity> targets = new ArrayList<>(list);
			if (!targets.contains(entity)) {
				targets.add(entity);
			}
			target.put(player, targets);
		}
	}

	public static LivingEntity getTarget(Player player) {
		List<LivingEntity> targets = target.get(player);
		if (targets == null || targets.isEmpty()) {
			return null;
		} 
		return targets.get(targets.size() - 1);
	}

	public static void removeDeadTarget(Player player) {
		if (CustomSuitPlugin.target.get(player) == null) {
			return;
		}
		List<LivingEntity> targets = new ArrayList<>(CustomSuitPlugin.target.get(player));
		if (targets.isEmpty()) {
			return;
		}
		List<LivingEntity> removed = new ArrayList<>();
		Iterator<LivingEntity> iterator = targets.iterator();
		while (iterator.hasNext()) {
			LivingEntity livingEntity = iterator.next();
			if (livingEntity.isDead()) {
				removed.add(livingEntity);
				iterator.remove();
			}
		}
		targets.removeAll(removed);
		CustomSuitPlugin.target.put(player, targets);
	}

	public static void spawnall(Player player) {
		isPlayed = false;
		List<Entity> list = player.getWorld().getEntities();
		for (Entity entity : list) {
			if (dao.isCreatedBy(entity, player) && entity.getVehicle() == null) { // 제일 아랫놈만 tp
				SuitUtils.teleportWithPassengers(player.getLocation(), entity);
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Teleported Suit----");
				isPlayed = true;
			}
		}
		if (!isPlayed) {
			player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "No such entity");
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
		}
	}

	public static void spawnSuit(Player player) {
		if (SuitUtils.checkItem(suitremote, SuitUtils.getHoldingItem(player))) {
			runSpawn(player);
		} else {
			SuitUtils.warn(player, "Plese Hold Your " + suitremote.getItemMeta().getDisplayName());
		}
	}

	public static void runSpawn(Player player) {
		List<Entity> near = player.getWorld().getEntities();
		if (returnEntity(near, player) != null) {
			PlayerEffect.playSpawningEffect(returnEntity(near, player), player);
		}
	}

	public static void putItemWithName(String name, Material material, String key) {
		ItemStack item = new ItemStack(material, 1);
		SetDisplayName(name, item);
		entity_Icon_Map.put(key, item);
		vehicle_Icon_Map.put(key, item);
	}

	public static void spawnEntity(String ENTITYNAME, String vehicle, int creatureCnt, LivingEntity target, Player spnSender, Location location) {
		String entityname = ENTITYNAME.toLowerCase();
		// int vehicleCount = 0;
		boolean useVehicle = false;

		if (!entityMap.containsKey(ENTITYNAME)) {
			SuitUtils.warn(spnSender, Values.CantFindEntityType);
			return;
		}
		if (vehicle != null) {
			if (!entityMap.containsKey(vehicle)) {
				SuitUtils.warn(spnSender, Values.CantFindEntityType);
				return;
			} else {
				useVehicle = true;
			}
		}

		/* spnSender: 명령어를 입력한 플레이어 */
		ItemStack inHand = SuitUtils.getHoldingItem(spnSender);
		if (SuitUtils.checkItem(suitremote, inHand)) {

			refreshInventory(spnSender);

			for (int cnt = 0; cnt < creatureCnt; cnt++) {
				Material type = Values.Suit_Spawn_Material;
				int level = 1;
				if (equipment.containsKey(spnSender)) {
					level = equipment.get(spnSender).getItem(8).getAmount();
				}
				int amount = useVehicle ? level*2 :level;
				ItemStack material = new ItemStack(type, amount);

				if (spnSender.getInventory().contains(type, amount)) {

					Class<Entity> entityClass = loadEntityClass(ENTITYNAME);

					/* spawning 위치를 잡아줍니다. */
					Entity spawnedEntity = spnSender.getWorld().spawn(location, entityClass);
					// int entityID = spawnedEntity.getEntityId();
					// logger.info("[Entity ID]: " + entityID
					// + " by " + spnSender.getName()
					// + "(" +spnSender.getEntityId()
					// + ")");
					addElseData(spawnedEntity, entityname, spnSender, target);
					dao.saveEntity(spawnedEntity, spnSender);
					spnSender.getInventory().removeItem(material);
					spnSender.updateInventory();
					spnSender.playSound(location, Sound.BLOCK_ANVIL_USE, 1.5F, 1.5F);

					if (spawnedEntity instanceof LivingEntity) {
						entityEquipArmor((LivingEntity) spawnedEntity, level, spnSender, entityname);
					}

					if (useVehicle) {
						CreateVehicle(spnSender, target, spawnedEntity, vehicle, ENTITYNAME, level);
					}
				} else {
					SuitUtils.lack(spnSender, "Material");
				}
			}
		}
	}

	private static void CreateVehicle(Player spnSender, LivingEntity target, Entity spawnedEntity, String vehicle, String EntityName, int level) {
		Entity Vehicle = spawnedEntity.getWorld().spawn(spawnedEntity.getLocation(), loadEntityClass(vehicle));
		setVehicleData(spawnedEntity, Vehicle, spnSender, target, EntityName, level);

		Vehicle.teleport(spawnedEntity.getLocation());
		Vehicle.addPassenger(spawnedEntity);
	}

	public static void addElseData(Entity spawnedEntity, String entityName, Player spnSender, LivingEntity target) {
		if (spawnedEntity instanceof Creature) {
			((Creature) spawnedEntity).setTarget(target);
		}
		if (spawnedEntity instanceof Horse) {
			setHorseData((Horse) spawnedEntity, entityName, spnSender, true);
		}
		if (spawnedEntity instanceof Enderman) {
			setMaterialForEnderMan((Enderman) spawnedEntity, Material.TNT);
		}
		if (spawnedEntity instanceof Wolf) {
			((Wolf) spawnedEntity).setAngry(true);
		}
		if (spawnedEntity instanceof IronGolem) {
			((IronGolem) spawnedEntity).setPlayerCreated(false);
			((IronGolem) spawnedEntity).playEffect(EntityEffect.IRON_GOLEM_ROSE);
		}
		if (spawnedEntity instanceof Creeper) {
			((Creeper) spawnedEntity).setPowered(true);
		}
		if (spawnedEntity instanceof Guardian) {
			if (entityName.endsWith("guardian_king")) {
				((Guardian)spawnedEntity).setElder(true);
			}
		}
	}

	private static void setVehicleData(Entity spawnedEntity, Entity Vehicle, Player spnSender, LivingEntity target, String EntityName, int level) {
		dao.saveEntity(Vehicle, spnSender);
		addElseData(spawnedEntity, EntityName, spnSender, target);
		if (Vehicle instanceof LivingEntity) {
			entityEquipArmor(((LivingEntity) Vehicle), level, spnSender, EntityName);
		}
		if (Vehicle instanceof Creature) {
			((Creature) spawnedEntity).setTarget(target);
		}
		if(Vehicle instanceof Horse){
			((Horse) Vehicle).setTamed(true);
		}
		Vehicle.addPassenger(spawnedEntity);
	}

	private static void entityEquipArmor(LivingEntity livingentity, int level, Player spnSender, String entityName) {
		livingentity.setRemoveWhenFarAway(false);
		addPotionEffects(livingentity, new PotionEffect[] { 
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999990, 1),
				new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999990, 1 + (int)(level/32D)),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999990, 1 + (int)(level/16D)),
				new PotionEffect(PotionEffectType.SPEED, 999999990, 1 + (int)(level/32D)),
				new PotionEffect(PotionEffectType.WATER_BREATHING, 999999990, 1) });
		if (SuitUtils.canWearArmor(livingentity)) {
			setEquipment(armorequipment.get(spnSender), spnSender, livingentity, entityName);
		}
	}

	private static void setHorseData(Horse horse, String entityName, Player spnSender, boolean isAdult) {
		if (isAdult) {
			horse.setAdult();
		}
		HorseInventory horseinventory = horse.getInventory();
		ItemStack item = armorequipment.get(spnSender).getItem(8);

		horse.getInventory().setContents(horseinventory.getContents());
		horse.setJumpStrength(1 + (double) getLevel(spnSender) / 64);

		horse.setOwner(spnSender);

		if (item != null && 
		(item.getType() == Material.IRON_HORSE_ARMOR || item.getType() == Material.GOLDEN_HORSE_ARMOR || item.getType() == Material.DIAMOND_HORSE_ARMOR)) {
				horseinventory.setItem(1, item);
		}
		horseinventory.addItem(new ItemStack(Material.SADDLE));
	}

	private static void setMaterialForEnderMan(Enderman enderman, Material Material) {
		BlockData data = Material.isBlock() ? Material.createBlockData() : null;
		enderman.setCarriedBlock(data);
	}

	private static void setEquipment(Inventory inventoryitem, Player player, LivingEntity spawnedEntity, String entityName) {
		boolean CustomColor = true;

		CustomColor = false;

		LivingEntity lentity = (LivingEntity) spawnedEntity;
		lentity.setHealth(lentity.getMaxHealth());

		lentity.setCustomName(player.getName() + "|" + Values.SuitName);
		ItemStack itemForCreature = createItemForCreature(lentity);
		lentity.getEquipment().setItemInMainHand(itemForCreature);
		Color HelmetColor = Color.MAROON;
		Color ChestplateColor = Color.MAROON;
		Color LeggingsColor = Color.MAROON;
		Color BootsColor = Color.MAROON;

		if (HelmetColorMap.containsKey(player)) {
			HelmetColor = HelmetColorMap.get(player);
		}
		if (LeggingsColorMap.containsKey(player)) {
			LeggingsColor = LeggingsColorMap.get(player);
		}
		if (BootsColorMap.containsKey(player)) {
			BootsColor = BootsColorMap.get(player);
		}
		if (ChestPlatecolorMap.containsKey(player)) {
			ChestplateColor = ChestPlatecolorMap.get(player);
		}

		int level = equipment.get(player).getItem(8).getAmount();

		lentity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999990, 10));

		/* 신발 신기기 */
		ItemStack helmetitem = inventoryitem.getItem(19);

		if (helmetitem != null) {
			addData(helmetitem, helmetequipment.get(player), level, player, CustomColor, HelmetColor);

			lentity.getEquipment().setHelmet(new ItemStack(helmetitem));
			lentity.getEquipment().setHelmetDropChance(0F);
		}
		
		ItemStack chestplate = inventoryitem.getItem(28);
		if (chestplate != null) {
			addData(chestplate, chestequipment.get(player), level, player, CustomColor, ChestplateColor);

			lentity.getEquipment().setChestplate(new ItemStack(chestplate));
			lentity.getEquipment().setChestplateDropChance(0F);
		}
		
		ItemStack leggings = inventoryitem.getItem(37);
		if (leggings != null) {
			addData(leggings, leggingsequipment.get(player), level, player, CustomColor, LeggingsColor);

			lentity.getEquipment().setLeggings(new ItemStack(leggings));
			lentity.getEquipment().setLeggingsDropChance(0F);
		}

		ItemStack boots = inventoryitem.getItem(46);
		if (boots != null) {

			addData(boots, bootsequipment.get(player), level, player, CustomColor, BootsColor);
			lentity.getEquipment().setBoots(new ItemStack(boots));
			lentity.getEquipment().setBootsDropChance(0F);
		}
		
		ItemStack hand = inventoryitem.getItem(29);
		if (hand != null) {
			SetDisplayName(ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level, hand);
			Enchant.enchantment(hand, new Glow(), 1, true);
			lentity.getEquipment().setItemInMainHand(hand);
			lentity.getEquipment().setItemInMainHandDropChance(0);
		}
	}

	public static void leatherDyecolor(ItemStack item, DyeColor color) {
		Color c = color.getColor();

		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(c);
		item.setItemMeta(meta);
	}

	public static void leathercolor(ItemStack item, Color color) {

		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
	}

	public static void addData(ItemStack item, Inventory enchantInventory, int level, Player player,
			boolean GetColorFromInventory, Color color) {
		if (item != null) {
			if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE
					|| item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS) {
				if (color != null) {
					leathercolor(item, color);
				}
			}

			if (enchantInventory != null) {
				SetDisplayName(ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level, item);
				int size = enchantInventory.getSize();

				if (size != 0) {
					for (int i = 0; i <= size - 1; i++) {
						ItemStack enchant = enchantInventory.getItem(i);
						Enchant.enchantFromBook(item, enchant);
					}
				}
			}
		}
	}

	private static ItemStack createItemForCreature(LivingEntity ett) {
		ItemStack item = null;
		if (ett.getType() == EntityType.ENDERMAN) {
			item = new ItemStack(Material.TNT);
		} else {
			item = new ItemStack(Values.SuitLauncher);

		}
		return item;
	}

	public static <T extends Entity> Class<T> loadEntityClass(String entityType) {
		return (Class<T>) entityMap.get(entityType.toLowerCase());
	}

	public static void addPotionEffects(LivingEntity ett, PotionEffect... effects) {
		PotionEffect[] arrayOfPotionEffect;
		int j = (arrayOfPotionEffect = effects).length;
		for (int i = 0; i < j; i++) {
			PotionEffect eft = arrayOfPotionEffect[i];
			ett.addPotionEffect(eft);
		}
	}

	public static void runSpawn(Location entitylocation, Location playerlocation, LivingEntity entity, Player player) {
		Vector vectorStart = entitylocation.toVector();
		Vector vectorEnd = playerlocation.toVector();
		Vector difference = vectorStart.subtract(vectorEnd);

		double distance = difference.length();
		if (distance < 0) {
			return;
		}

		Location currentLoc = playerlocation.clone();
		double dx = (difference.getX() / distance) * 0.5;
		double dy = (difference.getY() / distance) * 0.5;
		double dz = (difference.getZ() / distance) * 0.5;
		
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				if (i > distance) {
					cancel();
				}
				currentLoc.add(dx, dy, dz);
				SuitUtils.teleportWithPassengers(currentLoc, entity);
				i++;

			}
		}.runTaskTimer(plugin, 0, 30);
	}

	public static boolean isMarkEntity(LivingEntity lentity) {
		ItemStack [] armors = lentity.getEquipment().getArmorContents();
		if (armors==null) return false;
	
		for(ItemStack armor : armors){
			if (SuitUtils.checkName(armor, Values.SuitName + Values.SuitInforegex)){
				return true;
			}
		}
		return false;
	}

	private static Entity returnEntity(List<Entity> entities, Player player) {		
		Entity nearest = null;
		Location location = player.getLocation();
		double distanceSqrd = 1000*1000;
		for (Entity entity : entities) {
			if(entity instanceof LivingEntity){
				LivingEntity lentity = (LivingEntity) entity;
				if(SuitUtils.canWearArmor(lentity) && 
				   dao.isCreatedBy(lentity, player) && 
				   MathUtils.distanceSqrd(location, entity, distanceSqrd)) {
					distanceSqrd = entity.getLocation().distanceSquared(location);
					nearest = entity;
				}
			}
		}
		if (nearest == null) {
			player.sendMessage(Values.NoSuchEntity);
		}
		return nearest;
	}

	public SpawningDao getDao() {
		return CustomSuitPlugin.dao;
	}

	public static ItemStack getGun() {
		return gunitem;
	}

	public static void refreshInventory(Player spnSender) {
		reset(spnSender, equipment, inventory, InventoryNames.inventory_name);
		reset(spnSender, armorequipment, armorinventory, InventoryNames.armorinventory_name);

		reset(spnSender, helmetequipment, helmetinventory, InventoryNames.helmetinventory_name);
		reset(spnSender, chestequipment, chestinventory, InventoryNames.chestinventory_name);
		reset(spnSender, leggingsequipment, leggingsinventory, InventoryNames.leggingsinventory_name);
		reset(spnSender, bootsequipment, bootsinventory, InventoryNames.bootsinventory_name);
		resetCommand_GUI(spnSender, command_equipment, commandInventory);
	}

	private static void resetCommand_GUI(Player spnSender, HashMap<Player, Inventory> command_equipment, Inventory commandInventory) {

		Inventory NewcommandInventory = Bukkit.createInventory(null, commandInventory.getSize(),
				InventoryNames.CommandInventory_name + ":" + spnSender.getDisplayName());

		NewcommandInventory.setContents(commandInventory.getContents());
		ItemStack Head = Head(spnSender.getName());
		NewcommandInventory.setItem(14, Head);
		command_equipment.put(spnSender, NewcommandInventory);
	}

	private static void resetTypeMap() {
		Iterator<ItemStack> iterator = entity_Icon_Map.values().iterator();
		int slot = 0;
		while (iterator.hasNext()) {

			ItemStack item = iterator.next();
			type_inventory.setItem(slot, item);
			vehicle_inventory.setItem(slot, item);
			slot++;
		}
	}

	public static void SetDisplayName(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	public static void reset(Player player, HashMap<Player, Inventory> map, Inventory inventory, String name) {
		if (!map.containsKey(player)) {
			Inventory Newinventory = Bukkit.createInventory(player, inventory.getSize(), name);
			Newinventory.setContents(inventory.getContents());
			map.put(player, Newinventory);
		}
	}

	public static int getLevel(Player p) {
		if (p.getEquipment().getLeggings() != null) {

			String leggings = p.getEquipment().getLeggings().getItemMeta().getDisplayName();
			if (leggings != null) {
				if (leggings.contains(Values.SuitName + Values.SuitInforegex)) {

					String[] values = leggings.split(Values.SuitInforegex);
					return Integer.parseInt(values[1]);
				}

			}
		}
		if (p.getEquipment().getHelmet() != null) {
			String helmetname = p.getEquipment().getHelmet().getItemMeta().getDisplayName();
			if (helmetname != null) {

				if (helmetname.contains(Values.SuitName + Values.SuitInforegex)) {
					String[] values = helmetname.split(Values.SuitInforegex);
					return Integer.parseInt(values[1]);
				}
			}
		}
		return 0;
	}

	public static ItemStack[] Color_Items(ItemStack itemstack) {
		ItemStack[] items = new ItemStack[colorMap.size()];
		Iterator<String> colors = colorMap.keySet().iterator();
		int index = 0;
		while (colors.hasNext()) {
			String colorName = colors.next();
			Color color = colorMap.get(colorName);
			ItemStack item = itemstack.clone();
			leathercolor(item, color);
			SetDisplayName((colorName + "").toUpperCase(), item);
			items[index] = item;
			index++;
		}
		return items;
	}

	public static void resetColorIcon(ItemStack item, Player player) {
		if (armorequipment.get(player) == null) {
			refreshInventory(player);
		}
		Inventory inventory = armorequipment.get(player);
		Material type = item.getType();
		Color color = ((LeatherArmorMeta) item.getItemMeta()).getColor();
		if (type == Material.LEATHER_HELMET) {
			int slot = 25;
			ItemStack itemstack = inventory.getItem(slot);
			leathercolor(itemstack, color);
			inventory.setItem(slot, itemstack);
		} else if (type == Material.LEATHER_CHESTPLATE) {
			int slot = 34;
			ItemStack itemstack = inventory.getItem(slot);
			leathercolor(itemstack, color);
			inventory.setItem(slot, itemstack);
		} else if (type == Material.LEATHER_LEGGINGS) {
			int slot = 43;
			ItemStack itemstack = inventory.getItem(slot);
			leathercolor(itemstack, color);
			inventory.setItem(slot, itemstack);
		} else if (type == Material.LEATHER_BOOTS) {
			int slot = 52;
			ItemStack itemstack = inventory.getItem(slot);
			leathercolor(itemstack, color);
			inventory.setItem(slot, itemstack);
		}
	}
	
//	 public static Inventory createInventory(int size, String title){
//		 return Bukkit.createInventory(null, size, title);
//	 }

}
