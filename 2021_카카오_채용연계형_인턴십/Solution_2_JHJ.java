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

