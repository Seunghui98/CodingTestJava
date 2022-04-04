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

