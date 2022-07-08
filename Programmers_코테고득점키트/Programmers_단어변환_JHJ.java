// 22. 07. 08. 금 - 레벨 3 - DFS - 단어변환 

import java.util.*;

class Programmers_단어변환_JHJ {
    
    private int step = Integer.MAX_VALUE; // begin에서 목표한 target 단어로 변환하기 위해 필요한 최소단계
    private boolean[] visited;
    
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        visited = new boolean[words.length];
        
        dfs(0, begin, target, words);
        
        return answer = (step == Integer.MAX_VALUE ? 0 : step);
    }
    
    // target 단어를 찾는데 최소 단계를 알기위한 메소드 
    private void dfs(int level, String begin, String target, String[] words) {
        if(level >= words.length || level >= step) return;
        
        if(begin.equals(target)) step = Math.min(step, level);
        
        for(int i = 0, length = words.length; i < length; i++) {
            
            if(visited[i]) continue;
            
            if(isAvailable(begin, words[i])) {
                visited[i] = true;
                dfs(level+1, words[i], target, words);
                visited[i] = false;
            }
            
        }
    }
    
    // 한 단어만 다른지를 찾는 메소드
    private boolean isAvailable(String a, String b) {
        int cnt = 0; // 몇 개의 단어가 다른지를 담는 변수 
        
        for(int i = 0, length = a.length(); i < length; i++) {
            if(a.charAt(i) == b.charAt(i)) continue;
            else cnt++;
            
            if(cnt >= 2) return false;
        }
        return true;
    }
}
