package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.Setting.Enchant;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.Glow;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.PacketUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SuitWeapons implements Listener {

	public static TNTLauncher tnter; 	
	public static ArcReffecter reffecter;
	
	
	public static CustomSuitPlugin plugin;
	private static Material suitlauncher = Values.SuitLauncher;
	
	
	public SuitWeapons(CustomSuitPlugin plugin) {
		SuitWeapons.plugin = plugin;
		tnter = new TNTLauncher(plugin, 1);
		reffecter = new ArcReffecter(plugin, 1);
	}

	@EventHandler
	public void interectShield(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (SuitUtils.isRightClick(event) && player.isSneaking() && CustomSuitPlugin.isMarkEntity(player)
				&& SuitUtils.getHoldingItem(player).getType() == suitlauncher) {
			if (HungerScheduler.sufficeHunger(player, Values.SuitShieldHunger)) {
				final int sec = (CustomSuitPlugin.getSuitLevel(player)) / 20 + 3;
				player.setNoDamageTicks(sec * 20);
				player.sendMessage(ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "Created Shield: "
						+ ChatColor.DARK_AQUA + sec + " Seconds! ");
				
				ItemStack shield = new ItemStack(Material.SHIELD);
						
				BlockStateMeta shieldMeta = (BlockStateMeta) shield.getItemMeta();
				Banner ban = (Banner) shieldMeta.getBlockState();
				ban.setBaseColor(DyeColor.CYAN);
				ban.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
				
				shieldMeta.setBlockState(ban);
				shield.setItemMeta(shieldMeta);
				Enchant.enchantment(shield, new Glow(), 1, true);
				
				ItemUtil.name(shield, ChatColor.AQUA+"S.H.I.E.L.D.");
				ItemUtil.suffix(shield, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "KNOCKARMOR", 1.1, Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
				ItemUtil.suffix(shield, Attribute.GENERIC_ARMOR, "NANOARMOR", 1.8, Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
				ItemUtil.suffix(shield, Attribute.GENERIC_ARMOR_TOUGHNESS, "DURABLE", 1.4, Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
				player.getInventory().addItem(shield);
				player.updateInventory();
				SuitUtils.playSound(player, Values.SuitShieldSound, 2.0F, 2.0F);
			} else {
				SuitUtils.lack(player, "Energy");
			}
		}
	}
	
	@EventHandler
	public void toggleC(PlayerToggleSprintEvent e){
		System.out.println("toggle RUn! "+e.isSprinting());
	}
	
	@EventHandler
	public void toggleSneak(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		if(CustomSuitPlugin.isMarkEntity(p)){
			boolean wasGliding = p.isGliding();
			boolean toGlide = !p.isOnGround() && e.isSneaking(); 
			p.setFlying(p.isFlying() && !toGlide);
			p.setGliding(toGlide);
			
			boolean change = wasGliding != toGlide;
			if(change){
				if (toGlide) {
					SuitUtils.playSound(p, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 2F, 2F);
					SuitUtils.playSound(p, Sound.BLOCK_TRIPWIRE_ATTACH, 2F, 2F);
					SuitUtils.playSound(p, Sound.BLOCK_ANVIL_STEP, 1.5F, 2F);
					SuitUtils.playSound(p, Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 2F);
				} else {
					SuitUtils.playSound(p, Sound.BLOCK_PISTON_CONTRACT, 2F, 2F);
					SuitUtils.playSound(p, Sound.BLOCK_BEACON_POWER_SELECT, 2F, 2F);
				}
			}
		}
	}
	
	@EventHandler
	public void toggleGlide(EntityToggleGlideEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player p = (Player) e.getEntity();
			if(CustomSuitPlugin.isMarkEntity(p) && !e.isGliding() && !p.isOnGround()){
				e.setCancelled(true); //toggle jammer 면 cancel
			}
		}
	}

	@EventHandler
	public void launchFireball(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (SuitUtils.isRightClick(event)
				&& ItemUtil.checkItem(CustomSuitPlugin.missileLauncher, SuitUtils.getHoldingItem(player))) {

			if (ItemUtil.sufficeMaterial(player, Values.LauncherAmmo)) {
				SuitUtils.playSound(player, Values.LauncherSound, 5.0F, 5.0F);
				fireball(player);
			} else {
				SuitUtils.lack(player, "Missile");
			}
		}
	}

	private void fireball(Player player) {
		Location ploc = player.getLocation();
		Fireball fireball = player.launchProjectile(Fireball.class, ploc.getDirection().multiply(2));
		fireball.setIsIncendiary(true);
		fireball.setYield(Values.LauncherPower);
	}
	
	private static Material ammo = Material.TNT;
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent e) {
		if (SuitUtils.isLeftClick(e)) {
			Player player = e.getPlayer();
			String message = Values.BimMessage;

			int energy = Values.BimHunger;

			if (CustomSuitPlugin.isMarkEntity(player) 
					&& SuitUtils.getHoldingItem(player).getType() == suitlauncher) {

				if(!player.isSneaking()){
					if (HungerScheduler.sufficeHunger(player, energy)) {
						repulseBim(player, message);
					} else {
						SuitUtils.lack(player, "Energy");
					}
				} else if (!tnter.inTNTcooldown(player)) {

					if (ItemUtil.sufficeMaterial(player, ammo)) {
						tnter.throwTNT(player, 5);
					} else {
						SuitUtils.lack(player, ammo.name());
					}
				}
			}
		}
	}

	private void repulseBim(Player player, String message) {
		Snowball bim = player.launchProjectile(Snowball.class, player.getLocation().getDirection().multiply(2));
		bim.setGravity(false);
		bim.setInvulnerable(true);
		
		double damage = Values.Bim * ((Math.sqrt(CustomSuitPlugin.getSuitLevel(player))/8) + 1);
		float power = (float) (Values.BimExplosionPower * ((Math.sqrt(CustomSuitPlugin.getSuitLevel(player))/10) + 1));
		
		Metadative.imprint(bim, damage, power, false, true);
		
		PacketUtil.castDestroyPacket(bim);
		reffecter.register(bim);
		
		SuitUtils.playSound(player, Sound.ENTITY_WITHER_SHOOT, 1F, 5F);
		SuitUtils.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1F, 0F);
		SuitUtils.playSound(player, Sound.ENTITY_BLAZE_AMBIENT, 1F, -1F);
		
		player.sendMessage(message);
	}

	public static void breakblock(Block block) {
		if (!SuitUtils.isUnbreakable(block)) {
			ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, block.getLocation(), 10, 5D, block.getBlockData());
			block.breakNaturally();
		}
	}
}
