package grp.meca.irpf.Utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	
	public static void put(Map<Integer, Map<Integer, Double>> map, int firstKey, int secondKey, double value) {
		if(map.containsKey(firstKey))
			map.get(firstKey).put(secondKey, value);
		else {
			Map<Integer, Double> localMap = new HashMap<>();
			localMap.put(secondKey, value);
			map.put(firstKey, localMap);
		}
	}
	
	public static void add(Map<Integer, Map<Integer, Double>> map, int firstKey, int secondKey, double value) {
		if(map.containsKey(firstKey)) {
			if(map.get(firstKey).containsKey(secondKey))
				value += map.get(firstKey).get(secondKey);
			map.get(firstKey).put(secondKey, value);
		}
		else {
			Map<Integer, Double> localMap = new HashMap<>();
			localMap.put(secondKey, value);
			map.put(firstKey, localMap);
		}
	}
}
