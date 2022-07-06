// 22. 07. 05. 화 - 레벨 1 - 정렬 - K번째수 

import java.util.*;

class Programmers_K번째수_JHJ{
    public int[] solution(int[] array, int[][] commands) {
        
        int size = commands.length;
        int[] answer = new int[size];
        
        int i = 0, j = 0, k = 0;
        for(int n = 0; n < size; n++) {
            i = commands[n][0];
            j = commands[n][1];
            k = commands[n][2];
            
            int[] subArray = Arrays.copyOfRange(array, i-1, j);
            Arrays.sort(subArray);
            answer[n] = subArray[k-1];
        }
        
        return answer;
    }
}
