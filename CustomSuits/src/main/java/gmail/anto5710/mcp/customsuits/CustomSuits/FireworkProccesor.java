package gmail.anto5710.mcp.customsuits.CustomSuits;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import gmail.anto5710.mcp.customsuits.Utils.ColorUtils;
import gmail.anto5710.mcp.customsuits.Utils.MathUtils;

public class FireworkProccesor {

	public static FireworkEffect getEffect(Color color , Type type) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
	    
	    
		Color[] colors= ColorUtils.getColors(Color.fromRGB(r, g, b));
		Color[] fadecolors  = ColorUtils.getColors(Color.WHITE);
		
		
		FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(true).with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
		return effect;
	}

	public static FireworkEffect getRandomEffect(){
	    int type_index = (int) (MathUtils.wholeRandom(Type.values().length));
	    Type type = Type.values()[type_index];
	    int R= (int) MathUtils.wholeRandom(255);
	    int G= (int) MathUtils.wholeRandom(255);
	    int B= (int) MathUtils.wholeRandom(255);
	   
		Color[] colors= ColorUtils.getColors(Color.fromRGB(R, G, B));
		Color[] fadecolors  = ColorUtils.getColors(Color.WHITE);
		
		
		FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(true).with(type).withColor(colors).withFade(fadecolors).withFlicker().withTrail().build();
		return effect;
	}

	public static void spawnFirework(Color color, FireworkEffect.Type type ,int power,boolean Usetrail,boolean flicker,Color fadecolor, Location location){	
		FireworkEffect effect = FireworkEffect.builder().withColor(color)
		.with(type).trail(Usetrail).withFade(fadecolor).flicker(flicker).build();
		
		Firework firework =location.getWorld().spawn(location, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(power);
		
		meta.addEffect(effect);
		firework.setFireworkMeta(meta);
	}

}
