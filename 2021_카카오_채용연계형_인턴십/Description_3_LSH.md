# 2021 카카오 채용연계형 인턴십 - 표 편집

유형: 승희

# 해설

## 1. 아이디어

- n의 범위가 최대 1,000,000 이므로 시간 복잡도 염두
    - 우선순위 큐, 스택 활용하여 구현 활용
    
    ![K-018.png](2021%20%E1%84%8F%E1%85%A1%E1%84%8F%E1%85%A1%E1%84%8B%2090fb5/K-018.png)
    
    - 현재 선택 행을 기준으로 윗 행번호 저장할 최대힙과 현재행 ~ 마지막 행까지 저장할 최소힙 구성
    - 현재 행에서 위를 이동하면 윗 줄의 최대값으로 변경되며, 밑으로 내려가면 밑의 줄의 최소값으로 변경되기 때문
    - 삭제처리한 행을 delete 스택으로 관리 → 복구 시 가장 최근에 삭제한 행 정보를 복구 하기 때문

 

## 2. 구현

- 명령어 (U, D, C, Z 별로 나누어서 구현)
    - U의 경우 위로 이동할만큼의 move 만큼 right (최소힙)에서 최소값 꺼내서 left(최대힙)에 넣어주기
    - D의 경우 아래로 이동할만큼의 move 만큼 left(최대힙)에서 최대값 꺼내서 right(최소힙)에 넣어주기
    - C의 경우 현재 행의 정보를 stack에 push하고 만약 삭제하려는 행이 마지막 행일 경우 삭제 한 후 현재행의 정보를 right에 담기 위해서 left(최대힙)에서 최대값 꺼내 right(최소힙)에 넣어주기
    - Z의 경우 현재 행과 delete 스택의 pop한 값을 비교해서 만약 복구하려는 행이 더 크다면 right(최소힙)에 넣어주고 복구하려는 행이 더 작다면 left(최대힙)에 넣어주기
- 마지막 삭제한 행인지 아닌지 나타낼 정답값을 출력 → 시간 복잡도 고려해서 구현해야함
    
    

## 3. 효율성 테스트 결과

![K-017.png](2021%20%E1%84%8F%E1%85%A1%E1%84%8F%E1%85%A1%E1%84%8B%2090fb5/K-017.png)

# 코드

```java
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
```
