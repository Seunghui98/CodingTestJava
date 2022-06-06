import java.util.*;

// 22. 06. 07. - 카카오 블라인드 21 - Lv.3 - 카드 짝 맞추기

// 정확성 56.7 - 이유: 카드 순서 고려해주지 않았기 때문 (같은 번호 카드라도 다른 순서로 접근할 수 있음) 

public class Solution6_JHJ {

    public static void main(String[] args) {
        int answer = solution(new int[][]{{1, 4, 5, 3}, {2, 4, 6, 5}, {0, 0, 0, 2}, {3, 0, 1, 0}}, 3, 0);
        int answer2 = solution(new int[][]{{3, 0, 0, 2}, {0, 0, 1, 0}, {0, 1, 0, 0}, {2, 0, 0, 3}}, 0, 1);
        System.out.println(answer);
        System.out.println(answer2);
    }

    public static int solution(int[][] board, int r, int c) {

        int answer = Integer.MAX_VALUE;
        int card_cnt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) card_cnt++;
            }
        }
        card_cnt /= 2;

        boolean[] adopted = new boolean[card_cnt + 1];
        ArrayList<ArrayList<Integer>> orders = new ArrayList<>();
        dfs(0, card_cnt, adopted, orders, new ArrayList<Integer>());

        for (int i = 0, size = orders.size(); i < size; i++) {
            int[][] copied = copiedBoard(board);
            answer = Math.min(answer, search(copied, r, c, orders.get(i)));
        }
        return answer;
    }
  
  
    // 지도 복사
    private static int[][] copiedBoard(int[][] board) {
        int[][] copied = new int[4][4];
        for (int i = 0; i < 4; i++) {
            copied[i] = board[i].clone();
        }
        return copied;
    }

    // 최소거리구하기
    // 방향 전환이 일어날 때 +1 해준다. (ctrl 계산을 위해)
    private static int search(int[][] board, int r, int c, ArrayList<Integer> order) {
        int answer = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int index = 0;
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(r, c, -1, 0));
        boolean[][] visited = new boolean[4][4];
        int selectedItem = 0;
        visited[r][c] = true;

        if (board[r][c] != 0 && board[r][c] == order.get(index)) {
            answer++;
            selectedItem = board[r][c];
        }

        while (!queue.isEmpty()) {
            if (index >= order.size()) break;

            Point current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nr = current.r + dr[d];
                int nc = current.c + dc[d];
                int increase_distance = current.direction == d ? 0 : 1;

                if (nr < 0 || nc < 0 || nr >= 4 || nc >= 4 || visited[nr][nc]) continue;

                visited[nr][nc] = true;
                if (board[nr][nc] == order.get(index)) {
                    if (selectedItem == 0) {
                        selectedItem = board[nr][nc];
                    } else if (selectedItem == board[nr][nc]) {
                        selectedItem = 0;
                        index += 1;
                    }
                    board[nr][nc] = 0;
                    answer += (current.distance + increase_distance + 1);
                    queue.clear();
                    queue.add(new Point(nr, nc, -1, 0));
                    visited = new boolean[4][4];
                    break;
                }
                queue.add(new Point(nr, nc, d, current.distance + increase_distance));
            }
        }
        return answer;
    }

    // 카드 뽑는 순서 구하기
    private static void dfs(int level, int cnt, boolean[] adopted, ArrayList<ArrayList<Integer>> orders, ArrayList<Integer> order) {

        if (level == cnt) {
            ArrayList<Integer> copied_order = new ArrayList<Integer>();
            for (int i = 0; i < cnt; i++) {
                copied_order.add(order.get(i));
            }
            orders.add(copied_order);
            return;
        }

        for (int i = 1; i <= cnt; i++) {
            if (adopted[i]) continue;
            adopted[i] = true;
            order.add(i);
            dfs(level + 1, cnt, adopted, orders, order);
            adopted[i] = false;
            order.remove(level);
        }
    }

    private static class Point {
        int r;
        int c;
        int direction;
        int distance;

        Point(int r, int c, int direction, int distance) {
            this.r = r;
            this.c = c;
            this.direction = direction;
            this.distance = distance;
        }
    }
}
