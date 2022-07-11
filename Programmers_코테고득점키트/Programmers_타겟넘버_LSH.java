class Solution {
    public static int cnt;
    public static int r, t;
    public static int[] number;
    
    public int solution(int[] numbers, int target) {
        int answer = 0;
        number = numbers;
        t = target;
        r = numbers.length;
        
        dfs(0, 0);
        answer = cnt;
        return answer;
    }
    
    public static void dfs(int sum, int depth){
        if(depth == r){
            if(t == sum) cnt++;
            
            return;
        }
        
        dfs(sum+number[depth], depth+1);
        dfs(sum-number[depth], depth+1);
    }
}
