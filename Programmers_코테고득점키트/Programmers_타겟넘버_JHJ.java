// 22. 07. 06. 수 - 레벨 2 - DFS/BFS - 타겟넘버 

class Programmers_타겟넘버_JHJ {
    
    private int goal, cnt;
    
    public int solution(int[] numbers, int target) {
        int answer = 0;
        
        cnt = 0;
        goal = numbers.length;
        
        dfs(0, target, 0, 0,  numbers);
        
        return answer = cnt;
    }
    
    
    private void dfs(int depth, int target, int sum, int i, int[] numbers) {
        if(depth == goal){
            if(sum == target) cnt++;
            return;
        }
        
        dfs(depth + 1, target, sum + numbers[i], i + 1, numbers);
        dfs(depth + 1, target, sum - numbers[i], i + 1, numbers);
        
    }
}
