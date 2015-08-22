package gmail.anto5710.mcp.customsuits.CustomSuits;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;

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
import java.util.logging.Logger;

import javax.crypto.AEADBadTagException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
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
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.junit.internal.matchers.IsCollectionContaining;
import org.w3c.dom.ls.LSInput;

/**
 * Hello world!
 *
 */
public class CustomSuitPlugin extends JavaPlugin implements Listener {
	Logger logger;
	static JavaPlugin plugin;
	
	SchedulerHunger hscheduler;
	static boolean	isPlayed = false;
	/**
	 * entityType 이름과 대응하는 EntityClass 모음
	 */
	Map<String, Class<? extends Entity>> entityMap = new HashMap<>();

	Map<String, Color> colorMap = new HashMap<>();
	
	static ItemStack suitremote = new ItemStack(Material.DIODE);
	
	static ItemStack gunitem = new ItemStack(Material.IRON_BARDING);
	
	static ItemStack AmmoForSniper = new ItemStack(Material.GOLD_NUGGET);
	static ItemStack AmmoForMachineGun = new ItemStack(Material.FLINT);
	
	
	
	static Inventory inventory =Bukkit.createInventory(null, 9 , "[Settings]");
	static Inventory armorinventory =Bukkit.createInventory(null, 9 , "[Armor]");
	static Inventory chestinventory =Bukkit.createInventory(null, 9 , "[Chestplate]");
	static Inventory bootsinventory =Bukkit.createInventory(null, 9 , "[Boots]");
	static Inventory helmetinventory =Bukkit.createInventory(null, 9 , "[Helmet]");
	static Inventory leggingsinventory =Bukkit.createInventory(null, 9 , "[Leggings]");
	static Inventory handinventory =Bukkit.createInventory(null, 9 , "[Hand]");
  
	private static SpawningDao dao;
	static HashMap<Player, Inventory> handequipment= new HashMap<>();
	static HashMap<Player,Inventory> chestequipment= new HashMap<>();
	static HashMap<Player,Inventory> helmetequipment= new HashMap<>();
	static HashMap<Player,Inventory> leggingsequipment= new HashMap<>();
	static HashMap<Player,Inventory> bootsequipment= new HashMap<>();
	
	static  HashMap<Player ,Inventory> equipment = new HashMap<>() ;
	static  HashMap<Player ,Inventory> armorequipment = new HashMap<>() ;
	
	static List<Player>onlinesplayer ;
	
	
    static Inventory CommandInventory = Bukkit.createInventory(null, 9, "[Command]")     ;
	
	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
         ItemStack Protection = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta ProtectionMeta = (EnchantmentStorageMeta) Protection.getItemMeta();
         ProtectionMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
         Protection.setItemMeta(ProtectionMeta);
         
         ItemStack Blast_Protection = new ItemStack(Material.ENCHANTED_BOOK);
       
         EnchantmentStorageMeta Blast_ProtectionMeta = (EnchantmentStorageMeta) Blast_Protection.getItemMeta();
         
         Blast_ProtectionMeta.addStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
         
         Blast_Protection.setItemMeta(Blast_ProtectionMeta);
         
         ItemStack Fire_Protection = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta Fire_ProtectionMeta = (EnchantmentStorageMeta) Fire_Protection.getItemMeta();
         Fire_ProtectionMeta.addStoredEnchant(Enchantment.PROTECTION_FIRE, 4, true);
         
        Fire_Protection.setItemMeta(Fire_ProtectionMeta);
         
       
         ItemStack Feather_Falling = new ItemStack(Material.ENCHANTED_BOOK);
         
         EnchantmentStorageMeta Feather_FallingMeta = (EnchantmentStorageMeta)Feather_Falling.getItemMeta();
         
        Feather_FallingMeta.addStoredEnchant(Enchantment.PROTECTION_FALL, 4, true);
      
        Feather_Falling.setItemMeta(Feather_FallingMeta);
        
         ItemStack Respiration = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta ResprirationMeta = (EnchantmentStorageMeta)Respiration.getItemMeta();
         
         ResprirationMeta.addStoredEnchant(Enchantment.OXYGEN, 3, true);
         
         Respiration.setItemMeta(ResprirationMeta);
        
         ItemStack Aqua_Affinity = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta Aqua_AffinityMeta = (EnchantmentStorageMeta) Aqua_Affinity.getItemMeta();
         Aqua_AffinityMeta.addStoredEnchant(Enchantment.WATER_WORKER, 1, true);
         
         Aqua_Affinity.setItemMeta(Aqua_AffinityMeta);
       
         ItemStack Thorns = new ItemStack(Material.ENCHANTED_BOOK);
         
         EnchantmentStorageMeta ThornsMeta = (EnchantmentStorageMeta) Thorns.getItemMeta();
         ThornsMeta.addStoredEnchant(Enchantment.THORNS, 3, true);
         
         Thorns.setItemMeta(ThornsMeta);
         

         
         
         ItemStack Unbreaking = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta UnbreakingMeta = (EnchantmentStorageMeta)Unbreaking.getItemMeta();
        UnbreakingMeta.addStoredEnchant(Enchantment.DURABILITY, 4, true);
        
        Unbreaking.setItemMeta(UnbreakingMeta); 
        
         ItemStack Sharpness = new ItemStack(Material.ENCHANTED_BOOK);
         
         EnchantmentStorageMeta SharpnessMeta = (EnchantmentStorageMeta) Sharpness.getItemMeta();
        SharpnessMeta.addStoredEnchant(Enchantment.DAMAGE_ALL, 4, true);
        
        Sharpness.setItemMeta(SharpnessMeta);
         
         ItemStack KnockBack = new ItemStack(Material.ENCHANTED_BOOK);
         EnchantmentStorageMeta KnockBackMeta = (EnchantmentStorageMeta) KnockBack.getItemMeta();
        KnockBackMeta.addStoredEnchant(Enchantment.KNOCKBACK, 3, true);
        
        KnockBack.setItemMeta(KnockBackMeta);
       
         
         ItemStack Fire_Aspect = new ItemStack(Material.ENCHANTED_BOOK);
         
         EnchantmentStorageMeta Fire_AspectMeta = (EnchantmentStorageMeta) Fire_Aspect.getItemMeta();
         Fire_AspectMeta.addStoredEnchant(Enchantment.FIRE_ASPECT, 3, true);
         
         Fire_Aspect.setItemMeta(Fire_AspectMeta);
         
         ItemStack Looting = new ItemStack(Material.ENCHANTED_BOOK);
         
         EnchantmentStorageMeta LootingMeta = (EnchantmentStorageMeta) Looting.getItemMeta();
         LootingMeta.addStoredEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
         
         Looting.setItemMeta(LootingMeta);
       
         
         ItemStack levelitem = new ItemStack(Material.EXP_BOTTLE);
         ItemMeta lm = levelitem.getItemMeta();
         lm.setDisplayName(ChatColor.GREEN+"[Level]");
         levelitem.setItemMeta(lm);
         ItemStack armorset = new ItemStack(Material.DIAMOND_CHESTPLATE);
         ItemMeta am = armorset.getItemMeta();
         am.setDisplayName(ChatColor.GOLD+"[Armor]");
         armorset.setItemMeta(am);
         ItemStack command = new ItemStack(Material.REDSTONE_COMPARATOR);
         ItemMeta cm = command.getItemMeta();
         cm.setDisplayName(ChatColor.RED+"[Command]");
         command.setItemMeta(cm);
         
		inventory.setItem(0, new ItemStack(command));
		inventory.setItem(1, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(2, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(3, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(4, new ItemStack(armorset));
		inventory.setItem(5, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(6, new ItemStack(Material.IRON_FENCE));
		inventory.setItem(8, new ItemStack(levelitem));
		armorinventory.setItem(1, new ItemStack(Material.IRON_FENCE));
		armorinventory.setItem(3, new ItemStack(Material.IRON_FENCE));
		armorinventory.setItem(5, new ItemStack(Material.IRON_FENCE));
		armorinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
		armorinventory.setItem(0, new ItemStack(Material.GOLD_HELMET));
		armorinventory.setItem(2, new ItemStack(Material.LEATHER_CHESTPLATE));
		armorinventory.setItem(4, new ItemStack(Material.GOLD_LEGGINGS));
		armorinventory.setItem(6, new ItemStack(Material.LEATHER_BOOTS));
		armorinventory.setItem(8, new ItemStack(Material.NETHER_STAR));
		
		Wool wool = new Wool(Material.WOOL);
		wool.setColor(DyeColor.RED);
		ItemStack woolitem= wool.toItemStack();
		
		
		leggingsinventory.setItem(0,Protection);
		leggingsinventory.setItem(1, Unbreaking);
		leggingsinventory.setItem(2, Thorns);
		leggingsinventory.setItem(8,woolitem);
		
		
		
		
		leggingsinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
	
		
		chestinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
		
		chestinventory.setItem(0,Protection);
		chestinventory.setItem(1, Unbreaking);
		chestinventory.setItem(2, Thorns);
		chestinventory.setItem(8, woolitem);
		
		
		bootsinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
		bootsinventory.setItem(0,Fire_Protection);
		bootsinventory.setItem(1, Unbreaking);
		bootsinventory.setItem(2, Thorns);
		
		bootsinventory.setItem(4,Feather_Falling);
		bootsinventory.setItem(8,woolitem);
		
		
		
	
		helmetinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
	    helmetinventory.setItem(0, Respiration);
	    helmetinventory.setItem(1, Blast_Protection);
	    helmetinventory.setItem(2, Thorns);
	    helmetinventory.setItem(3, Aqua_Affinity);
	    helmetinventory.setItem(4, Unbreaking);
	    
	    helmetinventory.setItem(8,woolitem);
		
		
		handinventory.setItem(7, new ItemStack(Material.IRON_FENCE));
		
		handinventory.setItem(0, Unbreaking);
		handinventory.setItem(1, Sharpness);
		handinventory.setItem(2, KnockBack);
		handinventory.setItem(3, Fire_Aspect);
		handinventory.setItem(4, Looting);
		
		ItemStack skull = new ItemStack(397, 1, (short)0, (byte)3);
		ItemMeta sm = skull.getItemMeta();
		sm.setDisplayName(ChatColor.AQUA+"[Party Protocol]");
		skull.setItemMeta(sm);
		
		ItemStack helmetM = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta hm = helmetM.getItemMeta();
		hm.setDisplayName(ChatColor.AQUA+"[Suit Summon]");
		helmetM.setItemMeta(hm);
		
		CommandInventory.setItem(1, helmetM);
		ItemStack Targetitem = new ItemStack(Material.SKULL);
		ItemMeta tm = Targetitem.getItemMeta();
		tm.setDisplayName(ChatColor.BLUE+"[Target]");
		Targetitem.setItemMeta(tm);
		CommandInventory.setItem(3, Targetitem );
		ItemStack firework = new ItemStack(Material.FIREWORK);
		ItemMeta fm = firework.getItemMeta();
		fm.setDisplayName(ChatColor.DARK_RED+"FireWorks");
		firework.setItemMeta(fm);
		CommandInventory.setItem(7,firework );
		CommandInventory.setItem(5, skull);
		CommandInventory.setItem(0, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(2, new ItemStack(Material.IRON_FENCE));
		
	
		
		
		CommandInventory.setItem(4, new ItemStack(Material.IRON_FENCE));
		CommandInventory.setItem(6, new ItemStack(Material.IRON_FENCE));
		
		
		
		CommandInventory.setItem(8, new ItemStack(Material.IRON_FENCE));
		
		
		
		// key 에 대응하는 Entity 종류를 맵에 담아둡니다.
		logger = getLogger();

		ItemMeta suitmeta = suitremote.getItemMeta();
		suitmeta.setDisplayName(ChatColor.RED + "Suit Commander");
		
		ItemMeta gunmeta = gunitem.getItemMeta();
		gunmeta.setDisplayName(ChatColor.RED+"Knif-1120 :-:"+WeaponListner.maxformachine+":-:"+WeaponListner.maxforsniper);
		
	
		
		suitremote.setItemMeta(suitmeta);
		gunitem.setItemMeta(gunmeta);
		
		
		ShapedRecipe suitrecipe = new ShapedRecipe(suitremote);
		
		
		suitrecipe.shape("*@*", "$!%", "*^*");
		suitrecipe.setIngredient('*', Material.GOLD_INGOT);
		suitrecipe.setIngredient('@', Material.IRON_HELMET);
		suitrecipe.setIngredient('$', Material.IRON_CHESTPLATE);
		suitrecipe.setIngredient('%', Material.IRON_LEGGINGS);
		suitrecipe.setIngredient('!', Material.COMPASS);
		suitrecipe.setIngredient('^', Material.IRON_BOOTS);

		getServer().addRecipe(suitrecipe);

		
		ShapedRecipe gunrecipe = new ShapedRecipe(gunitem);
		
		gunrecipe.shape("&&*","^$!","&&*");
		gunrecipe.setIngredient('&', Material.IRON_BLOCK);
		gunrecipe.setIngredient('*', Material.SULPHUR);
		gunrecipe.setIngredient('!', Material.IRON_BARDING);
		gunrecipe.setIngredient('$', Material.REDSTONE);
		gunrecipe.setIngredient('^', Material.TNT);
		
		getServer().addRecipe(gunrecipe);
		
		
		
		
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
		entityMap.put("slime", Slime.class);
		entityMap.put("witch", Witch.class);
		entityMap.put("magmacube", MagmaCube.class);
		entityMap.put("zombie", Zombie.class);
		entityMap.put("squid", Squid.class);
		entityMap.put("silverfish",Silverfish.class);
		

		colorMap.put("red", Color.RED);
		colorMap.put("blue", Color.BLUE);
		colorMap.put("aqua", Color.AQUA);
		colorMap.put("black", Color.BLACK);
		colorMap.put("yellow", Color.YELLOW);
		colorMap.put("greem", Color.GREEN);
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
		manager.registerEvents(new SuitInventoryGUI(this),this);

		dao = new SpawningDao(this);
		dao.init();
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
		if (command.getName().equals("csuit")) {
			
			
			
			 
			Player mp = getServer().getPlayer(sender.getName());
			mp.sendMessage("");
			spawnsuit(mp);
		}
        if(command.getName().equals("get")){
        	Player p = getServer().getPlayer(sender.getName());
        	if(args.length==0){
        		p.sendMessage(ChatColor.RED+"[Warn] Wrong Command");
        	}
        	else {
        	String option =args[0];
        	
        	if(option.endsWith("commander")){
        		
        		p.getInventory().addItem(suitremote);
        	}
        	else if(option.endsWith("gun")){
        		p.getInventory().addItem(gunitem);
        	}else {
        		p.sendMessage(ChatColor.RED+"[Warn] Wrong Command");
        	}
        	
        	
        }
        }
		if (command.getName().equals("command")) {
			Player p = getServer().getPlayer(sender.getName());

			if (args.length == 0) {

			} else {
			
				if (args[0].equals("party")) {
				spawnall(p);
				} else {
					if (args[0].equals("target")) {
						targetPlayer(p , sender.getServer().getPlayer(args[1]));

					} else if(args[0].equals("firework")){
						spawnfireworks(p);
					}
						else {
					
						p.sendMessage(ChatColor.RED + "Wrong Command");
					}

				}
			}
		}
		Server server = getServer();
		Player spnSender = server.getPlayer(sender.getName());
		
		
		if (command.getName().equals("cspn")) {
			if (spnSender.getEquipment().getItemInHand().getType() == Material.DIODE||spnSender.getEquipment().getItemInHand().getItemMeta().getDisplayName().endsWith(suitremote.getItemMeta().getDisplayName())) {
			
				String entityName = args[0];
				String targetPlayerName = args[1];
				int creatureCnt = Integer.parseInt(args[2]);
                spawnentity(entityName , creatureCnt , spnSender, targetPlayerName);
                 
                 spnSender.openInventory(inventory);
                 
				// args[0] : <종류>
				// args[1] : <targetID>
			}
	}
		return super.onCommand(sender, command, label, args);
	}

	public static void targetPlayer(Player p, Player target ) {
		// TODO Auto-generated method stub
		isPlayed = false;
		List<Entity> list = p.getWorld().getEntities();
		
		for (Entity e : list) {
			if (dao.isCreatedBy(e, p)) {
				((Creature) e).setTarget(target);
				p.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "Target : "
						+ ChatColor.DARK_AQUA+target.getDisplayName());
				isPlayed = true;

			}

		}
		if(!isPlayed){
			p.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "No such entity");
		}
	}

	public static void spawnall(Player p) {
		isPlayed = false;
		List<Entity> list = p.getWorld().getEntities();
		for (Entity e : list) {
			if (dao.isCreatedBy(e, p)) {
				e.teleport(p.getLocation());
				p.sendMessage(ChatColor.AQUA
						+ "Teleported Suit----");
				isPlayed = true;
				sleep(100);
			}
		}
		if(!isPlayed){
			p.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "No such entity");
		}
		// TODO Auto-generated method stub
		
	}

	public static void spawnsuit(Player mp) {
		if (mp.getItemInHand().getType() == Material.REDSTONE_COMPARATOR) {
			List<Entity> near = mp.getWorld().getEntities();
			if(returnEntity(near, mp)!=null){
			playSpawningEffect(returnEntity(near, mp), mp);
			sleep(100);
			}
		} else {
			mp.sendMessage(ChatColor.RED
					+ "Please hold your RemoteControler");
			mp.playSound(mp.getLocation(), Sound.NOTE_STICKS, 6.0F, 6.0F);

		}
		// TODO Auto-generated method stub
		
	}
	public static void spawnsuitforGUI(Player mp) {
		
			List<Entity> near = mp.getWorld().getEntities();
			if(returnEntity(near, mp)!=null){
			playSpawningEffect(returnEntity(near, mp), mp);
			sleep(100);
			}
		
		// TODO Auto-generated method stub
		
	}
	private void spawnentity(String entityName, int creatureCnt,
			Player spnSender, String targetPlayerName) {
		// args[2] : 수량
		// args[3] : <종류>의 색상

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
		if(spnSender.getItemInHand()!=null){
			if(spnSender.getItemInHand().getType()==suitremote.getType()){
		if(spnSender.getItemInHand().getItemMeta().getDisplayName()!=null){
			
		if (spnSender.getItemInHand().getItemMeta().getDisplayName()
				.endsWith(suitremote.getItemMeta().getDisplayName())) {
			if(equipment.get(spnSender)==null){
				equipment.put(spnSender, inventory);
			}
			if(armorequipment.get(spnSender)==null){
				armorequipment.put(spnSender, armorinventory);
			}
			for (int ccnt = 0; height > ccnt; ccnt++) {
				for (int cnt = 0; width > cnt; cnt++) {
					Material type = Material.IRON_BLOCK;
					ItemStack material = new ItemStack(type, 1);

					if (spnSender.getInventory().contains(type, 1)) {

						Class<Entity> entityClass = loadEntityClass(entityName);
						Location loc = spnSender.getLocation();

						this.logger
								.info("entity class: " + entityClass);

						/* spawning 위치를 잡아줍니다. */
						Entity spawnedEntity = spnSender.getWorld()
								.spawn(new Location(
										spnSender.getWorld(),
										loc.getX() - (width / 2) + cnt,
										loc.getY(), loc.getZ()
												- (height / 2) + ccnt),
										entityClass);
						

						int entityID = spawnedEntity.getEntityId();
						logger.info("ENTITY ID: " + entityID + " by "
								+ spnSender.getName() + "("
								+ spnSender.getEntityId() + ")");

						dao.saveEntity(spawnedEntity, spnSender);
						spnSender.getInventory().removeItem(material);
						spnSender.updateInventory();
						spnSender.playSound(loc, Sound.ANVIL_USE, 1.5F, 1.5F);
						LivingEntity livingentity = (LivingEntity) spawnedEntity;
						livingentity.setRemoveWhenFarAway(false);
						setEquipment(targetPlayer,armorequipment.get(spnSender) ,spnSender, livingentity,entityName);
						
						
						
						/*
						 * 8412 by 2010 8413 by 2010 8413 by 2010 8413
						 * by 2010 8413 by 2010 8413 by 2010 8413 by
						 * 2010 8413 by 2010 8413 by 2010
						 */if (livingentity.getType() == EntityType.ENDERMAN) {
							Enderman enderman = (Enderman) livingentity;
							MaterialData data = new MaterialData(
									Material.TNT);
							
							enderman.setCarriedMaterial(data);
						}
						if (livingentity.getType() == EntityType.WOLF) {
							Wolf w = (Wolf) livingentity;
							w.setAngry(true);
						}
						if (livingentity.getType() == EntityType.IRON_GOLEM) {
							IronGolem ig = (IronGolem) livingentity;
							ig.setPlayerCreated(false);
						}
						if (livingentity.getType() == EntityType.CREEPER) {
							Creeper creeeper = (Creeper) livingentity;
							creeeper.setPowered(true);
						}

					} else {
						// end inner for
						spnSender
								.sendMessage(ChatColor.RED
										+ "[Warn]: You don't have Material for spawn Entities");
						spnSender.playSound(spnSender.getLocation(),
								Sound.NOTE_STICKS, 6.0F, 6.0F);
					}
				} // end outer for
			
	}
}
		}
		 else {
				spnSender.sendMessage(ChatColor.RED
						+ "[Warn]: Please hold your Suit Commander");
				spnSender.playSound(spnSender.getLocation(),
						Sound.NOTE_STICKS, 6.0F, 6.0F);
			}}
 else {
	spnSender.sendMessage(ChatColor.RED
			+ "[Warn]: Please hold your Suit Commander");
	spnSender.playSound(spnSender.getLocation(),
			Sound.NOTE_STICKS, 6.0F, 6.0F);
}
		}
		// TODO Auto-generated method stub
	
	}



	private void setEquipment(Player targetPlayer , Inventory inventoryitem ,Player player, LivingEntity spawnedEntity , String entityName) {
		
		if(CustomSuitPlugin.helmetequipment.get(player)==null){
 			
	 		CustomSuitPlugin.helmetequipment.put(player, CustomSuitPlugin.helmetinventory);
	 	}
	 		
	 
	
	 		if(CustomSuitPlugin.chestequipment.get(player)==null){
	 			
	     		CustomSuitPlugin.chestequipment.put(player, CustomSuitPlugin.chestinventory);
	     	}
	 		
	 
		if(CustomSuitPlugin.leggingsequipment.get(player)==null){
			
			CustomSuitPlugin.leggingsequipment.put(player, CustomSuitPlugin.leggingsinventory);
		}
	
	
		if(CustomSuitPlugin.bootsequipment.get(player)==null){
			
			CustomSuitPlugin.bootsequipment.put(player, CustomSuitPlugin.bootsinventory);
		}
		
	
		if(CustomSuitPlugin.handequipment.get(player)==null){
			
			CustomSuitPlugin.handequipment.put(player, CustomSuitPlugin.handinventory);
		}
		
		LivingEntity c = (LivingEntity) spawnedEntity;
		((Creature) c).setTarget(targetPlayer);

		addPotionEffects(c, new PotionEffect[] {
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999990,
						10),
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999990,
						10),
				new PotionEffect(PotionEffectType.ABSORPTION, 999999990, 10),
				new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999990, 10),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999990,
						10),
				new PotionEffect(PotionEffectType.SPEED, 999999990, 10),
				new PotionEffect(PotionEffectType.WATER_BREATHING, 999999990,
						10) });

		c.setHealth(c.getMaxHealth());

		c.setCustomName(targetPlayer.getName() + "|" + "Mark");
		ItemStack itemForCreature = createItemForCreature(c);
		c.getEquipment().setItemInHand(itemForCreature);

		// Color icolor = chooseColor(color);

		if ((entityName.equals("warrior")) || (entityName.equals("archer"))) {
			int level = equipment.get(player).getItem(8).getAmount();
			
			// warrior 이거나 archer인 경우는 갑옷을 입히기 위해서 별도의 처리를 해줍니다.
			// Material item = Material.LEATHER_BOOTS;
			// Material hell = Material.LEATHER_HELMET;


			c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
					999999990, 10));

			/* 신발 신기기 */
			ItemStack helmetitem = inventoryitem.getItem(0);
		
			if(helmetitem!=null){
			    addData(helmetitem, helmetequipment, level, player);
				
				c.getEquipment().setHelmet(new ItemStack(helmetitem));
				c.getEquipment().setHelmetDropChance(0F);
				
				
			}
			ItemStack chestplate = inventoryitem.getItem(2);
			if(chestplate!=null){
				 addData(chestplate, chestequipment, level, player);
				
				c.getEquipment().setChestplate(new ItemStack(chestplate));
				c.getEquipment().setChestplateDropChance(0F);
				
			}
			ItemStack leggings = inventoryitem.getItem(4);
			if(leggings!=null){
				 addData(leggings, leggingsequipment, level, player);
				
				c.getEquipment().setLeggings(new ItemStack(leggings));
				c.getEquipment().setLeggingsDropChance(0F);
			}
			
			ItemStack boots = inventoryitem.getItem(6);
			if(boots!=null){
				
				 addData(boots, bootsequipment, level, player);
			c.getEquipment().setBoots(new ItemStack(boots));
			}
	

			/* 바지 입히기 */
			
			
			

			/* 헬맷 씌우기 */
			
			


			/* 갑옷 입히기 */
			
			
			ItemStack handitem = inventoryitem.getItem(8);
			 addData(handitem, handequipment, level, player);
				
				c.getEquipment().setItemInHand(handitem);
				c.getEquipment().setItemInHandDropChance(100.0F);
			
				if(!entityName.equals("dragon")){
			((Creature) c).setTarget(targetPlayer);
				}
			c.setRemoveWhenFarAway(false);

		} // end if warrior or archer
	}

	public static void leathercolor(ItemStack item, DyeColor color) {
		Color c=color.getColor();
		
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(c);
		item.setItemMeta(meta);
		}
		
	
	
public void addData(ItemStack item , HashMap<Player, Inventory> map , int level,Player player ){
	if(item!=null){
		if(item.getType()==Material.LEATHER_HELMET||item.getType()==Material.LEATHER_CHESTPLATE||item.getType()==Material.LEATHER_LEGGINGS||item.getType()==Material.LEATHER_BOOTS){
			if(map.get(player).getItem(8)!=null){
				if(map.get(player).getItem(8).getType()==Material.WOOL){
					ItemStack wool =map.get(player).getItem(8);
					Wool w =new Wool(wool.getType(),wool.getData().getData());
					
					
					DyeColor dyecolor = w.getColor();
					leathercolor(item, dyecolor);
				}
			}
		}
		if(map.get(player)!=null){
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "Mark:"+level);
			item.setItemMeta(meta);
			
			int size = map.get(player).getSize();
			
			if(size!=0){
			for(int i = 0; i <= size-1;i++){
				if(map.get(player).getItem(i)!=null){
					if(map.get(player).getItem(i).getType()==Material.ENCHANTED_BOOK){
						EnchantmentStorageMeta echmeta = (EnchantmentStorageMeta) map.get(player).getItem(i).getItemMeta();
		      for(Enchantment key:echmeta.getStoredEnchants().keySet()){
					meta.addEnchant(key, echmeta.getStoredEnchants().get(key), true);
					
		      }
					}
				}
				}
			item.setItemMeta(meta);
			}
		}
	}
}
	private ItemStack createItemForCreature(LivingEntity ett) {
		ItemStack item = null;
		if (ett.getType() == EntityType.ENDERMAN) {
			item = new ItemStack(Material.TNT);
		} else {
			item = new ItemStack(Material.NETHER_STAR);
			// c.getEquipment().setItemInHand( new
			// ItemStack(Material.DIAMOND_BLOCK));

		}
		return item;
	}

	/**
	 * 주어진 숫자가 솟수(prime number)인지를 판단합니다. 몹을 생성할 때 수량이 소수이면 일자로 생성합니다. 그 밖에는
	 * 1이외의 가장 작은 약수와 , 전체/가장 작은 약수 를 가로, 세로로 직사각형 모양으로 소환합니다.
	 * 
	 * ex) 16 Z
	 * 
	 * 
	 * +----------------------> X | **** | **** | *P** | | | | v Z X
	 * +++++++++++++++++ X Z
	 * 
	 * @param spcnt
	 * @return
	 */
	private boolean isprime(int spcnt) {
		boolean returna = true;
		if (spcnt == 1) {

			returna = true;
		}
		for (int cnt = 2; cnt < spcnt; cnt++) {
			if (spcnt % cnt == 0) {
				returna = false;
			}
		}
		// TODO Auto-generated method stub

		return returna;
	}

	private <T extends Entity> Class<T> loadEntityClass(String entityType) {

		Class<T> cls = (Class<T>) entityMap.get(entityType.toLowerCase());

		if (cls == null) {
			logger.info("없는 엔티티: " + entityType);
		}

		return cls;

	}

	private Color chooseColor(String colorName) {
		Color color = colorMap.get(colorName.toLowerCase());
		if (color == null) {
			logger.info("color가 없다: " + colorName);
		}
		return color;
	}

	private void addPotionEffects(LivingEntity ett, PotionEffect... effects) {
		PotionEffect[] arrayOfPotionEffect;
		int j = (arrayOfPotionEffect = effects).length;
		for (int i = 0; i < j; i++) {
			PotionEffect eft = arrayOfPotionEffect[i];
			ett.addPotionEffect(eft);
		}
	}

	private static void playSpawningEffect(Entity entity, Player player) {
		Location playerlocation = player.getLocation();

		LivingEntity liveentity = (LivingEntity) entity;

		if (entity.getType() != EntityType.PLAYER) {
			if (entity.getType() == EntityType.SKELETON
					|| entity.getType() == EntityType.PIG_ZOMBIE) {
				if (MarkEntity(liveentity)&&dao.isCreatedBy(liveentity, player)) {

				Location entitylocation = liveentity.getLocation();
				int c = (int) Math
						.ceil(entitylocation.distance(playerlocation) / 2) - 1;
				if (c <= 0) {
					return;
				}
				Vector v = playerlocation.toVector()
						.subtract(entitylocation.toVector()).normalize()
						.multiply(2);
				Location l = entitylocation.clone();

				for (int i = 0; i < c; i++) {
					l.add(v);
					entity.teleport(l);

					l
					.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES,0,50);
					sleep(100);

				}

				ItemStack h = liveentity.getEquipment().getHelmet();
				ItemStack ch = liveentity.getEquipment().getChestplate();
				
				
				

				if (MarkEntity(liveentity)&&dao.isCreatedBy(liveentity, player)) {
					if (liveentity.getEquipment().getHelmet() != null) {

						player.getEquipment().setHelmet(h);
						player.playSound(player.getLocation(),
								Sound.ANVIL_LAND, 9.0F, 9.0F);
						player.playSound(player.getLocation(),
								Sound.VILLAGER_HIT, 9.0F, 9.0F);
						player.getWorld()
								.spigot()
								.playEffect(player.getLocation(),
										Effect.TILE_BREAK,
										Material.COBBLESTONE.getId(), 0, 0.0F,
										0.0F, 0.0F, 0.0F, 600, 200);
						sleep(500);
					}
					if (liveentity.getEquipment().getChestplate() != null) {

						player.getEquipment().setChestplate(ch);
						player.playSound(player.getLocation(),
								Sound.ANVIL_LAND, 9.0F, 9.0F);
						player.playSound(player.getLocation(),
								Sound.VILLAGER_HIT, 9.0F, 9.0F);
						player.getWorld()
								.spigot()
								.playEffect(player.getLocation(),
										Effect.TILE_BREAK,
										Material.COBBLESTONE.getId(), 0, 0.0F,
										0.0F, 0.0F, 0.0F, 600, 200);
						sleep(200);
					}
					if (liveentity.getEquipment().getLeggings() != null) {

						ItemStack cl = liveentity.getEquipment().getLeggings();
						player.getEquipment().setLeggings(cl);
						player.playSound(player.getLocation(),
								Sound.ANVIL_LAND, 9.0F, 9.0F);
						player.playSound(player.getLocation(),
								Sound.VILLAGER_HIT, 9.0F, 9.0F);
						player.getWorld()
								.spigot()
								.playEffect(player.getLocation(),
										Effect.TILE_BREAK,
										Material.COBBLESTONE.getId(), 0, 0.0F,
										0.0F, 0.0F, 0.0F, 600, 200);
						sleep(400);
					}
					if (liveentity.getEquipment().getBoots() != null) {

						ItemStack b = liveentity.getEquipment().getBoots();
						player.getEquipment().setBoots(b);
						player.playSound(player.getLocation(),
								Sound.ANVIL_LAND, 9.0F, 9.0F);
						player.playSound(player.getLocation(),
								Sound.VILLAGER_HIT, 9.0F, 9.0F);
						player.getWorld()
								.spigot()
								.playEffect(player.getLocation(),
										Effect.TILE_BREAK,
										Material.COBBLESTONE.getId(), 0, 0.0F,
										0.0F, 0.0F, 0.0F, 600, 200);
					}

					liveentity.damage(1000000.0D);
					player.playSound(player.getLocation(),
							Sound.ENDERDRAGON_DEATH, 9.0F, 9.0F);
					player.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "You called an armor");
				}
			}

		}
	}
	}

	private static boolean MarkEntity(LivingEntity p) {
		if(p.getEquipment().getLeggings()!=null){
			
			String leggings = p.getEquipment().getLeggings().getItemMeta().getDisplayName();
			if(leggings!=null){
			if(leggings.contains("Mark:")){
				
				return true;
			}
			
					
		}
		}
		if(p.getEquipment().getHelmet()!=null){
			String helmetname = p.getEquipment().getHelmet().getItemMeta().getDisplayName();
			if(helmetname!=null){
				
				if(helmetname.contains("Mark:")){
					
					return true;

				}
			}
		}
		return false;
		
	
// TODO Auto-generated method stub
		
	}

	private static Entity returnEntity(List<Entity> near, Player player) {
		// TODO Auto-generated method stub
		Entity entity = null;
		Location location = player.getLocation();
		double distance = 1000;
		for (Entity e : near) {
			if (e.getLocation().distance(location) < distance
					&& e.getType() != EntityType.PLAYER) {
				if (e.getType() == EntityType.PIG_ZOMBIE
						|| e.getType() == EntityType.SKELETON) {
					LivingEntity le = (LivingEntity) e;
					if (dao.isCreatedBy(e, player)) {

						distance = e.getLocation().distance(location);
						entity = e;
					}
				}
			}
		}
		if(entity ==null){
			
				player.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "No such entity");
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
		// TODO Auto-generated method stub
		return this.dao;
	}

	
	public static ItemStack getGun(){
		
		return gunitem;
	}

	public static double getPlayerLevel(Player player) {
		// TODO Auto-generated method stub
		double level =Mark(player);
		
		
		
		
		return level;
		
	}
    public static double Mark(Player p) {
    	
		if(p.getEquipment().getLeggings()!=null){
			
			String leggings = p.getEquipment().getLeggings().getItemMeta().getDisplayName();
			if(leggings!=null){
			if(leggings.contains("Mark:")){
				String []values = leggings.split(":");
				return Integer.parseInt(values[1]);
			}
			
					
		}
		}
		if(p.getEquipment().getHelmet()!=null){
			String helmetname = p.getEquipment().getHelmet().getItemMeta().getDisplayName();
			if(helmetname!=null){
				
				if(helmetname.contains("Mark:")){
					String []values = helmetname.split(":");
					return Integer.parseInt(values[1]);

				}
			}
		}
		return 0;
		
	
// TODO Auto-generated method stub

}

	public static void spawnfireworks(Player whoClicked) {
		isPlayed = false;
		List<Entity> list = whoClicked.getWorld().getEntities();
		for (Entity e : list) {
			if (dao.isCreatedBy(e, whoClicked)) {
				e.getWorld().createExplosion(e.getLocation(), 8.0F);
				Damageable d = (Damageable)e;
				d.damage(10000.0D, whoClicked);
				Location location = e.getLocation();
				Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
				  Random r = new Random();
		      FireworkEffect effect =  FireworkEffect.builder().flicker(r.nextBoolean()).withColor(org.bukkit.Color.RED).withFade(org.bukkit.Color.RED).with(org.bukkit.FireworkEffect.Type.STAR).trail(r.nextBoolean()).build();
		      
				FireworkMeta meta = firework.getFireworkMeta();
				meta.addEffect(effect);
				meta.setPower((int) 3);
				 firework.setFireworkMeta(meta);    
			}
		}
		if(!isPlayed){
			whoClicked.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "No such entity");
		}
		else {
			whoClicked.sendMessage(ChatColor.BLUE+"[Info]: "+ChatColor.AQUA + "Fireworks");
		}
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
	}



}
