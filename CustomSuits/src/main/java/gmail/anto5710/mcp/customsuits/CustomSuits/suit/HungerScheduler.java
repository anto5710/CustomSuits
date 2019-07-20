package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HungerScheduler extends BukkitRunnable {

	private CustomSuitPlugin mainPlugin;
	boolean isRunning = false;
	SpawningDao dao;
	public static List<Player> playerQueue = new ArrayList<>();
	public static ArrayList<Player> removedPlayer = new ArrayList<>();
	float maxFly_Speed = 0.75F;
	long count = 0;

	public HungerScheduler(CustomSuitPlugin main) {
		this.mainPlugin = main;
	}

	@Override
	public void run() {
		if (HungerScheduler.playerQueue.isEmpty()) {
			try {
				isRunning = false;
				cancel();
			} catch (IllegalStateException e) {
				CustomSuitPlugin.logger.info("EMPTY QUEUQ");				
			}
		}
		isRunning = true;
		Iterator<Player> itrerator = getPlayers().iterator();

		while (itrerator.hasNext()) {
			Player player = itrerator.next();
			if (shouldRemove(player)) {
				removedPlayer.add(player);
				itrerator.remove();
			} else {
				if (player.isFlying()) {
					if (!sufficeHunger(player, Values.leastFlyHunger)) {
						player.setFlySpeed((float) 0.5);
						player.setFlying(false);
					} else {
						if (count % 60 == 0) {
							addHunger(player, Values.SuitFlyHunger);
						}
						if (count % 100 == 0) {
							if (!sufficeHunger(player, Values.SuitEnoughFly)) {
								SuitUtils.warn(player, Values.FlyEnergyWarn);
							}
						}
					}
				} else if (count % 25 == 0) {
					addHunger(player, Values.SuitHungerRelod);
					repairarmor(player);
				}
			}
		}
		count++;
		playerQueue.removeAll(removedPlayer);
		removedPlayer.clear();
	}

	private boolean shouldRemove (Player player) {
		return !player.isOnline() || player.isDead() || !CustomSuitPlugin.isMarkEntity(player);
	}

	public static boolean containPlayer(Player player) {
		return playerQueue.contains(player);
	}

	private void repairarmor(Player player) {
		ItemStack[] armor = player.getEquipment().getArmorContents();
		addDurability(armor[0], (short) 1);
		addDurability(armor[1], (short) 1);
		addDurability(armor[2], (short) 1);
		addDurability(armor[3], (short) 1);
	}

	public static void addDurability(ItemStack item, short delta) {
		if (SuitUtils.isNull(item)) {
			return;
		}
		short durability = item.getDurability();
		short max_durability = item.getType().getMaxDurability();
		short final_durability = (short) MathUtils.bound(0, durability - delta, max_durability);
		
		item.setDurability(final_durability);
	}

	private List<Player> getPlayers() {
		return playerQueue;
	}

	public void addFlyingPlayer(Player flyingPlayer) throws IllegalStateException {
		if (playerQueue.contains(flyingPlayer) || flyingPlayer.isDead() || !flyingPlayer.isOnline()) {
			return;
		}
		if (!isRunning) {
			new HungerScheduler(mainPlugin).runTaskTimer(mainPlugin, 0, 1);
			if (!Target.isRunning) {
				new Target(mainPlugin).runTaskTimer(mainPlugin, 0, 1);
			}
		}
		HungerScheduler.playerQueue.add(flyingPlayer);
	}

	public List<Player> getList() {
		return playerQueue;
	}

	public static boolean addHunger(Player player, int delta) {
		int curHunger = player.getFoodLevel();
		int rough = curHunger + delta;
		int bounded = (int) MathUtils.bound(0, rough, 20);
		
		boolean suffice = bounded == rough;
		if(suffice)player.setFoodLevel(bounded);
		return suffice;
	}

	private boolean sufficeHunger(Player player, int hunger) {
		return player.getFoodLevel() >= hunger;
	}
}
