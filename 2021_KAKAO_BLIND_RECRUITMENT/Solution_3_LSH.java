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
