package gmail.anto5710.mcp.customsuits.Setting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
	
	
	final public static Effect ManBoostEffect = Effect.COLOURED_DUST;
	
	final public static Sound ManInvisibleSound = Sound.SHOOT_ARROW;
	final public static Sound ManvisibleSound = Sound.IRONGOLEM_DEATH;
	final public static EnumParticle ManInvisibleEffect = EnumParticle.SMOKE_NORMAL;
	final public static Effect ManInvisibleMoveEffect = Effect.SMALL_SMOKE;


	final public static int ManInvisibleHunger = -1;
	final public static int ManBoostHunger = -10;
	final public static double ManDeafultDamage = 8;
	final public static long ManHungerDealy = 60;
	final public static Sound ManHitGroundSound = Sound.GHAST_FIREBALL;
	final public static double ManHitGroundDamage = 35;
	final public static int ManHitGroundHunger = -5;
	
	final public static int ManSwordShotHunger = -2;
	final public static double ManSwordShotDamage = 20;
	final public static double ManSwordShotradius = 2.5;
	
	final public static Sound ManSwordShotSound = Sound.ITEM_BREAK;
	final public static float ManSwordShotExplosionPower = 4.5F;
	

	final public static Sound ManBombSound = Sound.NOTE_STICKS;

	final public static Sound ManSmokeSound = Sound.FIZZ;
	final public static long ManSmoke_Time = 20;


	final public static double HammerDamage = 6.5;
	final public static double LightningMissile = 8 ;
	final public static int LightningMissileHunger = -1;
	final public static Sound LightningMissileSound = Sound.AMBIENCE_THUNDER;

	final public static Sound ThorChangeSound = Sound.AMBIENCE_THUNDER;
//	final public static Effect HammerDefaultEffect = EnumParticle.LAVA_POP;
//	final public static Effect HammerTeleportEffect =EnumParticle.PORTAL;
//	final public static Effect HammerBackEffect = EnumParticle.HEART;
//	final public static Effect HammerHitGround = EnumParticle.ENDER_SIGNAL;
	final public static float HammerMissileExplosion_Power = 4F;
	final public static double HammerMissileDamage_Radius = 2;
//	final public static Effect HammerPickUpCancel = EnumParticle.STEP_SOUND;
	final public static int HammerPickUpCancel_Data = Material.IRON_BLOCK.getId();
	final public static Sound HammerTeleportSound = Sound.ENDERMAN_TELEPORT;
	final public static int Thunder_Strike_Hunger = -20;
//	final public static Effect Thunder_Strike_Effect =EnumParticle.MOBSPAWNER_FLAMES;
	final public static int Thunder_Strike_Radius = 50;
	final public static double Thunder_Strike_Damage = 5.5;
	final public static int Thunder_Strike_Time = 20;
//	final public static Effect Thunder_Strike_Spawn_Effect = EnumParticle.SMOKE;
	final public static Sound Thunder_Strike_Spawn_Sound = Sound.IRONGOLEM_DEATH;
	final public static Sound Thunder_Strike_Start_Sound = Sound.ENDERMAN_STARE;
//	final public static Effect Thunder_Strike_Start_Effect =EnumParticle.MOBSPAWNER_FLAMES;	
	
	final public static List<EntityType> Allowed_Suit_Summon_types =  Arrays.asList(EntityType.ZOMBIE , EntityType.SKELETON , EntityType.PIG_ZOMBIE);
	
	
	final public static Material Suit_Spawn_Material = Material.IRON_INGOT;
	final public static Material SuitLauncher = Material.AIR;
	


	

	final public static int SuitGetEffectData = Material.COBBLESTONE.getId();
	
	final public static Sound SuitSound = Sound.ENDERDRAGON_DEATH;
	final public static Sound SuitSneakSound = Sound.WITHER_SPAWN;
	final public static String SuitCallMessage =ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "You called an armor";
	final public static String CantFindEntityType = "Can't find that EntityType,   use '/clist entity'  to get list of EntityType";
	
	final public static String ZoomOnMessage = ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "Zoom in ("
			+ ChatColor.RED + "On" + ChatColor.AQUA
			+ ")";
	final public static String ZoomOutMessage = ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "Zoom in ("
			+ ChatColor.RED + "Out" + ChatColor.AQUA
			+ ")";
	final public static String NoSuchEntity =ChatColor.BLUE + "[Info]: "
			+ ChatColor.AQUA + "No such entity";
	final public static int SuitFlyDisableWhen = 2;
	final public static int SuitEnoughFly = 10;
	final public static String FlyEnergyWarn ="FlySpeed Decreasing";
	final public static int SuitFlyHunger = -2;
	final public static int SuitHungerRelod = 1;
	final public static long SuitHungerDelay = 60;
	   
	final public static int spawnSuit_max_target_distance = 200;
		   
	
	final public static double SniperDamage =25 ;
	final public static double SniperRadius = 0.75;
	final public static int SniperEffectRadius =2 ;
	final public static int SniperEffectAmount =3 ;
	final public static int SnipeAmmoAmount =8 ;
	final public static Material SniperAmmo = Material.GHAST_TEAR;
	final public static EnumParticle SniperEffect = EnumParticle.CRIT;
	
	final public static int Suit_Gun_Shot_Radius =200;
	final public static int Suit_Gun_Shot_Effect_Data =Material.STONE.getId();
	
	final public static String regex = ChatColor.GOLD+" 【《 》】 "+ChatColor.YELLOW;
	final public static String SuitInforegex = ChatColor.DARK_AQUA+":"+ChatColor.AQUA;
	final public static String SuitName = "[Mark]";
	
	
	final public static List<Material> IgnoreMaterials_Gun = Arrays.asList(Material.AIR , Material.LAVA,  Material.STATIONARY_LAVA , Material.WATER , Material.STATIONARY_WATER , Material.OBSIDIAN , Material.BEDROCK , Material.BEACON);
	
	
	final public static double MachineGunDamage =5 ;
	final public static double MachineGunDamageRadiues =1.5 ;
	final public static int MachineGunAmmoAmount = 50;
	final public static Material MachineGunAmmo = Material.FLINT;
	
	final public static double Bim = 7.5;
	final public static float BimExplosionPower =4F ;
	final public static int BimEffectAmount =5 ;
	final public static double BimRadius =2 ;
	final public static int BimHunger =-2 ;
	final public static String BimMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Bim!";
	final public static Sound BimSound = Sound.BLAZE_BREATH;
	final public static EnumParticle SuitProjectileEffect = EnumParticle.SPELL_MOB;
	final public static int SuitBim_MissileEffectData = Material.DIAMOND_BLOCK.getId();
	
	
	
	final public static Sound SuitShieldSound = Sound.FUSE;
	final public static int SuitShieldHunger = -20;
	
	final public static double Missile =10 ;
	final public static float MissileExplosionPower =30F ;
	final public static int MissileEffectAmount =10 ;
	final public static double MissileRadius =2 ;
	final public static int MissileHunger =- 8;
	final public static String MissileMessage =  ChatColor.BLUE + "[Info]: " + ChatColor.AQUA
			+ "Fired a Repulser Missile!";
	final public static Sound MissileSound = Sound.WITHER_DEATH;
	
	
	final public static float LauncherPower = 5;
	final public static Material LauncherAmmo = Material.FIREWORK_CHARGE;
	final public static Sound LauncherSound= Sound.FIREWORK_LAUNCH; 
	
	final public static int Explode_Falling_Block_Count_Divider = 4;

	final public static double HammerExplosionDamageRadius = 2;

	final public static EnumParticle HammerBackEffect = EnumParticle.HEART;

	final public static EnumParticle HammerPickUpCancel = EnumParticle.HEART;

	final public static EnumParticle HammerDefaultEffect = EnumParticle.CRIT;

		
	
}
