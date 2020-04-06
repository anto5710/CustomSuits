package gmail.anto5710.mcp.customsuits.Utils.fireworks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import gmail.anto5710.mcp.customsuits.Utils.ColorUtil;
import gmail.anto5710.mcp.customsuits.Utils.MathUtil;

public class FireworkProccesor {

	public static FireworkEffect getEffect(Color color, Type type) {
		int R = color.getRed();
		int G = color.getGreen();
		int B = color.getBlue();

		Color[] colors = ColorUtil.gradient(Color.fromRGB(R, G, B));
		Color[] fadecolors = ColorUtil.gradient(Color.WHITE);

		return FireworkEffect.builder().trail(true).flicker(true)
				.with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
	}

	public static FireworkEffect getRandomEffect() {
		int type_index = (int) (MathUtil.wholeRandom(Type.values().length));
		Type type = Type.values()[type_index];
		int R = (int) MathUtil.wholeRandom(255);
		int G = (int) MathUtil.wholeRandom(255);
		int B = (int) MathUtil.wholeRandom(255);

		Color[] colors = ColorUtil.gradient(Color.fromRGB(R, G, B));
		Color[] fadecolors = ColorUtil.gradient(Color.WHITE);

		return FireworkEffect.builder().trail(true).flicker(true)
				.with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
	}

	public static void spawnFirework(Color color, Type type, int power, boolean useTrail, boolean flicker, 
			Color fadecolor, Location location) {
		FireworkEffect effect = FireworkEffect.builder().withColor(color).with(type).withFade(fadecolor).
				trail(useTrail).flicker(flicker).build();

		Firework firework = location.getWorld().spawn(location, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(power);

		meta.addEffect(effect);
		firework.setFireworkMeta(meta);
	}
}
