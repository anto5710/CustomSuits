package gmail.anto5710.mcp.customsuits.CustomSuits.suit.weapons;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.standardized.DispenseEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.items.Enchant;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.particles.ParticleUtil;

public class TNTLauncher extends DispenseEncompassor<Item, Long>{
	private Set<Player> TNT_cooldowns = new HashSet<>();
	private static ItemStack tntStack;
	
	public TNTLauncher(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		tntStack = ItemUtil.createWithName(Material.TNT, ChatColor.AQUA + "[Bomb]");
		Enchant.englow(tntStack);
	}

	private final float TNT_strength = 3;
	
	public boolean inTNTcooldown(Player player){
		return TNT_cooldowns != null && TNT_cooldowns.contains(player);
	}
	
	public void throwTNT(Player player, int amount) {
		TNT_cooldowns.add(player);
		new BukkitRunnable() {
			int count = 0;
			@Override
			public void run() {
				if (count >= amount) {
					TNT_cooldowns.remove(player);
					SuitUtils.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 10F, 0F);
					this.cancel(); return;
				}
				shootTNT(player, player.getEyeLocation());
				SuitUtils.playSound(player, Sound.BLOCK_DISPENSER_FAIL, 7F, 1F);
				SuitUtils.playSound(player, Sound.BLOCK_IRON_DOOR_OPEN, 3F, 1F);
				count++;
			}
		}.runTaskTimer(plugin, 0, 4);
	}
	
	private void shootTNT(Player player, Location loc){
		Vector v = player.getLocation().getDirection().multiply(TNT_strength).add(MathUtil.randomVector(0.5));
	
		Item tnt = player.getWorld().dropItem(loc, tntStack);
		tnt.setFallDistance(0);
		tnt.setVelocity(v);
		tnt.setPickupDelay(20);
		register(tnt);
	}
	
	private void impact(Location loc){
		SuitUtils.createExplosion(loc, 6F, true, true);
	}
	
	@EventHandler
	public void cancelPickUpTNT(EntityPickupItemEvent event) {
		Item item = event.getItem();
		ItemStack itemStack = item.getItemStack();
		if (itemStack.getType() == Material.TNT && ItemUtil.compareName(itemStack, ChatColor.AQUA + "[Bomb]")) {
			impact(item.getLocation());
		}
	}

	@Override
	public void particulate(Item tnt, Long t) {
		Location loc = tnt.getLocation();
		ParticleUtil.playEffect(Particle.EXPLOSION_NORMAL, loc, 1);
		if (t%5 == 0) {
			SuitUtils.playSound(loc, Sound.ENTITY_CREEPER_HURT, 0.2F, 0.6F);
			SuitUtils.playSound(loc, Sound.ENTITY_TNT_PRIMED, 1F, 1F);
		}
		if (toRemove(tnt)) {
			impact(loc);
		}
		put(tnt, t+1);
	}

	@Override
	public Long defaultVal(Item tnt) {
		return 0L;
	}
}
