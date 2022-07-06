// 22. 07. 05. 화 - 레벨 2 - 스택/큐 - 기능개발 - 큐 이용

import java.util.*;

class Programmers_기능개발_JHJ {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};

        Queue<Integer> list_return = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();

        // 1. 작업 별 배포에 몇일이 걸리는지 계산
        for(int i = 0, length = progresses.length; i < length; i++) {
            if(speeds[i] != 1) {
                queue.add((100-progresses[i])/speeds[i] + 1);
            } else {
                queue.add(100-progresses[i]);
            }
        }

        // 2. 각 배포마다 몇 개의 기능이 배포되는지 계산
        int present = queue.poll();
        int cnt = 1;
        while(!queue.isEmpty()) {
            int next = queue.poll();
            if(present < next) {
                present = next;
                list_return.add(cnt);
                cnt = 1;
            } else {
                cnt++;
            }
        }
        list_return.add(cnt);

        // 3. answer 배열에 배포마다 배포되는 기능 수 넣기
        answer = new int[list_return.size()];
        int index = 0;
        while(!list_return.isEmpty()) {
            answer[index++] = list_return.poll();
        }

        return answer;
    }
}
