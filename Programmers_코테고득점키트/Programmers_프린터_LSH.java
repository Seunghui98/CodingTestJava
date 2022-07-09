import java.util.*;

class Solution {
    public static class Node{
        int idx;
        int num;
        public Node(int idx, int num){
            this.idx = idx;
            this.num = num;
        }
    }
    
    public int solution(int[] priorities, int location) {
        int answer = 0;
        Queue<Node> q = new LinkedList<>();
        
        Integer[] arr = new Integer[priorities.length];
    
        
        for(int i=0;i<priorities.length;i++){
            q.add(new Node(i, priorities[i]));
            arr[i] = priorities[i];
        }
        
        Arrays.sort(arr, Collections.reverseOrder());
        
        outer: for(int i=0;i<arr.length;i++){
            while(true){
                Node node = q.poll();
                if(node.num == arr[i]){
                    if(node.idx == location){
                        answer = i;
                        break outer;
                    } else {
                        continue outer;
                    }
                } else {
                    q.add(node);
                }
            }
        }
        
        
        return answer+1;
    }
}
