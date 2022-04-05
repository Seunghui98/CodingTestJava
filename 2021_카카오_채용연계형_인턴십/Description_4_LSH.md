# 2021 카카오 채용연계형 인턴십 - 미로 탈출

유형: 승희

# 해설

## 1. 아이디어

- 출발지에서 목적지까지 최단 거리 → 다익스트라로 해결
- 트랩 상태를 고려해서 구현을 해야함
- 트랩의 갯수 최대 10개라서 해당 트랩이 활성화 or 비활성화 상태인지 체크하면서 거리 구현
- 트랩의 상태를 체크할 때 비트마스킹 연산 활용

## 2. 구현

- 인접 행렬로 A 노드→ B 노드로 갈 때 비용을 저장
- 다익스트라
    - node 객체에 state라는 변수로 전에서 노드의 상태를 저장
        - state = 0(trap 아닌 노드 or trap 비활성화 상태) / state = 1 ~ 10
    - trapcheck로 현재 노드가 트랩인지 아닌지 체크(false=비활성화 상태거나 트랩이 아님)
    - 우선순위큐에 넣어 노드들 거리 계산할 때 방문체크 배열 visited 활용
        - boolean[][] visited = new boolean[n+1][1 << traps.length];
        - 0(트랩 아닌경우) ~ 트랩의 경우에서 온 건지 체크
        - visited[node.index][node.state] 큐에서 방문했는지 체크
    - 현재 노드가 만약 트랩이라면
        - 활성(역방향 이동) → 비활성화
            - state &= ~bit 로 비활성화 → state를 0으로 만드는 연산
        - 비활성(순방향 이동) → 활성화
            - state |= bit 로 활성화
    - 거리 갱신 부분
        - 현재 노드와 다음 노드가 둘 다 트랩이거나 둘 다 트랩이 아닌 경우 ?
            - 기존 순방향대로 거리 계산하여 우선순위 큐에 넣기
        - 둘 중 하나가 트랩 상태인 경우?
            - 그래프의 역방향을 적용하여 우선순위 큐에 넣기
        
        ![K-022](https://user-images.githubusercontent.com/54658745/161740613-694025a0-a1e1-470e-bce8-5540f38ccaac.png)

        
    

## 3. 효율성

![K-021](https://user-images.githubusercontent.com/54658745/161740626-b87ba7e7-a732-4dd9-a289-68a0a097347d.png)


# 코드

```java
// 2021 카카오 채용연계형 인턴십 - 미로탈출
// 다익스트라 + 비트마스킹
import java.util.*;

public class Solution_4_LSH {
    public static void main(String[] args){

    }
    public static int INF = (int)1e9;
    public static class Node implements Comparable<Node>{
        int index;
        int distance;
        int state;

        public Node(int index, int distance, int state){
            this.index = index;
            this.distance = distance;
            this.state = state;
        }

        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }
    public int solution(int n, int start, int end, int[][] roads, int[] traps){
        int answer  = INF;
        int[][] graph = new int[n+1][n+1];
        for(int i=0;i<=n;i++){
            Arrays.fill(graph[i], INF);
        }
        for(int i=1;i<=n;i++){
            graph[i][i] = 0;
        }

        for(int[] road:roads){
            graph[road[0]][road[1]] = Math.min(graph[road[0]][road[1]], road[2]);
        }

        //Dijkstra
        boolean[][] visited = new boolean[n+1][1 << traps.length];
        Queue<Node> q = new PriorityQueue<>();
        q.offer(new Node(start, 0, 0));
        while (!q.isEmpty()){
            Node node = q.poll();

            int state = node.state;

            if(node.index == end){
                answer = node.distance;
                break;
            }

            if(visited[node.index][node.state]){
                continue;
            }

            visited[node.index][node.state] = true;
            // 현재 노드가 활성화된 트랩인지 아닌지 저장
            // 트랩 노드가 아니라면 false임
            boolean trapcheck = false;
            // hashset에 활성화된 트랩 저장
            Set<Integer> trap = new HashSet<>();
            for(int i=0;i<traps.length;i++){
                int bit = 1 << i;

                // trap이 활성 상태인 경우 (역방향 이동)
                if((state & bit) != 0){
                    if(node.index == traps[i]){ // 그 트랩이 현재 노드인 경우
                        state &= ~bit;  // 비활성화
                        continue;
                    }
                    trap.add(traps[i]);
                    continue;
                }

                // 비트에 해당하는 trap이 비활성 상태인 경우
                // 그 트랩이 현재 노드일 때
                if(node.index == traps[i]){
                    state |= bit; // 활성화
                    trap.add(traps[i]);
                    trapcheck = true;
                }

            }

            for(int i=1;i<=n;i++){
                if(node.index == i){
                    continue; // 자기자신
                }
                // 다음 이동할 노드가 trap인지 체크
                boolean nextTrap = trap.contains(i);
                if(trapcheck == nextTrap){
                    // 현재 노드와 다음 노드가 둘 다 트랩이거나 둘 다 아니거나는 결과가 동일
                    if(graph[node.index][i] != INF){
                        q.offer(new Node(i, node.distance+graph[node.index][i], state));
                    }
                    continue;
                }

                // 둘 중 하나가 트랩이라면 그래프의 역방향을 적용
                if(graph[i][node.index] != INF){
                    q.offer(new Node(i, node.distance + graph[i][node.index], state));
                }
            }

        }

        return answer;
    }
}
```
