import java.util.*;

// 2022 카카오 테크 인턴쉽 - 성격 유형 검사하기 - 22.08.30.화 - 해시, 

class Solution_JHJ {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        
        // 성격유형을 저장하는 Hash - character(key) - 위치(integer)
        Map<Character, Integer> types = new HashMap<>();
        types.put('R', 0);
        types.put('T', 1);
        types.put('C', 2);
        types.put('F', 3);
        types.put('J', 4);
        types.put('M', 5);
        types.put('A', 6);
        types.put('N', 7);
        
        int cnt = survey.length; // 질문의 개수
        int[][] scores = new int[4][2]; // 각 유형에 따른 점수를 추가하는 배열 
        // survey에 따른 성격 분류
        // 선택했을 때의 점수 
        for(int i = 0; i < cnt; i++) {
          
            String item = survey[i];
            char left = item.charAt(0);
            char right = item.charAt(1);
            int left_idx = types.get(left);
            int right_idx = types.get(right);
            
            int first_idx = (int) Math.ceil(left_idx / 2);
            
            int score = 0;
            if(choices[i] < 4) {
                score = 4 - choices[i];
                scores[first_idx][left_idx % 2] += score;
            } else if(choices[i] > 4) {
                score = choices[i] - 4;
                scores[first_idx][right_idx % 2] += score;
            }
        }
        
      // 결과
        for(int i = 0; i < 4; i++) {
            switch(i) {
                case 0:
                    if(scores[i][0] >= scores[i][1]) 
                        answer += "R";
                    else 
                        answer += "T";
                    break;
                case 1:
                    if(scores[i][0] >= scores[i][1]) 
                        answer += "C";
                    else 
                        answer += "F";
                    break;
                case 2:
                    if(scores[i][0] >= scores[i][1]) 
                        answer += "J";
                    else 
                        answer += "M";
                    break;
                case 3:
                    if(scores[i][0] >= scores[i][1]) 
                        answer += "A";
                    else 
                        answer += "N";
                    break;
            }
        }
        
        return answer;
    }
}
