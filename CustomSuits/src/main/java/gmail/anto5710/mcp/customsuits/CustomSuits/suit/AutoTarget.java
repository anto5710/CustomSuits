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
		Entity t = event.getTarget();
		Entity e = event.getEntity();
		if (t instanceof Player && CustomSuitPlugin.dao.isCreatedBy(e, (Player) t)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void ignoreFriendlyFire(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			if (CustomSuitPlugin.isCreatedBy(entity, player)) {
				
				SuitIUISetting hdle = CustomSuitPlugin.handle(player);
				if (hdle.isTargetting(entity)) {
					LivingEntity target = hdle.getCurrentTarget();
					((Mob)entity).setTarget(target);
				}
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void damageByProjectile(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Projectile) {
			if (event.getEntity() instanceof Player) {
				Projectile projectile = (Projectile) event.getDamager();
				Player player = (Player) event.getEntity();
				if (projectile.getShooter() == null) {
					return;
				}

				if (projectile.getShooter() instanceof LivingEntity) {
					LivingEntity lentity = (LivingEntity) projectile.getShooter();
					CustomSuitPlugin.handle(player).putTarget(lentity);
				}
			}
		}
	}

	@EventHandler
	public void targetToProtect(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (CustomSuitPlugin.isMarkEntity(player) && event.getDamager() instanceof LivingEntity
					&& !CustomSuitPlugin.isCreatedBy(event.getDamager(), player)) {
				
				LivingEntity lentity = (LivingEntity) event.getDamager();
				CustomSuitPlugin.handle(player).putTarget(lentity);
			}
		}
	}

	@EventHandler
	public void cooperate(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (CustomSuitPlugin.isMarkEntity(player) && !CustomSuitPlugin.isCreatedBy(event.getEntity(), player) && 
					event.getEntity() instanceof LivingEntity) {
				LivingEntity lentity = (LivingEntity) event.getEntity();
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
		if(ultDamager==ultVictim) return; //friendlyfire
		
		//TODO player 가 슈트 있는 지 없는지 체크 하고 없으면 oytTarget X X
		if(ultDamager!=null && victim instanceof LivingEntity && victim != ultDamager){
			CustomSuitPlugin.handle(ultDamager).putTarget((LivingEntity) victim);
		}
		if(ultVictim!=null && damager instanceof LivingEntity && damager != ultVictim){
			CustomSuitPlugin.handle(ultVictim).putTarget((LivingEntity) damager);
		}
	}
}
