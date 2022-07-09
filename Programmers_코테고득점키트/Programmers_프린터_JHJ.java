// 22.07.09.토 - 레벨 2 - 큐 - 프린터 

import java.util.*;

class Programmers_프린터_JHJ {
    
    public int solution(int[] priorities, int location) {
           int answer = 0;

        // 대기목록을 만들어 문서를 넣는다.
        LinkedList<Integer> q = new LinkedList<>();
        for(int i = 0, length = priorities.length; i < length; i++) {
            q.add(priorities[i]);
        }

        while(!q.isEmpty()) {
            int current = q.poll();
            boolean flag = false; // 현재 문서 뒤 우선순위가 높은 문서가 있는지 확인하는 flag
            for(int i = 0; i < q.size(); i++) {
                if(q.get(i)> current) {
                    flag = true;
                    break;
                }
            }
            if(flag) {
                q.add(current);
                if(location == 0) location = q.size()-1;
                else location--;
            } else {
                answer++;
                if(location == 0) break;
                else location--;
            }
        }
        return answer;
    }
}
