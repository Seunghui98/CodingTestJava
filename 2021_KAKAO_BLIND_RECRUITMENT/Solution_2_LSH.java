// 2021 KAKAO BLIND RECRUITMENT - 메뉴 리뉴얼
// DFS(조합)

import java.util.*;

public class Solution_2_LSH {
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
        for(String order:orders){
            char[] chars = order.toCharArray();
            Arrays.sort(chars);
            order = new String(chars);
            for(int i=2;i<order.length()+1;i++){
                combination(0, 0, i, order, new int[i]);
            }

        }

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
                    if(m.getValue() == 1) continue;
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

