package gmail.anto5710.mcp.customsuits.Utils;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Color;

public class ColorUtil{
	
	public static Map<String, Color> colorMap = new HashMap<>();
	
	public static void initColorMap(){
		ColorUtil.colorMap.put("red", Color.RED);
		ColorUtil.colorMap.put("blue", Color.BLUE);
		ColorUtil.colorMap.put("aqua", Color.AQUA);
		ColorUtil.colorMap.put("black", Color.BLACK);
		ColorUtil.colorMap.put("yellow", Color.YELLOW);
		ColorUtil.colorMap.put("green", Color.GREEN);
		ColorUtil.colorMap.put("lime", Color.LIME);
		ColorUtil.colorMap.put("orange", Color.ORANGE);
		ColorUtil.colorMap.put("olive", Color.OLIVE);
		ColorUtil.colorMap.put("gray", Color.GRAY);
		ColorUtil.colorMap.put("purple", Color.PURPLE);
		ColorUtil.colorMap.put("white", Color.WHITE);
		ColorUtil.colorMap.put("silver", Color.SILVER);
		ColorUtil.colorMap.put("navy", Color.NAVY);
		ColorUtil.colorMap.put("maroon", Color.MAROON);
		ColorUtil.colorMap.put("fuchsia", Color.FUCHSIA);
		ColorUtil.colorMap.put("teal", Color.TEAL);
	}
	
	public static Color[] getColors(Color color){
		float [] HSB =ColorUtil.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
		float H = HSB[0];
		float S = HSB[1];
		float B = HSB[2];
		int N = 200;
		float add = -0.125F;
		
		Color[] colors= new Color[N];
		for(int n = 0; n<N ; n++){
			if(ColorUtil.isOutOfHSB(add, H, S ,B)){
				add*=-1;
			}
			colors[n]=ColorUtil.HSBtoRGB(H, S, B+add);
		}
		return colors;
	}

	public static Color HSBtoRGB(float H , float S ,float B){
		int rgb = java.awt.Color.HSBtoRGB(H, S, B);
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;
		return Color.fromRGB(red, green, blue);
	}

	private static boolean isOutOfHSB(float general_FadeColor_Add,float fH, float fS, float fB) {
//		float H = fH+general_FadeColor_Add;
//		float S = fS+general_FadeColor_Add;
		float B = fB+general_FadeColor_Add;
		return (B>1||B<0);
	}

	public static float[] RGBtoHSB(int R , int G , int B){
		float[] HSB = new float[3];
		java.awt.Color.RGBtoHSB(R, G, B, HSB);
		return HSB;
	}
}
