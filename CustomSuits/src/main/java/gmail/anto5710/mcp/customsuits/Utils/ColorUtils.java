package gmail.anto5710.mcp.customsuits.Utils;

import org.bukkit.Color;

public class ColorUtils {

	public static Color[] getColors(Color color){
		float [] HSB =ColorUtils.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
		float H = HSB[0];
		float S = HSB[1];
		float B = HSB[2];
		int N = 200;
		float add = -0.125F;
		
		Color[] colors= new Color[N];
		for(int n = 0; n<N ; n++){
			if(ColorUtils.isOutOfHSB(add, H, S ,B)){
				add*=-1;
			}
			colors[n]=ColorUtils.HSBtoRGB(H, S, B);
		}
		return colors;
	}

	public static Color HSBtoRGB(float H , float S ,float B){
	    
		  int rgb = java.awt.Color.HSBtoRGB(H ,S ,B);
		    int red = (rgb >> 16) & 0xFF;
		    int green = (rgb >> 8) & 0xFF;
		    int blue = rgb & 0xFF;
		return Color.fromRGB(red, green, blue);
	}

	static boolean isOutOfHSB(float general_FadeColor_Add,float fH, float fS, float fB) {
		float H = fH+general_FadeColor_Add;
		float S = fS+general_FadeColor_Add;
		float B = fB+general_FadeColor_Add;
	
		return (B>1||B<0);
	}

	public static float[] RGBtoHSB(int R , int G , int B){
		float[] HSB = new float[3];
		java.awt.Color.RGBtoHSB(R, G, B, HSB);
		return HSB;
	}

}
