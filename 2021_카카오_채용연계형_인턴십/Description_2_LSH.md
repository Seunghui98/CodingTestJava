# 2021 카카오 채용연계형 인턴십 - 거리두기 확인하기

유형: BFS

# 해설

## 1. 아이디어

- BFS를 통해 거리 구하기
    - 한 사람을 기준으로 BFS 함수를 통해 거리두기가 가능한지 체크
    - 상, 하, 좌, 우 이동하면서 만약 거리가 2 이하인데 사람 P가 있으면 거리두기 불가
    - 거리가 2 이하일 때만 상, 하, 좌, 우 이동하며 그 3이상의 거리는 거리를 체크할 필요 없음

 

## 2. 구현

- 한 사람을 기준(P)으로 bfs() 함수 호출
- bfs()
    - 다시 재방문을 하지 않기 위해 방문 체크 배열 visited 선언
    - 인접 4방향 돌면서 현재 거리가 1이하 (→이동하면 2 이하인 경우) 인데 이동할 곳에 사람이 있으면 거리두기 실패로 false return
    - 인접 4방향 돌면서 현재 거리가 1이하 (→ 이동하면 2 이하인 경우) 인데 이동할 곳이 빈 곳 (O 인 경우) 라면 방문 처리 후 queue에 해당 노드(거리 +1한 후) append

# 코드

```java
// 2021 카카오 채용 연계형 인턴십 - 거리두기 확인하기
// BFS 풀이
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_2_LSH {

    public static void main(String[] args)  {

    }

    public static class Node{
        int x;
        int y;
        int dis;
        public Node(int x, int y, int dis){
            this.x = x;
            this.y = y;
            this.dis = dis;
        }
    }

    public int[] solution(String[][] places){
        int[] answer = new int[5];
        StringTokenizer st = null;
        for(int p=0;p<places.length;p++){
            char[][] map = new char[5][5];
            for(int i=0;i<5;i++){
                map[i] = places[p][i].toCharArray();
            }

            boolean check = false;
            outer : for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(map[i][j] == 'P'){
                        if(bfs(map, i, j)){
                            check = true;
                            break outer;
                        }
                    }
                }
            }

            if(check){
                answer[p] = 0;
            } else {
                answer[p] = 1;
            }
        }
        return answer;
    }

    public static int[] dx = {-1, 1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};

    public static boolean bfs(char[][] map, int x, int y){
        Queue<Node> q = new LinkedList<>();
        boolean[][] visited = new boolean[5][5];
        visited[x][y] = true;
        q.offer(new Node(x, y, 0));
        while (!q.isEmpty()){
            Node node = q.poll();
            for(int d=0;d<4;d++){
                int nx = node.x + dx[d];
                int ny = node.y + dy[d];
                if (0 <= nx && nx < 5 && 0 <= ny && ny < 5){
                    if(!visited[nx][ny]){
                        if(node.dis <= 1 && map[nx][ny] == 'P'){
                            return true;
                        }

                        if(node.dis <= 1 && map[nx][ny] == 'O'){
                            visited[nx][ny] = true;
                            q.add(new Node(nx, ny, node.dis+1));
                        }
                    }
                }
            }
        }
        return false;
    }
}
```
