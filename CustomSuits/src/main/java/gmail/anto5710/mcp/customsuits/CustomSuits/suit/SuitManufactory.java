package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;

public class SuitManufactory {

	public static void manufacture(CustomEntities entityType, CustomEntities vehicleType, int creatureCnt,
			LivingEntity target, Player spnSender, Location location) {
		/* spnSender: 명령어를 입력한 플레이어 */
		boolean useVehicle = vehicleType != null && vehicleType != CustomEntities.NONE;
		SuitSettings hdle = CustomSuitPlugin.handle(spnSender);
		int level = hdle.level();
		int amount = useVehicle ? level * 2 : level;
		hdle.reinitUInven();
		Material type = Values.Suit_Spawn_Material;

		for (int cnt = 0; cnt < creatureCnt; cnt++) {
			ItemStack material = new ItemStack(type, amount);
			if (spnSender.getInventory().contains(type, amount)) {
				Entity rider = createEntity(entityType, location, spnSender, level, target);

				spnSender.getInventory().removeItem(material);
				spnSender.updateInventory();
				if (useVehicle) {
					Entity vehicle = createEntity(vehicleType, location, spnSender, level, target);
					SuitManufactory.jockize(rider, vehicle);
				}
			} else {
				SuitUtils.lack(spnSender, "Material");
			}
		}
	}

	static Entity createEntity(CustomEntities species, Location loc, Player spnSender, int level, LivingEntity target){
		@SuppressWarnings("unchecked")
		Class<Entity> entityClass = (Class<Entity>) species.getSpecies();
	
		/* spawning 위치를 잡아줍니다. */
		Entity spawnedEntity = spnSender.getWorld().spawn(loc, entityClass);
	
		SuitManufactory.addElseData(spawnedEntity, spnSender, level, target);
		CustomSuitPlugin.dao.saveEntity(spawnedEntity, spnSender);
	
		SuitUtils.playSound(loc, Sound.BLOCK_ANVIL_USE, 1.5F, 1.5F);		
		return spawnedEntity;
	}

	static void jockize(Entity rider, Entity vehicle){
		vehicle.teleport(rider);
		vehicle.addPassenger(rider);
	}

	public static void addElseData(Entity entity, Player spnSender, int level, LivingEntity target) {
		if (entity instanceof LivingEntity) {
			LivingEntity lentity = (LivingEntity) entity;
			lentity.setRemoveWhenFarAway(false);
			
			SuitManufactory.armorize(lentity, spnSender);
			SuitManufactory.steroid(lentity, level);
		}
		if (entity instanceof Mob) {
			((Mob) entity).setTarget(target);
		}
		if(entity instanceof Tameable){
			Tameable tamed = ((Tameable) entity);
			tamed.setTamed(true);
			tamed.setOwner(spnSender);
		}
		if (entity instanceof Horse) {
			SuitManufactory.setHorseData((Horse) entity, spnSender, level);
		}
		if (entity instanceof Enderman) {
			SuitManufactory.setMaterialForEnderMan((Enderman) entity, Material.TNT);
		}
		if (entity instanceof Wolf) {
			((Wolf) entity).setAngry(true);
		}
		if (entity instanceof IronGolem) {
			IronGolem golem = ((IronGolem) entity);
			golem.setPlayerCreated(false);
			golem.playEffect(EntityEffect.IRON_GOLEM_ROSE);
		}
		if (entity instanceof Creeper) {
			((Creeper) entity).setPowered(true);
		}
	}

	private static void steroid(LivingEntity lentity, int level) {
		PotionBrewer.addPotions(lentity, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999990, 1),
				new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999990, 1 + (int) (level / 32D)),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999990, 1 + (int) (level / 16D)),
				new PotionEffect(PotionEffectType.SPEED, 999999990, 1 + (int) (level / 32D)),
				new PotionEffect(PotionEffectType.WATER_BREATHING, 999999990, 1));
	}

	private static void armorize(LivingEntity lentity, Player spnSender) {
		if (SuitUtils.isArmable(lentity)) {
			SuitManufactory.setEquipment(CustomSuitPlugin.handle(spnSender).armor, spnSender, lentity);
		}
	}

	private static void setHorseData(Horse horse, Player spnSender, int level) {
		horse.setAge(level);
		
		HorseInventory horseinventory = horse.getInventory();
		ItemStack item = CustomSuitPlugin.handle(spnSender).armor.getItem(8);
	
		horse.setJumpStrength(1 + level/48D);
	
		if (ItemUtil.isHorseArmor(item)) {
			horseinventory.setItem(1, item);
		}
		horseinventory.addItem(new ItemStack(Material.SADDLE));
	}

	private static void setMaterialForEnderMan(Enderman enderman, Material material) {
		BlockData data = material.isBlock() ? material.createBlockData() : null;
		enderman.setCarriedBlock(data);
	}

	@SuppressWarnings("deprecation")
	private static void setEquipment(Inventory armorInv, Player player, LivingEntity spawnedEntity) {
		LivingEntity lentity = (LivingEntity) spawnedEntity;
		lentity.setHealth(lentity.getMaxHealth());
		lentity.setCustomName(player.getName() + "|" + Values.SuitName);
		lentity.setCustomNameVisible(true);
	
		SuitSettings hdle = CustomSuitPlugin.handle(player);
		int level = hdle.level();
	
		PotionBrewer.addPotion(lentity, PotionEffectType.INVISIBILITY, 999999990, 10);
		
		Color HelmetColor = hdle.getHelmetColor();
		Color ChestplateColor = hdle.getChestColor();
		Color LeggingsColor = hdle.getLeggingsColor();
		Color BootsColor = hdle.getBootsColor();
	
		/* 신발 신기기 */
		ItemStack helmetitem = armorInv.getItem(19);
	
		if (helmetitem != null) {
			SuitManufactory.addData(helmetitem, hdle.helmetEnchants, level, player, HelmetColor);
			lentity.getEquipment().setHelmet(new ItemStack(helmetitem));
			lentity.getEquipment().setHelmetDropChance(0F);
		}
		
		ItemStack chestplate = armorInv.getItem(28);
		if (chestplate != null) {
			SuitManufactory.addData(chestplate, hdle.chestEnchants, level, player, ChestplateColor);
			lentity.getEquipment().setChestplate(new ItemStack(chestplate));
			lentity.getEquipment().setChestplateDropChance(0F);
		}
		
		ItemStack leggings = armorInv.getItem(37);
		if (leggings != null) {
			SuitManufactory.addData(leggings, hdle.leggingsEnchants, level, player, LeggingsColor);
			lentity.getEquipment().setLeggings(new ItemStack(leggings));
			lentity.getEquipment().setLeggingsDropChance(0F);
		}
	
		ItemStack boots = armorInv.getItem(46);
		if (boots != null) {
			SuitManufactory.addData(boots, hdle.bootsEnchants, level, player, BootsColor);
			lentity.getEquipment().setBoots(new ItemStack(boots));
			lentity.getEquipment().setBootsDropChance(0F);
		}
		
		ItemStack hand = armorInv.getItem(29);
		if (hand != null) {
			ItemUtil.name(hand, ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level);
			Enchant.enchantment(hand, new Glow(), 1, true);
			lentity.getEquipment().setItemInMainHand(hand);
			lentity.getEquipment().setItemInMainHandDropChance(0);
		}
	}

	public static void addData(ItemStack item, Inventory enchantInven, int level, Player player, Color color) {
		if (item == null) return;
		
		ItemUtil.dye(item, color); //try dying
		if (enchantInven != null) {
			ItemUtil.name(item, ChatColor.AQUA + Values.SuitName + Values.SuitInforegex + level);
			enchantInven.forEach(book -> Enchant.enchantFromBook(item, book, level));
		}
	}
}
