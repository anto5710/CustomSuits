package gmail.anto5710.mcp.customsuits.Setting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
/**
 * The Values Of This Plugin
 * 
 * @author anto5710
 *
 */
public class Values {
	
	
	final public static Effect ManBoostEffect = Effect.SMOKE; // TODO to be fixed
	
	final public static Sound ManInvisibleSound = Sound.ENTITY_ARROW_SHOOT;
	final public static Sound ManvisibleSound = Sound.ENTITY_IRON_GOLEM_DEATH;
	final public static Particle ManInvisibleEffect = Particle.SMOKE_NORMAL;
	
	final public static Effect ManInvisibleMoveEffect = Effect.SMOKE;


	final public static int ManInvisibleHunger = -1;
	final public static int ManBoostHunger = -10;
	final public static double ManDeafultDamage = 8;
	final public static long ManHungerDealy = 60;
	final public static Sound ManHitGroundSound = Sound.ENTITY_GHAST_SHOOT;
	final public static double ManHitGroundDamage = 35;
	final public static int ManHitGroundHunger = -5;
	
	final public static int ManSwordShotHunger = -2;
	final public static double ManSwordShotDamage = 20;
	final public static double ManSwordShotradius = 2.5;
	
	final public static Sound ManSwordShotSound = Sound.ENTITY_ITEM_BREAK;
	final public static float ManSwordShotExplosionPower = 4.5F;
	

	final public static Sound ManBombSound = Sound.BLOCK_DISPENSER_FAIL;

	final public static Sound ManSmokeSound = Sound.ENTITY_GENERIC_EXTINGUISH_FIRE;
	final public static long ManSmoke_Time = 20;

	final public static Color Chestplate_Man_Color = Color.fromRGB(217, 206, 206);
	final public static Color Leggings_Man_Color = Color.fromRGB(31, 28, 28);
	
	
	
	final public static int SuitMaxLevel = 64;
	final public static Material Suit_Spawn_Material = Material.IRON_INGOT;
	final public static Material SuitLauncher = Material.AIR;
	
	final public static Sound SuitSound = Sound.ENTITY_ENDER_DRAGON_DEATH;
	final public static Sound SuitSneakSound = Sound.ENTITY_WITHER_SPAWN;
	final public static String SuitCallMessage =ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "You called an armor";
	final public static String CantFindEntityType = "Can't find that EntityType,   use '/clist entity'  to get list of EntityType";
	
	final public static String NoSuchEntity =ChatColor.BLUE + "[Info]: " + ChatColor.AQUA + "No such entity";
	final public static int leastFlyHunger = 5;
	final public static int SuitEnoughFly = 10;
	final public static String FlyEnergyWarn ="FlySpeed Decreasing";
	final public static int SuitFlyHunger = -2;
	final public static int SuitHungerRelod = 1;
	
	final public static int spawnSuit_max_target_distance = 200;
		   
	
	final public static double SniperDamage =25 ;
	final public static double SniperRadius = 0.75;
	final public static int SniperEffectRadius =2 ;
	final public static int SniperEffectAmount =1 ;
	final public static int SnipeAmmoAmount =8 ;
	final public static Material SniperAmmo = Material.GHAST_TEAR;
	final public static Particle SniperEffect = Particle.CRIT;
	
	final public static int Suit_Gun_Shot_Radius =500;
	final public static BlockData Suit_Gun_Shot_Effect_Data = Material.ANVIL.createBlockData();
	
	final public static String gun_regex = ChatColor.YELLOW+"» ∞ «";
	final public static String SuitInforegex = ChatColor.DARK_AQUA+":";
	final public static String SuitName = "Mark";
	
	final public static String MachineGunName = "Knif-1220";
	final public static double MachineGunDamage =7 ;
	final public static double MachineGunDamageRadiues = 1.5;
	final public static int MachineGunAmmoAmount = 50;
	final public static Material MachineGunAmmo = Material.FLINT;
	
	final public static double Bim = 8;
	final public static float BimExplosionPower =2.7F ;
	final public static int BimEffectAmount =5 ;
	final public static double BimRadius =1 ;
	final public static int BimHunger =-2 ;
	final public static String BimMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Bim!";
	final public static Sound BimSound = Sound.ENTITY_BLAZE_AMBIENT;
	
	final public static Sound SuitShieldSound = Sound.ITEM_FIRECHARGE_USE;
	final public static int SuitShieldHunger = -20;
	
	
	
	final public static float LauncherPower = 5;
	final public static Material LauncherAmmo = Material.FIREWORK_STAR;
	final public static Sound LauncherSound= Sound.ENTITY_FIREWORK_ROCKET_LAUNCH; 
	
	final public static int Explode_Falling_Block_Count_Divider = 3;

	
	
	final public static double HammerDamage = 10;
	final public static double HammerArmor = 2;
	final public static double HammerPrompt = 1.10;
	final public static double LightningMissile = 10D ;
	final public static int LightningMissileHunger = -1;
	final public static Sound LightningMissileSound = Sound.ENTITY_LIGHTNING_BOLT_THUNDER;
	
	
	final public static Sound ThorChangeSound = Sound.ENTITY_LIGHTNING_BOLT_THUNDER;

	final public static Sound HammerTeleportSound = Sound.ENTITY_ENDERMAN_TELEPORT;
	final public static int Thunder_Creeper_Hunger = -15;

	final public static Sound Thunder_Creeper_Start_Sound = Sound.ENTITY_ENDERMAN_STARE;
	
	final public static double HammerExplosionDamageRadius = 2;

	final public static Particle HammerReturnEffect = Particle.NAUTILUS;

	final public static Particle HammerPickUpCancel = Particle.BARRIER;

	final public static Particle HammerDefaultEffect = Particle.CRIT;

	final public static double ThorForceLightningDamage = 6;
		
	final public static Color ThorChestplateColor = Color.fromRGB(35, 35, 35); 
	final public static Color ThorLeggingsColor = Color.fromRGB(0, 0, 65);
		
	final public static float GearGas_Catapult = -0.05F;
	final public static float GearGas_Pull = -0.01F;
	final public static float GearGas_Shift = -0.04F;
	
}
