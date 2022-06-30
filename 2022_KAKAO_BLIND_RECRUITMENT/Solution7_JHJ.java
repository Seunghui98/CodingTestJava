// ... 테스트케잇 모두 통과 X ... 수정중

public class Solution {
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}
                , new int[]{1, 0}
                , new int[]{1, 2}));
        System.out.println(solution(new int[][]{{1, 1, 1, 1, 1}}
                , new int[]{0, 0}
                , new int[]{0, 0}));
        System.out.println(solution(new int[][]{{1}}
                , new int[]{0, 0}
                , new int[]{0, 0}));
    }

    static int[] dr = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    static int[] dc = {0, 0, -1, 1};
    static int N, M;
    static int a_win, b_win; // a 가 이길 경우 최적, b가 이길 경우 최적

    public static int solution(int[][] board, int[] aloc, int[] bloc) {
        N = board.length;
        M = board[0].length;
        a_win = Integer.MAX_VALUE;
        b_win = Integer.MAX_VALUE;
        return game(true, 0, aloc, bloc, board)[1];
    }

    // playerA의 관점에서 게임 진행 - 이겼다, 졌다의 여부
    // 2: 0이면 A가 이겼다.
    // 1: 1이면 A가 졌다.
    private static int[] game(boolean turn, int depth, int[] aloc, int[] bloc, int[][] board) {
        int ar = aloc[0];
        int ac = aloc[1];
        int br = bloc[0];
        int bc = bloc[1];
        int[] result = null;
        int lose = 0, win = N * M;

        // player_a 가 졌다.
        if(board[ar][ac] == 0 && turn) return new int[]{1, depth};

        // player_a가 이겼다.
        if(board[br][bc] == 0 && !turn) return new int[]{2, depth};

        int nr = 0, nc = 0;
        boolean movable = false;
        for(int d = 0; d < 4; d++) {
            if(turn) {
                nr = ar + dr[d];
                nc = ac + dc[d];
            } else {
                nr = br + dr[d];
                nc = bc + dc[d];
            }
            if(nr < 0 || nc < 0 || nr >= N || nc >= M || board[nr][nc] == 0) continue;

            if(turn) aloc = new int[]{nr, nc};
            else bloc = new int[]{nr, nc};

            movable = true;
            if(turn) board[ar][ac] = 0;
            else board[br][bc] = 0;
            // 다음 위치로 갈 때 현재 위치는 0으로 만든다.
            result = game(!turn, depth + 1, aloc, bloc, board);

            if(result[0] == 2) // A가 이겼다면,
                win = Math.min(result[1], win);
            else // B가 이겼다면,
                lose = Math.max(result[1], lose);

            if(turn) board[ar][ac] = 1;
            else board[br][bc] = 1;
        }

        if(!movable)
            return new int[]{1, depth};
        else if(result[0] == 2)
            return new int[]{2, win};
        else
            return new int[]{1, lose};
    }
}
