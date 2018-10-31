import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unity {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(13);
        list.add(1);
        list.add(1);
        Map<String, Integer> map = unity(list);
        System.out.println(map);
    }

    private static Map<String, Integer> unity(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = list.get(0);
        int maxNum = 1;
        for (int a : list) {
            if (map.containsKey(a)) {
                map.put(a, map.get(a) + 1);
                if (maxNum < map.get(a)) {
                    maxNum = map.get(a);
                    max = a;
                }
            } else {
                map.put(a, 1);
            }
        }
        Map<String, Integer> maxMap = new HashMap<>();
        maxMap.put("众数", max);
        maxMap.put("重数", maxNum);
        return maxMap;
    }
}
