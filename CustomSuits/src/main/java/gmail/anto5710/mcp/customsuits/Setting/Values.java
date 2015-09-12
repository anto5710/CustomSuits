package gmail.anto5710.mcp.customsuits.Setting;

import java.util.ArrayList;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Ghast;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
/**
 * The Values Of This Plugin
 * 
 * @author anto5710
 *
 */
public class Values {
	
	
	
	
   

	final public static double HammerDamage = 20;
	final public static double LightningMissile = 20 ;
	final public static int LightningMissileHunger = -1;
	final public static double HammerExplosionRing = 20;
	final public static float HammerExplosionPower = 5;
	final public static int HammerExplosionRingHunger = -4;
	final public static Sound ThorChangeSound = Sound.ENDERMAN_STARE;
	
	final public static Material SuitLauncher = Material.NETHER_STAR;
	final public static Sound SuitSound = Sound.ENDERDRAGON_DEATH;
	final public static String ZoomOnMessage = ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "Zoom in ("
			+ ChatColor.RED + "On" + ChatColor.AQUA
			+ ")";
	final public static String ZoomOutMessage = ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "Zoom in ("
			+ ChatColor.RED + "Out" + ChatColor.AQUA
			+ ")";
	
	final public static double SniperDamage =30 ;
	final public static double SniperRadius = 0.75;
	final public static int SniperEffectRadius =2 ;
	final public static int SniperEffectAmount =8 ;
	final public static int SnipeAmmoAmount =8 ;
	final public static Material SniperAmmo = Material.GHAST_TEAR;
	
	final public static String regex = ChatColor.GOLD+" 【《 》】 "+ChatColor.YELLOW;
	
	final public static double MachineGunDamage =6 ;
	final public static int MachineGunAmmoAmount = 50;
	final public static Material MachineGunAmmo = Material.FLINT;
	
	final public static double Bim = 35;
	final public static float BimExplosionPower =4F ;
	final public static int BimEffectRadius = 2;
	final public static int BimEffectAmount =40 ;
	final public static double BimRadius =2 ;
	final public static int BimHunger =-2 ;
	final public static String BimMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Bim!";
	final public static Sound BimSound = Sound.BLAZE_BREATH;
	
	final public static Sound SuitShieldSound = Sound.FUSE;
	final public static int SuitShieldHunger = -18;
	
	final public static double Missile =75 ;
	final public static float MissileExplosionPower =50F ;
	final public static int MissileEffectRadius = 2;
	final public static int MissileEffectAmount =40 ;
	final public static double MissileRadius =2 ;
	final public static int MissileHunger =- 8;
	final public static String MissileMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Missile!";
	final public static Sound MissileSound = Sound.WITHER_DEATH;
	
	
	final public static float LauncherPower = 5;
	final public static Material LauncherAmmo = Material.FIREWORK_CHARGE;
	final public static Sound LauncherSound= Sound.FIREWORK_LAUNCH; 
	
	
}
