package mgear;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.Utils.InventoryUtil;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class Spindle {
	protected final Player p;
	protected Arrow catapult;
	protected LivingEntity anchor;
	protected Entity dynamic_anchoror;
	
	protected Vector anchor_yoffset;
	protected Vector tension;
	protected final String uuid;
	
	public Spindle(Player p){
		this.p = p;
		this.uuid = UUID.randomUUID().toString();
	}
	
	public static String ANCHOR = "ANCHORE";
	public static String PSEUDO_ANCHOR = "AFAKE ANCHOR";
	public static String CATAPULT = "CATAPULTE";
	public static String SPINDLE = "SPINDLE WHO";
	
	public void catapult(){
		Location loc = p.getEyeLocation();
		Location target = SuitUtils.getTargetLoc(p, 100);
		float d = (float) target.distance(loc);
		
		catapult = p.getWorld().spawnArrow(loc, target.subtract(loc).toVector(), Math.max(0.2f*d,2f), 5f);
		catapultize(catapult);
	}
	
	public boolean anchored(){
		return anchor!=null;
	}
	
	public boolean pseudoAnchored(){
		return anchored() && isPseudoAnchor(anchor);
	}
	
	public boolean catapulted(){
		return catapult != null || (anchored()&&!isPseudoAnchor(anchor));
	}
	
	private static Set<EntityType>dynamics = Sets.newHashSet(EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.GIANT, EntityType.GHAST);
	private boolean dynamicAnchorable(@Nonnull Entity e){
		return dynamics.contains(e.getType());
	}
	
	public void anchor(Entity toAnchorAt){
		Location aloc = toAnchorAt.getLocation();
		Location cloc = catapult.getLocation();
		if(catapulted()){
			if(pseudoAnchored()) anchor.remove();
			
			boolean dynamic = dynamicAnchorable(toAnchorAt);
			if(toAnchorAt instanceof LivingEntity && !dynamic && ((LivingEntity)toAnchorAt).setLeashHolder(p)){
				anchor = (LivingEntity) toAnchorAt;
			}else{
				anchor = spawnPseudoAnchor(catapult.getLocation());
				anchor.setLeashHolder(p);
	
				if(dynamic){
					
					anchor_yoffset = cloc.subtract(aloc).toVector().setX(0).setZ(0);
					dynamic_anchoror = toAnchorAt;
					Metadative.imprint(dynamic_anchoror, ANCHOR, true);
					Metadative.imprint(dynamic_anchoror, SPINDLE, uuid);
				}else{
					anchor_yoffset = null;
				}
				integrate();
			} 
			Metadative.imprint(anchor, ANCHOR, true);
			Metadative.imprint(anchor, SPINDLE, uuid);
		}
	}
	
	public void anchor(){
		anchor(catapult);
	}
	
	private static LivingEntity spawnPseudoAnchor(Location loc){
		LivingEntity anchor = loc.getWorld().spawn(loc, Bat.class);
		anchor.setAI(false);
		anchor.setSilent(true);
		anchor.setInvulnerable(true);

		PotionBrewer.permaPotion(anchor, PotionEffectType.INVISIBILITY, 10);
		Metadative.imprint(anchor, PSEUDO_ANCHOR, true);
		return anchor;
	}
	
	private void catapultize(Arrow arrow){
		arrow.setShooter(p);
		arrow.setSilent(true);		
		Metadative.imprint(arrow, CATAPULT, true);
		Metadative.imprint(arrow, SPINDLE, uuid);
		arrow.setPickupStatus(PickupStatus.DISALLOWED);
	}
	
	public void retrieve(){
		if(isPseudoAnchor(anchor)){
			anchor.remove();
		}else anchor.setLeashHolder(null);
		
		anchor = null;
		dynamic_anchoror = null;
		catapult.remove(); catapult = null;
	}
	 
	public Vector updateTension(){
		Vector diff = MathUtil.disposition(anchor, p);
		double R2 = Math.pow(diff.lengthSquared(),0.75);
		double speed2 = Math.max(1, p.getVelocity().lengthSquared());
		
//		double centrip = pmass*speed2/(R2>10*10? R2 : 125);
		double centrip = 0.65*Math.sqrt(speed2)/(R2>31.6227766? R2 :31.6227766);
		
		tension = diff.multiply(centrip); 
		if(InventoryUtil.inMainHand(p, MainGear.trigger)){
			ItemStack trigger = InventoryUtil.getMainItem(p);
			ItemUtil.name(trigger, MainGear.tname+ String.format(
		ChatColor.WHITE+ " (X: "+ChatColor.YELLOW+"%f"+
		ChatColor.WHITE+" | Y: "+ChatColor.YELLOW+"%f"+
		ChatColor.WHITE+" | Z: "+ChatColor.YELLOW+"%f"+
		ChatColor.WHITE+")", tension.getX(),tension.getY(),tension.getZ()));
		}
		return tension;
	}
		
	public void integrate(){		
		if(pseudoAnchored() && dynamic_anchoror!=null){			
			anchor.teleport(dynamic_anchoror.getLocation().add(anchor_yoffset));
		}else{
			Location cloc = catapult.getLocation();
			anchor.teleport(cloc.add(0, -0.5, 0));
		}
	}
	
	public static boolean isAnchor(@Nonnull Entity entity) {
		return entity instanceof LivingEntity && Metadative.excavatruth(entity, ANCHOR);
	}
	
	public static boolean isPseudoAnchor(@Nonnull Entity anchor){
		return anchor.getType()==EntityType.BAT && Metadative.excavatruth(anchor, PSEUDO_ANCHOR);
	}
	
	public static boolean isCatapult(@Nonnull Entity entity) {
		return entity.getType()==EntityType.ARROW && Metadative.excavatruth(entity, CATAPULT);
	}

	public void rejoice() {
		if(isPseudoAnchor(anchor)){
			anchor.remove();
		}else anchor.setLeashHolder(null);
		
		anchor = null;
		dynamic_anchoror = null;
		Location cloc = catapult.getLocation();
		catapult.remove(); 
		catapult = p.getWorld().spawn(cloc, Arrow.class);
		catapultize(catapult);
	}
}
