package gmail.anto5710.mcp.customsuits.Setting;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;

public class Values {
	CustomSuitPlugin plugin ;
	public static String regex = ChatColor.GOLD+" 【《 》】 "+ChatColor.YELLOW;
	public static double HammerDamage = 20;
	public static double LightningMissile = 20 ;
	public static double HammerExplosionRing = 20;
	public static float HammerExplosionPower = 5;
	
	public static double SniperDamage =30 ;
	public static double SniperRadius = 0.75;
	public static int SniperEffectRadius =2 ;
	public static int SniperEffectAmount =8 ;
	public static int SnipeAmmoAmount =8 ;
	
	
	public static double MachineGunDamage =6 ;
	public static int MachineGunAmmoAmount = 50;
	
	public static double Bim = 35;
	public static float BimExplosionPower =4F ;
	public static int BimEffectRadius = 2;
	public static int BimEffectAmount =40 ;
	public static double BimRadius =2 ;
	public static int BimHunger =-2 ;
	public static String BimMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Bim!";
	public static Sound BimSound = Sound.BLAZE_BREATH;
	
	public static double Missile =75 ;
	public static float MissileExplosionPower =50F ;
	public static int MissileEffectRadius = 2;
	public static int MissileEffectAmount =40 ;
	public static double MissileRadius =2 ;
	public static int MissileHunger =- 8;
	public static String MissileMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Missile!";
	public static Sound MissileSound = Sound.WITHER_DEATH;
	
	
	public static float LauncherPower = 5;
	
	public Values(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
}
