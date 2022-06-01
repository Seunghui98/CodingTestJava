public class Solution_4_JHJ {

    public static void main(String[] args) {
        int[][] fares = new int[][]{{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}};
        System.out.println(solution(6, 4, 6, 2, fares));
    }

    public static int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;

        // 1. 그래프 만들기 (출발점이 1부터 시작한다) - 출발지부터 도착지까지의 비용
        int[][] graph = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j)
                    graph[i][j] = 100000 * (n-1) + 1; // 자기 자신을 제외한 모든 지역을 다 거쳐왔을 때의 합 + 1
            }
        }

        for (int i = 0, length = fares.length; i < length; i++) {
            int start = fares[i][0];
            int end = fares[i][1];
            int fare = fares[i][2];

            // 무방향 그래프
            graph[start][end] = fare;
            graph[end][start] = fare;
        }

        // 2. 각 정점까지의 최솟값 구하기 (플로이드 와샬)
        for (int k = 1; k <= n; k++) { // 경유지
            for (int i = 1; i <= n; i++) { // 출발지
                if (i == k) continue;
                for (int j = 1; j <= n; j++) { // 도착지
                    if (i == j || j == k) continue;
                    graph[i][j] = graph[i][j] > graph[i][k] + graph[k][j] ? graph[i][k] + graph[k][j] : graph[i][j];
                }
            }
        }

        // 3. 탑승구역 비용 구하기
        for (int i = 1; i <= n; i++) { // i : 같이 탑승한 지점
            int cost = graph[s][i] + graph[i][a] + graph[i][b];
            if (answer > cost)
                answer = cost;
        }
        return answer;
    }
}
