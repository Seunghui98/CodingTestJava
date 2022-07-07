import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        HashMap<String, Integer> hash = new HashMap<>();
        for(String[] arr:clothes){
            hash.put(arr[1], hash.getOrDefault(arr[1], 0)+1);
        }
        
        Set<String> hashSet = hash.keySet();
        int answer = 1;
        for(String h:hashSet){
            answer *= (hash.get(h)+1);
        }
        
        return answer-1;
    }
}
