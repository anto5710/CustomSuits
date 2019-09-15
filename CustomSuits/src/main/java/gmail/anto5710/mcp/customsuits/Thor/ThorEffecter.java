package gmail.anto5710.mcp.customsuits.Thor;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.PotionEffects;
import gmail.anto5710.mcp.customsuits.Utils.ParticleUtil;
import gmail.anto5710.mcp.customsuits.Utils.PotionBrewer;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;


public class ThorEffecter extends MapEncompassor<Player, VorticalMeta>{	
	private final double radius = 1;
	public ThorEffecter(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}

	@Override
	public void register(Player player) {
		super.register(player);
	}
	
	@Override
	public void remove(Player player) {
		removeThorPotionEffects(player);
		player.setAllowFlight(player.getGameMode()==GameMode.CREATIVE);
		super.remove(player);
	}
	
	@Override
	public boolean toRemove(Player player) {
		return !Hammer.isPractiallyThor(player) || !player.isOnline();
	}

	@Override
	public void particulate(Player player, VorticalMeta v) {
		if(!toRemove(player)){
			addThorPotionEffects(player);
			player.setAllowFlight(true);
			
			vorticalize(player, v);
		}
	}
	
	private void vorticalize(Player player, VorticalMeta v){
		Location origin = player.getLocation();
		Location loci = origin.clone();
		double y = loci.getY() + v.getY_Offset();
		v.addTheta(0.2);

		Vector dv = v.dLinear();
		loci.add(dv);
		loci.setY(y);
		ParticleUtil.playEffect(Particle.FLAME, loci, 5);

		// invert
		Location locii = origin.clone();
		locii.subtract(dv);
		locii.setY(y);
		ParticleUtil.playEffect(Particle.FLAME, locii, 5);
		v.addY_Offset(0.1);
	}

	@Override
	public VorticalMeta defaultVal(Player e) {
		return new VorticalMeta(radius);
	}

	private void addThorPotionEffects(Player player) {
		PotionBrewer.addPotions(player, PotionEffects.Thor_FAST_DIGGING, PotionEffects.Thor_FIRE_RESISTANCE,
				PotionEffects.Thor_HEALTH_BOOST, PotionEffects.Thor_INCREASE_DAMAGE, PotionEffects.Thor_JUMP,
				PotionEffects.Thor_REGENERATION, PotionEffects.Thor_SPEED, PotionEffects.Thor_WATER_BREATHING, PotionEffects.Thor_ANTIDAMAGE);
	}

	private void removeThorPotionEffects(Player player) {
		PotionBrewer.removePotionEffects(player, PotionEffects.Thor_FAST_DIGGING, PotionEffects.Thor_FIRE_RESISTANCE,
				PotionEffects.Thor_HEALTH_BOOST, PotionEffects.Thor_INCREASE_DAMAGE, PotionEffects.Thor_JUMP,
				PotionEffects.Thor_REGENERATION, PotionEffects.Thor_SPEED, PotionEffects.Thor_WATER_BREATHING, PotionEffects.Thor_ANTIDAMAGE);
	}
}
