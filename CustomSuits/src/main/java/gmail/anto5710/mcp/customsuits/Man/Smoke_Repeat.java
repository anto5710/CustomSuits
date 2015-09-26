package gmail.anto5710.mcp.customsuits.Man;

import java.util.HashMap;
import java.util.Iterator;

import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;

import org.bukkit.Effect;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class Smoke_Repeat extends BukkitRunnable{
	CustomSuitPlugin plugin;
	static HashMap<Item, Long> SmokeCount = new HashMap<>();
	public Smoke_Repeat(CustomSuitPlugin plugin){
		this.plugin =plugin;
	}
	public void remove(Item item){
		SmokeCount.remove(item);
	}
	@Override
	public void run() throws IllegalStateException{
		if(SmokeCount.size() == 0){
			try {
				
				ThorUtils.cancel(getTaskId());
			} catch (IllegalStateException e) {
				
			}
		}
		if(SmokeCount.size() ==1){
			Iterator<Item>iterator = SmokeCount.keySet().iterator();
			Item smoke = iterator.next();
			if(SmokeCount.get(smoke)>=Values.ManSmoke_Time*20){
				smoke.remove();
				remove(smoke);
			}
			if(SmokeCount.size() != 0)	
			{
			SmokeCount.put(smoke, SmokeCount.get(smoke)+5);
			
			}
			smoke.setPickupDelay(20);
			smoke.setFireTicks(0);
			SuitUtils.playEffect(smoke.getLocation(), Effect.EXPLOSION_HUGE, 35, 0, 50);
		}else{
		for(Item smoke : SmokeCount.keySet()){
			if(SmokeCount.size() == 0){
				SmokeCount.put(smoke, (long) 0);
			}else{
				SmokeCount.put(smoke, SmokeCount.get(smoke)+5);
				
			}
			if(SmokeCount.get(smoke)>=Values.ManSmoke_Time*20){
				smoke.remove();
				remove(smoke);
			}
			smoke.setPickupDelay(20);
			smoke.setFireTicks(0);
			SuitUtils.playEffect(smoke.getLocation(), Effect.EXPLOSION_HUGE, 35, 0, 50);
		}
	}
	}
}
