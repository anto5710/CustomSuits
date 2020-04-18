package gmail.anto5710.mcp.customsuits.Utils.compressor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gmail.anto5710.mcp.customsuits.Utils.MathUtil;

public class Compressn {

	public static void main(String[] args) {
		Map<Double, Integer> map1 = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();
		Map<Double, Integer> map3 = new HashMap<>();
		ShiftCoordEncoder coder = new ShiftCoordEncoder();  
		int mapSize = 60000, tSize = 500;
		
		class Loco{
			double x,y,z;
			public Loco(double x, double y, double z){
				this.x = x; this.y = y; this.z = z;
			}
			int bX() {return (int) x;}
			int bZ() {return (int) z;}
			int bY() {return (int) y;}
		}
		
		System.out.println(write(908,-47));
		long add1 = 0, add2 = 0, add3=0, temp = 0;
		long get1 =0, get2=0, get3=0;
		String add1ar = "{", add2ar = "{",add3ar = "{";
		String get1ar = "{", get2ar = "{",get3ar = "{";
		for(int tt =0; tt < tSize ; tt++) {
			System.out.println(tt+1+"번째 테스트 - "+(1+tt)*100/tSize+"% 진행됨");
			Map<Double, String> dom = new HashMap<>();
			List<Loco> locs = new ArrayList<>();
		
			for(int i = 0; i < mapSize; i++) {
				Loco loco = new Loco((int)MathUtil.randomRadius(1000), (int)MathUtil.randomRadius(100), (int)MathUtil.randomRadius(1000));
					
				temp = System.currentTimeMillis();
				double k = coder.encodeDouble(loco.bX(), loco.bZ());/* writeN(loco.bX(), loco.bZ()); */
				map1.put(k, loco.bY());
				temp= System.currentTimeMillis() - temp;
				if (dom.containsKey(k) && !(loco.bX()+", "+loco.bZ()).equals(dom.get(k))) {
					
					System.out.println(k +" from "+ loco.bX()+", "+loco.bZ() + "   이전 -> "+dom.get(k));
				}else dom.put(k, loco.bX()+", "+loco.bZ());
				add1 += temp;
				
				temp = System.currentTimeMillis();
				map2.put(writeAr(loco.bX(), loco.bZ()), loco.bY());
				temp = System.currentTimeMillis() - temp;
				add2+=temp;
				
				temp = System.currentTimeMillis();
				map3.put(writeN(loco.bX(), loco.bZ()), loco.bY());
				temp = System.currentTimeMillis() - temp;
				add3+=temp;
				
				locs.add(loco);
			}
//			System.out.println(writeN(-987,-113));
//			System.out.printf("Time took initiating map for <Double, Y>: %d\n", add1);
//			System.out.printf("Time took initiating map for <String, Y>: %d\n", add2);
			if(map1.size()!=map2.size()) System.out.println(map1.size()+ " : "+map2.size());
			
	//		System.out.println(map2);
			for(Loco loco : locs) {
				temp = System.currentTimeMillis();
				Object res1 = map1.get(coder.encodeDouble(loco.bX(), loco.bZ()));
				temp= System.currentTimeMillis() - temp;
				get1+=temp;
				
				temp = System.currentTimeMillis();
				Object res2 = map2.get(writeAr(loco.bX(), loco.bZ()));
				temp= System.currentTimeMillis() - temp;
				get2+=temp;
				
				temp = System.currentTimeMillis();
				Object res3 = map3.get(writeN(loco.bX(), loco.bZ()));
				temp= System.currentTimeMillis() - temp;
				get3+=temp;
				
				if(res1!=res2 || res2 != res3) {
					System.out.println(loco.bX() + "  "+ loco.bZ()+"   => "+loco.bY());
					System.out.println(res1+"  !=  "+res2);
					break;
				}
			}
			add1ar += add1+",";
			add2ar += add2+",";
			add3ar += add2+",";
			get1ar += get1+",";
			get2ar += get2+",";
			get3ar += get2+",";
			map1.clear();
			map2.clear();
			map3.clear();
		}
		
		System.out.printf("Time took initiating map for <Double, Y>: %d\n", add1);
		System.out.println(add1ar.substring(0, add1ar.length()-1)+"}\n");
		System.out.printf("Time took initiating map for <String, Y>: %d\n", add2);
		System.out.println(add2ar.substring(0, add2ar.length()-1)+"}\n");
		System.out.printf("Time took initiating map for <String, Y>: %d\n", add3);
		System.out.println(add3ar.substring(0, add3ar.length()-1)+"}\n");
		System.out.printf("Total "+"Time took loading map for <Double, Y>: %d\n", get1);
		System.out.println(get1ar.substring(0, get1ar.length()-1)+"}\n");
		System.out.printf("Time took loading map for <String, Y>: %d\n", get2);
		System.out.println("Total "+get2ar.substring(0, get2ar.length()-1)+"}\n");
		System.out.printf("Time took loading map for Compd<D, Y>: %d\n", get3);
		System.out.println("Total "+get3ar.substring(0, get3ar.length()-1)+"}\n");
		
		System.out.printf("즉, 저장할때는 %s이 %f%% 더 효율적이고\n", add1<add2? "Double 소숫점방식":"String방식",
				((Math.max(add1, add2)/(1D*Math.min(add1,add2)))-1)*100);
		System.out.printf("    찾을때는 %s이 %f%% 더 효율적이다\n", get1<get2? "Double 소숫점방식":"String방식",
				((Math.max(get1, get2)/(1D*Math.min(get1,get2)))-1)*100);
		System.out.printf("즉, 저장할때는 %s이 %f%% 더 효율적이고\n", add1<add3? "Double 소숫점방식":"compared Double 소숫점방식",
				((Math.max(add1, add3)/(1D*Math.min(add1,add3)))-1)*100);
		System.out.printf("    찾을때는 %s이 %f%% 더 효율적이다\n", get1<get3? "Double 소숫점방식":"compared Double 소숫점방식",
				((Math.max(get1, get3)/(1D*Math.min(get1,get3)))-1)*100);
		
	}
	
	private static String writeAr(int i, int v) {
		return new String (i+","+v);
	}
//	
	public static void masin(String[] args) {
//		System.out.println(writeN(-134343,-2321233));
//		System.out.println(writeNN(-134343,-2321233));
		Map<Integer[], Integer>map = new HashMap<>();
		map.put(new Integer[] {1,2},3);
		System.out.println(map.get(new Integer[] {1,2}));
	}
	
	/*
	 * 
	 * 
	 * (-123, 230) -> -123230.3
	 * */
	@SuppressWarnings("unused")
	private static double writeL(int i, int v) {
		int abV = Math.abs(v);
//		int Ndigits = v==0? 1: (int) Math.log10(abV) +1;

//		i *= (int)Math.pow(10, Ndigits);
//		Vsig *= Math.pow(10, -Ndigits+2);
//		while(Vsig!=0 && Vsig>1){Vsig*=0.1;};
		
		int Ndigits = (int) Math.log10(abV)+1;
		double Vsig = Ndigits;
		for(int c = 0; c < Ndigits-1; c++) {
			i*=10;
			Vsig*=0.1;
		}
		i*=10;  
		if(v<0) Vsig*=0.1D;
////		
//		if(i==0) Vsig*=0.1D;
		return i<0 ? i - abV - Vsig : i + abV + Vsig;  
	}
	
//	private static double cogS = Math.pow(10, -4);
	private static double shifter = Math.pow(10, -8);

	
	/**
	 * Returns a Double compression of Integer coord. by shifting appropriate decimal digits.
	 * @param x
	 * @param y
	 * @return
	 */
	public static double writeN(int x, int y) {
		if(y == 0) {return x;}
		if(y<0) {
			return x<0? x-(-y*10+1)*shifter:x+(-y*10+1)*shifter; 
		}
		return x<0?x-(y*10+2)*shifter:x+(y*10+2)*shifter;
	}
	private static double cogC = Math.pow(10, -7);
	public static double writeNComp(int i, int v) {
		if(v == 0) {return i;}		
		if(v<0) {
			return i<0? i-(-v*10+1)*cogC:i+(-v*10+1)*cogC; 
		}
		return i<0?i-(v*10+2)*cogC:i+(v*10+2)*cogC;
	}
	
	public static double writeNOirg(int i, int v) {
		if(v == 0) {return i;}
		int state = v<0? 1:2;
		double absV = Math.abs(v);
		return i<0? i-(absV*10+state)*Math.pow(10, -(int)Math.log10(absV)-2) : i+(absV*10+state)*Math.pow(10, -(int)Math.log10(absV)-2);
	}
	
	public static double writeNN(int i, int v) {
		if(v == 0) {return i;}
		int state = v<0? 1:2;
		double absV = Math.abs(v);
		int digits = (int) (Math.log10(absV)+2);
		absV = Math.abs(v)*10+state;
		for(int c=0; c<digits;c++) {
			absV*=0.1;
		}
		return i<0?i-absV : i+absV;
	}
	
	private static double write(int i, int v) {
		double abV = Math.abs(v);
		int toShift = (int)(Math.log10((int)abV) + (v>=0?1:2));
		for(int c=0; c<toShift; c++) {
			abV*=0.1;
		}
		return i >= 0? i+abV : i-abV;    
	}
}
