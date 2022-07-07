// 22. 07. 08. 금 - 레벨 3 - DFS - 네트워크

import java.util.*;

class Programmers_네트워크_JHJ {
    
    boolean[] visited;

    public int solution(int n, int[][] computers) {
        int answer = 0;
        visited = new boolean[n];
      
        for(int i = 0; i < n; i++) {
            if(!visited[i]){
                dfs(0, n, i, computers);
                answer++;
            }
        }
        return answer;
    }

    // 연결된 하나의 그래프 찾기
    private void dfs(int level, int n, int current,  int[][] computers) {
        for(int i = 0; i < n; i++) {
            if(visited[i]) continue;

            if(computers[current][i] == 1) {
                visited[i] = true;
                dfs(level + 1, n, i, computers);
            }
        }
        return;
    }
}
