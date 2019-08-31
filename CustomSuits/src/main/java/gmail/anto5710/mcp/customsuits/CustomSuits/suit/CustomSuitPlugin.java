package gmail.anto5710.mcp.customsuits.CustomSuits.suit;


import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.CancelAirClick;

import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.InventoryNames;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.SuitInventoryGUI;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets.MachineGun;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.gadgets.SuitWeapons;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Recipe;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Thor.CreeperDicer;
import gmail.anto5710.mcp.customsuits.Thor.ForceLightning;
import gmail.anto5710.mcp.customsuits.Thor.Hammer;
import gmail.anto5710.mcp.customsuits.Thor.HammerWeapons;
import gmail.anto5710.mcp.customsuits.Utils.ColorUtil;
import gmail.anto5710.mcp.customsuits.Utils.EnchantBuilder;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;


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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
	static SuitEffecter suitEffecter; 
	public static SpawningDao dao;
	
	
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

	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */	
//	static Map<String, Class<? extends Entity>> entityMap = new HashMap<>();
//	public static HashMap<String, ItemStack> entity_Icon_Map = new HashMap<>(), vehicle_Icon_Map = new HashMap<>();

	@Override
	public void onEnable() {
		suitEffecter = new SuitEffecter(this, 1);
		plugin = this;
		Glow.registerGlow();

		ItemUtil.name(Bomb, ChatColor.YELLOW + "[Bomb]");

		ItemUtil.name(Smoke, ChatColor.GRAY + "[Smoke]");
		ItemMeta meta = Smoke.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Smoke for " + Values.ManSmoke_Time + " Seconds");
		meta.setLore(lore);
		Smoke.setItemMeta(meta);

		Enchant.enchantBooks();
		
		ColorUtil.initColorMap();

		ItemStack levelitem = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemUtil.name(levelitem, ChatColor.GREEN + "[Level]");

		ItemStack armorset = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemUtil.name(armorset, ChatColor.GOLD + "[Armor]");

		ItemStack command = new ItemStack(Material.COMPARATOR);
		ItemUtil.name(command, ChatColor.RED + "[Command]");
		
		hammer.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.DAMAGE_ALL, 5)
				.enchant(Enchantment.DURABILITY, 10).enchant(Enchantment.FIRE_ASPECT, 8)
				.enchant(Enchantment.LOOT_BONUS_MOBS, 5).enchant(Enchantment.KNOCKBACK, 8).serialize());

		ItemUtil.name(Helemt_Thor, ChatColor.GOLD + "Thor Helmet");
		ItemUtil.name(Chestplate_Thor, ChatColor.GOLD + "Thor ChestPlate");
		ItemUtil.name(Leggings_Thor, ChatColor.GOLD + "Thor Leggings");
		ItemUtil.name(Boots_Thor, ChatColor.GOLD + "Thor Boots");

		Helemt_Thor.addUnsafeEnchantments(
				new EnchantBuilder().enchant(Enchantment.PROTECTION_FIRE, 15).enchant(Enchantment.DURABILITY, 50)
						.enchant(Enchantment.OXYGEN, 2).enchant(Enchantment.THORNS, 15).serialize());

		Chestplate_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 15)
				.enchant(Enchantment.THORNS, 15).enchant(Enchantment.DURABILITY, 50).serialize());

		Leggings_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 15)
				.enchant(Enchantment.DURABILITY, 50).enchant(Enchantment.PROTECTION_EXPLOSIONS, 15).serialize());

		Boots_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 15)
				.enchant(Enchantment.DURABILITY, 50).enchant(Enchantment.PROTECTION_FALL, 15).serialize());

		Chestplate_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8)
				.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8).enchant(Enchantment.DURABILITY, 30).serialize());
		Leggings_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8)
				.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8).enchant(Enchantment.DURABILITY, 30).serialize());
		Boots_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.THORNS, 8)
				.enchant(Enchantment.PROTECTION_FALL, 8).enchant(Enchantment.DURABILITY, 30).serialize());

		Sword_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.DAMAGE_ALL, 10)
				.enchant(Enchantment.FIRE_ASPECT, 10).enchant(Enchantment.LOOT_BONUS_MOBS, 10)
				.enchant(Enchantment.KNOCKBACK, 10).enchant(Enchantment.DURABILITY, 20).serialize());

		ItemUtil.name(Sword_Man, ChatColor.YELLOW + "Sword Of Killer");

		Color Chestplate_Man_Color = Color.fromRGB(217, 206, 206);
		Color Leggings_Man_Color = Color.fromRGB(31, 28, 28);

		ItemUtil.dye(Chestplate_Man, Chestplate_Man_Color);
		ItemUtil.dye(Leggings_Man, Leggings_Man_Color);

		ItemUtil.dye(Chestplate_Thor, Color.fromRGB(35, 35, 35));
		ItemUtil.dye(Leggings_Thor, Color.fromRGB(0, 0, 65));

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

		ItemUtil.name(helmet, ChatColor.AQUA + "[Helmet]");
		ItemUtil.name(chestplate, ChatColor.AQUA + "[ChestPlate]");
		ItemUtil.name(leggings, ChatColor.AQUA + "[Leggings]");
		ItemUtil.name(boots, ChatColor.AQUA + "[Boots]");

		armorinventory.setItem(19, new ItemStack(Material.GOLDEN_HELMET));
		armorinventory.setItem(28, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(29, new ItemStack(Material.IRON_HORSE_ARMOR));
		armorinventory.setItem(37, new ItemStack(Material.GOLDEN_LEGGINGS));
		armorinventory.setItem(46, new ItemStack(Material.LEATHER_BOOTS));

		ItemUtil.name(ArmorIcon, ChatColor.YELLOW + "[Set Armor]");

		ItemUtil.name(enchantIcon, ChatColor.AQUA + "[Enchant]");

		ItemUtil.name(ColorIcon, ChatColor.GOLD + "[Set Color]");

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
		ItemUtil.name(skull, ChatColor.AQUA + "[Party Protocol]");

		ItemStack helmetM = new ItemStack(Material.DIAMOND_HELMET);
		ItemUtil.name(helmetM, ChatColor.AQUA + "[Suit Summon]");

		ItemStack Targetitem = new ItemStack(Material.SKELETON_SKULL);
		ItemUtil.name(Targetitem, ChatColor.BLUE + "[Target]");

		ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET);
		ItemUtil.name(firework, ChatColor.DARK_RED + "[Fireworks]");

		ItemUtil.name(hammer, ChatColor.GOLD + "Mjöllnir");

		ItemUtil.name(Leggings_Man, ChatColor.GRAY + "Leggings");
		ItemUtil.name(Chestplate_Man, ChatColor.GRAY + "ChestPlate");
		ItemUtil.name(Boots_Man, ChatColor.GRAY + "Boots");

		ItemStack ColorHelmet = new ItemStack(Material.LEATHER_HELMET);
		Enchant.enchantment(ColorHelmet, new Glow(), 1, true);
		ItemUtil.name(ColorHelmet, ChatColor.GOLD + "[Helmet Color]");
		
		ItemStack ColorChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		Enchant.enchantment(ColorChestplate, new Glow(), 1, true);
		ItemUtil.name(ColorChestplate, ChatColor.GOLD + "[Chestplate Color]");
		
		ItemStack ColorLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Enchant.enchantment(ColorLeggings, new Glow(), 1, true);
		ItemUtil.name(ColorLeggings, ChatColor.GOLD + "[Leggings Color]");
		
		ItemStack ColorBoots = new ItemStack(Material.LEATHER_BOOTS);
		Enchant.enchantment(ColorBoots, new Glow(), 1, true);
		ItemUtil.name(ColorBoots, ChatColor.GOLD + "[Boots Color]");

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

		ItemUtil.name(suitremote, ChatColor.RED + "[Suit Commander]");
		ItemUtil.name(gunitem, ChatColor.YELLOW + "Knif-1220 " + "«" + Values.MachineGunAmmoAmount + Values.gun_regex + Values.SnipeAmmoAmount + "»");

		List<String> GunLore = new ArrayList<>();
		GunLore.add(ChatColor.WHITE + "Machine Gun Ammo: " + ChatColor.YELLOW + Values.MachineGunAmmoAmount);
		GunLore.add(ChatColor.WHITE + "Sniper Ammo: " + ChatColor.YELLOW + Values.SnipeAmmoAmount);
		GunLore.add(ChatColor.WHITE + "Machine Gun Bullet Spread: " + ChatColor.YELLOW + 0.3 + ChatColor.WHITE + " | "+ ChatColor.YELLOW + 0.05 + "(Zoom)");
		GunLore.add(ChatColor.WHITE + "Sniper Bullet Spread: " + ChatColor.YELLOW + 5.0 + ChatColor.WHITE + " | " + ChatColor.YELLOW + 0 + "(Zoom)");
		GunLore.add(ChatColor.WHITE + "Machine Gun Damage: " + ChatColor.YELLOW + Values.MachineGunDamage);
		GunLore.add(ChatColor.WHITE + "Sniper Damage: " + ChatColor.YELLOW + Values.SniperDamage);
		GunLore.add(ChatColor.WHITE + "Machine Gun Bullet Velocity: " + ChatColor.YELLOW + 50.0 + " Block/Second");
		GunLore.add(ChatColor.WHITE + "Sniper Bullet Velocity: " + ChatColor.YELLOW + 119 + " Block/Second");

		ItemMeta gunMeta = gunitem.getItemMeta();
		gunMeta.setLore(GunLore);
		gunitem.setItemMeta(gunMeta);
		ItemUtil.name(missileLauncher, ChatColor.DARK_RED + "[Launcher]");

		PluginManager manager = getServer().getPluginManager();

		this.targetting = new Target(this);
		this.targetting.awaken();
		this.hscheduler = new HungerScheduler(this, 1);
				
		CustomSuitPlugin.logger.info("starting Hunger Thread");
		
		manager.registerEvents(new PlayerEffect(this), this);
		manager.registerEvents(new SuitWeapons(this), this);
		manager.registerEvents(SuitWeapons.tnter, this);
		manager.registerEvents(new SuitInventoryGUI(this), this);
		manager.registerEvents(new HammerWeapons(this), this);
		manager.registerEvents(new CancelAirClick(this), this);
		manager.registerEvents(new CreeperDicer(this), this);
		manager.registerEvents(new Hammer(this), this);
		manager.registerEvents(new ForceLightning(this, 10), this);
		manager.registerEvents(new AutoTarget(this), this);
		manager.registerEvents(new MachineGun(this), this);
		manager.registerEvents(MachineGun.effecter, this);
	

		Recipe.addRecipe(getServer());
		
		dao = new SpawningDao(this);
		inventory.setItem(18, CustomEntities.WARRIOR.getIcon());
		inventory.setItem(22, CustomEntities.SPIDER.getIcon());
		dao.init();

		new CustomEffects(this);

		initTypeMap();

		HelmetColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_HELMET)));
		ChestplateColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_CHESTPLATE)));
		LeggingsColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_LEGGINGS)));
		BootsColorInventory.setContents(ItemUtil.dyeSpectrum(new ItemStack(Material.LEATHER_BOOTS)));
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
					for (String key : ColorUtil.colorMap.keySet()) {
						spnSender.sendMessage(ChatColor.BLUE + "[Input]: " + ChatColor.WHITE + key);
					}
				} else {
					SuitUtils.wrongCommand(spnSender, command);
				}
			}
		}
		if (command.getName().equals("csuit")) {
			spnSender.sendMessage("summon...");
			summonNearestSuit(spnSender);
		}
		if (command.getName().equals("get")) {
			
			if (args.length == 0) {
				SuitUtils.wrongCommand(spnSender, command);
			} else {
				String option = args[0];

				if (option.endsWith("commander")) {

					spnSender.getInventory().addItem(suitremote);
				} else if (option.endsWith("gun")) {
					spnSender.getInventory().addItem(gunitem);
				} else if (option.endsWith("launcher")) {
					spnSender.getInventory().addItem(missileLauncher);
				} else if (option.endsWith("hammer")) {
					spnSender.getInventory().addItem(hammer);
					// }else if(option.endsWith("man")){
					// player.getInventory().addItem(Chestplate_Man);
					// player.getInventory().addItem(Leggings_Man);
					// player.getInventory().addItem(Boots_Man);
					// player.getInventory().addItem(Sword_Man);
				} else if (option.endsWith("smoke")) {
					spnSender.getInventory().addItem(Smoke);
				} else if (option.endsWith("bomb")) {
					spnSender.getInventory().addItem(Bomb);
				} else {
					SuitUtils.wrongCommand(spnSender, command);
				}

			}
			spnSender.updateInventory();
		}
		if (command.getName().equals("command")) {
			if (args.length == 0) {
				SuitUtils.wrongCommand(spnSender, command);
			} else {
				if (args[0].equals("party")) {
					summonAll(spnSender);
				} else {
					if (command.getName().equals("target") && args.length >= 1) {
						handle(spnSender).putTarget(Bukkit.getPlayer(args[0]));
					} else if (args[0].equals("firework")) {
						PlayerEffect.spawnFireworks(spnSender);
					} else {
						SuitUtils.wrongCommand(spnSender, command);
					}
				}
			}
		}
		if (command.getName().equals("head")) {
			if (args.length == 0) {
				ItemStack head = ItemUtil.decapitate(spnSender.getName());
				spnSender.getInventory().addItem(head);
				spnSender.updateInventory();
			} else {

				if (args[0] != null) {
					ItemStack head = ItemUtil.decapitate(args[0]);
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
				if (!ColorUtil.colorMap.containsKey(color)) {
					SuitUtils.warn(spnSender, "Can't Find That Color : " + (color + "").toUpperCase());
					isWrong = true;
				}
				color.toLowerCase();
				if (!isWrong) {
					Color Color = ColorUtil.colorMap.get(color);
					SuitSettings hdle = handle(spnSender);
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
				
				if(!handle(spnSender).assessVehicleType(entityName)){
					SuitUtils.wrongCommand(spnSender, command);
					SuitUtils.playerSound(spnSender, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
				}
			} else {
				SuitUtils.wrongCommand(spnSender, command);
				SuitUtils.playerSound(spnSender, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
			}
		}

		if (command.getName().equals("cspn")) {
			if (ItemUtil.checkItem(suitremote, SuitUtils.getHoldingItem(spnSender))) {
				if (args.length < 2) {
					SuitUtils.wrongCommand(spnSender, command);
					SuitUtils.playerSound(spnSender, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);

				} else if (args.length >= 2) {				
					try {
						int creatureCnt = Integer.parseInt(args[1]);
						handle(spnSender).setCount(creatureCnt);
					} catch (NumberFormatException e) {
						SuitUtils.wrongCommand(spnSender, command);
						SuitUtils.playerSound(spnSender, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
					}
					if(!handle(spnSender).asessSentityType(args[0])){
						SuitUtils.warn(spnSender, Values.CantFindEntityType);
					}else{
						spawnSuit(spnSender, spnSender.getLocation());
					}
				}
			} else {
				SuitUtils.warn(spnSender, "Please Hold Your " + suitremote.getItemMeta().getDisplayName());
			}
		}
		return true;
	}

	public static SuitSettings handle(Player p){
		if(!settings.containsKey(p)){
			settings.put(p, new SuitSettings(p));
		}
		return settings.get(p);
	}
	
	public static boolean isCreatedBy(Entity entity, Player player) {
		return dao.isCreatedBy(entity, player);
	}
	
	

	public static void summonAll(Player player) {
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
			SuitUtils.playerSound(player, Sound.BLOCK_DISPENSER_FAIL, 6.0F, 6.0F);
		}
	}

	public static void summonNearestSuit(Player player) {
		if (SuitUtils.isHolding(player, CustomSuitPlugin.suitremote)){
			List<LivingEntity> near = player.getWorld().getLivingEntities();

			LivingEntity nearest = nearestArmedLentity(near, player, 1000);
			if (nearest != null) {
				PlayerEffect.playSpawningEffect(nearest, player);
			} else {
				player.sendMessage(Values.NoSuchEntity);
			}
		} else {
			SuitUtils.warn(player, "Plese Hold Your " + suitremote.getItemMeta().getDisplayName());
		}
	}

	public static void spawnSuit(Player player, Location loc) {
		if (SuitUtils.isHolding(player, CustomSuitPlugin.suitremote)){
			SuitSettings sett = handle(player);
			SuitManufactory.manufacture(sett.getSentity(), sett.getVehicle(), sett.getCount(), sett.getCurrentTarget(), player, loc);
		} else {
			SuitUtils.warn(player, "Plese Hold Your " + suitremote.getItemMeta().getDisplayName());
		}
	}
	
	public static void runSummon(LivingEntity lentity, Player player) {
		Location entityLoc = lentity.getLocation(), playerLoc = player.getLocation();
		Vector diff = entityLoc.subtract(playerLoc).toVector();

		double distance = diff.length();
		if (distance < 0) {
			return;
		}
		Location currentLoc = playerLoc.clone();
		Vector dV = diff.normalize();
		new BukkitRunnable() {
			int i = 0;

			@Override
			public void run() {
				if (i > distance) {
					cancel();
				}
				currentLoc.add(dV);
				SuitUtils.teleportWithPassengers(currentLoc, lentity);
				i++;
			}
		}.runTaskTimer(plugin, 0, 30);
	}

	public static boolean isMarkEntity(LivingEntity lentity) {
		ItemStack [] armors = lentity.getEquipment().getArmorContents();
		return armors != null && Arrays.stream(armors).anyMatch(
				armor->ItemUtil.checkName(armor, Values.SuitName + Values.SuitInforegex));
	}

	private static LivingEntity nearestArmedLentity(List<LivingEntity> near, Player player, double range) {		
		LivingEntity nearest = null;
		Location O = player.getLocation();
		double distanceSqrd = range * range;
		for (LivingEntity lentity : near) {
			if (SuitUtils.isArmable(lentity) && dao.isCreatedBy(lentity, player)
					&& MathUtils.distanceSqrdBody(O, lentity, distanceSqrd)) {
				distanceSqrd = lentity.getLocation().distanceSquared(O);
				nearest = lentity;
			}
		}
		return nearest;
	}

	public SpawningDao getDao() {
		return CustomSuitPlugin.dao;
	}

	List<Player> getSuitedPlayers(Map<String, String> stringMap) {
		List<Player> list = new ArrayList<Player>();
		Iterator<String> iterator = stringMap.values().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Player player = Bukkit.getServer().getPlayer(name);
			if (list.isEmpty() && player != null) {
				list.add(player);
			} else if (player != null && !list.contains(player)) {
				list.add(player);
			}
		}
	
		return list;
	}

	

	public static ItemStack getGun() {
		return gunitem;
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

	public static Inventory copyCommandGUI(Player spnSender, Inventory commandInventory) {
		Inventory NewcommandInventory = Bukkit.createInventory(null, commandInventory.getSize(), InventoryNames.CommandInventory_name + ":" + spnSender.getDisplayName());
		NewcommandInventory.setContents(commandInventory.getContents());
		ItemStack Head = ItemUtil.decapitate(spnSender.getName());
		ItemUtil.name(Head, ItemUtil.getName(commandInventory.getItem(14)));
		NewcommandInventory.setItem(14, Head);
		return NewcommandInventory;
	}

	public static Inventory copyInv(Player player, Inventory inventory, String title){
		Inventory newInventory = Bukkit.createInventory(player, inventory.getSize(), title);
		newInventory.setContents(inventory.getContents());
		return newInventory;
	}

	public static int getSuitLevel(Player p) {
		for(ItemStack armor : p.getEquipment().getArmorContents()){
			if(ItemUtil.checkName(armor, Values.SuitName + Values.SuitInforegex)){				
				String name = armor.getItemMeta().getDisplayName();
				String[] values = name.split(Values.SuitInforegex);
				return Integer.parseInt(values[1]);
			}
		}
		return handle(p).level();
	}

	public static void refreshInventory(Player player) {
		handle(player).reinitUInv();
	}
}
