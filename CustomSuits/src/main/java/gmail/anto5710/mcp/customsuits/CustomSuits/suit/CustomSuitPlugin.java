package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;

import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.CancelAirClick;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.InventoryNames;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.SuitInventoryGUI;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.EnchantBuilder;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */
//	static Map<String, Class<? extends Entity>> entityMap = new HashMap<>();
	static Map<String, Color> colorMap = new HashMap<>();
	public static Map<Player, SuitSettings> settings = new HashMap<>();

	public static ItemStack Bomb = new ItemStack(Material.FIREWORK_STAR);
	public static ItemStack Smoke = new ItemStack(Material.CLAY_BALL);

	public static HashMap<Player, Inventory> command_equipment = new HashMap<>();

	public static ItemStack suitremote = new ItemStack(Material.COMPARATOR);

	public static ItemStack gunitem = new ItemStack(Material.IRON_HORSE_ARMOR);

	static ItemStack AmmoForSniper = new ItemStack(Material.GOLD_NUGGET);
	static ItemStack AmmoForMachineGun = new ItemStack(Material.FLINT);

	public static Inventory inventory = Bukkit.createInventory(null, 27, InventoryNames.inventory_name);
	public static Inventory armorinventory = Bukkit.createInventory(null, 54, InventoryNames.armorinventory_name);
	static Inventory chestinventory = Bukkit.createInventory(null, 27, InventoryNames.chestinventory_name);
	static Inventory bootsinventory = Bukkit.createInventory(null, 27, InventoryNames.bootsinventory_name);
	static Inventory helmetinventory = Bukkit.createInventory(null, 27, InventoryNames.helmetinventory_name);
	static Inventory leggingsinventory = Bukkit.createInventory(null, 27, InventoryNames.leggingsinventory_name);

	public static Inventory type_inventory = Bukkit.createInventory(null, 27, InventoryNames.type_inventory_name);
	public static Inventory vehicle_inventory = Bukkit.createInventory(null, 27, InventoryNames.vehicle_inventory_name);

	public static SpawningDao dao;

	public static Inventory HelmetColorInventory = Bukkit.createInventory(null, 45, InventoryNames.HelmetColorInventory_name);
	public static Inventory ChestplateColorInventory = Bukkit.createInventory(null, 45, InventoryNames.ChestPlateColorInventory_name);
	public static Inventory LeggingsColorInventory = Bukkit.createInventory(null, 45, InventoryNames.LeggingsColorInventory_name);
	public static Inventory BootsColorInventory = Bukkit.createInventory(null, 45, InventoryNames.BootsColorInventory_name);
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

//	public static HashMap<String, ItemStack> entity_Icon_Map = new HashMap<>(), vehicle_Icon_Map = new HashMap<>();

	@Override
	public void onEnable() {
		plugin = this;
		Glow.registerGlow();

		name(ChatColor.YELLOW + "[Bomb]", Bomb);

		name(ChatColor.GRAY + "[Smoke]", Smoke);
		ItemMeta meta = Smoke.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Smoke for " + Values.ManSmoke_Time + " Seconds");
		meta.setLore(lore);
		Smoke.setItemMeta(meta);

		Enchant.enchantBooks();

		ItemStack levelitem = new ItemStack(Material.EXPERIENCE_BOTTLE);
		name(ChatColor.GREEN + "[Level]", levelitem);

		ItemStack armorset = new ItemStack(Material.DIAMOND_CHESTPLATE);
		name(ChatColor.GOLD + "[Armor]", armorset);

		ItemStack command = new ItemStack(Material.COMPARATOR);
		name(ChatColor.RED + "[Command]", command);
		
		hammer.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.DAMAGE_ALL, 5)
				.enchant(Enchantment.DURABILITY, 10).enchant(Enchantment.FIRE_ASPECT, 8)
				.enchant(Enchantment.LOOT_BONUS_MOBS, 5).enchant(Enchantment.KNOCKBACK, 8).serialize());

		name(ChatColor.GOLD + "Thor Helmet", Helemt_Thor);
		name(ChatColor.GOLD + "Thor ChestPlate", Chestplate_Thor);
		name(ChatColor.GOLD + "Thor Leggings", Leggings_Thor);
		name(ChatColor.GOLD + "Thor Boots", Boots_Thor);

		Helemt_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.PROTECTION_FIRE, 15).enchant(Enchantment.DURABILITY, 50)
				.enchant(Enchantment.OXYGEN, 2).enchant(Enchantment.THORNS, 15).serialize());
		
		Chestplate_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 15)
				.enchant(Enchantment.THORNS, 15).enchant(Enchantment.DURABILITY, 50).serialize());
		
		Leggings_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 15)
				.enchant(Enchantment.DURABILITY, 50).enchant(Enchantment.PROTECTION_EXPLOSIONS, 15).serialize());

		Boots_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 15).enchant(Enchantment.DURABILITY, 50).enchant(Enchantment.PROTECTION_FALL, 15).serialize());
	

		Chestplate_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8).
				enchant(Enchantment.DURABILITY, 30).serialize());
		Leggings_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8).
				enchant(Enchantment.DURABILITY, 30).serialize());
		Boots_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8).enchant(Enchantment.PROTECTION_FALL, 8).
				enchant(Enchantment.DURABILITY, 30).serialize());

		Sword_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.DAMAGE_ALL, 10)
				.enchant(Enchantment.FIRE_ASPECT, 10).enchant(Enchantment.LOOT_BONUS_MOBS, 10)
				.enchant(Enchantment.KNOCKBACK, 10).enchant(Enchantment.DURABILITY, 20).serialize());

		name(ChatColor.YELLOW + "Sword Of Killer", Sword_Man);

		Color Chestplate_Man_Color = Color.fromRGB(217, 206, 206);
		Color Leggings_Man_Color = Color.fromRGB(31, 28, 28);

		dye(Chestplate_Man, Chestplate_Man_Color);
		dye(Leggings_Man, Leggings_Man_Color);

		dye(Chestplate_Thor, Color.fromRGB(35, 35, 35));
		dye(Leggings_Thor, Color.fromRGB(0, 0, 65));

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

		name(ChatColor.AQUA + "[Helmet]", helmet);
		name(ChatColor.AQUA + "[ChestPlate]", chestplate);
		name(ChatColor.AQUA + "[Leggings]", leggings);
		name(ChatColor.AQUA + "[Boots]", boots);

		armorinventory.setItem(19, new ItemStack(Material.GOLDEN_HELMET));
		armorinventory.setItem(28, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(29, new ItemStack(Material.IRON_HORSE_ARMOR));
		armorinventory.setItem(37, new ItemStack(Material.GOLDEN_LEGGINGS));
		armorinventory.setItem(46, new ItemStack(Material.LEATHER_BOOTS));

		name(ChatColor.YELLOW + "[Set Armor]", ArmorIcon);

		name(ChatColor.AQUA + "[Enchant]", enchantIcon);

		name(ChatColor.GOLD + "[Set Color]", ColorIcon);

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
		name(ChatColor.AQUA + "[Party Protocol]", skull);

		ItemStack helmetM = new ItemStack(Material.DIAMOND_HELMET);
		name(ChatColor.AQUA + "[Suit Summon]", helmetM);

		ItemStack Targetitem = new ItemStack(Material.SKELETON_SKULL);
		name(ChatColor.BLUE + "[Target]", Targetitem);

		ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET);
		name(ChatColor.DARK_RED + "[Fireworks]", firework);

		name(ChatColor.GOLD + "Mjöllnir", hammer);

		name(ChatColor.GRAY + "Leggings", Leggings_Man);
		name(ChatColor.GRAY + "ChestPlate", Chestplate_Man);
		name(ChatColor.GRAY + "Boots", Boots_Man);

		ItemStack ColorHelmet = new ItemStack(Material.LEATHER_HELMET);
		Enchant.enchantment(ColorHelmet, new Glow(), 1, true);
		name(ChatColor.GOLD + "[Helmet Color]", ColorHelmet);
		ItemStack ColorChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		Enchant.enchantment(ColorChestplate, new Glow(), 1, true);
		name(ChatColor.GOLD + "[Chestplate Color]", ColorChestplate);
		ItemStack ColorLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Enchant.enchantment(ColorLeggings, new Glow(), 1, true);
		name(ChatColor.GOLD + "[Leggings Color]", ColorLeggings);
		ItemStack ColorBoots = new ItemStack(Material.LEATHER_BOOTS);
		Enchant.enchantment(ColorBoots, new Glow(), 1, true);
		name(ChatColor.GOLD + "[Boots Color]", ColorBoots);

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

		name(ChatColor.RED + "[Suit Commander]", suitremote);

		name(ChatColor.YELLOW + "Knif-1220 " + "«" + WeaponListner.maxformachine + Values.regex
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
		name(ChatColor.DARK_RED + "[Launcher]", missileLauncher);


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
		
		//test
//		manager.registerEvents(new The_World(this), this);
		
		Recipe.addRecipe(getServer());

		
		dao = new SpawningDao(this);
		inventory.setItem(18, CustomEntities.WARRIOR.getIcon());
		inventory.setItem(22, CustomEntities.SPIDER.getIcon());
		dao.init();

		new PlayEffect(this);

		initTypeMap();

		HelmetColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_HELMET)));
		ChestplateColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_CHESTPLATE)));
		LeggingsColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_LEGGINGS)));
		BootsColorInventory.setContents(dyeSpectrum(new ItemStack(Material.LEATHER_BOOTS)));
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
					for (CustomEntities species : CustomEntities.values()) {
						String key = species.getName();
						String official_name = species.getClass().getSimpleName();
						spnSender.sendMessage(ChatColor.BLUE + "[Input]: " + ChatColor.AQUA + key + ChatColor.BLUE
								+ "    [Entity]: " + ChatColor.AQUA + official_name);
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
						hdle(spnSender).putTarget(player);
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
				ItemStack head = decapitate(spnSender.getName());
				spnSender.getInventory().addItem(head);
				spnSender.updateInventory();
			} else {

				if (args[0] != null) {
					ItemStack head = decapitate(args[0]);
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
					SuitSettings hdle = hdle(spnSender);
					if (armor.equals("HELMET")) {
						hdle.setHelmetColor(Color);
						
					} else if (armor.equals("CHESTPLATE")) {
						hdle.setChestColor(Color);

					} else if (armor.equals("LEGGINGS")) {
						hdle.setLeggingsColor(Color);

					} else if (armor.equals("BOOTS")) {
						hdle.setBootsColor(Color);

					}
					spnSender.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Changed " + armor
							+ "'s Color to " + color.toUpperCase());
				}
			}
		}
		if (command.getName().equals("setvehicle")) {
			if (args.length >= 1) {
				String entityName = args[0].toLowerCase();
				
				if(!hdle(spnSender).assessVehicleType(entityName)){
					SuitUtils.wrongCommand(spnSender, command);
					spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
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
					try {
						int creatureCnt = Integer.parseInt(args[1]);
						hdle(spnSender).setCount(creatureCnt);
					} catch (NumberFormatException e) {
						SuitUtils.wrongCommand(spnSender, command);
						spnSender.playSound(spnSender.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
					}
					if(!hdle(spnSender).asessSentityType(args[0])){
						SuitUtils.warn(spnSender, Values.CantFindEntityType);
					}else{
						spawnEntity(spnSender, spnSender.getLocation());
					}
				}
			} else {
				SuitUtils.warn(spnSender, "Please Hold Your " + suitremote.getItemMeta().getDisplayName());
			}
		}
		return true;
	}

	public static SuitSettings hdle(Player p){
		if(!settings.containsKey(p)){
			settings.put(p, new SuitSettings(p));
		}
		return settings.get(p);
	}
	
	public static boolean isCreatedBy(Entity entity, Player player) {
		return dao.isCreatedBy(entity, player);
	}

	public static ItemStack decapitate(String name) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setOwner(name);
		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static void spawnall(Player player) {
		boolean isPlayed = false;
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

	public static ItemStack createWithName(Material material, String name) {
		ItemStack item = new ItemStack(material, 1);
		name(name, item);
		return item;
	}

	public static void spawnEntity(Player player, Location loc) {
		SuitSettings sett = hdle(player);
		spawnEntity(sett.getSentity(), sett.getVehicle(), sett.getCount(), sett.getCurrentTarget(), player, loc);
	}
	
	public static void spawnEntity(CustomEntities entity, CustomEntities vehicle, int creatureCnt, LivingEntity target, Player spnSender, Location location) {
		boolean useVehicle = vehicle != null && vehicle != CustomEntities.NONE;
		/* spnSender: 명령어를 입력한 플레이어 */
		ItemStack inHand = SuitUtils.getHoldingItem(spnSender);
		if (SuitUtils.checkItem(suitremote, inHand)) {
			SuitSettings hdle = hdle(spnSender);
			hdle.reinitInv();

			for (int cnt = 0; cnt < creatureCnt; cnt++) {
				Material type = Values.Suit_Spawn_Material;
				int level = hdle.level();
			
				int amount = useVehicle ? level * 2 : level;
				ItemStack material = new ItemStack(type, amount);

				if (spnSender.getInventory().contains(type, amount)) {

					@SuppressWarnings("unchecked")
					Class<Entity> entityClass = (Class<Entity>) entity.getSpecies();

					/* spawning 위치를 잡아줍니다. */
					Entity spawnedEntity = spnSender.getWorld().spawn(location, entityClass);

					addElseData(spawnedEntity, spnSender, target);
					dao.saveEntity(spawnedEntity, spnSender);
					spnSender.getInventory().removeItem(material);
					spnSender.updateInventory();
					spnSender.playSound(location, Sound.BLOCK_ANVIL_USE, 1.5F, 1.5F);

					if (spawnedEntity instanceof LivingEntity) {
						entityEquipArmor((LivingEntity) spawnedEntity, level, spnSender);
					}
					if (useVehicle) {
						createVehicle(spnSender, target, spawnedEntity, vehicle, level);
					}
				} else {
					SuitUtils.lack(spnSender, "Material");
				}
			}
		}
	}

	private static void createVehicle(Player spnSender, LivingEntity target, Entity spawnedEntity, CustomEntities vehicle, int level) {
		Entity Vehicle = spawnedEntity.getWorld().spawn(spawnedEntity.getLocation(), vehicle.getSpecies());
		setVehicleData(spawnedEntity, Vehicle, spnSender, target, level);

		Vehicle.teleport(spawnedEntity.getLocation());
		Vehicle.addPassenger(spawnedEntity);
	}

	public static void addElseData(Entity spawnedEntity, Player spnSender, LivingEntity target) {
		if (spawnedEntity instanceof Mob) {
			((Mob) spawnedEntity).setTarget(target);
		}
		if (spawnedEntity instanceof Horse) {
			setHorseData((Horse) spawnedEntity, spnSender, true);
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
	}

	private static void setVehicleData(Entity spawnedEntity, Entity Vehicle, Player spnSender, LivingEntity target, int level) {
		dao.saveEntity(Vehicle, spnSender);
		addElseData(spawnedEntity, spnSender, target);
		if (Vehicle instanceof LivingEntity) {
			entityEquipArmor(((LivingEntity) Vehicle), level, spnSender);
		}
		if(Vehicle instanceof Mob){
			((Mob)Vehicle).setTarget(target);
		}
		if(Vehicle instanceof Horse){
			((Horse) Vehicle).setTamed(true);
		}
		Vehicle.addPassenger(spawnedEntity);
	}

	private static void entityEquipArmor(LivingEntity livingentity, int level, Player spnSender) {
		livingentity.setRemoveWhenFarAway(false);
		addPotionEffects(livingentity, new PotionEffect[] { 
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999990, 1),
				new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999990, 1 + (int)(level/32D)),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999990, 1 + (int)(level/16D)),
				new PotionEffect(PotionEffectType.SPEED, 999999990, 1 + (int)(level/32D)),
				new PotionEffect(PotionEffectType.WATER_BREATHING, 999999990, 1) });
		if (SuitUtils.canWearArmor(livingentity)) {
			setEquipment(hdle(spnSender).armorequipment, spnSender, livingentity);
		}
	}

	private static void setHorseData(Horse horse, Player spnSender, boolean isAdult) {
		if (isAdult) {
			horse.setAdult();
		}
		HorseInventory horseinventory = horse.getInventory();
		ItemStack item = hdle(spnSender).armorequipment.getItem(8);

		horse.getInventory().setContents(horseinventory.getContents());
		horse.setJumpStrength(1 + (double) getLevel(spnSender) / 64);

		horse.setOwner(spnSender);

		if (item != null && (item.getType() == Material.IRON_HORSE_ARMOR
				|| item.getType() == Material.GOLDEN_HORSE_ARMOR || item.getType() == Material.DIAMOND_HORSE_ARMOR)) {
			horseinventory.setItem(1, item);
		}
		horseinventory.addItem(new ItemStack(Material.SADDLE));
	}

	private static void setMaterialForEnderMan(Enderman enderman, Material Material) {
		BlockData data = Material.isBlock() ? Material.createBlockData() : null;
		enderman.setCarriedBlock(data);
	}

	@SuppressWarnings("deprecation")
	private static void setEquipment(Inventory inventoryitem, Player player, LivingEntity spawnedEntity) {
		boolean CustomColor = true;

		CustomColor = false;

		LivingEntity lentity = (LivingEntity) spawnedEntity;
		lentity.setHealth(lentity.getMaxHealth());

		lentity.setCustomName(player.getName() + "|" + Values.SuitName);
		ItemStack itemForCreature = createItemForCreature(lentity);
		lentity.getEquipment().setItemInMainHand(itemForCreature);

		SuitSettings hdle = hdle(player);
		int level = hdle.equipment.getItem(8).getAmount();

		lentity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999990, 10));
		Color HelmetColor = hdle.getHelmetColor();
		Color ChestplateColor = hdle.getChestColor();
		Color LeggingsColor = hdle.getLeggingsColor();
		Color BootsColor = hdle.getBootsColor();

		/* 신발 신기기 */
		ItemStack helmetitem = inventoryitem.getItem(19);

		if (helmetitem != null) {
			addData(helmetitem, hdle.helmetequipment, level, player, CustomColor, HelmetColor);

			lentity.getEquipment().setHelmet(new ItemStack(helmetitem));
			lentity.getEquipment().setHelmetDropChance(0F);
		}
		
		ItemStack chestplate = inventoryitem.getItem(28);
		if (chestplate != null) {
			addData(chestplate, hdle.chestequipment, level, player, CustomColor, ChestplateColor);

			lentity.getEquipment().setChestplate(new ItemStack(chestplate));
			lentity.getEquipment().setChestplateDropChance(0F);
		}
		
		ItemStack leggings = inventoryitem.getItem(37);
		if (leggings != null) {
			addData(leggings, hdle.leggingsequipment, level, player, CustomColor, LeggingsColor);

			lentity.getEquipment().setLeggings(new ItemStack(leggings));
			lentity.getEquipment().setLeggingsDropChance(0F);
		}

		ItemStack boots = inventoryitem.getItem(46);
		if (boots != null) {

			addData(boots, hdle.bootsequipment, level, player, CustomColor, BootsColor);
			lentity.getEquipment().setBoots(new ItemStack(boots));
			lentity.getEquipment().setBootsDropChance(0F);
		}
		
		ItemStack hand = inventoryitem.getItem(29);
		if (hand != null) {
			name(ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level, hand);
			Enchant.enchantment(hand, new Glow(), 1, true);
			lentity.getEquipment().setItemInMainHand(hand);
			lentity.getEquipment().setItemInMainHandDropChance(0);
		}
	}
	
	public static void dye(ItemStack item, Color color) {
		if(!dyeable(item)) return;
		
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
					dye(item, color);
				}
			}

			if (enchantInventory != null) {
				name(ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level, item);
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

	public static void name(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	private static void initTypeMap() {
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

	public static Inventory copyCommand_GUI(Player spnSender, Inventory commandInventory) {
		Inventory NewcommandInventory = Bukkit.createInventory(null, commandInventory.getSize(), InventoryNames.CommandInventory_name + ":" + spnSender.getDisplayName());
		NewcommandInventory.setContents(commandInventory.getContents());
		ItemStack Head = decapitate(spnSender.getName());
		NewcommandInventory.setItem(14, Head);
		return NewcommandInventory;
	}

	public static Inventory copy(Player player, Inventory inventory, String title){
		Inventory Newinventory = Bukkit.createInventory(player, inventory.getSize(), title);
		Newinventory.setContents(inventory.getContents());
		return Newinventory;
	}

	public static int getLevel(Player p) {
		for(ItemStack armor : p.getEquipment().getArmorContents()){
			if(SuitUtils.checkName(armor, Values.SuitName + Values.SuitInforegex)){				
				String name = armor.getItemMeta().getDisplayName();
				String[] values = name.split(Values.SuitInforegex);
				return Integer.parseInt(values[1]);
			}
		}
		return hdle(p).level();
	}

	public static boolean dyeable(ItemStack item){
		if(item==null) return false;
		
		Material type = item.getType();
		return type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS;
	}
	
	public static ItemStack[] dyeSpectrum(ItemStack itemstack) {
		ItemStack[] items = new ItemStack[colorMap.size()];
		Iterator<String> colors = colorMap.keySet().iterator();
		int index = 0;
		while (colors.hasNext()) {
			String colorName = colors.next();
			Color color = colorMap.get(colorName);
			ItemStack item = itemstack.clone();
			dye(item, color);
			name((colorName).toUpperCase(), item);
			items[index] = item;
			index++;
		}
		return items;
	}

	public static void refreshInventory(Player player) {
		hdle(player).reinitInv();
	}
}
