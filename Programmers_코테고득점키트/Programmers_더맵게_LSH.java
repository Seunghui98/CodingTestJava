import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        for(int s:scoville){
            heap.add(s);
        }
        
        int count = 0;
        boolean check = false;
        while(heap.size() >= 1){
            int num1 = heap.poll();
            if(num1 >= K){
                check = true;
                break;
            } else if(heap.size() >= 1){
                int num2 = heap.poll();
                heap.add(num1 + num2 * 2);
                count++;
            }
        }
        
        return check?count:-1;
    }
}
