// 2022 KAKAO BLIND RECRUITMENT - 사라지는 발판
// 백트래킹

class Solution7_LSH {
    public static int[] dx = {-1, 1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};
    public static int n, m;

    
    static class Result {
        boolean win;
        int move_cnt;
        
        public Result(boolean win, int move_cnt){
            this.win = win;
            this.move_cnt = move_cnt;
        }
    }
    
    public int solution(int[][] board, int[] aloc, int[] bloc) {
        n = board.length;
        m = board[0].length;
        return dfs(board, aloc[0], aloc[1], bloc[0], bloc[1], 0, 0).move_cnt;
    }
    
    public static Result dfs(int[][] board, int a_x, int a_y, int b_x, int b_y, int a_depth, int b_depth){
        boolean win = false;
        int win_cnt = 5*5; 
        int lose_cnt = a_depth + b_depth;
        
        // A 움직임
        if(a_depth == b_depth && board[a_x][a_y] == 1){
            for(int d=0;d<4;d++){
                int nx = a_x + dx[d];
                int ny = a_y + dy[d];
                if(!isMovePossible(board, nx, ny)) continue;
                board[a_x][a_y] = 0;
                Result r = dfs(board, nx, ny, b_x, b_y, a_depth+1, b_depth);
                win |= !r.win;
                if(r.win) lose_cnt = Math.max(lose_cnt, r.move_cnt);
                else win_cnt = Math.min(win_cnt, r.move_cnt);
                board[a_x][a_y] = 1;
            }
        }
        // B 움직임
        else if(a_depth > b_depth && board[b_x][b_y] == 1){
            for(int d=0;d<4;d++){
                int nx = b_x + dx[d];
                int ny = b_y + dy[d];
                if(!isMovePossible(board, nx, ny)) continue;
                board[b_x][b_y] = 0;
                Result r = dfs(board, a_x, a_y, nx, ny, a_depth, b_depth+1);
                win |= !r.win;
                if(r.win) lose_cnt = Math.max(lose_cnt, r.move_cnt);
                else win_cnt = Math.min(win_cnt, r.move_cnt);
                board[b_x][b_y] = 1;
            }
        }
        return new Result(win, win ? win_cnt:lose_cnt);
    }
    
    public static boolean isMovePossible(int[][] board, int x, int y){
        if(x < 0 || x >= n || y < 0 || y >= m || board[x][y] == 0) return false;
        return true;
    }
}
