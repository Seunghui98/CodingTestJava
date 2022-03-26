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
