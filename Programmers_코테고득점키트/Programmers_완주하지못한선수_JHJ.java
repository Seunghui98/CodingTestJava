// 22. 07. 04. 월 - 레벨 1 - 해시 - 완주하지 못한 선수 

import java.util.*; 

class Programmers_완주하지못한선수_JHJ {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        
        // 1. 참가자 파악하기
        HashMap<String, Integer> hash_participants = new HashMap<>();
        for(int i = 0, length = participant.length; i < length; i++) {
            String key = participant[i];
            if(hash_participants.containsKey(key)) {
                int value = hash_participants.get(key) + 1;
                hash_participants.put(key, value);
            } else {
                hash_participants.put(key, 1);
            }
        }
        
        // 2. 완주자 파악하기
        for(int i = 0, length = completion.length; i < length; i++) {
            String key = completion[i];
            
            int value = hash_participants.get(key);
            if(value == 1) {
                hash_participants.remove(key);
            } else {
                hash_participants.put(key, value-1);
            }
        }
        
        Set<String> uncompletion = hash_participants.keySet();
        for(String each : uncompletion)
            answer = each;
        
        return answer;
    }
}
