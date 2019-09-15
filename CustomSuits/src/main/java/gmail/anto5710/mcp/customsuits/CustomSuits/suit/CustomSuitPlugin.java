package gmail.anto5710.mcp.customsuits.CustomSuits.suit;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
import gmail.anto5710.mcp.customsuits.CustomSuits.InvetoryGUI.SuitInventoryGUI;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.MachineGun;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.SuitWeapons;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons.repulsor.ArcCompressor;
import gmail.anto5710.mcp.customsuits.Setting.Recipe;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Thor.CreeperDicer;
import gmail.anto5710.mcp.customsuits.Thor.ForceLightning;
import gmail.anto5710.mcp.customsuits.Thor.Hammer;
import gmail.anto5710.mcp.customsuits.Thor.HammerWeapons;
import gmail.anto5710.mcp.customsuits.Utils.ColorUtil;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.EnchantBuilder;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageControl;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageUtil;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

/**
 * Hello world!
 *
 */
public class CustomSuitPlugin extends JavaPlugin {
	public static Logger logger;
	private static JavaPlugin plugin;
	Target targetting;
	HungerScheduler hscheduler;
	public static SuitEffecter suitEffecter; 
	public static SpawningDao dao;
	
	private static Map<Player, SuitSettings> settings = new HashMap<>();

	public static ItemStack Bomb = ItemUtil.createWithName(Material.FIREWORK_STAR, ChatColor.YELLOW + "[Bomb]");
	public static ItemStack Smoke = ItemUtil.createWithName(Material.CLAY_BALL, ChatColor.GRAY + "[Smoke]");

	public static HashMap<Player, Inventory> command_equipment = new HashMap<>();

	public static ItemStack suitremote = new ItemStack(Material.COMPARATOR);

	public static ItemStack gunitem = new ItemStack(Material.IRON_HORSE_ARMOR);

	public static ItemStack missileLauncher = new ItemStack(Material.GOLDEN_HORSE_ARMOR);

	public static ItemStack hammer = ItemUtil.createWithName(Material.IRON_AXE, ChatColor.GOLD + "Mjöllnir");

	public static ItemStack Helemt_Thor = new ItemStack(Material.IRON_HELMET);
	public static ItemStack Chestplate_Thor = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Thor = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Thor = new ItemStack(Material.IRON_BOOTS);

	public static ItemStack Chestplate_Man = new ItemStack(Material.LEATHER_CHESTPLATE);
	public static ItemStack Leggings_Man = new ItemStack(Material.LEATHER_LEGGINGS);
	public static ItemStack Boots_Man = new ItemStack(Material.IRON_BOOTS);
	public static ItemStack Sword_Man = new ItemStack(Material.GOLDEN_SWORD);

	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */	

	@Override
	public void onEnable() {
		plugin = this;
		logger = getLogger();
		suitEffecter = new SuitEffecter(this, 1);
		
		Glow.registerGlow();
		new Metadative(this);
		new Enchant();
		new SuitUtils(this);
		new DamageUtil();

		Enchant.enchantBooks();
		ColorUtil.initColorMap();
		Inventories.init();

		ItemUtil.suffix(hammer, Attribute.GENERIC_ATTACK_DAMAGE, Values.HammerDamage, EquipmentSlot.HAND);
		ItemUtil.suffix(hammer, Attribute.GENERIC_ATTACK_DAMAGE, Values.HammerDamage, EquipmentSlot.OFF_HAND);
		
		ItemUtil.suffix(hammer, Attribute.GENERIC_ARMOR_TOUGHNESS, ChatColor.GOLD + "Kudos for Asgard", Values.HammerArmor, Operation.ADD_SCALAR,  EquipmentSlot.HAND);
		ItemUtil.suffix(hammer, Attribute.GENERIC_ARMOR_TOUGHNESS, ChatColor.GOLD + "Kudos for Asgard", Values.HammerArmor,  Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
		ItemUtil.suffix(hammer, Attribute.GENERIC_ARMOR, ChatColor.GOLD + "Kudos for Asgard", Values.HammerArmor, Operation.ADD_SCALAR,  EquipmentSlot.HAND);
		ItemUtil.suffix(hammer, Attribute.GENERIC_ARMOR, ChatColor.GOLD + "Kudos for Asgard", Values.HammerArmor,  Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
		
		ItemUtil.suffix(hammer, Attribute.GENERIC_MOVEMENT_SPEED, ChatColor.GOLD + "Odin's Bless",Values.HammerPrompt, Operation.ADD_SCALAR, EquipmentSlot.HAND);
		ItemUtil.suffix(hammer, Attribute.GENERIC_MOVEMENT_SPEED, ChatColor.GOLD + "Odin's Bless",Values.HammerPrompt, Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
	
		hammer.addUnsafeEnchantments(new EnchantBuilder().enchant(Enchantment.DAMAGE_ALL, 10)
				.enchant(Enchantment.DURABILITY, 10).enchant(Enchantment.FIRE_ASPECT, 10)
				.enchant(Enchantment.LOOT_BONUS_MOBS, 10).enchant(Enchantment.KNOCKBACK, 10).serialize());

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

		ItemUtil.dye(Chestplate_Man, Values.Chestplate_Man_Color);
		ItemUtil.dye(Leggings_Man, Values.Leggings_Man_Color);

		ItemUtil.dye(Chestplate_Thor, Values.ThorChestplateColor);
		ItemUtil.dye(Leggings_Thor, Values.ThorLeggingsColor);

		ItemUtil.name(Leggings_Man, ChatColor.GRAY + "Leggings");
		ItemUtil.name(Chestplate_Man, ChatColor.GRAY + "ChestPlate");
		ItemUtil.name(Boots_Man, ChatColor.GRAY + "Boots");

		ItemUtil.setLore(Smoke, ChatColor.GOLD + "Smoke for " + Values.ManSmoke_Time + " Seconds");

		ItemUtil.name(suitremote, ChatColor.RED + "[Suit Commander]");
		ItemUtil.name(gunitem, ChatColor.YELLOW + "Knif-1220 " + "«" + Values.MachineGunAmmoAmount + Values.gun_regex + Values.SnipeAmmoAmount + "»");

		ItemUtil.addLore(gunitem,
				ChatColor.WHITE + "Machine Gun Ammo: " + ChatColor.YELLOW + Values.MachineGunAmmoAmount,
				ChatColor.WHITE + "Sniper Ammo: " + ChatColor.YELLOW + Values.SnipeAmmoAmount,
				ChatColor.WHITE + "Machine Gun Bullet Spread: " + ChatColor.YELLOW + 0.3 + ChatColor.WHITE + " | " + ChatColor.YELLOW + 0.05 + "(Zoom)",
				ChatColor.WHITE + "Sniper Bullet Spread: " + ChatColor.YELLOW + 5.0 + ChatColor.WHITE + " | " + ChatColor.YELLOW + 0 + "(Zoom)",
				ChatColor.WHITE + "Machine Gun Damage: " + ChatColor.YELLOW + Values.MachineGunDamage,
				ChatColor.WHITE + "Sniper Damage: " + ChatColor.YELLOW + Values.SniperDamage,
				ChatColor.WHITE + "Machine Gun Bullet Velocity: " + ChatColor.YELLOW + 50.0 + " Block/Second",
				ChatColor.WHITE + "Sniper Bullet Velocity: " + ChatColor.YELLOW + 119 + " Block/Second");
		ItemUtil.name(missileLauncher, ChatColor.DARK_RED + "[Launcher]");
		
		this.targetting = new Target(this);
		this.targetting.awaken();
		this.hscheduler = new HungerScheduler(this, 1);
				
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new PlayerEffect(this), this);
		manager.registerEvents(new SuitWeapons(this), this);
		manager.registerEvents(SuitWeapons.tnter, this);
		manager.registerEvents(SuitWeapons.compressor, this);
		manager.registerEvents(new SuitInventoryGUI(this), this);
		manager.registerEvents(new HammerWeapons(this), this);
		manager.registerEvents(new CancelAirClick(this), this);
		manager.registerEvents(new CreeperDicer(this), this);
		manager.registerEvents(new Hammer(this), this);
		manager.registerEvents(new ForceLightning(this, 10), this);
		manager.registerEvents(new AutoTarget(this), this);
		manager.registerEvents(new MachineGun(this), this);
		manager.registerEvents(new DamageControl(this), this);
		
		Recipe.addRecipes(getServer());

		dao = new SpawningDao(this);
		dao.init();

		new CustomEffects(this);
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
				String option = args[0].toLowerCase();

				if (option.endsWith("commander")) {
					InventoryUtil.give(spnSender, suitremote);
					
				} else if (option.endsWith("gun")) {
					InventoryUtil.give(spnSender, gunitem);
					
				} else if (option.endsWith("launcher")) {
					InventoryUtil.give(spnSender, missileLauncher);
					
				} else if (option.endsWith("hammer")) {
					InventoryUtil.give(spnSender, hammer);
					
				} else if (option.endsWith("smoke")) {
					InventoryUtil.give(spnSender, Smoke);
					
				} else if (option.endsWith("bomb")) {
					InventoryUtil.give(spnSender, Bomb);
					
				} else if(option.endsWith("star")){
					InventoryUtil.give(spnSender, ArcCompressor.star);
					
				} 
				else {
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
				InventoryUtil.give(spnSender, head);
				spnSender.updateInventory();
			} else {
				if (args[0] != null) {
					ItemStack head = ItemUtil.decapitate(args[0]);
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
					SuitUtils.tick(spnSender);
				}
			} else {
				SuitUtils.wrongCommand(spnSender, command);
				SuitUtils.tick(spnSender);
			}
		}

		if (command.getName().equals("cspn")) {
			if (ItemUtil.checkItem(suitremote, InventoryUtil.getMainItem(spnSender))) {
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
			SuitUtils.tick(player);
		}
	}

	public static void summonNearestSuit(Player player) {
		if (InventoryUtil.inMainHand(player, CustomSuitPlugin.suitremote)){
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
		if (InventoryUtil.inMainHand(player, CustomSuitPlugin.suitremote)){
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
				SuitUtils.teleportWithPassengers(currentLoc.add(MathUtil.randomVector(3).setY(0)), lentity);
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
					&& MathUtil.distanceSqrdBody(O, lentity, distanceSqrd)) {
				distanceSqrd = lentity.getLocation().distanceSquared(O);
				nearest = lentity;
			}
		}
		return nearest;
	}

	public static Inventory copyCommandGUI(Player player, Inventory cmdInven) {
		Inventory newInven = copyInven(player, cmdInven, Inventories.commandinventory_name + ":" + player.getDisplayName());
		ItemStack Head = ItemUtil.decapitate(player.getName());
		ItemUtil.name(Head, ItemUtil.getName(cmdInven.getItem(14)));
		newInven.setItem(14, Head);
		return newInven;
	}

	public static Inventory copyInven(Player player, Inventory inven, String title){
		Inventory newInven = Bukkit.createInventory(player, inven.getSize(), title);
		newInven.setContents(inven.getContents());
		return newInven;
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
		handle(player).reinitUInven();
	}
}
