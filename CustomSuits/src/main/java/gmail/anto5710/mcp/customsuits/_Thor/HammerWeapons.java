package gmail.anto5710.mcp.customsuits._Thor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.PlayerEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.suit.SchedulerHunger;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.ThorUtils;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftCreeper;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class HammerWeapons implements Listener{
	CustomSuitPlugin plugin;
	public HammerWeapons(CustomSuitPlugin plugin){
		this.plugin = plugin;
	}
	/**
	 * Launch LightningMissile if player is Thor and have enough Hunger
	 * @param event PlayerInteractEvent 
	 */
	@EventHandler
	public void Lightning(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.AIR && Hammer.Thor(player)&&
			    SchedulerHunger.hunger(player, Values.LightningMissileHunger)) { 
					launchLightningMissile(player);
			}
		}
	}
	@EventHandler
	public void thunderCreeper(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(!Hammer.Thor(player)){
			return;
		}
		if(Hammer.thor!=player){
			return;
		}
		if(!player.isSneaking()||event.getAction()!=Action.LEFT_CLICK_AIR&&event.getAction()!=Action.LEFT_CLICK_BLOCK){
			return;
		}
		if(!SchedulerHunger.hunger(player, Values.Thunder_Creeper_Hunger)){
			return;
		}
		final Location location = player.getLocation();
		player.playSound(location, Values.Thunder_Creeper_Start_Sound, 10F,1F);
		location.setDirection(new Vector(1, 0, 1));
		final World world = player.getWorld();
		final int Point = 7;
		final int Count = 20;
		new BukkitRunnable() {
			float angle = 360/Point;
			int count = 1;
			int point = 0;
			double y = 2.5;
			@Override
			public void run() {
				if(count>=Count){
					this.cancel();
				}
				for(point =0 ; point<Point; point++){
				Location loc = location.clone();
				float current_angle = angle*point;
				loc.setYaw(current_angle);
				Vector vector = loc.getDirection().normalize().multiply(1.5).setY(y);
				
				final Creeper creeper =world.spawn(location, Creeper.class);
				creeper.setPowered(true);
				PlayerEffect.addpotion(new PotionEffect(PotionEffectType.JUMP,100000, 10), creeper);
				PlayerEffect.addpotion(new PotionEffect(PotionEffectType.INVISIBILITY,100000, 1), creeper);
				creeper.setVelocity(vector);
				
				BukkitRunnable runnable = new BukkitRunnable() {
					
					HashMap<Creeper, Integer>tnts = new HashMap<>();
					@Override
					public void run() {
						
						Iterator<Creeper>iterator = tnts.keySet().iterator();
						while(iterator.hasNext()){
							Creeper creeper = iterator.next();
							int ticks = tnts.get(creeper);
							if (creeper.isOnGround() || creeper.isDead()) {
								spawnTNT(creeper);
								this.cancel();
							}
							if (ticks >=2000) {
								tnts.remove(creeper);
								creeper.remove();
							}
							
						}
					}

				};
					runnable.runTaskTimer(plugin, 10, 20);
				}
				count++;
			}
		}.runTaskTimer(plugin, 0, 1);

	}
	private void spawnTNT(Creeper creeper) {
		final TNTPrimed tnt = creeper.getWorld().spawn(creeper.getLocation(), TNTPrimed.class);
		tnt.setIsIncendiary(true);
		tnt.setVelocity(new Vector(0 , 1.75, 0));
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(tnt.isOnGround()||tnt.isDead()){
					this.cancel();
				}
				SuitUtils.playEffect(tnt.getLocation(), EnumParticle.EXPLOSION_NORMAL,1,0,0);
				
			}
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public static void launchLightningMissile(Player player){
		Location targetblock = SuitUtils.getTargetBlock(player, 100).getLocation();
		player.playSound(player.getLocation(), Values.LightningMissileSound, 16.0F,0F);
		SuitUtils.LineParticle(targetblock, player.getEyeLocation(), player, EnumParticle.FLAME, 3,  2, Values.LightningMissile,2,false , true, false,5);
	}
	/**
	 * Throw Hammer if Player is Thor
	 * @param event PlayerInteractEvent
	 */
	@EventHandler
	public void ThrowHammer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(player.isSneaking()){
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (ThorUtils.isHammerinHand(player)) {
				if(Hammer.Thor(player)){
					ThrowHammer(player);
				}
			else{
				if(player == Hammer.thor||Hammer.thor==null){
					PlayEffect.play_Thor_Change_Effect(player , 0);
					Hammer.thor =player;
					}
				}
			}else{
				Location playerEyeLocation = player.getEyeLocation();
				Item hammer =ThorUtils.getItem(player.getWorld() , player);
				if(hammer ==null){
					return;
				}
				Hammer.TeleportItem(hammer , playerEyeLocation );
				player.playSound(playerEyeLocation, Values.HammerTeleportSound, 6.0F,6.0F);
			}
		}

	}
	public void ThrowHammer(Player player) {
		Location location = player.getEyeLocation();
		
		ItemStack ItemInHand =player.getItemInHand();
		Item dropped = player.getWorld().dropItem(location,ItemInHand);
		dropped.setFallDistance(0);
		
		if (ItemInHand.getAmount() == 1) {
			player.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
		} else {
			ItemInHand.setAmount(ItemInHand.getAmount()-1);
			player.setItemInHand(ItemInHand);
		}
		player.updateInventory();

//		double gravity = 0.0165959600149011612D;
		Vector v = player.getLocation().getDirection().normalize().multiply(2);
		dropped.teleport(location);
		dropped.setVelocity(v);
		
		addEffectToHammer(dropped, player);
	
		player.playSound(player.getLocation(), Sound.ANVIL_LAND,6.0F, 6.0F);
		player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT,4.0F, 2.0F);
				
	}
	/**
	 * Add Particle Trail to thrown hammer
	 * @param dropped Hammer Item
	 * @param player Player
	 */
	public void addEffectToHammer(Item dropped, Player player) {
		Hammer_Throw_Effect.listPlayer.put(dropped, player);
		
		if(!Hammer_Throw_Effect.isRunning()){
			new Hammer_Throw_Effect().runTaskTimer(plugin, 0, 1);
		}
			

	}
	
}
