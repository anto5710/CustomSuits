package gmail.anto5710.mcp.customsuits.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class BinarySearch {
	
	/**
	 * Returns the index of the last element that is less than or equal to the given element. <br> 
	 * Essentially, this gives one less the total number of E, E ≤ floor.<br>
	 * For example, 
	 * <blockquote><pre>
	 * floorIndex(2, {1,2,2,4,4}, true) returns 2;
	 * </pre></blockquote>            
	 * @param <N> The comparable type
	 * @param floor Max acceptable value of E.
	 * @param list The list to consider.
	 * @param sort Whether the list should be sorted before.
	 * @return the last under-floor index or -1 if not found.
	 */
	public static <N extends Comparable<N>> int floorIndex(N floor, List<N>list, boolean sort) {
		if(sort) list.sort((a,b)->a.compareTo(b));
			
		return recurseFloorIndex(floor, list, 0, list.size()-1);
	}
	
	private static <N extends Comparable<N>> int recurseFloorIndex(N floor, List<N>list, int begin, int end) {
		if(end-begin<=1) {
			if(list.get(end).compareTo(floor)<=0) return end;
			
			return list.get(begin).compareTo(floor)<=0 ? begin : -1;  
		}
		
		int medianI = medianIndex(list, begin, end);  
		if(floor.compareTo(list.get(medianI))<0) { // E smaller than the median.   
			return recurseFloorIndex(floor, list, begin, medianI);
		} 
		return recurseFloorIndex(floor, list, medianI, end); 
	}
	
	private static <E> int medianIndex(@Nonnull List<E>list, int begin, int end) {
		return (begin+end)/2;
	}
		
	/**
	 * Returns the index of the first element that is greater than or equal to the given element. <br> 
	 * Essentially, this gives one less the total number of E, E ≤ floor.           
	 * @param <N> The comparable type
	 * @param floor Max acceptable value of E.
	 * @param list The list to consider.
	 * @param sort Whether the list should be sorted before.
	 * @return the last under-floor index or -1 if not found.
	 */
	public static <N extends Comparable<N>> int ceilingIndex(N ceiling, List<N>list, boolean sort) {
		if(sort) list.sort((a,b)->a.compareTo(b));
			
		return recurseCeilingIndex(ceiling, list, 0, list.size()-1);
	}
	
	private static <N extends Comparable<N>> int recurseCeilingIndex(N ceiling, List<N>list, int begin, int end) {
		if(end-begin<=1) {
			if(ceiling.compareTo(list.get(begin))<=0) return begin;
			
			return ceiling.compareTo(list.get(end))<=0 ? end : -1;  
		}
		
		int medianI = medianIndex(list, begin, end);  
		if(list.get(medianI).compareTo(ceiling)<0) { // median < ceiling    
			return recurseCeilingIndex(ceiling, list, medianI, end); 
		} 
		return recurseCeilingIndex(ceiling, list, begin, medianI);
	}
	
	private static List<Integer>randomList(int l, int r){
		List<Integer>list = new ArrayList<>();
		for(int i =0; i<l;i++) {
			list.add((int) MathUtil.randomRadius(r));
		}
		return list;
	}
	public static void main(String[] args) {
		List<Integer> l;
		int s, coc;
		int testC = 20;
		for(int c=0; c<testC; c++) {
			l = randomList((int) MathUtil.random(1, 10), 400);
			coc = (int) MathUtil.randomRadius(400);
			s = ceilingIndex(coc, l, true);
			System.out.print(coc+"의 위치: ("+s+") ");
			for(int i =0; i < l.size(); i++) {
				if(i==s) System.out.print("√ ");
				System.out.print(l.get(i)+(i<l.size()-1?", ":""));
//				if(i==s) System.out.print("√ ");
			}
			System.out.println();
		}
//		List<Integer>l = Lists.newArrayList(-278, 2, 142);
//		System.out.println(ceilingIndex(381, l, true));
	}
}
