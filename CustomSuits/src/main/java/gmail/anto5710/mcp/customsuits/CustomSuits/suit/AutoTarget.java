package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.settings.SuitIUISetting;
import gmail.anto5710.mcp.customsuits.Utils.damagiom.DamageUtil;

public class AutoTarget implements Listener{
	@SuppressWarnings("unused")
	private CustomSuitPlugin plugin;
	public AutoTarget(CustomSuitPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void cancelTreachery(EntityTargetEvent event) {
		Entity target = event.getTarget();
		Entity traitor = event.getEntity();
		if (target instanceof Player && CustomSuitPlugin.dao.isCreatedBy(traitor, (Player) target)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void ignoreFriendlyFire(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.isCreatedBy(damager, player)) {
				
				SuitIUISetting hdle = CustomSuitPlugin.handle(player);
				if (hdle.isTargetting(damager)) {
					LivingEntity target = hdle.getCurrentTarget();
					((Mob)damager).setTarget(target);
				}
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void damageByProjectile(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {		
			Projectile proj = (Projectile) event.getDamager();
			Player player = (Player) event.getEntity();

			if (proj.getShooter() != null && proj.getShooter() instanceof LivingEntity) {
				LivingEntity lentity = (LivingEntity) proj.getShooter();
				CustomSuitPlugin.handle(player).putTarget(lentity);
			}			
		}
	}

	@EventHandler
	public void targetToProtect(EntityDamageByEntityEvent event) {
		Entity usurper = event.getDamager(); 
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (CustomSuitPlugin.isMarkEntity(player) && usurper instanceof LivingEntity
					&& !CustomSuitPlugin.isCreatedBy(usurper, player)) {
				
				LivingEntity lentity = (LivingEntity) usurper;
				CustomSuitPlugin.handle(player).putTarget(lentity);
			}
		}
	}

	@EventHandler
	public void cooperate(EntityDamageByEntityEvent event) {
		Entity vitcim = event.getEntity();
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			if (CustomSuitPlugin.isMarkEntity(player) && 
				! CustomSuitPlugin.isCreatedBy(vitcim, player) && 
				vitcim instanceof LivingEntity) {
				LivingEntity lentity = (LivingEntity) vitcim;
				CustomSuitPlugin.handle(player).putTarget(lentity);
			}
		}
	}
	
	@EventHandler
	public void cooperateWithTeam(EntityDamageByEntityEvent event){
		Entity damager = event.getDamager(), victim = event.getEntity();
		if(event.getDamager() instanceof Projectile){
			Projectile proj = (Projectile) damager;
			Entity shooter = DamageUtil.getShooter(proj);
			if(shooter!=null) targetMutual(shooter, victim);
		}else{
			targetMutual(damager, victim);
		}
	}
	
	private Player equestLord(Entity wouldBeLord){
		return wouldBeLord instanceof Player ? (Player) wouldBeLord : CustomSuitPlugin.dao.getOwner(wouldBeLord);
	}
	
	public void targetMutual(Entity damager, Entity victim){
		Player ultDamager = equestLord(damager);
		Player ultVictim = equestLord(victim);
		if(ultDamager != ultVictim) {  //friendlyfire
			//TODO player 가 슈트 있는 지 없는지 체크 하고 없으면 oytTarget X X
			if(ultDamager!=null && victim instanceof LivingEntity && victim != ultDamager){
				CustomSuitPlugin.handle(ultDamager).putTarget((LivingEntity) victim);
			}
			if(ultVictim!=null && damager instanceof LivingEntity && damager != ultVictim){
				CustomSuitPlugin.handle(ultVictim).putTarget((LivingEntity) damager);
			}
		}
	}
}
