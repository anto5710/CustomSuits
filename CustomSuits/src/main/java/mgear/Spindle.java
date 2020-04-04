package mgear;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets;

import gmail.anto5710.mcp.customsuits.Utils.Coolable;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.metadative.Metadative;

public class Spindle extends Coolable{
	private final Player p;
	private Arrow catapult;
	
	private LivingEntity anchor;
	private Entity anchor_target;
	private Vector target_offset;
	
	private Vector tension;
	private final String uuid;
	private float gas;	
	
	public Spindle(Player p){
		this.p = p;
		this.uuid = UUID.randomUUID().toString();
		this.gas = 100;
	}
	
	public static String ANCHOR = "ANCHORE";
	public static String PSEUDO_ANCHOR = "AFAKE ANCHOR";
	public static String ANCHOR_TARGET = "ANCHORE_LOC_REFERENCE";
	public static String CATAPULT = "CATAPULTE";
	public static String SPINDLE = "SPINDLE-WHO";
	
	public void catapult(){
		Location loc = p.getEyeLocation().add(0,-0.5,0);
		Location target = SuitUtils.getTargetLoc(p, 100);
		float d = (float) target.distance(loc);
		
		catapult = p.getWorld().spawnArrow(loc, target.subtract(loc).toVector(), Math.max(0.2f*d,2f), 5f);
		catapultize(catapult);
	}

	public static boolean isCatapult(@Nonnull Entity entity) {
		return entity.getType()==EntityType.ARROW && Metadative.getBoolean(entity, CATAPULT);
	}
	
	private static Set<EntityType>offsetsRequired = Sets.newHashSet(EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.GIANT, EntityType.GHAST);
	private boolean dynamicAnchorable(@Nonnull Entity e){
		return offsetsRequired.contains(e.getType());
	}
	
	public void anchor(Entity toAnchorAt){
		Location targetLoc = toAnchorAt.getLocation();
		Location arrowLoc = catapult.getLocation();
		if(hasCatapulted()){
			if(pseudoAnchored()) anchor.remove();
			
			boolean needsYoffset = dynamicAnchorable(toAnchorAt);
			if(toAnchorAt instanceof LivingEntity && !needsYoffset && ((LivingEntity)toAnchorAt).setLeashHolder(p)){
				anchor = (LivingEntity) toAnchorAt;
			}else{
				anchor = spawnPseudoAnchor(arrowLoc);
				anchor.setLeashHolder(p);

				if(needsYoffset){
					target_offset = new Vector().setY(MathUtil.bound(0,arrowLoc.getY() - targetLoc.getY(),toAnchorAt.getHeight()));
				}else{
					target_offset = new Vector().setY(-0.5);
				}
				anchor_target = toAnchorAt;
				Metadative.imprint(anchor_target, ANCHOR_TARGET, true);
				Metadative.imprint(anchor_target, SPINDLE, uuid);
				integrate();
			} 
			Metadative.imprint(anchor, ANCHOR, true);
			Metadative.imprint(anchor, SPINDLE, uuid);
		}
	}
	
	public void anchor(){
		anchor(catapult);
	}

	public static boolean isAnchor(@Nonnull Entity entity) {
		return entity instanceof LivingEntity && Metadative.getBoolean(entity, ANCHOR);
	}
		
	public static boolean isPseudoAnchor(@Nonnull Entity anchor){
		return anchor.getType()==EntityType.BAT && Metadative.getBoolean(anchor, PSEUDO_ANCHOR);
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
		arrow.setPickupStatus(PickupStatus.DISALLOWED);
		Metadative.imprint(arrow, CATAPULT, true);
		Metadative.imprint(arrow, SPINDLE, uuid);
	}
	
	public void retrieve(boolean returnToPlayer, long cooltime){
		if(hasCatapulted()) {
			if (hasAnchored()) { // removes pseudo-anchors and detach leash. 
				if (isPseudoAnchor(anchor)) {
					anchor.remove();
				} else {
					anchor.setLeashHolder(null);
				}
				anchor = null;
			}
			if (anchor_target != null) { // removes spindle metadata from the anchor target (either un-leashable entities or the arrow). 
				Metadative.remove(anchor_target, ANCHOR_TARGET);
				Metadative.remove(anchor_target, SPINDLE);
				anchor_target = null;
				target_offset = null;
			}
			// retrieves the catapult.

			if(returnToPlayer){ // tp to player and removes it afterward
				catapult.setDamage(0);
				if(p.isOnline() && !p.isDead()) catapult.teleport(p);
				
				Arrow toRemove = catapult;
				SuitUtils.runAfter(()-> {
					toRemove.remove();
					catapult = null;
				}, Math.min(cooltime/2, 8));
			}else{ // drops a new copy of arrow. 
				Location cloc = catapult.getLocation();
				catapult.remove(); 
				catapult = p.getWorld().spawn(cloc, Arrow.class);
				catapultize(catapult);
			}		
			cooldown(cooltime);				
		}
	}
	 
	public Vector updateTension(){
		Vector diff = MathUtil.disposition(anchor, p);
		double R2 = Math.pow(diff.lengthSquared(),0.75);
		double v2 = p.getVelocity().lengthSquared();
		if(v2<=1) v2++;
		// 10^1.5 = 31.6227766
		double centrip = 0.65*Math.sqrt(v2)/Math.max(R2, 31.6227766);		
		tension = diff.multiply(centrip); 
		return tension;
	}
		
	public void integrate(){		
		anchor.teleport(anchor_target.getLocation().add(target_offset));
	}
			
	public boolean pseudoAnchored(){
		return hasAnchored() && isPseudoAnchor(anchor);
	}
	
	public boolean hasAnchored(){
		return anchor!=null;
	}
	
	public boolean hasCatapulted(){
		return catapult!=null;
	}

	public Entity getAnchorTarget() {return anchor_target;}

	public String getUUID() {return uuid;}
	
	public Arrow getCatapult() {return catapult;}
	
	public LivingEntity getAnchor() {return anchor;}
	
	public Vector getTension() {return tension;}

	public Player getPlayer() {return p;}

	public float getGas() {return gas;}

	public void setGas(float gas) {this.gas = MathUtil.bound(0, gas, 100);}
	
	public boolean sufficeGas(float delta) {
		double prev = gas;
		setGas(gas + delta);
		return prev == gas;
	}
	
	public boolean outOfGas() {
		return gas <= 0;
	}
}
