package gmail.anto5710.mcp.customsuits.Utils.metadative;

import javax.annotation.Nonnull;

import org.bukkit.entity.Explosive;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageControl;

public class Metadative {
	private static CustomSuitPlugin plugin;
	
	public Metadative(CustomSuitPlugin plugin){
		Metadative.plugin = plugin;
	}
	
	public static void imprint(@Nonnull Projectile prj, double damage, float yield, boolean fire, boolean destroy) {
		Metadative.imprint(prj, DamageControl.DAMAGE, damage);

		if (yield > 0) {
			Metadative.imprint(prj, DamageControl.EXPLOSIVE, yield);
			
			if(prj instanceof Explosive){
				setExplosive((Explosive)prj, yield, fire);
			}else{
				if (!fire) {
					Metadative.imprint(prj, DamageControl.NONFIRE, fire);
				}
				if (!destroy) {
					Metadative.imprint(prj, DamageControl.NONDESTROY, destroy);
				}
			}
		}
	}
	
	public static void imprint(@Nonnull Projectile prj, double damage){
		imprint(prj, damage, 0, false, false);
	}
	
	public static void imprint(Metadatable e, String key, Object val){
		if(SuitUtils.anyNull(e, key)) return;
		
		e.setMetadata(key, new FixedMetadataValue(plugin, val));
	}
	
	public static String excavatext(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asString();
	}
	
	public static double excavate(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asDouble();
	}
	
	public static float excafate(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asFloat();
	}
	
	public static int excavint(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asInt();
	}
	
	public static boolean excavatruth(Metadatable e, String key) {
		return e.hasMetadata(key) && e.getMetadata(key).get(0).asBoolean();
	}

	public static void setExplosive(Explosive e, float yield, boolean fire){
		e.setYield(yield);
		e.setIsIncendiary(fire);
	}
}
