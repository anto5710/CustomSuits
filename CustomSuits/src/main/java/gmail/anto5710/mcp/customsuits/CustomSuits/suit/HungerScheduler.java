package gmail.anto5710.mcp.customsuits.CustomSuits.suit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import gmail.anto5710.mcp.customsuits.CustomSuits.dao.SpawningDao;
import gmail.anto5710.mcp.customsuits.Setting.Values;
import gmail.anto5710.mcp.customsuits.Utils.ItemUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;
import gmail.anto5710.mcp.customsuits.Utils.SuitUtils;
import gmail.anto5710.mcp.customsuits.Utils.encompassor.LinearEncompassor;

public class HungerScheduler extends LinearEncompassor<Player> {
	SpawningDao dao;
	float maxFly_Speed = 0.75F;

	public HungerScheduler(CustomSuitPlugin plugin, long period) {
		super(plugin, period);
		setMaxTick(300);	
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
			repairArmor(player);
		}
	}

	private void repairArmor(Player player) {
		ItemStack[] armor = player.getEquipment().getArmorContents();
		ItemUtil.addDurability(armor[0], (short) 1);
		ItemUtil.addDurability(armor[1], (short) 1);
		ItemUtil.addDurability(armor[2], (short) 1);
		ItemUtil.addDurability(armor[3], (short) 1);
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
