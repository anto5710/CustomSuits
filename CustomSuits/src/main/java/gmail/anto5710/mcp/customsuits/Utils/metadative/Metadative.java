package gmail.anto5710.mcp.customsuits.Utils.metadative;

import javax.annotation.Nonnull;

import org.bukkit.entity.Explosive;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
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
				setExplosive((Explosive) prj, yield, fire);
			}else{
				if (fire) {
					Metadative.imprint(prj, DamageControl.FIRE, fire);
				}
				if (!destroy) {
					Metadative.imprint(prj, DamageControl.DESTROY, destroy);
				}
			}
		}
	}
	
	public static void remove(@Nonnull Metadatable e, String key) {
		if(e.hasMetadata(key)) e.removeMetadata(key, plugin);
	}
	
	public static void imprint(@Nonnull Projectile prj, double damage){
		imprint(prj, damage, 0, false, false);
	}
	
	public static void imprint(Metadatable e, String key, Object val){
		if(SuitUtils.anyNull(e, key)) return;
		
		e.setMetadata(key.toString(), new FixedMetadataValue(plugin, val));
	}
	
	public static String getString(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asString();
	}
	
	public static double getDouble(Metadatable e, String explosive) {
		return e.getMetadata(explosive).get(0).asDouble();
	}
	
	public static float getFloat(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asFloat();
	}
	
	public static int getInt(Metadatable e, String key) {
		return e.getMetadata(key).get(0).asInt();
	}
	
	public static boolean getBoolean(Metadatable e, String key) {
		return e.hasMetadata(key) && e.getMetadata(key).get(0).asBoolean();
	}

	public static void setExplosive(Explosive e, float yield, boolean fire){
		e.setYield(yield);
		e.setIsIncendiary(fire);
	}
}
