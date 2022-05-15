# 2021 KAKAO BLIND RECRUITMENT - 순위 검색

유형: 승희

# 해설

## 1. 아이디어

- DFS(조합) + 구현
    - DFS(조합)
        - query 조건에 -라는 조건 때문에 info 을 활용하여 언어, 직군, 경력, 소울 푸드로 만들 수 있는 쿼리문의 조합을 다 만든다.
        - 예를 들어 “java backend junior pizza 150”의 경우 “——”, “java——”, “javabackend—”, ... ~ “javabackendjuniorpizza”의 문자열 경우를 다 만든 후  해당 문자열을 key값, 점수를 value로 된 hashMap에 넣어준다.
    - 구현
        - hashMap 안에 점수를 기준으로 오름차순 정렬을 한다.
        - 쿼리문을 하나씩 빼서 점수를 제외한 분을 “ and “을 “ “로, “-”을 “”으로 바꿔줘서 위에서 구한 모든 경우의 수 문자열의 형태와 동일하게 변경한다. 그리고 hashMap에서 해당 문자열과 같은 key값이 존재하는지 확인한다.
        - 만약 해당 query문에 존재하는 경우가 없다면 answer에 갯수를 0개로 설정한다.
        - 만약 해당 query문에 존재하는 경우가 있다면 이진탐색을 통해 해당 점수 이상이 된 조합이 몇 개 있는지를 구해서 answer에 값을 넣어준다.
    

## 2. 코드

```java
// 2021 KAKAO BLIND RECRUITMENT - 순위 검색
// DFS(조합) + 해쉬맵 + 정렬 + 이진탐색

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Solution_3_LSH {
    public static void main(String[] args) {
        String[] info = {"java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"};
        String[] query = {"java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"};
        int[] ans = solution(info, query);
        for(int a:ans){
            System.out.println(a);
        }
    }
  
    public static HashMap<String, ArrayList<Integer>> hashMap;
    public static int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        hashMap = new HashMap<>();
        // 해쉬맵 key = info의 조합, value = info의 점수 
        hashMap.put("", new ArrayList<>());
        // -------info를 활용하여 모든 부분집합 만드는 부분
       
        for(String s:info){
            String[] data = s.split(" ");
            int score = Integer.parseInt(data[4]);
            hashMap.get("").add(score);

            for(int i=1;i<=4;i++){
                dfs(0, 0, i, "", data, score);
            }

        }

        int cnt = 0;
        // 각 hashmap 안에 info 조합에 들어있는 점수들의 정렬하는 부분
        ArrayList<String> score_lst = new ArrayList<>(hashMap.keySet());
        for(int i=0;i<score_lst.size();i++){
            ArrayList<Integer> scoreList = hashMap.get(score_lst.get(i));
            Collections.sort(scoreList);
        }

        for(String str:query){
            String[] data = str.replaceAll(" and ", "").split(" ");
            int q_score = Integer.parseInt(data[data.length-1]);

            String q = data[0];

            q = q.replaceAll(" and ", "");
            q = q.replaceAll("-", "");
        
            // query 하나씩 뽑아서 해당 hashmap에 키(info 조합) 내에 점수들이 존재한다면 이진탐색으로 점수 이상의 개수 구하기
            ArrayList<Integer> list = hashMap.get(q);

            if(list == null){
                answer[cnt++] = 0;

            } else {

                int start = 0;
                int end = list.size()-1;
                while (start <= end){
                    int mid = (start+end) / 2;

                    if(list.get(mid) < q_score){
                        start = mid+1;
                    } else {
                        end = mid -1;
                    }
                }

                answer[cnt++] = (list.size()-start);
            }

        }

        return answer;
    }

    public static void dfs(int depth, int start, int r, String s, String[] data, int score){
        if(depth == r){

            if(hashMap.containsKey(s)){
                hashMap.get(s).add(score);
            } else {
                ArrayList<Integer> score_list = new ArrayList<>();
                score_list.add(score);
                hashMap.put(s, score_list);
            }
            return;
        }
        for(int i=start;i<4;i++){
            dfs(depth+1, i+1, r,s+data[i], data, score);
        }
    }
}
```
