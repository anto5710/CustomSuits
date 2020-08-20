package gmail.anto5710.mcp.customsuits.CustomSuits;


import static gmail.anto5710.mcp.customsuits.Setting.Values.CantFindEntityType;
import static gmail.anto5710.mcp.customsuits.Setting.Values.Chestplate_Man_Color;
import static gmail.anto5710.mcp.customsuits.Setting.Values.HammerArmor;
import static gmail.anto5710.mcp.customsuits.Setting.Values.HammerDamage;
import static gmail.anto5710.mcp.customsuits.Setting.Values.HammerPrompt;
import static gmail.anto5710.mcp.customsuits.Setting.Values.Leggings_Man_Color;
import static gmail.anto5710.mcp.customsuits.Setting.Values.MachineGunAmmoAmount;
import static gmail.anto5710.mcp.customsuits.Setting.Values.MachineGunDamage;
import static gmail.anto5710.mcp.customsuits.Setting.Values.ManSmoke_Time;
import static gmail.anto5710.mcp.customsuits.Setting.Values.NoSuchEntity;
import static gmail.anto5710.mcp.customsuits.Setting.Values.SnipeAmmoAmount;
import static gmail.anto5710.mcp.customsuits.Setting.Values.SniperDamage;
import static gmail.anto5710.mcp.customsuits.Setting.Values.SuitInforegex;
import static gmail.anto5710.mcp.customsuits.Setting.Values.SuitName;
import static gmail.anto5710.mcp.customsuits.Setting.Values.ThorChestplateColor;
import static gmail.anto5710.mcp.customsuits.Setting.Values.ThorLeggingsColor;
import static gmail.anto5710.mcp.customsuits.Setting.Values.gun_regex;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.addLore;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.compare;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.compareName;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.createWithName;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.decapitate;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.dye;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.name;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.setLore;
import static gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil.suffix;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.YELLOW;
import static org.bukkit.enchantments.Enchantment.DAMAGE_ALL;
import static org.bukkit.enchantments.Enchantment.DURABILITY;
import static org.bukkit.enchantments.Enchantment.FIRE_ASPECT;
import static org.bukkit.enchantments.Enchantment.KNOCKBACK;
import static org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS;
import static org.bukkit.enchantments.Enchantment.OXYGEN;
import static org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL;
import static org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS;
import static org.bukkit.enchantments.Enchantment.PROTECTION_FALL;
import static org.bukkit.enchantments.Enchantment.PROTECTION_FIRE;
import static org.bukkit.enchantments.Enchantment.THORNS;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.CancelAirClick;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.Inventories;
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.SuitIUI;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.AutoTarget;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomEntities;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitEffecter;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SuitManufactory;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.Target;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.settings.SuitIUISetting;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.MachineGun;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.repulsor.ArcCompressor;
import gmail.anto5710.mcp.customsuits.Setting.FuelReciper;
import gmail.anto5710.mcp.customsuits.Setting.Recipe;
import gmail.anto5710.mcp.customsuits.Thor.CreeperDicer;
import gmail.anto5710.mcp.customsuits.Thor.ForceLightning;
import gmail.anto5710.mcp.customsuits.Thor.Hammer;
import gmail.anto5710.mcp.customsuits.Thor.HammerWeapons;
import gmail.anto5710.mcp.customsuits.Utils.ColorUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageControl;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageUtil;
import gmail.anto5710.mcp.customsuits.Utils.items.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.items.EnchantBuilder;
import gmail.anto5710.mcp.customsuits.Utils.items.Glow;
import gmail.anto5710.mcp.customsuits.Utils.items.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;
import gmail.anto5710.mcp.customsuits.Utils.particles.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleModeller;
import gmail.anto5710.mcp.mgear.MainGear;
import gmail.anto5710.mcp.mgear.ibamboo.IronBamboo;

/**
 * Hello world!
 *
 */
public class CustomSuitPlugin extends JavaPlugin {
	public static Logger logger;
	private static JavaPlugin plugin;
	public Target targetting;
	public HungerScheduler hscheduler;
	public static SuitEffecter suitEffecter; 
	public static SpawningDao dao;
	public static IronBamboo bamboomer; 
	
	private static Map<Player, SuitIUISetting> settings = new HashMap<>();

	public static ItemStack Bomb = createWithName(Material.FIREWORK_STAR, YELLOW + "[Bomb]");
	public static ItemStack Smoke = createWithName(Material.CLAY_BALL, GRAY + "[Smoke]");

	public static HashMap<Player, Inventory> command_equipment = new HashMap<>();

	public static ItemStack suitremote = new ItemStack(Material.COMPARATOR);

	public static ItemStack gunitem = new ItemStack(Material.IRON_HORSE_ARMOR);

	public static ItemStack missileLauncher = new ItemStack(Material.GOLDEN_HORSE_ARMOR);

	public static ItemStack hammer = createWithName(Material.IRON_AXE, GOLD + "Mjöllnir");

	public static ItemStack Helemt_Thor = new ItemStack(Material.IRON_HELMET);
	public static ItemStack Chestplate_Thor = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Thor = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Thor = new ItemStack(Material.IRON_BOOTS);

	public static ItemStack Chestplate_Man = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Man = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Man = new ItemStack(Material.IRON_BOOTS);
	public static ItemStack Sword_Man = new ItemStack(Material.GOLDEN_SWORD);

	public static ItemStack mg_ultrasteel = createWithName(Material.IRON_INGOT, "Ultahard steel"),
							mg_trigger = createWithName(Material.IRON_SWORD, MainGear.trigger_name),
							mg_blade = createWithName(Material.IRON_SWORD, "Ultrahard blade"),
							mg_ironbamboo = createWithName(Material.BAMBOO, "Iron bamboo");
	
	
	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */	

	@Override
	public void onEnable() {
		plugin = this;
		logger = getLogger();
		suitEffecter = new SuitEffecter(this, 1);
		
		ColorUtil.initColorMap();
		Glow.register();
		new Metadative(this);
		new Enchant();
		new SuitUtils(this);
		new DamageUtil();
		new ParticleModeller(this);
		new CustomEffects(this);

		Inventories.init();

		suffix(hammer, Attribute.GENERIC_ATTACK_DAMAGE, HammerDamage, EquipmentSlot.HAND);
		suffix(hammer, Attribute.GENERIC_ATTACK_DAMAGE, HammerDamage, EquipmentSlot.OFF_HAND);
		
		suffix(hammer, Attribute.GENERIC_ARMOR_TOUGHNESS, GOLD + "Kudos for Asgard", HammerArmor, 
				Operation.ADD_SCALAR, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);

		suffix(hammer, Attribute.GENERIC_ARMOR, GOLD + "Kudos for Asgard", HammerArmor, 
				Operation.ADD_SCALAR, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);
		
		suffix(hammer, Attribute.GENERIC_MOVEMENT_SPEED, GOLD + "Odin's Bless", HammerPrompt,
				Operation.ADD_SCALAR, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);
	
		hammer.addUnsafeEnchantments(new EnchantBuilder().enchant(DAMAGE_ALL, 10)
				.enchant(DURABILITY, 10).enchant(FIRE_ASPECT, 10)
				.enchant(LOOT_BONUS_MOBS, 10).enchant(KNOCKBACK, 10).serialize());

		name(Helemt_Thor, GOLD + "Thor Helmet");
		name(Chestplate_Thor, GOLD + "Thor ChestPlate");
		name(Leggings_Thor, GOLD + "Thor Leggings");
		name(Boots_Thor, GOLD + "Thor Boots");

		Helemt_Thor.addUnsafeEnchantments(
				new EnchantBuilder().enchant(PROTECTION_FIRE, 15).enchant(DURABILITY, 50)
						.enchant(OXYGEN, 2).enchant(THORNS, 15).serialize());

		Chestplate_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(PROTECTION_ENVIRONMENTAL, 15)
				.enchant(THORNS, 15).enchant(DURABILITY, 50).serialize());

		Leggings_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(THORNS, 15)
				.enchant(DURABILITY, 50).enchant(PROTECTION_EXPLOSIONS, 15).serialize());

		Boots_Thor.addUnsafeEnchantments(new EnchantBuilder().enchant(THORNS, 15)
				.enchant(DURABILITY, 50).enchant(PROTECTION_FALL, 15).serialize());

		Chestplate_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(THORNS, 8)
				.enchant(PROTECTION_ENVIRONMENTAL, 8).enchant(DURABILITY, 30).serialize());
		Leggings_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(THORNS, 8)
				.enchant(PROTECTION_ENVIRONMENTAL, 8).enchant(DURABILITY, 30).serialize());
		Boots_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(THORNS, 8)
				.enchant(PROTECTION_FALL, 8).enchant(DURABILITY, 30).serialize());
		Sword_Man.addUnsafeEnchantments(new EnchantBuilder().enchant(DAMAGE_ALL, 10)
				.enchant(FIRE_ASPECT, 10).enchant(LOOT_BONUS_MOBS, 10)
				.enchant(KNOCKBACK, 10).enchant(DURABILITY, 20).serialize());

		name(Sword_Man, YELLOW + "Sword Of Killer");

		dye(Chestplate_Man, Chestplate_Man_Color);
		dye(Leggings_Man, Leggings_Man_Color);

		dye(Chestplate_Thor, ThorChestplateColor);
		dye(Leggings_Thor, ThorLeggingsColor);

		name(Leggings_Man, GRAY + "Leggings");
		name(Chestplate_Man, GRAY + "ChestPlate");
		name(Boots_Man, GRAY + "Boots");

		setLore(Smoke, GOLD + "Smoke for " + ManSmoke_Time + " Seconds");

		name(suitremote, RED + "[Suit Commander]");
		name(gunitem, YELLOW + "Knif-1220 " + "«" + MachineGunAmmoAmount + gun_regex + SnipeAmmoAmount + "»");

		
		addLore(gunitem,
				ColorUtil.colorf(
					"Machine Gun Ammo: <yellow>" + MachineGunAmmoAmount+"<//>,"+ 
					"Sniper Ammo: <yellow>" + SnipeAmmoAmount+"<//>,"+
					"Machine Gun Bullet Spread: <yellow>0.3<//> | <yellow>0.05 (Zoom)<//>,"+
					"Sniper Bullet Spread: <yellow>5.0 | <yellow>0 (Zoom)<//>,"+
					"Machine Gun Damage: <yellow>" + MachineGunDamage + "<//>,"+
					"Sniper Damage: <yellow>" + SniperDamage+"<//>,"+
					"Machine Gun Bullet Velocity: <yellow>50.0 Block/Second<//>,"+
					"Sniper Bullet Velocity: <yellow>119 Block/Second<//>", WHITE).split(",")
		);
		name(missileLauncher, DARK_RED + "[Launcher]");
		
		addLore(CustomSuitPlugin.mg_trigger, "Press 《Q》 to catapult the left anchor", 
								  			"Press 《E》 to catapult the right anchor");
		suffix(mg_trigger, Attribute.GENERIC_ATTACK_SPEED, 3);
				
		Enchant.englow(mg_ironbamboo);
		addLore(mg_ironbamboo, "A precious strain of bamboo pertrified with steel");
		
		this.targetting = new Target(this);
		this.targetting.awaken();
		this.hscheduler = new HungerScheduler(this, 1);
		
		PluginManager manager = getServer().getPluginManager();
		
		manager.registerEvents(new PlayerEffect(this), this);
		manager.registerEvents(new SuitWeapons(this), this);
		manager.registerEvents(SuitWeapons.tnter, this);
		manager.registerEvents(SuitWeapons.compressor, this);
		manager.registerEvents(new SuitIUI(this), this);
		manager.registerEvents(new HammerWeapons(this), this);
		manager.registerEvents(new CancelAirClick(this), this);
		manager.registerEvents(new CreeperDicer(this), this);
		manager.registerEvents(new Hammer(this), this);
		manager.registerEvents(new ForceLightning(this, 10), this);
		manager.registerEvents(new AutoTarget(this), this);
		manager.registerEvents(new MachineGun(this), this);
		manager.registerEvents(new DamageControl(this), this);
		
		manager.registerEvents(new MainGear(this,2), this);
		manager.registerEvents(MainGear.spindler, this);
		
		Recipe.addRecipes(getServer());
		manager.registerEvents(new FuelReciper(), this);
		
		mkdir(this);
		dao = new SpawningDao(this);
		dao.init();

		bamboomer = new IronBamboo(this, 10);
		bamboomer.init();
		manager.registerEvents(bamboomer, this);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(plugin);
		bamboomer.save();
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
						spnSender.sendMessage(BLUE + "[Input]: " + AQUA + key + BLUE 
														+ "    [Entity]: " + AQUA + official_name);
					}
				} else if (args[0].equals("color")) {
					for (String key : ColorUtil.colorMap.keySet()) {
						spnSender.sendMessage(BLUE + "[Input]: " + WHITE + key);
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
				String option = args[0].toLowerCase();
				
				ItemStack toGet = null; 
				if (option.endsWith("commander")) {
					toGet = suitremote;
					
				} else if (option.endsWith("gun")) {
					toGet = gunitem;
					
				} else if (option.endsWith("launcher")) {
					toGet = missileLauncher;
					
				} else if (option.endsWith("hammer")) {
					toGet = hammer;
					
				} else if (option.endsWith("smoke")) {
					toGet = Smoke;
					
				} else if (option.endsWith("bomb")) {
					toGet = Bomb;
					
				} else if(option.endsWith("star")){
					toGet = ArcCompressor.star;
				
				} else if(option.endsWith("trigger")){
					toGet = CustomSuitPlugin.mg_trigger;
					
				} else {
					SuitUtils.wrongCommand(spnSender, command);
				}
				if(toGet!=null){ 
					InventoryUtil.give(spnSender, toGet);
					spnSender.updateInventory();
				}
			}
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
				ItemStack head = decapitate(spnSender.getName());
				InventoryUtil.give(spnSender, head);
				spnSender.updateInventory();
			} else {
				if (args[0] != null) {
					ItemStack head = decapitate(args[0]);
					InventoryUtil.give(spnSender, head);
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
					SuitIUISetting hdle = handle(spnSender);
					if (armor.equals("HELMET")) {
						hdle.dyeHelmet(Color);
					} else if (armor.equals("CHESTPLATE")) {
						hdle.dyeChestplate(Color);
					} else if (armor.equals("LEGGINGS")) {
						hdle.dyeLeggings(Color);
					} else if (armor.equals("BOOTS")) {
						hdle.dyeBoots(Color);
					}
					spnSender.sendMessage(BLUE + "[Info]: " + AQUA + "Changed " + armor
							+ "'s Color to " + color.toUpperCase());
				}
			}
		}
		if (command.getName().equals("setvehicle")) {
			if (args.length >= 1) {
				String entityName = args[0].toLowerCase();
				
				if(!handle(spnSender).assessVehicleType(entityName)){
					SuitUtils.wrongCommand(spnSender, command);
					SuitUtils.tick(spnSender);
				}
			} else {
				SuitUtils.wrongCommand(spnSender, command);
				SuitUtils.tick(spnSender);
			}
		}

		if (command.getName().equals("cspn")) {
			if (compare(suitremote, InventoryUtil.getMainItem(spnSender))) {
				if (args.length < 2) {
					SuitUtils.wrongCommand(spnSender, command);
					SuitUtils.tick(spnSender);

				} else if (args.length >= 2) {				
					try {
						int creatureCnt = Integer.parseInt(args[1]);
						handle(spnSender).setCount(creatureCnt);
					} catch (NumberFormatException e) {
						SuitUtils.wrongCommand(spnSender, command);
						SuitUtils.tick(spnSender);
					}
					if(!handle(spnSender).asessSentityType(args[0])){
						SuitUtils.warn(spnSender, CantFindEntityType);
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

	public static File mkdir(JavaPlugin plugin) {
		File pluginDir = plugin.getDataFolder();
		plugin.getLogger().info("[Plugin Directory]: "+pluginDir.getAbsolutePath());
		pluginDir.mkdir();
		return pluginDir;
	}

	public static SuitIUISetting handle(Player p){
		if(!settings.containsKey(p)){
			settings.put(p, new SuitIUISetting(p));
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
				player.sendMessage(BLUE + "[Info]: " + AQUA + "Teleported Suit----");
				isPlayed = true;
			}
		}
		if (!isPlayed) {
			player.sendMessage(BLUE + "[Info]: " + AQUA + "No such entity");
			SuitUtils.tick(player);
		}
	}

	public static void summonNearestSuit(Player player) {
		if (InventoryUtil.inAnyHand(player, suitremote)){
			List<LivingEntity> near = player.getWorld().getLivingEntities();

			LivingEntity nearest = nearestArmedLentity(near, player, 1000);
			if (nearest != null) {
				PlayerEffect.playSpawningEffect(nearest, player);
			} else {
				player.sendMessage(NoSuchEntity);
			}
		} else {
			SuitUtils.warn(player, "Plese Hold Your " + suitremote.getItemMeta().getDisplayName());
		}
	}

	public static void spawnSuit(Player player, Location loc) {
		if (InventoryUtil.inAnyHand(player, suitremote)){
			SuitIUISetting sett = handle(player);
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
				SuitUtils.teleportWithPassengers(currentLoc.add(MathUtil.randomVector(3).setY(0)), lentity);
				i++;
			}
		}.runTaskTimer(plugin, 0, 30);
	}

	public static boolean isMarkEntity(@Nonnull LivingEntity lentity) {
		ItemStack [] armors = lentity.getEquipment().getArmorContents();
		return armors != null && Arrays.stream(armors).anyMatch(
				armor->compareName(armor, SuitName + SuitInforegex));
	}

	private static LivingEntity nearestArmedLentity(List<LivingEntity> near, Player player, double range) {		
		LivingEntity nearest = null;
		Location O = player.getLocation();
		double distanceSqrd = range * range;
		for (LivingEntity lentity : near) {
			if (SuitUtils.isArmable(lentity) && dao.isCreatedBy(lentity, player)
					&& MathUtil.distanceSqrdBody(O, lentity, distanceSqrd)) {
				distanceSqrd = lentity.getLocation().distanceSquared(O);
				nearest = lentity;
			}
		}
		return nearest;
	}
	
	public static int getSuitLevel(Player p) {
		for(ItemStack armor : p.getEquipment().getArmorContents()){
			if(compareName(armor, SuitName + SuitInforegex)){				
				String name = armor.getItemMeta().getDisplayName();
				String[] values = name.split(SuitInforegex);
				return Integer.parseInt(values[1]);
			}
		}
		return handle(p).level();
	}

	public static void refreshInventory(Player player) {
		handle(player).reinitIUI(false);
	}
}
