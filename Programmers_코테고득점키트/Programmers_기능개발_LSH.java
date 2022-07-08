import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        Stack<Integer> stack = new Stack<>();
        
        
        for(int i=progresses.length-1;i>=0;i--){
            int diff = 100 - progresses[i];
            if(diff%speeds[i] > 0){
                stack.push(diff/speeds[i] + 1);
            } else {
                stack.push(diff/speeds[i]);
            }
                
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        while(!stack.isEmpty()){
            int count = 1;
            int num = stack.pop();
            while(!stack.isEmpty()){
                int num2 = stack.peek();
                if(num < num2){
                    break;
                } else {
                    count++;
                    stack.pop();
                }
            }
            list.add(count);
        }
        
        int[] answer = new int[list.size()];
        
        for(int i=0;i<list.size();i++){
            answer[i] = list.get(i);
        }

        
        return answer;
    }
}
