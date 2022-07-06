// 22. 07. 05. 화 - 레벨 1 - 완전탐색 - 모의고사 

import java.util.*;

class Programmers_모의고사_JHJ {
    public int[] solution(int[] answers) {
        int[] answer = {};
        
        int[] person1 = new int[]{1, 2, 3, 4, 5};
        int[] person2 = new int[]{2, 1, 2, 3, 2, 4, 2, 5};
        int[] person3 = new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        
        int score1 = 0;
        int score2 = 0;
        int score3 = 0;
        
        for(int i = 0; i < answers.length; i++) {
            if(answers[i] == person1[i%5]) score1++;
            if(answers[i] == person2[i%8]) score2++;
            if(answers[i] == person3[i%10]) score3++;
        }
        
        int max = Math.max(score1, score2);
        max = Math.max(max, score3);
        
        ArrayList<Integer> list_correct = new ArrayList<>();
        
        if(max == score1) list_correct.add(1);
        if(max == score2) list_correct.add(2);
        if(max == score3) list_correct.add(3);
        
        
        answer = new int[list_correct.size()];
        for(int i = 0; i < list_correct.size(); i++) {
            answer[i] = list_correct.get(i);
        }
        
        
        return answer;
    }
}
