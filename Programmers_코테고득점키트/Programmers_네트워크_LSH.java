class Solution {
    public static boolean[] visited;
    public static int[][] computer;
    public int solution(int n, int[][] computers) {
        int answer = 0;
        visited = new boolean[n];
        computer = computers;
        for(int i=0;i<n;i++){
            if(!visited[i]){
                answer++;
                dfs(n, i);
            }
        }
        
        
        return answer;
    }
    
    public static void dfs(int n, int c){
        for(int i=0;i<n;i++){
            if(computer[c][i] == 1 && !visited[i]){
                visited[i] = true;
                dfs(n, i);
            }
        }
    }
}
