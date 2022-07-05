import java.util.*;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        Map<String, Integer> hash = new HashMap<String, Integer>();
        for(String p:participant) {
            if(!hash.containsKey(p)){
                hash.put(p, 1);
            } else {
                hash.put(p, hash.get(p)+1);
            }
        }
        
        for(String c:completion){
            hash.put(c, hash.get(c)-1);
        }
        
        for(String p:participant) {
            if(hash.get(p) > 0){
                answer += p;
                break;
            }
        }
        
        
        
        return answer;
    }
}
