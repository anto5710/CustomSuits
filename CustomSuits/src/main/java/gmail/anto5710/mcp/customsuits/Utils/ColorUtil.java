package gmail.anto5710.mcp.customsuits.Utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class ColorUtil{
	
	public static Map<String, Color> colorMap = new HashMap<>();
	private static Map<String, String> chatColorMap = new HashMap<>();
	
	public static void initColorMap(){
		colorMap.put("red", Color.RED);
		colorMap.put("blue", Color.BLUE);
		colorMap.put("aqua", Color.AQUA);
		colorMap.put("black", Color.BLACK);
		colorMap.put("yellow", Color.YELLOW);
		colorMap.put("green", Color.GREEN);
		colorMap.put("lime", Color.LIME);
		colorMap.put("orange", Color.ORANGE);
		colorMap.put("olive", Color.OLIVE);
		colorMap.put("gray", Color.GRAY);
		colorMap.put("purple", Color.PURPLE);
		colorMap.put("white", Color.WHITE);
		colorMap.put("silver", Color.SILVER);
		colorMap.put("navy", Color.NAVY);
		colorMap.put("maroon", Color.MAROON);
		colorMap.put("fuchsia", Color.FUCHSIA);
		colorMap.put("teal", Color.TEAL);
		
		for(ChatColor ccolor : ChatColor.values()) {
			chatColorMap.put(ccolor.name(), ccolor.toString());
		}
		chatColorMap.put("I", ChatColor.ITALIC.toString());
		chatColorMap.put("B", ChatColor.BOLD.toString());
		chatColorMap.put("U", ChatColor.UNDERLINE.toString());
		chatColorMap.put("DEL", ChatColor.STRIKETHROUGH.toString());
	}
	
	public static String colorf(@Nonnull String text) {
		return colorf(text, ChatColor.RESET);
	}
		
	/**
	 * Enables color-coding of the text in HTML-like format, 
	 * such as <font color="gray">{@literal <blue>}</font>blue<font color="gray">{@literal <//>}</font>white, by replacing {@literal <keywords>}       
	 * into Bukkit's ChatColor code strings. Replacement {@literal <keywords>} are as follows:
	 * <ul>
	 * 	<li> <b>{@literal <color>}</b>: <p><a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/ChatColor.html">ChatColor</a> name; 
	 * 		the color will be kept for the rest of the text unless interrupted by new {@literal <color>}<br>
	 * 		Spacing and capitalization do not matter as long as the text part matches.</p>        
	 *
	 * 	<li> <b>{@literal </>}</b>: Changes to previously tagged {@literal <color>}.
	 * 	<li> <b>{@literal <b>}</b>: Emboldens the text using {@link org.bukkit.ChatColor#BOLD ChatColor.BOLD} 
	 * 	<li> <b>{@literal <i>}</b>: Italicizes the text using {@link org.bukkit.ChatColor#ITALIC ChatColor.ITALIC}
	 * 	<li> <b>{@literal <u>}</b>: Underlines the text using {@link org.bukkit.ChatColor#UNDERLINE ChatColor.UNDERLINE}
	 *	<li> <b>{@literal <del>}</b>: Deletes the text using {@link org.bukkit.ChatColor#STRIKETHROUGH ChatColor.STRIKETHROUGH}
	 * </ul>
	 * Only the innermost {@literal <tag>} will be converted in case multiple brackets are nested.<p> 
	 * Examples:
     * <blockquote><pre>
     * colorf("<b><font color="gray">{@literal<brown >}</font></b>choco <b><font color="gray">{@literal<YellOw>}</font></b>banana <b><font color="gray">{@literal</>}</font></b>cake <b><font color="gray">{@literal<//>}house}</font></b>", ChatColor.BLACK)
     * returns "<font color="brown">choco </font><font color="yellow">banana </font><font color="brown">cake </font><font color="black">house</font>"
     * colorf("{@literal<A}<b><font color="gray">{@literal<b><i><dark aqua>}</font></b>QUA> <b><font color="gray">{@literal<//><u>}</font></b>necklace<b><font color="gray">{@literal<del>}</font></b>II", ChatColor.RESET)
     * returns "{@literal <A}<font color="#00AAAA"><b><i>{@literal QUA>} <i></b></font><u>necklace<del>II</u></del>"
     * </pre></blockquote>
	 * 
	 * @param text text to convert from
	 * @param defaultColor default color for the text 
	 * @return text with all HTML color formatting converted into ChatColor equivalents.  
	 */
	public static String colorf(@Nonnull String text, ChatColor defaultColor) {
		String last_color = defaultColor.toString();
		String copy = defaultColor.toString(), regex, color_code, converted = "";
		char cur; 
		int start = -1, end = -1;
		
		for(int i =0; i< text.length(); i++) {
			cur = text.charAt(i);
			if(cur == '<') {
				start = i;
			} else if (cur == '>' && start != -1) {
				regex = text.substring(start + 1, i);
				color_code = regex.trim().toUpperCase().replace(' ', '_');

				boolean foundMatch = true;
				if (color_code.startsWith("/")) {
					converted = color_code.startsWith("//") ? defaultColor.toString() : last_color;
				} else if (chatColorMap.containsKey(color_code)) {
					converted = chatColorMap.get(color_code);
				} else {
					foundMatch = false;
					converted = "<" + regex + ">";
				}
				if (foundMatch) last_color = converted;

				copy += text.substring(end + 1, start) + converted;
				end = i;
				start = -1;
			}
		}
		return copy + text.substring(end+1, text.length());
	}
		
	public static Color[] gradient(Color color){
		float [] HSB =RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
		float H = HSB[0];
		float S = HSB[1];
		float B = HSB[2];
		int N = 200; //200 variants
		float add = -0.125F;
		
		Color[] colors = new Color[N];
		for(int n = 0; n < N ; n++){
			if(willBeOutOfHSB(add, H, S ,B)){
				add*=-1;
			}
			colors[n]=HSBtoRGB(H, S, B+add);
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

	private static boolean willBeOutOfHSB(float delta, float fH, float fS, float fB) {
//		float H = fH+general_FadeColor_Add;
//		float S = fS+general_FadeColor_Add;
		float B = fB+delta;
		return (B>1||B<0);
	}

	public static float[] RGBtoHSB(int R , int G , int B){
		float[] HSB = new float[3];
		java.awt.Color.RGBtoHSB(R, G, B, HSB);
		return HSB;
	}
}
