package gmail.anto5710.mcp.customsuits.Utils.compressor;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import static gmail.anto5710.mcp.customsuits.Utils.MathUtil.*;

public class CompressnII {

	private static int ss = 0;
	private static String res1= "현재 진행도 (총 1000 세션 × 60000개 항목맵): \n" + 
				"========================================================================================================================================================================================================\n" + 
				"검색속도 기준: \n" + 
				"1위: Method: Bijective 함수방식\n" + 
				"	총 소요시간: 17.12초 (종합 116배 효율적)\n" + 
				"	맵 할당 소요시간: 11.65초 (82배 효율적)\n" + 
				"		평균 할당속도: 초당 5145개\n" + 
				"	맵 검색 소요시간: 5.47초 (190배 효율적)\n" + 
				"		평균 검색속도: 초당 10958개\n" +
				"————————————————————————————————————————\n" + 
				"2위: Method: 소수점 일관이동방식\n" + 
				"	총 소요시간: 18.34초 (종합 102배 효율적)\n" + 
				"	맵 할당 소요시간: 12.25초 (73배 효율적)\n" + 
				"		평균 할당속도: 초당 4897개\n" + 
				"	맵 검색 소요시간: 6.09초 (161배 효율적)\n" + 
				"		평균 검색속도: 초당 9860개\n" +
				"————————————————————————————————————————\n" + 
				"3위: Method: Objects 내장 Hash생성함수\n" + 
				"	총 소요시간: 19.02초 (종합 95배 효율적)\n" + 
				"	맵 할당 소요시간: 12.51초 (69배 효율적)\n" + 
				"		평균 할당속도: 초당 4575개\n" + 
				"	맵 검색 소요시간: 6.51초 (144배 효율적)\n" + 
				"		평균 검색속도: 초당 8800개\n" +
				"————————————————————————————————————————\n" + 
				"4위: Method: 상용로그소수점 이동방식\n" + 
				"	총 소요시간: 33.07초 (종합 12배 효율적)\n" + 
				"	맵 할당 소요시간: 19.44초 (9배 효율적)\n" + 
				"		평균 할당속도: 초당 3087개\n" + 
				"	맵 검색 소요시간: 13.63초 (16배 효율적)\n" + 
				"		평균 검색속도: 초당 4402개\n" + 
				"————————————————————————————————————————\n" + 
				"5위: Method: String 결합방식\n" + 
				"	총 소요시간: 37.03초 (종합 0배 효율적)\n" + 
				"	맵 할당 소요시간: 21.15초 (0배 효율적)\n" + 
				"		평균 할당속도: 초당 2836개\n" + 
				"	맵 검색 소요시간: 15.88초 (0배 효율적)\n" + 
				"		평균 검색속도: 초당 3779개\n" + 
				"————————————————————————————————————————";
	
	public static void main(String[] args) {
		class Loco{
			public final int x, y, z;
			public Loco(int x, int y, int z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}		
			
			@Override
			public String toString() {
				return "("+x +", "+y+", "+z+")";
			}
		}	
		
		class StringCoordEncoder extends CoordEncodingAdaptor{
			@Override
			public Object encode(int x, int y) {
				return encodeString(x, y);
			}
		}
		
		Map<CoordEncodingAdaptor, Map<Object, Integer>> maps = new HashMap<>();
		Map<CoordEncodingAdaptor, Timer> putTimers = new HashMap<>();
		Map<CoordEncodingAdaptor, Timer> getTimers = new HashMap<>();
		Lists.newArrayList(new ShiftCoordEncoder(), new LogCoordEncoder(), new StringCoordEncoder(), new BijectiveCoordEncoder(), new ObjectsHashEncoder()).forEach(coorder->{
			maps.put(coorder, new HashMap<>());
			putTimers.put(coorder, new Timer());
			getTimers.put(coorder, new Timer());
		});
		
		int mapSize = 60000;
		int sessionC = 1000;
		int barL = 182; char bar = '=';
		System.out.printf("현재 진행도 (총 %d 세션 × %d개 항목맵): \n", sessionC, mapSize);
		for(int s = 0; s < sessionC; s++) {
			maps.forEach((coorder, map)->{
				map.clear();
			});
			for (int t = 0; t < mapSize; t++) {
				Loco loc = new Loco((int)randomRadius(10000), (int)randomRadius(10000), (int)randomRadius(10000));
				
				maps.forEach((coorder, map)->{
					Timer timer = putTimers.get(coorder);
					timer.start();
					map.put(coorder.encode(loc.x, loc.z), loc.y);
					timer.lapt();
				});
				
				maps.forEach((coorder, map)->{
					Timer timer = getTimers.get(coorder);
					timer.start();
					int y = map.get(coorder.encode(loc.x, loc.z));
					timer.lapt();
					if(y!=loc.y) {
						System.err.printf("%s가 %s를 잘못 불러옴: wrong y-> %s", name(coorder), loc.toString(), y);
					}
				});
			}
			ss = s;
			maps.forEach((coorder, map)->{
				Timer putTimer = putTimers.get(coorder);
				Timer getTimer = getTimers.get(coorder);
//				putTimer.laps.add(1D*putTimer.total());
//				getTimer.laps.add(1D*getTimer.total());
			});
			if(s%(sessionC/barL)==0) {
				System.out.printf(bar+"");
			}
		}
		System.out.println();
		
		TreeMap<CoordEncodingAdaptor, Timer> SputTimers = new TreeMap<>((ac, bc)->{return (int)(putTimers.get(ac).total() - putTimers.get(bc).total());});
		TreeMap<CoordEncodingAdaptor, Timer> SgetTimers = new TreeMap<>((ac, bc)->{return (int)(putTimers.get(ac).total() - getTimers.get(bc).total());});
		SputTimers.putAll(putTimers);
		SgetTimers.putAll(getTimers);
		
		double Lput = SputTimers.lastEntry().getValue().total()/1000D;
		double Lget = SgetTimers.lastEntry().getValue().total()/1000D;

		System.out.println("검색속도 기준: ");
		SgetTimers.forEach((coorder, getTimer)->{
			double put = putTimers.get(coorder).total()/1000D;
			double get = getTimers.get(coorder).total()/1000D;
			int size = maps.get(coorder).size();
			System.out.printf("%d위: Method: %s\n", place, name(coorder));
			System.out.printf("\t총 소요시간: %.2f초 (종합 %.0f배 효율적)\n", put+get, ((Lput+Lget)/(put+get)-1)*100);
			System.out.printf("\t맵 할당 소요시간: %.2f초 (%.0f배 효율적)\n", put, (Lput/put-1)*100);
			System.out.printf("\t\t평균 할당속도: 초당 %d개\n", Math.round(size/put));
			System.out.printf("\t맵 검색 소요시간: %.2f초 (%.0f배 효율적)\n", get, (Lget/get-1)*100);
			System.out.printf("\t\t평균 검색속도: 초당 %d개\n", Math.round(size/get));
			System.out.println("————————————————————————————————————————");
			place++;
		});
		
		SgetTimers.forEach((coorder, getTimer)->{
			System.out.println(name(coorder));
//			System.out.println("\t"+putTimers.get(coorder).laps);
			System.out.println("\n");
//			System.out.println("\t"+getTimers.get(coorder).laps);
			System.out.println("\n");
		});
	}
	
	private static int place = 1;
	public static String name(CoordEncodingAdaptor c) {
		switch (c.getClass().getSimpleName()) {
		case "ShiftCoordEncoder":
			return "소수점 일관이동방식";
		case "LogCoordEncoder":
			return "상용로그소수점 이동방식";
		case "StringCoordEncoder":
			return "String 결합방식";
		case "BijectiveCoordEncoder":
			return "Bijective 함수방식";
		case "ObjectsHashEncoder":
			return "Objects 내장 Hash생성함수";
		default:
			return "null";
		}
	}
	
}
