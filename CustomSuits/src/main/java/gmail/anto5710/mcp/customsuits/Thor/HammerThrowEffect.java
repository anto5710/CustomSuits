package gmail.anto5710.mcp.customsuits.Thor;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.CustomEffects;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public class HammerThrowEffect extends MapEncompassor<Item, Player>{

	public HammerThrowEffect(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
	
	@Override
	public boolean toRemove(Item item) {
		return ThorUtils.isOnGround(item)|| item.isDead();
	}

	@Override
	public void particulate(Item item, Player player) {
		item.setFireTicks(0);
		item.setPickupDelay(1);
		Location loc = item.getLocation();

		ParticleUtil.playEffect(Values.HammerDefaultEffect, loc, 5, 0.1);
		List<Entity> list = WeaponUtils.findEntities(loc, player, 1);

		ThorUtils.damage(list, Hammer.HammerDeafultDamage, player);
		
		if(toRemove(item)) impact(item);
	}
	
	private void impact(Item item){
		Player player = get(item);
		CustomEffects.playHammerHitGround(item);
		item.getWorld().strikeLightning(item.getLocation());
		player.getInventory().addItem(item.getItemStack());
		item.remove();

		Location loc = item.getLocation();
		SuitUtils.createExplosion(loc, 6F, false, false);
		explosionEffect(loc);
	}

	@Override
	public Player defaultVal(Item item) {
		return null;
	}
	
	/**
	 * Plays Explosion Effect in targeted Location
	 * @param location Center
	 */
	public static void explosionEffect(Location loc) {
		boolean hollow = false;
		boolean sphere = false;
		int r = 3;
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + 2); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {

						Location l = new Location(loc.getWorld(), x, y - 2, z);
						if(!SuitUtils.isUnbreakable(l.getBlock())){
							float sx = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
							float sy = (float) (2 / l.distance(loc) + MathUtil.randomRadius(0.025));
							float sz = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
							spawnFallingBlock(l.getBlock(), new Vector(sx * 0.4, sy * 0.4, sz));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Spawns Falling block with Velocity
	 * @param block Target Block
	 * @param vector Vector to add
	 */
	private static void spawnFallingBlock(Block block, Vector vector) {
		World world = block.getWorld();
		Location loc = block.getLocation();
		BlockData data = block.getBlockData();
		block.breakNaturally();
		FallingBlock fallingBlock = world.spawnFallingBlock(loc, data);
		fallingBlock.setVelocity(vector);
	}
}
