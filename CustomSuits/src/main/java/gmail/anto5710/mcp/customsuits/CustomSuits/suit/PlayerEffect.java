package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.PlayEffect;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Man.Man;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ManUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits._Thor.Hammer;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.junit.internal.matchers.IsCollectionContaining;

/**

 * 
 * @author anto5710
 *
 */

public class PlayerEffect implements Listener {
	private static CustomSuitPlugin mainPlugin;
	private Logger logger;
	SchedulerHunger hungerscheduler;
	String regex = Values.regex;

	public PlayerEffect(CustomSuitPlugin main) {
		this.mainPlugin = main;
		logger = mainPlugin.getLogger();
		hungerscheduler = main.hscheduler;
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		Player player = (Player) e.getEntity();
		if (this.hungerscheduler.getList().contains(player)
				&& player.getGameMode() != GameMode.CREATIVE) {
			removingeffects(player);

		}
	
	}
	public static void playSpawningEffect(Entity entity, final Player player) {
		
			
		
		
		Location playerlocation = player.getLocation();

		 LivingEntity livingentity = (LivingEntity) entity;
		

		if (entity.getType() != EntityType.PLAYER) {
			if (PlayerEffect.hasArmor(livingentity.getEquipment().getArmorContents())&&Values.Allowed_Suit_Summon_types.contains(livingentity.getType())) {
				if (CustomSuitPlugin.MarkEntity(livingentity)
						&& CustomSuitPlugin.dao.isCreatedBy(livingentity, player)) {

					Location entitylocation = livingentity.getLocation();
					
					Entity vehicle = null ;
					CustomSuitPlugin.runSpawn(entitylocation , vehicle , playerlocation , livingentity , player);
					final LivingEntity livingEntity = livingentity;
					new BukkitRunnable() {
						
						 ItemStack helmet = livingEntity.getEquipment().getHelmet();
						 ItemStack chestplate = livingEntity.getEquipment().getChestplate();
						
						 LivingEntity Entity = livingEntity;
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (CustomSuitPlugin.MarkEntity(livingEntity)
									&& CustomSuitPlugin.dao.isCreatedBy(livingEntity, player)) {
								player.setNoDamageTicks(20);
								boolean waitFor =PlayEffect.play_Suit_Get(player.getLocation(), player);
								Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {
									
									@Override
									public void run() {
										if (Entity.getEquipment().getHelmet() != null) {

											player.getEquipment().setHelmet(helmet);
											player.playSound(player.getLocation(),
													Sound.ANVIL_LAND, 9.0F, 9.0F);
											
											
										}
										
									}
								}, 2);
								Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {
									
									@Override
									public void run() {
										if (Entity.getEquipment().getChestplate() != null) {

											player.getEquipment().setChestplate(chestplate);
											player.playSound(player.getLocation(),
													Sound.ANVIL_LAND, 9.0F, 9.0F);
											
											
										}
										
									}
								}, 12);
								Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {
									
									@Override
									public void run() {
										if (Entity.getEquipment().getLeggings() != null) {
											ItemStack leggings = Entity.getEquipment()
													.getLeggings();
											player.getEquipment().setLeggings(leggings);
											player.playSound(player.getLocation(),
													Sound.ANVIL_LAND, 9.0F, 9.0F);
											
											
										}
										
									}
								}, 16);	
								Bukkit.getScheduler().runTaskLater(mainPlugin, new Runnable() {
									
									@Override
									public void run() {
										if (Entity.getEquipment().getBoots() != null) {
											ItemStack boots = Entity.getEquipment()
													.getBoots();
											player.getEquipment().setBoots(boots);
											player.playSound(player.getLocation(),
													Sound.ANVIL_LAND, 9.0F, 9.0F);
											
											
										}
										
									}
								}, 24);
								

								livingEntity.damage(1000000.0D);
								player.playSound(player.getLocation(),
										Sound.ENDERDRAGON_DEATH, 9.0F, 9.0F);
								player.sendMessage(Values.SuitCallMessage);
								player.updateInventory();
								
							}
						}

					
							
					}.runTaskLater(mainPlugin, 10);
		
		
				
			}
		}
	
		}
	
	}

	@EventHandler
	public void ClickspawnSuit(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(!SuitUtils.CheckItem(CustomSuitPlugin.suitremote, player.getItemInHand())){
			return;
		}
		if(event.getAction()!=Action.LEFT_CLICK_AIR&&event.getAction()!=Action.LEFT_CLICK_BLOCK){
			return;
		}
		Location location_entity =SuitUtils.getTargetBlock(player, Values.spawnSuit_max_target_distance).getLocation();
		location_entity.add(0, 1.5, 0);
		if(location_entity == null){
			location_entity = player.getLocation();
		}
		if(!CustomSuitPlugin.target.containsKey(player)){
			CustomSuitPlugin.target.put(player, "");
		}
		if(!CustomSuitPlugin.color.containsKey(player)){
			CustomSuitPlugin.color.put(player, "red");
		}
		if(!CustomSuitPlugin.Type_Map.containsKey(player)){
			CustomSuitPlugin.Type_Map.put(player, "warrior");
		}
		if(!CustomSuitPlugin.amount.containsKey(player)){
			CustomSuitPlugin.amount.put(player, 1);
		}
		String target = CustomSuitPlugin.target.get(player);
		String entityname = CustomSuitPlugin.Type_Map.get(player);
		int amount = CustomSuitPlugin.amount.get(player);
		String color = CustomSuitPlugin.color.get(player);
		
		
		CustomSuitPlugin.spawnentity(entityname, amount, player, target, color , location_entity);
	}
	@EventHandler
	public void onPlayermove(PlayerMoveEvent moveevent) {
		Material material = Material.STONE;
		Player player = moveevent.getPlayer();

		Boolean IsOnAir = false;
		if (!CustomSuitPlugin.MarkEntity(player)) {
			return;
		}

		if (CustomSuitPlugin.MarkEntity(player)) {
			int level = CustomSuitPlugin.getLevel(player);
			addpotion(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999,
					((int) level / 16) + 1), player);
			addpotion(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999,
					1 + level), player);
			addpotion(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,
					99999999, 2 + level), player);
			
			addpotion(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
					99999999, 1 + ((int) level / 16)), player);
			addpotion(new PotionEffect(PotionEffectType.SPEED, 99999999,
					2 + ((int) level / 32)), player);
			addpotion(new PotionEffect(PotionEffectType.WATER_BREATHING,
					99999999, 1), player);
			addpotion(new PotionEffect(PotionEffectType.JUMP, 99999999, 2),
					player);
			addpotion(new PotionEffect(PotionEffectType.REGENERATION, 99999999,
					1 + (int) level / 16), player);
			
			if(!Player_Move.list.contains(player))
			{
				if(Player_Move.list.isEmpty()){
					BukkitTask task = new Player_Move(mainPlugin).runTaskTimer(mainPlugin, 0, 1);
				}
				Player_Move.list.add(player);
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity DeadEntity = event.getEntity();
		if(SpawningDao.spawnMap.containsKey(DeadEntity.getEntityId())){
		
		if (DeadEntity instanceof Player==false) {

			SpawningDao dao = this.mainPlugin.getDao();
			dao.remove(DeadEntity);
			}
		}

	}

	/**
	 * Get the Suit
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteracted(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		

		SpawningDao dao = this.mainPlugin.getDao();
		if(!Values.Allowed_Suit_Summon_types.contains(entity.getType())){
			return;
		}
		
		if(!dao.isCreatedBy(entity, player)){
			return;
		}

		LivingEntity livingentity = (LivingEntity) entity;
		if(!hasArmor(livingentity.getEquipment().getArmorContents())){
			return;
		}
		// 4개를 다 가지고 있음!

		ItemStack chestItem = livingentity.getEquipment().getChestplate();

		ItemStack leggings = livingentity.getEquipment().getLeggings();
		ItemStack boots = livingentity.getEquipment().getBoots();
		ItemStack helmet = livingentity.getEquipment().getHelmet();

		player.getEquipment().setHelmet(helmet);
		player.getEquipment().setChestplate(chestItem);
		player.getEquipment().setLeggings(leggings);
		player.getEquipment().setBoots(boots);
		livingentity.damage(10000000D);
		player.updateInventory();
		PlayEffect.play_Suit_Get(player.getLocation() , player );

		player.playSound(player.getLocation(),Values.SuitSound, 9.0F, 9.0F);
		dao.remove(livingentity);
	}
	
	public static boolean hasArmor(ItemStack[] armorContents) {
		int checkCount = 0;
		for(int index = 0; index < armorContents.length ; index++){
				if(armorContents[index].getType() == Material.AIR){
					checkCount++;
				}
			}
		if(checkCount == armorContents.length){
		return false;
		}
		return true;
	}

	@EventHandler
	public void zoom(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (CustomSuitPlugin.MarkEntity(player)) {
				ItemStack item = player.getItemInHand();
				if(item == null || item.getType() == Material.AIR){
					return;
				}
				if(item.getItemMeta().getDisplayName()==null||item.getItemMeta().getDisplayName()==""){
					return;
				}
				
				if (item.getItemMeta().getDisplayName().contains(regex)) {

					String name = CustomSuitPlugin.getGun().getItemMeta()
							.getDisplayName();
					String gunname = player.getItemInHand().getItemMeta()
							.getDisplayName();
					if(gunname == null||gunname == ""){
						return;
					}
					if (gunname.contains(regex)) {
						String[] values = name.split(regex);
						String[] names = gunname.split(regex);
						if (values[0].endsWith(names[0])) {
							if (ContainPotionEffect(player, PotionEffectType.SLOW, 10)) {
								player.sendMessage(Values.ZoomOutMessage);
								player.removePotionEffect(PotionEffectType.SLOW);
							} else {

								player.addPotionEffect(new PotionEffect(
										PotionEffectType.SLOW, 100000, 10));
								player.sendMessage(Values.ZoomOnMessage);

							}

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent p) {
		Player player = p.getPlayer();

		if (CustomSuitPlugin.MarkEntity(player)) {
			if (player.getFoodLevel() < 2) {
				SuitUtils.Wrong(player, "Energy");

			}

			else {



				player.setAllowFlight(true);
				player.setFlying(true);
				player.setFlySpeed(1.0F);


				hungerscheduler.addFlyingPlayer(player);

				player.playSound(player.getLocation(), Values.SuitSneakSound,
						1.0F, 1.0F);
			}
		}
	}
	@EventHandler
	public void move(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if(!CustomSuitPlugin.MarkEntity(player)){
			return;
		}
		if(!SchedulerHunger.containPlayer(player)){
			hungerscheduler.addFlyingPlayer(player);
		}
	}

	public static void addpotion(PotionEffect effect, LivingEntity livingEntity) {
		if (!ContainPotionEffect(livingEntity, effect.getType(),
				effect.getAmplifier())) {
			livingEntity.removePotionEffect(effect.getType());
		}
		livingEntity.addPotionEffect(effect);
	}

	public static boolean ContainPotionEffect(LivingEntity livingEntity,
			PotionEffectType type, int level) {

		for (PotionEffect effect : livingEntity.getActivePotionEffects()) {

			if (effect.getType().equals(type) && effect.getAmplifier() == level) {

				return true;

			}
		}
		
		return false;
	}



	@EventHandler
	public void onquit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		hungerscheduler.removeflyingplayer(player);

	}
	

	@EventHandler
	public void stopDisabledPlayer(PlayerMoveEvent moveEvent) {
		Player player = moveEvent.getPlayer();
		if(CustomSuitPlugin.hasAbillity(player)){
			return;
		}
			if(player.getFlySpeed()!= 0.5){
				player.setFlySpeed(0.5F);
			
		if (player.getGameMode() != GameMode.CREATIVE) {
		
		
				removingeffects(player);
				return;
			}
		}
	}

	@EventHandler
	public void RestartToFlyinCreative(PlayerMoveEvent moveEvent) {
		
		Player player = moveEvent.getPlayer();
		if(player.getAllowFlight()==false){

			if (player.getGameMode() == GameMode.CREATIVE) {
			player.setAllowFlight(true);
			player.setFlySpeed(0.5F);
			}
		}
	}

	private void removingeffects(Player player) {

		player.setFlying(false);
		player.setAllowFlight(false);
		player.setFlySpeed(0.5F);

		hungerscheduler.removeflyingplayer(player);

		player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
		player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.SLOW);
		

		
	}

}
