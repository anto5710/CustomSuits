package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;


public enum CustomEntities {
	NONE(Zombie.class, "none", ItemUtil.createWithName(Material.GLASS_PANE, ChatColor.RED + "NONE")),
	ARCHER(Zombie.class, "archer", ItemUtil.createWithName(Material.BOW, ChatColor.WHITE + "archer")),
	GOLEM(IronGolem.class, "golem", ItemUtil.createWithName(Material.ROSE_BUSH, ChatColor.WHITE + "Golem")),
	SPIDER(CaveSpider.class, "spider", ItemUtil.createWithName(Material.SPIDER_EYE, ChatColor.RED + "Spider")),
	BOMB(Creeper.class, "bomb", ItemUtil.createWithName(Material.GUNPOWDER, ChatColor.GRAY + "BOMB")),
	TITAN(Giant.class, "titan", ItemUtil.createWithName(Material.ZOMBIE_HEAD, ChatColor.DARK_GREEN + "Titan")),
	ENDERMAN(Enderman.class, "enderman", ItemUtil.createWithName(Material.ENDER_PEARL, ChatColor.DARK_PURPLE + "Enderman")),
	SNOWMAN(Snowman.class, "snowman", ItemUtil.createWithName(Material.PUMPKIN, ChatColor.WHITE + "SnowGolem")),
	BLAZE(Blaze.class, "blaze", ItemUtil.createWithName(Material.BLAZE_POWDER, ChatColor.DARK_RED + "Blazer")),
	GHAST(Ghast.class, "ghast", ItemUtil.createWithName(Material.GHAST_TEAR, ChatColor.WHITE + "Ghastea")),
	ZOMBIE(Zombie.class, "zombie", ItemUtil.createWithName(Material.ZOMBIE_HEAD, ChatColor.GREEN + "Zombie")),
	SILVERFISH(Silverfish.class, "silverfish", ItemUtil.createWithName(Material.STONE, ChatColor.WHITE + "Silveralge")),
	HORSE(Horse.class, "horse", ItemUtil.createWithName(Material.IRON_HORSE_ARMOR, ChatColor.GRAY + "Horse")),
	ZOMBIE_HORSE(ZombieHorse.class, "zombie_horse", ItemUtil.createWithName(Material.GOLDEN_HORSE_ARMOR, ChatColor.DARK_GREEN + "Zombie Horse")),
	SKELETON_HORSE(SkeletonHorse.class, "skeleton_horse", ItemUtil.createWithName(Material.DIAMOND_HORSE_ARMOR, ChatColor.BLACK + "The Undead Horse")),
	GUARDIAN(Guardian.class, "guardian", ItemUtil.createWithName(Material.PRISMARINE_CRYSTALS, ChatColor.AQUA + "Guardians of the Galaxy")),
	WARRIOR(PigZombie.class, "warrior", ItemUtil.createWithName(Material.GOLDEN_SWORD, ChatColor.GOLD + "Spartan")),
	VARCHER(WitherSkeleton.class, "varcher", ItemUtil.createWithName(Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + "V-archer")),
	WOLF(Wolf.class, "wolf", ItemUtil.createWithName(Material.BONE, ChatColor.WHITE + "Cerberus")),
	ILLUSIONER(Illusioner.class, "illusioner", ItemUtil.createWithName(Material.BLAZE_ROD, ChatColor.LIGHT_PURPLE + "MAGIC KAITO")),
	SHULKER(Shulker.class, "shulker", ItemUtil.createWithName(Material.SHULKER_SHELL, ChatColor.LIGHT_PURPLE + "MAGIC BOX"));
	
	private Class<? extends Entity> species;
	private String name;
	private ItemStack icon;
	
	public ItemStack getIcon(){
		return icon;
	}
	
	public Class<? extends Entity> getSpecies() {
		return species;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> Class<T> loadEntityClass(String entityType) {
		CustomEntities species = valueOf(entityType.toUpperCase());
		return species == null ? null : (Class<T>) species.getSpecies();
	}
	
	private CustomEntities(Class<? extends Entity> species, String name, ItemStack icon){
		this.species = species;
		this.name = name;
		this.icon = icon;
	}
	
	public static boolean contain(String KeY){
		String key = KeY.toLowerCase();
		return Arrays.stream(values()).anyMatch(e->e.name.equals(key));
	}
	
	public static boolean contain(ItemStack icon){
		return icon!= null && Arrays.stream(values()).anyMatch(e->e.icon.equals(icon));
	}
	
	public static CustomEntities get(ItemStack icon){
		for(CustomEntities e : values()){
			if(e.icon.equals(icon)) return e;
		}
		return null;
	}
	
	public static CustomEntities get(String KeY){
		String key = KeY.toLowerCase();
		for(CustomEntities e : values()){
			if(e.name.equals(key)) return e;
		}
		return null;
	}
}
