package gmail.anto5710.mcp.customsuits._Thor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEnderCrystal;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.HungerScheduler;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import gmail.anto5710.mcp.customsuits.Utils.WeaponUtils;

public class ForceLightning extends BukkitRunnable implements Listener{
	private static Map<Player, EnderCrystal> crystals = new HashMap<>();
	private static boolean running = false;
	static CustomSuitPlugin plugin;
	public ForceLightning(CustomSuitPlugin plugin) {
		running = false;
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		running = true;
		if(crystals.isEmpty()){
			running = false; cancel();  return;
		}
		Set<Player>toRemove = new HashSet<>();
		
		for(Player p : crystals.keySet()){
			if(!canUseLazer(p)){
				toRemove.add(p);
				continue;
			}
			Location ploc = p.getLocation();
			SuitUtils.playSound(ploc, Sound.ENTITY_BLAZE_AMBIENT, 1F, 3F);
//			SuitUtils.playSound(ploc, Sound.ENTITY_ENDER_DRAGON_SHOOT, 4F, 3F);
			SuitUtils.playSound(ploc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.2F, 1F);
			new BlockIterator(p.getWorld(), p.getEyeLocation().toVector(), ploc.getDirection(), 0, 100).forEachRemaining(b->{
				Location curloc = b.getLocation();
				WeaponUtils.damageNeffect(curloc, 6, p, false, false, 1);
			});;
			ThorUtils.strikeLightnings(SuitUtils.getTargetBlock(p, 100).getLocation(), p, 2);
		}
		toRemove.forEach(p->stopLazer(p));
	}
	
	@EventHandler
	public void adjustTrajectory(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(crystals.containsKey(p)){
			EnderCrystal cr = crystals.get(p);
			if(cr!=null){
				adjustTarget(p, cr);
				synchronizeLoc(p, cr);
			}
		}
	}
	
	@EventHandler
	public void toggleLazer(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		if(!canUseLazer(p)) return;
		
		if(e.isSneaking()){
			if(!isUsingLazer(p) && HungerScheduler.sufficeHunger(p, Values.LightningMissileHunger)) startLazer(p);
		}else{
			stopLazer(p);
		}
	}
	
	private boolean canUseLazer(Player p){
		return Hammer.isThor(p) && Hammer.isPractiallyThor(p) && ThorUtils.isHammerinHand(p);
	}
	
	private boolean isUsingLazer(Player p){
		return crystals.get(p) !=null;
	}
	
	private void synchronizeLoc(Player p, EnderCrystal cr){
		if(!SuitUtils.anyNull(cr, p)){
			cr.teleport(p.getEyeLocation());
		}
	}
	
	private void adjustTarget(Player p, EnderCrystal cr){
		Location target = SuitUtils.getTargetBlock(p, 100).getLocation().add(0,-1,0);
		cr.setBeamTarget(target);
	}
	
	private void startLazer(Player p){
		EnderCrystal cr = p.getWorld().spawn(p.getEyeLocation(), EnderCrystal.class);
		
		((CraftEnderCrystal)cr).getHandle().setInvisible(true);
		cr.setGravity(false);
		cr.setShowingBottom(false);
		cr.setInvulnerable(true);
		crystals.put(p, cr);
		SuitUtils.playSound(p.getLocation(), Sound.ENTITY_MAGMA_CUBE_DEATH, 1F, 3F);
		SuitUtils.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2F, 1F);
		
		if(!running){
			new ForceLightning(plugin).runTaskTimer(plugin, 0, 10);
		}
	}
	
	private void stopLazer(Player p){
		EnderCrystal cr = crystals.get(p);
		if(cr!=null){
			crystals.remove(p);
			cr.remove();
		}
	}

	private void reset(){
		for(Player p: crystals.keySet()){
			stopLazer(p);
		}
//		if(!isCancelled()) cancel();
	}
	
	@EventHandler
	public void whenServerClose(PluginDisableEvent e){
		reset();
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e){
		stopLazer(e.getPlayer());
	}
}
