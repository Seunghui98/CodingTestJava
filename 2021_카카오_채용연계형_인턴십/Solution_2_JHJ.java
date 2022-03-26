import java.util.*;

public class Solution_2_JHJ {

    public static void main(String[] args) {
        String[][] places = {{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}, {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"},
                {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}, {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"},
                {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}};
        solution(places);
    }

    public static int[] solution(String[][] places) {
        int[] answer = new int[5];
        // 5개의 대기실
        for (int roomNo = 0; roomNo < 5; roomNo++) {
            // 1. 각 대기실 배치
            char[][] map = new char[5][5];
            LinkedList<Point> pplPoints = new LinkedList<Point>(); // 각 응시자들의 위치
            for (int r = 0; r < 5; r++) {
                String temp = places[roomNo][r];
                for (int c = 0; c < 5; c++) {
                    map[r][c] = temp.charAt(c);
                    if (map[r][c] == 'P') {
                        pplPoints.add(new Point(r, c));
                    }
                }
            }
            Collections.sort(pplPoints);
            // ----------------- input end ------------------------

            // 2. 각 사람들 위치마다 거리두기가 잘 지켜졌는지 확인
            answer[roomNo] = 1;
            for (int i = 0, length = pplPoints.size(); i < length; i++) {
                Point person1 = pplPoints.get(i);
                for (int j = i + 1; j < length; j++) {
                    Point person2 = pplPoints.get(j);
                    int distance = Math.abs(person1.r - person2.r) + Math.abs(person1.c - person2.c);
                    if (distance < 2) {
                        answer[roomNo] = 0;
                        break;
                    } else if (distance == 2 && !isAvailable(map, person1, person2)) {
                        answer[roomNo] = 0;
                        break;
                    }
                }
            }
        }
        return answer;
    }

    private static boolean isAvailable(char[][] map, Point p1, Point p2) {

        int nr = p1.r + 1;
        int nc = p1.c + 1;

        // 각 응시자가 일직선상에 위치할 경우 (가로) 응시자 사이 파티션이 있다면 거리두기를 지킨 것
        if (p1.r == p2.r && map[p1.r][nc] == 'X') return true;

        // 각 응시자가 일직선상에 위치할 경우 (세로) 응시자 사이 파티션이 있다면 거리두기를 지킨 것
        if (p1.c == p2.c && map[nr][p1.c] == 'X') return true;

        // 각 응시자가 대각선으로 위치한 경우 파티션을 사이에 두고 앉은 경우 거리두기를 지킨 것
        if (map[p1.r][p2.c] == 'X' && map[p2.r][p1.c] == 'X') return true;

        return false;
    }

    public static boolean BFS(char[][] map, int r, int c) {

        int[] dr = {-1, 1, 0, 0}; // 좌상, 상, 우상, 좌, 우, 좌하, 하, 우하
        int[] dc = {0, 0, -1, 1};

        boolean[][] visited = new boolean[5][5];
        Queue<Point> searchPoints = new LinkedList<Point>(); // 각 응시자의 위치 기준으로 탐색 가능한 범위
        searchPoints.offer(new Point(r, c));
        visited[r][c] = true;

        while (!searchPoints.isEmpty()) {
            Point p = searchPoints.poll();
            for (int d = 0; d < 8; d++) {
                int nr = p.r + dr[d];
                int nc = p.c + dc[d];
                int distance = Math.abs(r - nr) + Math.abs(c - nc); // 응시자의 위치와 다음 탐색할 위치와의 맨해튼 거리

                // 범위를 벗어나거나 방문한 곳이라면 패스
                if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5 || visited[nr][nc] || distance > 2)
                    continue;

                // 맨해튼 거리 2 초과 거리두기가 지켜졌는지를 보장하기 위해서는
                // 맨해튼 거리가 2 이하 거리두기 제한사항을 지켰는지에 관해 살펴보아야 한다.

                // 다음 탐색 위치에 사람이 앉아있거나, 빈칸이라면
                if (map[nr][nc] == 'P')
                    return false;

                // 다음 탐색 위치에 파티션이 위치한다면 그 다음 위치에 다른 응시자가 있어도 거리두기가 지켜진 것이다.
                visited[nr][nc] = true;
                searchPoints.offer(new Point(nr, nc));

            }
        }
        // 응시자의 위치 기준 맨해튼 거리 내 아무 응시자가 없는 경우 (즉, 거리두기가 잘 지켜진 경우)
        return true;
    }

    private static class Point implements Comparable<Point> {
        int r;
        int c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Point o) {
            if (r == o.r) return c - o.c;
            return r - o.r;
        }
    }
}
