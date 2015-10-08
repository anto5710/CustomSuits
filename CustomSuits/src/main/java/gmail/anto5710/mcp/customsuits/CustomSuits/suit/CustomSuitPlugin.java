package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.Listner_Plugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Man.Man;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Recipe;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;
import gmail.anto5710.mcp.customsuits._Thor.HammerWeapons;

import java.awt.SystemColor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.AEADBadTagException;
import javax.print.attribute.standard.MediaSize.NA;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.junit.internal.matchers.IsCollectionContaining;
import org.w3c.dom.ls.LSInput;

import com.avaje.ebeaninternal.server.type.TypeManager;
import com.google.common.primitives.Ints;

/**
 * Hello world!
 *
 */
public class CustomSuitPlugin extends JavaPlugin implements Listener {
	
	public 	static Logger logger;
	static JavaPlugin plugin;
	

	SchedulerHunger hscheduler;
	static boolean isPlayed = false;
	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */
	static Map<String, Class<? extends Entity>> entityMap = new HashMap<>();
	
	static Map<String, Color> colorMap = new HashMap<>();

	public static ItemStack Bomb = new ItemStack(Material.FIREWORK_CHARGE);
	public static ItemStack Smoke = new ItemStack(Material.CLAY_BALL);
	
	
	public static ItemStack suitremote = new ItemStack(Material.DIODE);

	public static ItemStack gunitem = new ItemStack(Material.IRON_BARDING);

	static ItemStack AmmoForSniper = new ItemStack(Material.GOLD_NUGGET);
	static ItemStack AmmoForMachineGun = new ItemStack(Material.FLINT);

	static Inventory inventory = Bukkit.createInventory(null, 9, "[Settings]");
	static Inventory armorinventory = Bukkit
			.createInventory(null, 9, "[Armor]");
	static Inventory chestinventory = Bukkit.createInventory(null, 9,
			"[Chestplate]");
	static Inventory bootsinventory = Bukkit
			.createInventory(null, 9, "[Boots]");
	static Inventory helmetinventory = Bukkit.createInventory(null, 9,
			"[Helmet]");
	static Inventory leggingsinventory = Bukkit.createInventory(null, 9,
			"[Leggings]");
	static Inventory handinventory = Bukkit.createInventory(null, 9, "[Hand]");
	static HashMap<Player, String>Type_Map = new HashMap<>();
	static HashMap<Player, String>target = new HashMap<>();
	static HashMap<Player, String>color = new HashMap<>();
	static HashMap<Player, Integer>amount = new HashMap<>();

	
	
	private static SpawningDao dao;
	static HashMap<Player, Inventory> handequipment = new HashMap<>();
	static HashMap<Player, Inventory> chestequipment = new HashMap<>();
	static HashMap<Player, Inventory> helmetequipment = new HashMap<>();
	static HashMap<Player, Inventory> leggingsequipment = new HashMap<>();
	static HashMap<Player, Inventory> bootsequipment = new HashMap<>();

	static HashMap<Player, Inventory> equipment = new HashMap<>();
	static HashMap<Player, Inventory> armorequipment = new HashMap<>();
	
	

	static List<Player> onlinesplayer;

	static Inventory CommandInventory = Bukkit.createInventory(null, 9,
			"[Command]");
	public static ItemStack missileLauncher = new ItemStack(Material.GOLD_BARDING);
	
	public static ItemStack hammer =new ItemStack(Material.IRON_AXE, 1, (short) Values.HammerDamage);
	
	public static ItemStack Helemt_Thor = new ItemStack(Material.IRON_HELMET);
	
	public static ItemStack Chestplate_Thor = new ItemStack(Material.LEATHER_CHESTPLATE);
	
	public static ItemStack Leggings_Thor = new ItemStack(Material.LEATHER_LEGGINGS);
	
	public static ItemStack Boots_Thor = new ItemStack(Material.IRON_BOOTS);
	
	public static ItemStack Chestplate_Man = new ItemStack(Material.LEATHER_CHESTPLATE);
	
	public static ItemStack Leggings_Man = new ItemStack(Material.LEATHER_LEGGINGS);
	
	public static ItemStack Boots_Man = new ItemStack(Material.IRON_BOOTS);
	
	public static ItemStack Sword_Man = new ItemStack(Material.GOLD_SWORD, 1, (short) Values.ManDeafultDamage);
	
	

	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
		
		
		
		SetDisplayName(ChatColor.YELLOW + "[Bomb]", Bomb);
		
		SetDisplayName(ChatColor.GRAY + "[Smoke]", Smoke);
		ItemMeta meta = Smoke.getItemMeta();
		ArrayList<String>lore=  new ArrayList<>();
		lore.add(ChatColor.GOLD+"Smoke for "+Values.ManSmoke_Time+" Seconds");
		meta.setLore(lore);
		Smoke.setItemMeta(meta);
		
		Enchant.enchantBooks();
	

		ItemStack levelitem = new ItemStack(Material.EXP_BOTTLE);
		SetDisplayName(ChatColor.GREEN + "[Level]", levelitem);

		ItemStack armorset = new ItemStack(Material.DIAMOND_CHESTPLATE);
		SetDisplayName(ChatColor.GOLD + "[Armor]", armorset);

		ItemStack command = new ItemStack(Material.REDSTONE_COMPARATOR);
		SetDisplayName(ChatColor.RED + "[Command]", command);
		
	

		Enchant.enchantment(hammer, Enchantment.DAMAGE_ALL, 6, true);
		Enchant.enchantment(hammer, Enchantment.DURABILITY, 10, true);
		Enchant.enchantment(hammer, Enchantment.FIRE_ASPECT, 8, true);
		Enchant.enchantment(hammer, Enchantment.LOOT_BONUS_MOBS, 12, true);
		Enchant.enchantment(hammer, Enchantment.KNOCKBACK, 15, true);
		
		SetDisplayName(ChatColor.GOLD+"Thor_Helmet", Helemt_Thor);
		SetDisplayName(ChatColor.GOLD+"Thor_ChestPlate", Chestplate_Thor);
		SetDisplayName(ChatColor.GOLD+"Thor_Leggings",Leggings_Thor);
		SetDisplayName(ChatColor.GOLD+"Thor_Boots", Boots_Thor);
		
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
		SetDisplayName(ChatColor.YELLOW+"Sword Of Killer",Sword_Man);
		
		Color Chestplate_Man_Color = Color.fromRGB(217, 206, 206);
		Color Leggings_Man_Color = Color.fromRGB(31, 28, 28);
		
		
		leathercolor(Chestplate_Man, Chestplate_Man_Color);
		leathercolor(Leggings_Man, Leggings_Man_Color);
		
		leatherDyecolor(Chestplate_Thor, DyeColor.GRAY);
		leatherDyecolor(Leggings_Thor, DyeColor.BLACK);
		
		inventory.setItem(0, new ItemStack(command));

		inventory.setItem(1, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(2, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(3, new ItemStack(Material.IRON_FENCE));

		inventory.setItem(4, new ItemStack(armorset));

		inventory.setItem(5, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(6, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		inventory.setItem(8, new ItemStack(levelitem));

		armorinventory.setItem(0, new ItemStack(Material.GOLD_HELMET));

		armorinventory.setItem(1, new ItemStack(Material.IRON_FENCE));

		armorinventory.setItem(2, new ItemStack(Material.LEATHER_CHESTPLATE));

		armorinventory.setItem(3, new ItemStack(Material.IRON_FENCE));

		armorinventory.setItem(4, new ItemStack(Material.GOLD_LEGGINGS));

		armorinventory.setItem(5, new ItemStack(Material.IRON_FENCE));

		armorinventory.setItem(6, new ItemStack(Material.LEATHER_BOOTS));

		armorinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		armorinventory.setItem(8, new ItemStack(Material.NETHER_STAR));

		
		
		Wool wool = new Wool(Material.WOOL);
		wool.setColor(DyeColor.RED);
		ItemStack woolitem = wool.toItemStack();

		leggingsinventory.setItem(0, Enchant.Protection);
		leggingsinventory.setItem(1, Enchant.Unbreaking);
		leggingsinventory.setItem(2, Enchant.Thorns);
		leggingsinventory.setItem(8, woolitem);

		leggingsinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		chestinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		chestinventory.setItem(0, Enchant.Protection);
		chestinventory.setItem(1, Enchant.Unbreaking);
		chestinventory.setItem(2, Enchant.Thorns);
		chestinventory.setItem(8, woolitem);

		bootsinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		bootsinventory.setItem(0, Enchant.Fire_Protection);
		bootsinventory.setItem(1, Enchant.Unbreaking);
		bootsinventory.setItem(2, Enchant.Thorns);

		bootsinventory.setItem(4, Enchant.Feather_Falling);
		bootsinventory.setItem(8, woolitem);

		helmetinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		helmetinventory.setItem(0, Enchant.Respiration);
		helmetinventory.setItem(1, Enchant.Blast_Protection);
		helmetinventory.setItem(2, Enchant.Thorns);
		helmetinventory.setItem(3, Enchant.Aqua_Affinity);
		helmetinventory.setItem(4, Enchant.Unbreaking);

		helmetinventory.setItem(8, woolitem);

		handinventory.setItem(7, new ItemStack(Material.IRON_FENCE));

		handinventory.setItem(0, Enchant.Unbreaking);
		handinventory.setItem(1, Enchant.Sharpness);
		handinventory.setItem(2, Enchant.KnockBack);
		handinventory.setItem(3, Enchant.Fire_Aspect);
		handinventory.setItem(4, Enchant.Looting);

		ItemStack skull = new ItemStack(397, 1, (short) 0, (byte) 3);
		SetDisplayName(ChatColor.AQUA + "[Party Protocol]", skull);

		ItemStack helmetM = new ItemStack(Material.DIAMOND_HELMET);
		SetDisplayName(ChatColor.AQUA + "[Suit Summon]", helmetM);

		ItemStack Targetitem = new ItemStack(Material.SKULL_ITEM);
		SetDisplayName(ChatColor.BLUE + "[Target]", Targetitem);

		ItemStack firework = new ItemStack(Material.FIREWORK);
		SetDisplayName(ChatColor.DARK_RED + "[Fireworks]", firework);
		
		SetDisplayName(ChatColor.GOLD+"Mjöllnir",hammer );

		SetDisplayName(ChatColor.GRAY+"Leggings", Leggings_Man);
		SetDisplayName(ChatColor.GRAY
				+"ChestPlate", Chestplate_Man);
		SetDisplayName(ChatColor.GRAY+"Boots", Boots_Man);

		
		
		CommandInventory.setItem(0, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(1, helmetM);
		CommandInventory.setItem(2, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(3, Targetitem);
		CommandInventory.setItem(4, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(5, skull);
		CommandInventory.setItem(6, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(7, firework);
		CommandInventory.setItem(8, new ItemStack(Material.IRON_FENCE));

		// key 에 대응하는 Entity 종류를 맵에 담아둡니다.
		logger = getLogger();

		SetDisplayName(ChatColor.RED + "[Suit Commander]", suitremote);

		SetDisplayName(ChatColor.RED + "[Knif-1220] " + Values.regex
				+ WeaponListner.maxformachine + Values.regex
				+ WeaponListner.maxforsniper, gunitem);
		SetDisplayName(ChatColor.DARK_RED+"[Launcher]", missileLauncher);



		entityMap.put("warrior", PigZombie.class);
		entityMap.put("archer", Skeleton.class);
		entityMap.put("golem", IronGolem.class);
		entityMap.put("spider", CaveSpider.class);
		entityMap.put("bomb", Creeper.class);
		entityMap.put("buster", Wither.class);
		entityMap.put("dragon", EnderDragon.class);
		entityMap.put("giant", Giant.class);
		entityMap.put("enderman", Enderman.class);
		entityMap.put("ghast", Ghast.class);
		entityMap.put("snowman", Snowman.class);
		entityMap.put("blaze", Blaze.class);
		entityMap.put("wolf", Wolf.class);
		
		entityMap.put("witch", Witch.class);
		
		entityMap.put("zombie", Zombie.class);
		entityMap.put("squid", Squid.class);
		entityMap.put("silverfish", Silverfish.class);
		entityMap.put("horse", Horse.class);
		entityMap.put("undead_horse", Horse.class);
		entityMap.put("skeleton_horse", Horse.class);
		entityMap.put("donkey", Horse.class);
		entityMap.put("mule", Horse.class);
		entityMap.put("guardian_king", org.bukkit.entity.Guardian.class);
		entityMap.put("guardian",  org.bukkit.entity.Guardian.class);

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

		this.hscheduler = new SchedulerHunger(this);
		this.hscheduler.startThread();

		this.logger.info("starting Hunger Thread");

		manager.registerEvents(new PlayerEffect(this), this);
		manager.registerEvents(new WeaponListner(this), this);
		manager.registerEvents(new SuitInventoryGUI(this), this);
		manager.registerEvents(new Hammer(this), this);
		manager.registerEvents(new HammerWeapons(this), this);
		manager.registerEvents(new Man(this), this);
		manager.registerEvents(new Listner_Plugin(this), this);
		
		
		Recipe.addRecipe(getServer());

		dao = new SpawningDao(this);
		dao.init();
		
		
		PlayEffect playEffect = new PlayEffect(this);
		// listener 를 달아줍니다.

	}

	public SchedulerHunger getHungerScheduler() {
		return this.hscheduler;
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		Server server = getServer();
		Player spnSender = server.getPlayer(sender.getName());
		if(command.getName().equals("clist")){
			if(args.length==0){
				SuitUtils.Warn(spnSender, "Wrong Commands");
			}else{
				if(args[0].equals("entity")){
					for(String key : entityMap.keySet()){
					
						spnSender.sendMessage(ChatColor.BLUE+"[Input]: "+ChatColor.AQUA+key+  ChatColor.BLUE+"    [Entity]: "+ChatColor.AQUA+entityMap.get(key).getSimpleName());
					}
				}else if(args[0].equals("color")){
					for(String key : colorMap.keySet()){
						
						
						spnSender.sendMessage(ChatColor.BLUE+"[Input]: "+ChatColor.WHITE+key );
					}
				}
					else{
						SuitUtils.Warn(spnSender, "Wrong Commands");
					}
				
			}
		}
		if (command.getName().equals("csuit")) {

			Player mp = getServer().getPlayer(sender.getName());
			mp.sendMessage("");
			spawnsuit(mp);
		}
		if (command.getName().equals("get")) {
			Player player = getServer().getPlayer(sender.getName());
			if (args.length == 0) {
				SuitUtils.Warn(spnSender, "Wrong Commands");
			} else {
				String option = args[0];

				if (option.endsWith("commander")) {

					player.getInventory().addItem(suitremote);
				} else if (option.endsWith("gun")) {
					player.getInventory().addItem(gunitem);
				}
				 else if (option.endsWith("launcher")) {
						player.getInventory().addItem(missileLauncher);
					}
				 else if (option.endsWith("hammer")) {
						player.getInventory().addItem(hammer);
					}else if(option.endsWith("man")){
						player.getInventory().addItem(Chestplate_Man);
						player.getInventory().addItem(Leggings_Man);
						player.getInventory().addItem(Boots_Man);
						player.getInventory().addItem(Sword_Man);
					}else if(option.endsWith("smoke")){
						player.getInventory().addItem(Smoke);
					}else if(option.endsWith("bomb")){
						player.getInventory().addItem(Bomb);
					}
					else {
						SuitUtils.Warn(spnSender, "Wrong Commands");
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
						targetPlayer(player, sender.getServer().getPlayer(args[1]));

					} else if (args[0].equals("firework")) {
						spawnfireworks(player);
					} else {

						SuitUtils.Warn(spnSender, "Wrong Commands");
					}

				}
			}
		}
		if(command.getName().equals("head")){
			if(args.length==0){
				ItemStack skull = new ItemStack(397, 1, (short) 0, (byte) 3);
				skull.getData().setData((byte)3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(spnSender.getName());
				skull.setItemMeta(meta);
				spnSender.getInventory().addItem(skull);
			}
			else {
				
				if(args[0]!=null){
					ItemStack skull = new ItemStack(397, 1, (short) 0, (byte) 3);
				skull.getData().setData((byte)3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(args[0]);
				skull.setItemMeta(meta);
				spnSender.getInventory().addItem(skull);
				}else if(args[0]==null){
					SuitUtils.Warn(spnSender, "Can't Find That Players");
					
				}
			}
		}

		if (command.getName().equals("cspn")) {
			if (SuitUtils.CheckItem(suitremote, spnSender.getItemInHand())) {
				if(args.length<3){
					SuitUtils.Warn(spnSender, "Wrong Commands");
					spnSender.playSound(spnSender.getLocation(), Sound.NOTE_STICKS,
							6.0F, 6.0F);
					
				}else if(args.length>=3){

				String entityName = args[0];
				String targetPlayerName = args[1];
				int creatureCnt = 0;
				try {
					 creatureCnt = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					
				}
				String color ="";
				if(args.length>=4){
				 color = args[3].toLowerCase();
				}
				
				
				spawnentity(entityName, creatureCnt, spnSender,
						targetPlayerName ,color , spnSender.getLocation());
				Type_Map.put(spnSender, entityName);
				target.put(spnSender, targetPlayerName);
				CustomSuitPlugin.color.put(spnSender, color);
				amount.put(spnSender, creatureCnt);
				Inventory inventory = CustomSuitPlugin.inventory;
				
				spnSender.openInventory(inventory);

				
				}
			}
			else {
				SuitUtils.Warn(spnSender, "Please Hold Your "+suitremote.getItemMeta().getDisplayName());
			}
		}
		return super.onCommand(sender, command, label, args);
	}

	public static void targetPlayer(Player p, Player target) {
		
		isPlayed = false;
		List<Entity> list = p.getWorld().getEntities();

		for (Entity e : list) {
			if (dao.isCreatedBy(e, p)&&e instanceof Creature) {
				
				((Creature) e).setTarget(target);
				p.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
						+ "Target : " + ChatColor.DARK_AQUA
						+ target.getDisplayName());
				isPlayed = true;

			}

		}
		if (!isPlayed) {
			p.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
					+ "No such entity");
			p.playSound(p.getLocation(), Sound.NOTE_STICKS,
					6.0F, 6.0F);
		}
	}

	public static void spawnall(Player player) {
		isPlayed = false;
		List<Entity> list = player.getWorld().getEntities();
		for (Entity entity : list) {
			if (dao.isCreatedBy(entity, player)) {
							Entity passenger = entity.getPassenger();
							Entity vehicle = entity.getVehicle();
							if(entity!=null){
							entity.eject();
							}
							if(passenger!=null){
							passenger.eject();
							}
							if(vehicle!=null){
							vehicle.eject();
							}
							if(vehicle!=null){
								if(vehicle.getPassenger()!=null){
									vehicle.teleport(player.getLocation());
								}
								if(entity.getPassenger()!=null){
									passenger.teleport(player.getLocation());
									
								}
							}
				
				entity.teleport(player.getLocation());
				if(vehicle!=null){
						vehicle.setPassenger(entity);	
				}
				if(passenger!=null){
					entity.setPassenger(passenger);	
				}	
				player.sendMessage(ChatColor.BLUE + "[Info]: "+ChatColor.AQUA + "Teleported Suit----");
				isPlayed = true;
				
				
			}
		}
		if (!isPlayed) {
			player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
					+ "No such entity");
			player.playSound(player.getLocation(), Sound.NOTE_STICKS,
					6.0F, 6.0F);
		}
		

	}

	public static void spawnsuit(Player player) {
		if (SuitUtils.CheckItem(suitremote,player.getItemInHand() )) {
			runSpawn(player );
		} else {
			SuitUtils.Warn(player, "W");

		}
		

	}

	public static void runSpawn(Player player) {
		List<Entity> near = player.getWorld().getEntities();
		if (returnEntity(near, player) != null) {
			playSpawningEffect(returnEntity(near, player), player);
			sleep(100);
		}
		
		
	}



	public static void spawnentity(String entityName, int creatureCnt,
			Player spnSender, String targetPlayerName , String Color, Location location) {
		
		
		String VehicleName = "";
		String EntityName = entityName;
		int vehicleCount = 0;
		String[]Names = null;
		if(entityName.contains(":")){
			Names = entityName.split(":");
			entityName = Names[0].toLowerCase();
			if(Names.length ==2||Names.length ==3){
				VehicleName = Names[1].toLowerCase();
				if(!entityMap.containsKey(VehicleName)){
					SuitUtils.Warn(spnSender, Values.CantFindEntityType);
					return;
				}
				if(Names.length==3){
					try {
						 vehicleCount = Integer.parseInt(Names[2]);
					} catch (NumberFormatException e) {
					
					}
					 
				}
			}
			
		}
		if( !entityMap.containsKey(entityName)){
			SuitUtils.Warn(spnSender, Values.CantFindEntityType);
			return;
		}
		

		int height = 1;
		int width = 1;
		if (isprime(creatureCnt) != true) {
			for (int i = 3; i < creatureCnt; i++) {
				if (creatureCnt % i == 0) {
					height = i;
					break;
				}

			}

		} else {
			height = 1;
		}

		width = creatureCnt / height;

		Player targetPlayer = spnSender.getServer().getPlayer(targetPlayerName);

		/* spnSender: 명령어를 입력한 플레이어 */
		if (spnSender.getItemInHand() != null) {
			if (spnSender.getItemInHand().getType() == suitremote.getType()) {
				if (spnSender.getItemInHand().getItemMeta().getDisplayName() != null) {

					if (spnSender
							.getItemInHand()
							.getItemMeta()
							.getDisplayName()
							.endsWith(suitremote.getItemMeta().getDisplayName())) {
						
						resetInventory(spnSender);
						for (int ccnt = 0; height > ccnt; ccnt++) {
							for (int cnt = 0; width > cnt; cnt++) {
								Material type = Values.Suit_Spawn_Material;
								int level = 1;
								if(equipment.containsKey(spnSender)){
								 level = equipment.get(spnSender).getItem(8).getAmount();
								}
								int amount = vehicleCount +level;
								ItemStack material = new ItemStack(type, amount);

								if (spnSender.getInventory().contains(type, amount)) {

									Class<Entity> entityClass = loadEntityClass(entityName);
									Class<Entity> vehicleClass = loadEntityClass(VehicleName);
									

									logger.info("entity class: "
											+ entityClass);

									/* spawning 위치를 잡아줍니다. */
									Entity spawnedEntity = spnSender.getWorld()
											.spawn(new Location(
													spnSender.getWorld(),
													location.getX() - (width / 2)
															+ cnt, location.getY(),
													location.getZ() - (height / 2)
															+ ccnt),
													entityClass);
									int entityID = spawnedEntity.getEntityId();
									logger.info("[Entity ID]: " + entityID
											+ " by " + spnSender.getName()
											+ "(" + spnSender.getEntityId()
											+ ")");
									addElseData(spawnedEntity, EntityName, spnSender);
									if(spawnedEntity instanceof Creature){
										((Creature) spawnedEntity).setTarget(targetPlayer);
										}
						

									dao.saveEntity(spawnedEntity, spnSender);
									
									
									
										spnSender.getInventory().removeItem(material);
									
										spnSender.updateInventory();
										
									
								
									spnSender.playSound(location, Sound.ANVIL_USE,
											1.5F, 1.5F);
									if(spawnedEntity instanceof LivingEntity){
									EntityAddData((LivingEntity)spawnedEntity, spnSender, targetPlayer, EntityName ,Color);
									}
									if(VehicleName!=""){
										if(vehicleCount==1||Names.length==2){
											Entity Vehicle = spawnedEntity.getWorld().spawn(spawnedEntity.getLocation(), vehicleClass);
											setVehicleData(spawnedEntity, Vehicle, spnSender, targetPlayer, EntityName,Color);
										}else if(vehicleCount>1){
										CreateVehicles      ( spnSender ,targetPlayer, spawnedEntity , spnSender.getWorld().spawn(spawnedEntity.getLocation(), vehicleClass) ,VehicleName,vehicleCount,Color );
										}
									}
								} else {
									
									SuitUtils.Wrong(spnSender, "Material");
									
								}
							} 

						}
					}
				}
			} 
		}
	

	}
	
	private static void CreateVehicles(Player spnSender,Player targetPlayer, Entity spawnedEntity,
			Entity Vehicle, String EntityName, int vehicleCount ,String Color) {
		
		
		for(int c= 0; c<vehicleCount ; c++){
			setVehicleData(spawnedEntity, Vehicle, spnSender, targetPlayer, EntityName,  Color);

			spawnedEntity =Vehicle;
			Location loc = spawnedEntity.getLocation();
			if(c+1<vehicleCount){
			Vehicle = spawnedEntity.getWorld().spawn(loc,spawnedEntity.getClass() );
			}
		
	
		}
		
	}
	public static void addElseData(Entity spawnedEntity , String entityName , Player spnSender){
		if(spawnedEntity instanceof Horse){
			setHorseData((Horse)spawnedEntity, entityName, spnSender , true );
		}
		
		 if (spawnedEntity instanceof Enderman) {
			 setMaterialForEnderMan((Enderman) spawnedEntity,Material.TNT);
		}
		 
		if (spawnedEntity instanceof Wolf) {
			
			((Wolf)spawnedEntity).setAngry(true);
		}
		if (spawnedEntity instanceof IronGolem) {
			
			((IronGolem)spawnedEntity).setPlayerCreated(false);
			((IronGolem)spawnedEntity).playEffect(EntityEffect.IRON_GOLEM_ROSE);
		}
		if (spawnedEntity instanceof Creeper) {
			((Creeper) spawnedEntity).setPowered(true);;
			
		}
		if (spawnedEntity instanceof Guardian) {
			addGuardian_Data((Guardian) spawnedEntity, entityName, spnSender);
			
		}
	}
	public static void addGuardian_Data(Guardian spawnedEntity , String entityName , Player spnSender){
		if(entityName.endsWith("guardian_king")){
		spawnedEntity.setElder(true);
		}
	}
	private static void setVehicleData(Entity spawnedEntity,Entity Vehicle, Player spnSender , Player targetPlayer , String EntityName ,String Color) {
		dao.saveEntity(Vehicle, spnSender);
		addElseData(spawnedEntity, EntityName, spnSender);
		if(Vehicle instanceof LivingEntity){
			EntityAddData((LivingEntity)Vehicle, spnSender, targetPlayer, EntityName ,Color);
			
		}
		
		
		if(Vehicle instanceof Creature){
		((Creature) spawnedEntity).setTarget(targetPlayer);
		}
		Vehicle.setPassenger(spawnedEntity);
		
		
	}

	private static void EntityAddData(LivingEntity livingentity , Player spnSender ,Player targetPlayer, String entityName , String color) {
	int level = equipment.get(spnSender).getItem(8).getAmount();
		livingentity.setRemoveWhenFarAway(false);
		
		addPotionEffects(
				livingentity,
				new PotionEffect[] {
						new PotionEffect(
								PotionEffectType.FIRE_RESISTANCE,
								999999990, 1),
						
						
						new PotionEffect(
								PotionEffectType.HEALTH_BOOST,
								999999990, 1+((int)level/32)),
						new PotionEffect(
								PotionEffectType.INCREASE_DAMAGE,
								999999990, 1+ ((int)level/16) ),
						new PotionEffect(
								PotionEffectType.SPEED,
								999999990, 1+((int)level/32)),
						new PotionEffect(
								PotionEffectType.WATER_BREATHING,
								999999990, 1) });
		if (livingentity.getType() == EntityType.SKELETON
				|| livingentity.getType() == EntityType.ZOMBIE
				|| livingentity.getType() == EntityType.PIG_ZOMBIE) {
			setEquipment(
					armorequipment.get(spnSender),
					spnSender, livingentity,
					entityName , color);
		} 

		
	}

	private static void setHorseData(Horse horse, String entityName , Player spnSender, boolean isAdult ) {
		
		 if(isAdult){	
		 horse.setAdult();
		 }
		 HorseInventory horseinventory = horse.getInventory();
		 ItemStack item = armorequipment.get(spnSender).getItem(8);
		
		 
		
		 horse.getInventory().setContents(horseinventory.getContents());
		 horse.setJumpStrength(1+(double)getLevel(spnSender)/64);
		 
		 horse.setOwner(spnSender);
		 Variant varient = Variant.HORSE;
		 if(entityName.equals("skeleton_horse")){
			 varient = Variant.SKELETON_HORSE;
		 }else if(entityName.equals("undead_horse")){
			 varient = Variant.UNDEAD_HORSE;
		 }else if (entityName.equals("mule")){
			 varient = Variant.MULE;
		 }else if (entityName.equals("donkey")){
			 varient = Variant.DONKEY;
		 }
		 
		 horse.setVariant(varient);
		 if(item!=null){
		 if(item.getType()==Material.IRON_BARDING||item.getType()==Material.GOLD_BARDING||item.getType()==Material.DIAMOND_BARDING){
			 if(varient == Variant.HORSE){
				 horseinventory.setItem(1, item);
			 }
		 }
		 }
		 horseinventory.addItem(new ItemStack(Material.SADDLE));
		
	}

	private static void setMaterialForEnderMan(Enderman enderman, Material Material) {
		
		
				MaterialData data = new MaterialData(
						Material);

				enderman.setCarriedMaterial(data);
			
		
	}

	private static void setEquipment( Inventory inventoryitem,
			Player player, LivingEntity spawnedEntity, String entityName , String color) {
		boolean CustomColor = true;
		if(color!=null){
			CustomColor = false;
		}
		

		LivingEntity livingEntity = (LivingEntity) spawnedEntity;
	
		

		livingEntity.setHealth(livingEntity.getMaxHealth());

		livingEntity.setCustomName(player.getName() + "|" + Values.SuitName);
		ItemStack itemForCreature = createItemForCreature(livingEntity);
		livingEntity.getEquipment().setItemInHand(itemForCreature);
		Color HandColor  = getColor(handequipment , player);
		Color HelmetColor  = getColor(helmetequipment , player);
		Color ChestplateColor = getColor(chestequipment, player);
		Color LeggingsColor = getColor(leggingsequipment, player);
		Color BootsColor = getColor(bootsequipment, player);
		if(!CustomColor){
		Color icolor = colorMap.get(color);
		if(icolor ==null){
			icolor = Color.RED;
		}
		if(colorMap.containsKey(color)){
		  icolor = colorMap.get(color);
		}else{
			if(color.contains(":")){
				String[]Colors =  color.split(":");
				if(Colors.length>=1){
					if(Colors[0]!=null){
						if(colorMap.containsKey(Colors[0])){
							HelmetColor = colorMap.get(Colors[0]);
						}
					}
				}
				if(Colors.length>=2){
					if(Colors[1]!=null){
						if(colorMap.containsKey(Colors[1])){
							ChestplateColor = colorMap.get(Colors[1]);
						}
					}
				}
				if(Colors.length>=3){
					if(Colors[2]!=null){
						if(colorMap.containsKey(Colors[2])){
							LeggingsColor = colorMap.get(Colors[2]);
						}
					}
				}
				if(Colors.length>=4){
					if(Colors[3]!=null){
						if(colorMap.containsKey(Colors[3])){
							BootsColor = colorMap.get(Colors[3]);
						}
					}
				}
				if(Colors.length>=5){
					if(Colors[4]!=null){
						if(colorMap.containsKey(Colors[4])){
							HandColor = colorMap.get(Colors[4]);
						}
					}
				}
			}
		}
		 
		}
		
			int level = equipment.get(player).getItem(8).getAmount();

			

			livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
					999999990, 10));
			
			/* 신발 신기기 */
			ItemStack helmetitem = inventoryitem.getItem(0);

			if (helmetitem != null) {
				addData(helmetitem, helmetequipment, level, player, CustomColor, HelmetColor);

				livingEntity.getEquipment().setHelmet(new ItemStack(helmetitem));
				livingEntity.getEquipment().setHelmetDropChance(0F);

			}
			ItemStack chestplate = inventoryitem.getItem(2);
			if (chestplate != null) {
				addData(chestplate, chestequipment, level, player, CustomColor, ChestplateColor);

				livingEntity.getEquipment().setChestplate(new ItemStack(chestplate));
				livingEntity.getEquipment().setChestplateDropChance(0F);

			}
			ItemStack leggings = inventoryitem.getItem(4);
			if (leggings != null) {
				addData(leggings, leggingsequipment, level, player, CustomColor , LeggingsColor);

				livingEntity.getEquipment().setLeggings(new ItemStack(leggings));
				livingEntity.getEquipment().setLeggingsDropChance(0F);
			}

			ItemStack boots = inventoryitem.getItem(6);
			if (boots != null) {

				addData(boots, bootsequipment, level, player, CustomColor, BootsColor);
				livingEntity.getEquipment().setBoots(new ItemStack(boots));
			}

		

			ItemStack handitem = inventoryitem.getItem(8);
			addData(handitem, handequipment, level, player, CustomColor, HandColor);

			livingEntity.getEquipment().setItemInHand(handitem);
			livingEntity.getEquipment().setItemInHandDropChance(100.0F);

		

		
	}

	private static Color getColor(HashMap<Player, Inventory> equipment , Player player) {
		if(equipment.containsKey(player)){
			if(equipment.get(player).getItem(8)!= null){
			if(equipment.get(player).getItem(8).getType()== Material.WOOL){
				ItemStack woolitem = equipment.get(player).getItem(8);
				Wool wool = new Wool(woolitem.getType(), woolitem.getData().getData());
				Color color = wool.getColor().getColor();
				return color;
			}
		}
		}
		return Color.RED;
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

	public static void addData(ItemStack item, HashMap<Player, Inventory> map,
			int level, Player player , boolean GetColorFromInventory , Color icolor) {
		if (item != null) {
			
			if (item.getType() == Material.LEATHER_HELMET
					|| item.getType() == Material.LEATHER_CHESTPLATE
					|| item.getType() == Material.LEATHER_LEGGINGS
					|| item.getType() == Material.LEATHER_BOOTS) {
				if (map.get(player).getItem(8) != null&&GetColorFromInventory) {
					if (map.get(player).getItem(8).getType() == Material.WOOL) {
						ItemStack wool = map.get(player).getItem(8);
						Wool w = new Wool(wool.getType(), wool.getData()
								.getData());
						
						DyeColor dyecolor = w.getColor();
						leatherDyecolor(item, dyecolor);
					}
				}
				else if(icolor!=null&&!GetColorFromInventory){
					
					
					leathercolor(item, icolor);
				}
			}
			if (map.get(player) != null) {

				SetDisplayName(ChatColor.AQUA +Values.SuitName +Values.SuitInforegex + level, item);
				ItemMeta meta = item.getItemMeta();

				int size = map.get(player).getSize();

				if (size != 0) {
					for (int i = 0; i <= size - 1; i++) {
						if (map.get(player).getItem(i) != null) {
							if (map.get(player).getItem(i).getType() == Material.ENCHANTED_BOOK) {
								
								EnchantmentStorageMeta enchantmeta = (EnchantmentStorageMeta) map
										.get(player).getItem(i).getItemMeta();
								
								Map<Enchantment, Integer> Enchantments = enchantmeta.getStoredEnchants();
								
								for (Enchantment key : Enchantments.keySet()) {

									meta.addEnchant(key, enchantmeta
											.getStoredEnchants().get(key), true);

								}
								item.setItemMeta(meta);
								
							}
						}
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

	
	private static boolean isprime(int spcnt) {
		boolean returna = true;
		if (spcnt == 1||spcnt ==2) {

			returna = true;
		}
		if(spcnt%2 ==0){
			return false;
		}
		for (int cnt = 3; cnt < spcnt; cnt+=2) {
			if (spcnt % cnt == 0) {
				returna = false;
			}
		}
		

		return returna;
	}

	private static <T extends Entity> Class<T> loadEntityClass(String entityType) {
		
		Class<T> cls = (Class<T>) entityMap.get(entityType.toLowerCase());

	

		return cls;

	}

	

	public static void addPotionEffects(LivingEntity ett, PotionEffect... effects) {
		PotionEffect[] arrayOfPotionEffect;
		int j = (arrayOfPotionEffect = effects).length;
		for (int i = 0; i < j; i++) {
			PotionEffect eft = arrayOfPotionEffect[i];
			ett.addPotionEffect(eft);
		}
	}
	
	private static void playSpawningEffect(Entity entity, Player player) {
		try {
			
		
		
		Location playerlocation = player.getLocation();

		LivingEntity livingentity = (LivingEntity) entity;
		

		if (entity.getType() != EntityType.PLAYER) {
			if (PlayerEffect.hasArmor(livingentity.getEquipment().getArmorContents())&&Values.Allowed_Suit_Summon_types.contains(livingentity.getType())) {
				if (MarkEntity(livingentity)
						&& dao.isCreatedBy(livingentity, player)) {

					Location entitylocation = livingentity.getLocation();
					Vector vectorStart = entitylocation.toVector();
					
					Vector vectorEnd = playerlocation.toVector();
					
					Vector difference = vectorStart.subtract(vectorEnd);
					Entity vehicle = null ;
					if(entity.getVehicle()!=null){
						vehicle=entity.getVehicle();
					}
					
					double distance = difference.length();
					if (distance < 0) {
						return;
					}

					Location currentLoc = playerlocation.clone();
					double dx = (difference.getX() / distance) * 0.5;
					double dy = (difference.getY() / distance) * 0.5;
					double dz = (difference.getZ() / distance) * 0.5;
					for (int i = 0; i <=distance; i++) {
						currentLoc.add(dx , dy , dz);
						
						entity.teleport(currentLoc);
						if(entity.getVehicle()!=null){
						vehicle.teleport(currentLoc);
						vehicle.setPassenger(entity);
						}
						PlayEffect.play_Suit_Spawning_Effect(currentLoc, 10, 0, player ,livingentity.getEquipment().getArmorContents());
						
						

					}

					ItemStack helmet = livingentity.getEquipment().getHelmet();
					ItemStack chestplate = livingentity.getEquipment().getChestplate();

					if (MarkEntity(livingentity)
							&& dao.isCreatedBy(livingentity, player)) {
						Location location = player.getLocation();
						if (livingentity.getEquipment().getHelmet() != null) {

							player.getEquipment().setHelmet(helmet);
							player.playSound(player.getLocation(),
									Sound.ANVIL_LAND, 9.0F, 9.0F);
						
							
							sleep(500);
						}
						if (livingentity.getEquipment().getChestplate() != null) {

							player.getEquipment().setChestplate(chestplate);
							player.playSound(player.getLocation(),
									Sound.ANVIL_LAND, 9.0F, 9.0F);
							
							
							sleep(200);
						}
						if (livingentity.getEquipment().getLeggings() != null) {

							ItemStack cl = livingentity.getEquipment()
									.getLeggings();
							player.getEquipment().setLeggings(cl);
							player.playSound(player.getLocation(),
									Sound.ANVIL_LAND, 9.0F, 9.0F);
							
							sleep(400);
						}
						if (livingentity.getEquipment().getBoots() != null) {

							ItemStack b = livingentity.getEquipment().getBoots();
							player.getEquipment().setBoots(b);
							player.playSound(player.getLocation(),
									Sound.ANVIL_LAND, 9.0F, 9.0F);
							
						}

						livingentity.damage(1000000.0D);
						player.playSound(player.getLocation(),
								Sound.ENDERDRAGON_DEATH, 9.0F, 9.0F);
						player.sendMessage(Values.SuitCallMessage);
						player.updateInventory();
						PlayEffect.play_Suit_Get(player.getLocation(), player);
					}
				}

			}
		}
		} catch (NullPointerException e) {
		}
	}

	public static boolean MarkEntity(LivingEntity LivingEntity) {
		
		if (LivingEntity.getEquipment().getLeggings() != null) {
			ItemStack Check = LivingEntity.getEquipment().getLeggings();
			if(Check.getItemMeta().getDisplayName()!=null){
				if(Check.getItemMeta().getDisplayName().contains(Values.SuitName+Values.SuitInforegex)){
					return true;
				}
			}
			
				
		}
		if (LivingEntity.getEquipment().getHelmet() != null) {
			
			ItemStack Check = LivingEntity.getEquipment().getHelmet();
			
			if(Check.getItemMeta().getDisplayName()!=null){
				if(Check.getItemMeta().getDisplayName().contains(Values.SuitName+Values.SuitInforegex)){
					return true;
				}
			}
			
		}
		return false;

		

	}

	private static Entity returnEntity(List<Entity> ListEntity, Player player) {
		
		Entity entity = null;
		Location location = player.getLocation();
		double distance = 1000;
		for (Entity Entity : ListEntity) {
			if (Entity.getLocation().distance(location) < distance
					&& Entity.getType() != EntityType.PLAYER) {
				if (Entity.getType() == EntityType.PIG_ZOMBIE
						|| Entity.getType() == EntityType.SKELETON) {
					
					if (dao.isCreatedBy(Entity, player)) {

						distance = Entity.getLocation().distance(location);
						entity = Entity;
					}
				}
			}
		}
		if (entity == null) {

			player.sendMessage(Values.NoSuchEntity);
		}

		return entity;

	}

	private static void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public SpawningDao getDao() {
		
		return this.dao;
	}

	public static ItemStack getGun() {

		return gunitem;
	}

	
	public static void resetInventory(Player spnSender ) {
		
		reset(spnSender, equipment, inventory);
		reset(spnSender, armorequipment, armorinventory);
		
		reset(spnSender, handequipment, handinventory);
		reset(spnSender, helmetequipment, helmetinventory);
		reset(spnSender, chestequipment, chestinventory);
		reset(spnSender, leggingsequipment, leggingsinventory);
		reset(spnSender, bootsequipment, bootsinventory);
	}
	
	public static void spawnfireworks(Player whoClicked) {
		isPlayed = false;
		List<Entity> list = whoClicked.getWorld().getEntities();
		for (Entity entity : list) {
			if (dao.isCreatedBy(entity, whoClicked)&&entity instanceof Damageable) {
				entity.getWorld().createExplosion(entity.getLocation(), 8.0F);
				Damageable damgaeable = (Damageable) entity;
				damgaeable.damage(1000000.0D, whoClicked);
				Location location = entity.getLocation();
				Firework firework = (Firework) location.getWorld().spawnEntity(
						location, EntityType.FIREWORK);
				Random random = new Random();
				FireworkEffect effect = FireworkEffect.builder()
						.flicker(random.nextBoolean())
						.withColor(org.bukkit.Color.RED)
						.withFade(org.bukkit.Color.RED)
						.with(org.bukkit.FireworkEffect.Type.STAR)
						.trail(random.nextBoolean()).build();

				FireworkMeta meta = firework.getFireworkMeta();
				meta.addEffect(effect);
				meta.setPower((int) 3);
				firework.setFireworkMeta(meta);
			}
		}
		if (!isPlayed) {
			whoClicked.sendMessage(Values.NoSuchEntity);
		} else {
			whoClicked.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
					+ "Fireworks");
		}
		

		

	}

	public static void SetDisplayName(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	

	
	public static void reset(Player player , HashMap<Player, Inventory>map , Inventory inventory){
		if(!map.containsKey(player)){
			Inventory Newinventory = Bukkit.createInventory(null, inventory.getSize(), inventory.getName()+":"+player.getDisplayName());
			Newinventory.setContents(inventory.getContents());
			map.put(player, Newinventory);
		}
	}
	
	public static int getLevel(Player p) {
		if (p.getEquipment().getLeggings() != null) {

			String leggings = p.getEquipment().getLeggings().getItemMeta()
					.getDisplayName();
			if (leggings != null) {
				if (leggings.contains(Values.SuitName+Values.SuitInforegex)) {

					String[]values = leggings.split(Values.SuitInforegex);
					return Integer.parseInt(values[1]);
				}

			}
		}
		if (p.getEquipment().getHelmet() != null) {
			String helmetname = p.getEquipment().getHelmet().getItemMeta()
					.getDisplayName();
			if (helmetname != null) {

				if (helmetname.contains(Values.SuitName+Values.SuitInforegex)) {

					String[]values = helmetname.split(Values.SuitInforegex);
					return Integer.parseInt(values[1]);

				}
			}
		}
		return 0;

		

	}
	public static boolean hasAbillity(Player player){
		if(ManUtils.Man(player)||CustomSuitPlugin.MarkEntity(player)||Hammer.Thor(player)){
			return true;
		}
		return false;
	}
	
	
}
