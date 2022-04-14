# 2021 KAKAO BLIND RECRUITMENT - 메뉴 리뉴얼



# 해설

## 1. 아이디어

- DFS(조합) + 구현
    - DFS(조합)
        - 손님이 주문한 단품메뉴로 메뉴 갯수 2개 이상인 조합 만들어서 combi_list에 넣기
        - 조합 구하기 전에 주문한 단품메뉴 조합(문자열)을 정렬 해줘야함
            - 안그러면 XY, YX 다른 식으로 조합 구해질 수 있음...
    - 구현
        - HashMap 활용해서 key = 조합메뉴, value = 모든 손님에게 주문된 횟수 를 구한다.
        - course 에서 각 단품 메뉴에 갯수만큼 만들어진 조합에서 가장 많이 주문된 메뉴를 ans_list에 넣어주고 만약 가장 많이 주문된 메뉴가 2개 이상이라면 그만큼 ans_list에 넣어준다.
        - 만약 해당 주문 조합이 모든손님에게서 총 1번 주문 됐으면 continue 처리 → ** 문제 조건
        - answer 배열에 넣기 전에 한번 오름차순 정렬 후 넣어준다.
    

## 2. 코드

```java
import java.util.*;

public class Kakao_BLIND_2021_2 {
    public static void main(String[] args) {
        String[] aa = solution(new String[]{"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"}, new int[]{2, 3, 4});
        for(String a:aa){
            System.out.println(a);
        }
    }
    public static ArrayList<String> combi_list;

    public static String[] solution(String[] orders, int[] course) {
        String[] answer = {};
        combi_list = new ArrayList<>();
        // 단품 메뉴 조합 구하는 곳
        for(String order:orders){
            // 조합 구하기 전에 손님에게 주문된 메뉴 구성 정렬
            char[] chars = order.toCharArray();
            Arrays.sort(chars);
            order = new String(chars);
            for(int i=2;i<order.length()+1;i++){
                combination(0, 0, i, order, new int[i]);
            }

        }
        // 각 조합 메뉴마다 모든 손님에게 얼만큼 주문됐는지 구하기 위해 hashmap 활용
        HashMap<String, Integer> menu = new HashMap<>();
        for(String c:combi_list){
            if(menu.containsKey(c)){
                menu.put(c, menu.get(c)+1);
            } else {
                menu.put(c, 1);
            }
        }
        ArrayList<String> ans_list = new ArrayList<>();

        for(int i=0;i<course.length;i++){
            ArrayList<String> list = new ArrayList<>();

            int cnt = 2;
            for(Map.Entry<String, Integer> m:menu.entrySet()){
                if(course[i] == m.getKey().length()){
                    // 총 주문 횟수가 1번인 메뉴 조합은 고려 X
                    if(m.getValue() == 1) continue;
                    // 해당 단품 메뉴의 갯수에서 최대 주문 횟수 가지는 메뉴 구하는 부분

                    if(m.getValue() > cnt){
                        list.clear();
                        cnt = m.getValue();
                        list.add(m.getKey());
                    } else if(m.getValue() == cnt){
                        list.add(m.getKey());
                    }
                }
            }

            for(String l:list){
                ans_list.add(l);
            }
        }

        Collections.sort(ans_list);
        String[] ans_array = new String[ans_list.size()];
        for(int i=0;i<ans_list.size();i++){
            ans_array[i] = ans_list.get(i);

        }
        answer = ans_array;
        return answer;
    }

    public static void combination(int depth, int start, int r, String order, int[] arr){
        if(depth == r){
            String c = "";
            for(int a:arr){
                c += order.charAt(a);
            }

            combi_list.add(c);
            return;
        }

        for(int i=start;i<order.length();i++){
            arr[depth] = i;
            combination(depth+1, i+1, r, order, arr);
        }
    }
}
```
