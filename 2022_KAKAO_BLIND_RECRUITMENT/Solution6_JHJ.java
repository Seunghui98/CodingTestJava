
// 22. 06. 29. 수 - 파괴되지 않은 건물 - imos algorithm

public class Solution6_JHJ{
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
                , new int[][]{{1, 1, 1, 2, 2, 4}, {1, 0, 0, 1, 1, 2}, {2, 2, 0, 2, 0, 100}}));
    }

    public static int solution(int[][] board, int[][] skill) {
        int answer = 0;

        int N = board.length;
        int M = board[0].length;

        // 1. 누적합을 보관할 배열 만들기
        int[][] cumulative_map = new int[N+1][M+1];

        // 2. skill 배열 값을 읽으며 누적합 계산을 위해 범위의 시작과 끝에 degree 값 세팅하기
        for(int i = 0, length = skill.length; i < length; i++) {
            int type = skill[i][0];
            int r1 = skill[i][1];
            int c1 = skill[i][2];
            int r2 = skill[i][3];
            int c2 = skill[i][4];
            int degree = type == 1 ? skill[i][5] * -1 : skill[i][5];

            cumulative_map[r1][c1] += degree;
            cumulative_map[r1][c2+1] += degree * -1;
            cumulative_map[r2+1][c1] += degree * -1;
            cumulative_map[r2+1][c2+1] += degree;
        }

        // 2. 세팅된 degree 값 이용하여 누적합 해주기 - 행
        for(int r = 0; r <= N; r++) {
            int prior = cumulative_map[r][0];
            for(int c = 1; c <= M; c++) {
                cumulative_map[r][c] += prior;
                prior = cumulative_map[r][c];
            }
        }

        // 열
        for(int c = 0; c <= M; c++) {
            int prior = cumulative_map[0][c];
            for(int r = 1; r <= N; r++) {
                cumulative_map[r][c] += prior;
                prior = cumulative_map[r][c];
            }
        }

        // 3. 원래 배열과 더해주기
        for(int r = 0; r < N; r++) {
            for(int c = 0; c < M; c++) {
                board[r][c] += cumulative_map[r][c];
                if(board[r][c] > 0) answer++;
            }
        }

        return answer;
    }
}
