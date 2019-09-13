package gmail.anto5710.mcp.customsuits.Thor;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.BlockIterator;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageAttribute;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageUtil;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.MapEncompassor;

public class ForceLightning extends MapEncompassor<Player, EnderCrystal>{

	public ForceLightning(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
	}
		
	@EventHandler
	public void toggleLazer(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		if(!canUseLazer(p)) return;
		
		if(e.isSneaking()){
			if(!isRegistered(p) && HungerScheduler.sufficeHunger(p, Values.LightningMissileHunger)) startLazer(p);
		}else{
			remove(p);
		}
	}
	
	@Override
	public void particulate(Player p, EnderCrystal cr) {
		if(!toRemove(p)){
			Location ploc = p.getEyeLocation();
			SuitUtils.playSound(ploc, Sound.ENTITY_BLAZE_AMBIENT, 1F, 3F);
			SuitUtils.playSound(ploc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.2F, 1F);
			
			adjustTarget(p, cr);
			synchronizeLoc(p, cr);	
			
			new BlockIterator(p.getWorld(), p.getEyeLocation().toVector(), ploc.getDirection(), 0, 100).forEachRemaining(b->{
				Location curloc = b.getLocation();
				DamageUtil.areaDamage(curloc, Values.ThorForceLightningDamage, p, 1, DamageAttribute.X_TEN);
			});
			ThorUtils.strikeLightnings(SuitUtils.getTargetLoc(p, 100), p, 2);
		}
	}
	
	@Override
	public void remove(Player e) {
		EnderCrystal cr = get(e);
		super.remove(e);
		if(cr!=null) cr.remove();
	}
	
	@Override
	public boolean toRemove(Player player) {
		return !canUseLazer(player) || player.isDead() || !player.isOnline();
	}

	private boolean canUseLazer(Player p){
		return Hammer.isThor(p) && Hammer.isPractiallyThor(p) && ThorUtils.isHammerinHand(p);
	}
		
	private void synchronizeLoc(Player p, EnderCrystal cr){
		if(!SuitUtils.anyNull(cr, p)){
			cr.teleport(p.getEyeLocation());
		}
	}
	
	private void adjustTarget(Player p, EnderCrystal cr){
		Location target = SuitUtils.getTargetLoc(p, 100).add(0,-2,0);
		cr.setBeamTarget(target);
	}
	
	private void startLazer(Player p){
		register(p);
		SuitUtils.playSound(p.getLocation(), Sound.ENTITY_MAGMA_CUBE_DEATH, 1F, 3F);
		SuitUtils.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2F, 1F);
	}
	
	@Override
	public EnderCrystal defaultVal(Player p) {
		EnderCrystal cr = p.getWorld().spawn(p.getEyeLocation(), EnderCrystal.class);
		cr.setGravity(false);
		cr.setShowingBottom(false);
		cr.setInvulnerable(true);		
		return cr;
	}
}
