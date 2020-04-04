package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.CustomSuitPlugin;
import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;
import gmail.anto5710.mcp.customsuits.Utils.items.ItemUtil;

public class HungerScheduler extends LinearEncompassor<Player> {
	SpawningDao dao;
	float maxFly_Speed = 0.75F;

	public HungerScheduler(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		setMaxTick(300);	
		CustomSuitPlugin.logger.info("starting Hunger Thread");
	}

	@Override
	public boolean toRemove(Player player) {
		return !player.isOnline() || player.isDead() || !CustomSuitPlugin.isMarkEntity(player);
	}
	
	@Override
	public void particulate(Player player) {
		if (toRemove(player)) return;

		if (player.isFlying()) {
			if (!checkHunger(player, Values.leastFlyHunger)) {
				player.setFlySpeed(0.5F);
				player.setFlying(false);
			} else {
				if (t % 60 == 0) {
					sufficeHunger(player, Values.SuitFlyHunger);
				}
				if (t % 100 == 0) {
					if (!checkHunger(player, Values.SuitEnoughFly)) {
						SuitUtils.warn(player, Values.FlyEnergyWarn);
					}
				}
			}
		} else if (t % 25 == 0) {
			sufficeHunger(player, Values.SuitHungerRelod);
			repairArmor(player, (short) 1);
		}
	}

	private void repairArmor(Player player, short deltaPer) {
		ItemStack[] armor = player.getEquipment().getArmorContents();
		ItemUtil.addDurability(armor[0], deltaPer);
		ItemUtil.addDurability(armor[1], deltaPer);
		ItemUtil.addDurability(armor[2], deltaPer);
		ItemUtil.addDurability(armor[3], deltaPer);
	}

	@Override
	public void register(Player e) {
		if (isRegistered(e) || e.isDead() || !e.isOnline()) {
			return;
		}
		super.register(e);
		if(!plugin.targetting.isRunning()) plugin.targetting.awaken();
	}

	public static boolean sufficeHunger(Player player, int delta) {
		int curHunger = player.getFoodLevel();
		int rough = curHunger + delta;
		int bounded = (int) MathUtil.bound(0, rough, 20);

		boolean suffice = bounded == rough;
		if (suffice) player.setFoodLevel(bounded);
		
		return suffice;
	}

	private boolean checkHunger(Player player, int hunger) {
		return player.getFoodLevel() >= hunger;
	}
}
