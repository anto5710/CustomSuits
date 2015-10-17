package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.html.HTMLDocument.Iterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList.Itr;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Control extends BukkitRunnable{
	CustomSuitPlugin plugin;
	static SpawningDao dao;
	static int count = 0;
	static ArrayList<String>removed = new ArrayList<>();
	static java.util.Iterator<String>iterator = SpawningDao.spawnMap.keySet().iterator();
	public Control(CustomSuitPlugin plugin , SpawningDao dao){
		this.plugin = plugin;
		this.dao = dao;
	}
	@Override
	public void run(){
	    iterator = SpawningDao.spawnMap.keySet().iterator();
		if(SpawningDao.spawnMap.isEmpty()){
			count = 0;
			this.cancel();
		}
		while(iterator.hasNext()){
			
			String uuid = iterator.next();
			run(uuid);
		
			
		
		}
		count ++;
		for(String uuid: removed){
		dao.remove(uuid);
		}
		removed.clear();
	}
	private void run(String uuid) {
		Entity entity = getEntity(uuid);
	
		if(entity ==null){
			removed.add(uuid);
			iterator.remove();
		}else
			if(entity.isDead()){
				removed.add(uuid);
				iterator.remove();
			
		}
		
	if(entity != null){
		if(!entity.isDead()){
		if(Arrays.asList(EntityType.SKELETON , EntityType.ZOMBIE , EntityType.PIG_ZOMBIE ).contains(entity.getType())){
			PlayEffect.playSuit_Move_Effect(entity.getLocation(), entity);
			
			if(count%40==0){
				if(canLaunch(entity)){
					Creature creature  = (Creature)entity;
					entity.getWorld().playSound(entity.getLocation(), Values.BimSound, 4F, 4F);
					SuitUtils.LineParticle(creature.getTarget().getLocation(), ((LivingEntity)entity).getEyeLocation(), entity,EnumParticle.SPELL_MOB, 1,  0, Values.Bim, 1.5, true, true, false);
				}
		}
		}else{
		Location loc = entity.getLocation();
		PlayEffect.play_Arc_Reactor(loc);
		}
		}
	}
	}
	private boolean canLaunch(Entity entity) {
		if (entity instanceof Creature) {
			Creature creature = (Creature) entity;
			if (creature.getTarget() != null) {
				if (!creature.getTarget().isDead()) {
					if (SuitUtils.distance(creature.getLocation(),
							creature.getTarget(), 50, 1)) {
						if (creature.getTarget() instanceof Player) {
							Player player = (Player) creature.getTarget();
							if (!player.isOnline()) {
								return false;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	private Entity getEntity(String uuid) {
		for(World world : Bukkit.getServer().getWorlds()){
			for(Entity entity: world.getEntities()){
				if(entity.getUniqueId().toString().equals(uuid)){
					return entity;
				}
			}
		}
		return null;
	}
}
