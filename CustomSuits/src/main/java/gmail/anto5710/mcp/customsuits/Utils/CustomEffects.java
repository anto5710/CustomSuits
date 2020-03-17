package gmail.anto5710.mcp.customsuits.Utils;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;

import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Thor.Hammer;
import gmail.anto5710.mcp.customsuits.Utils.fireworks.FireworkPlay;
import gmail.anto5710.mcp.customsuits.Utils.fireworks.FireworkProccesor;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CustomEffects {
	static CustomEffects playEffect;
	static CustomSuitPlugin plugin;
	
	public CustomEffects(CustomSuitPlugin plugin){
		CustomEffects.plugin = plugin;
	}

	public static void play_Suit_Get(final Location Location, Player player) {
		new BukkitRunnable() {
			double radius = 2;
			double Y = Location.getY();
			double y = 0;

			@Override
			public void run() {
				for (int c = 0; c < 10; c++) {
					if (y > 10) {
						this.cancel();
					}
					double x = Math.sin(y * radius);
					double z = Math.cos(y * radius);
					Location loc = Location.clone();
					loc.setX(loc.getX() + x);
					loc.setY(Y + y);
					loc.setZ(loc.getZ() + z);

					play_Suit_ARC(loc);
					Vector vector = new Vector(x, 0, z).multiply(-1);
					loc.subtract(x, 0, z);
					play_Suit_ARC(loc.clone().add(vector));
					y += 0.05;
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public static void play_Suit_NoDamageTime(Player player){	
		ParticleUtil.playEffect(Particle.FIREWORKS_SPARK, player.getLocation(), 30, 3);
		SuitUtils.playSound(player, Sound.BLOCK_ANVIL_PLACE, 10, 6);
	}                             

	public static void play_GunShotEffect(Player player) {
		ParticleUtil.playBlockEffect(Particle.BLOCK_CRACK, player.getEyeLocation(), 5, Values.Suit_Gun_Shot_Effect_Data);	
	}

	/**
	 * +--+--+--+--+
	 * |  |  |  |  |
	 * +--C--C--C--+
	 * |  |  |  |  |
	 * +--+--P--C--+
	 * |  |  |  |  |
	 * +--C--+--C--+
	 * 
	 */
	public static void play_Suit_Move_Fly(Player player) {
		ParticleModeller.footstep(player, loc-> play_Suit_ARC(loc));				
	}

	public static void play_Suit_Move_UnderWater(Player player) {
		ParticleModeller.footstep(player, loc-> ParticleUtil.playEffect(Particle.WATER_BUBBLE, loc, 2));		
	}
	
	public static void play_Suit_Move_Move(Entity entity) {
		ParticleModeller.footstep(entity, loc-> play_Suit_ARC(loc));
	}
	
	public static void play_Suit_Move_Glide(Player player) {
		ParticleModeller.footstep(player, loc-> ParticleUtil.playEffect(Particle.EXPLOSION_NORMAL, loc, 2));
	}
	private static final DustOptions MAIN_ARC_COLOR =  new DustOptions(Color.fromRGB(102, 255, 255), 1);
	private static final DustOptions SUB_ARC_COLOR =  new DustOptions(Color.fromRGB(0, 153, 204), 0.6F);
	
	public static void play_Suit_ARC(Location location) {
		ParticleUtil.playDust(location, 0, 0, 0, 2, 0, MAIN_ARC_COLOR);
		ParticleUtil.playDust(location, 0, 0, 0, 2, 0, SUB_ARC_COLOR);
	}
	
	public static void play_Thor_Change(Player player) {
		Hammer.thorform.register(player);
	}

	public static void play_Thor_FormationFin(Player player) {
		FireworkEffect effect = FireworkProccesor.getEffect(Color.RED, Type.STAR);
		Location loc = player.getEyeLocation();
		FireworkPlay.spawn(loc, effect);
		player.getWorld().strikeLightningEffect(loc);
	}

	public static void play_Thor_HammerHitGround(Item item) {
		new BukkitRunnable() {
			Location loc = item.getLocation();
			double t = 0;
			int count = 0;
			double a = 0.2;

			@Override
			public void run() {
				if (count >= 500) {
					this.cancel();
				}
				for (int c = 1; c < 15; c++) {
					t += 0.01 * Math.PI;
					for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 3) {
						double x = t * Math.cos(theta) * a;
						double y = 0.65 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
						double z = t * Math.sin(theta) * a;
						loc.add(x, y, z);
						ParticleUtil.playEffect(Particle.FLAME, loc, 22);
						loc.subtract(x, y, z);
					}
					count++;
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public static void play_Rotation_Effect(double t, Location loc, Player player){
		double x,y,z;
		
		double r = 0.2;
		
		double xii = Math.sin(2 * t);
		double yii = 2 * Math.cos(t);
		double zii = Math.sin(3 * t);

		t -= Math.PI / 200;

		double xi = Math.sin(2 * t);
		double yi = 2 * Math.cos(t);
		double zi = Math.sin(3 * t);
		t += Math.PI / 200;

		Vector direction = new Vector(xii - xi, yii - yi, zii - zi);
		Location finalLoc = new Location(player.getWorld(), 0, 0, 0).setDirection(direction.normalize());
		finalLoc.setDirection(direction.normalize());

		if (t % 1.0 < 0.1) {
			for (double c = 0; c <= 2 * Math.PI; c += Math.PI / 8) {
				x = 0.2 * t;
				y = r * Math.sin(c) + 2 * Math.sin(10 * t) + 2.8;
				z = r * Math.cos(c);
				
				Vector vector = new Vector(x, y, z);
				vector = MathUtil.rotate(vector, finalLoc);
				loc.add(vector);
				ParticleUtil.playEffect(Particle.FLAME, loc, 0, 0, 0, 1, 0, null);
				if (c == 0) {
					ParticleUtil.playEffect(Particle.LAVA, loc, 1);
				}
				loc.subtract(vector);
			}
		}
	}
	
//	private static void Particletrail( final Location location,Vector direction, int r) {
//		final double max_y_offset = 0.25;
//		direction.normalize();
//		direction.multiply(0.1);
//		final Vector v = direction;
//		new BukkitRunnable() {
//			double add_y_offset = 0.02;
//			double current_y_offset = 0;
//			int count = 0;
//			Location loc = location.clone();
//			@Override
//			public void run() {
//				if(count>=40){
//					this.cancel();
//				}
//				loc.add(v);
//				loc.add(0, add_y_offset, 0);
//				current_y_offset+=add_y_offset;
//				SuitUtils.playEffect(loc, Particle.FLAME, 1, 0, 0);
//				if(current_y_offset>=max_y_offset){
//					add_y_offset*=-1;
//				}
//				else if(current_y_offset<=0){
//					add_y_offset*=-1;
//				}
//				
//				count ++;
//			}
//		}.runTaskTimer(plugin, 0, 1);
//	}

}
