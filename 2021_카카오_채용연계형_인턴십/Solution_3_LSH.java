// 2021 카카오 채용연계형 인턴십 - 표 편집
// 자료구조(우선순위 큐, 스택)
import java.util.PriorityQueue;
import java.util.Stack;


public class Solution_3_LSH {
    public static void main(String[] args){

    }

    public String solution(int n, int k, String[] cmd) {
        String answer = "";
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();
        Stack<Integer> delete = new Stack<>();

        for(int i=0;i<k;i++){
            left.offer(-i);
        }
        for(int i=k;i<n;i++){
            right.offer(i);
        }

        for(String c:cmd){
            if(c.length() > 1){
                String[] com = c.split(" ");
                int move = Integer.parseInt(com[1]);  // 이동 횟수
                // 위로 이동
                if(com[0].equals("U")){
                    for(int i=0;i<move;i++){
                        if(!left.isEmpty()){
                            right.offer(-left.poll());
                        }
                    }

                } else {
                // 아래로 이동
                    for(int i=0;i<move;i++){
                        if(!right.isEmpty()){
                            left.offer(-right.poll());
                        }
                    }
                }
            } else {
                // 삭제
                if(c.equals("C")){
                    delete.push(right.poll());
                    if(right.isEmpty()){
                        right.offer(-left.poll());
                    }
                } else { // 복구
                    int num = delete.pop();
                    if(right.peek() < num){
                        right.offer(num);
                    } else {
                        left.offer(-num);
                    }
                }
            }
        }

        StringBuffer sb = new StringBuffer("O".repeat(n));
        while (!delete.isEmpty()){
            sb.setCharAt(delete.pop(), 'X');
        }

        return sb.toString();
    }
}
