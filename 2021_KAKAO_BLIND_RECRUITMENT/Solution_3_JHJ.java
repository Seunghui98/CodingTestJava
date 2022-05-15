// 정확성 테스트 - 런타이 에러 / 효율서 테스 통과
// 22.05.15. - 2021 Kakao Blind - Lv2. 순위 검색

import java.util.*;

public class Kakao_2021B_lv2 {
    public static void main(String[] args) {

        String[] info = new String[]{"java backend junior pizza 150",
                "python frontend senior chicken 210", "python frontend senior chicken 150",
                "cpp backend senior pizza 260", "java backend junior chicken 80",
                "python backend senior chicken 50"};
        String[] query = new String[]{"java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200",
                "cpp and - and senior and pizza 250",
                "- and backend and senior and - 150",
                "- and - and - and chicken 100",
                "- and - and - and - 150"};
        int[] answer = solution(info, query);
        System.out.println(Arrays.toString(answer));
    }

    private static int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];

        // info 기반 키 생성
        HashMap<String, ArrayList<Integer>> hm = new HashMap<String, ArrayList<Integer>>();
        for (int i = 0, size = info.length; i < size; i++) {
            dfs(0, "", info[i].split(" "), hm);
        }

        // 각 key별 value 오름차순 정렬
        for (String key : hm.keySet()) {
            Collections.sort(hm.get(key));
        }

        // 조건을 만족하는 지원자가 몇 명인지 찾기
        for (int i = 0, size = query.length; i < size; i++) {
            String[] each = query[i].replace(" and ", " ").split(" ");
            int criteria = Integer.parseInt(each[4]);

            StringBuilder key = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                key.append(each[j].trim());
            }

            ArrayList<Integer> scores = hm.get(key.toString());
            int search_point = search(scores, criteria);
            answer[i] = hm.get(key.toString()).size() - search_point;
        }
        return answer;
    }

    // 지원자 정보 기반 가능한 모든 키 생성
    private static void dfs(int d, String str, String[] info, HashMap<String, ArrayList<Integer>> hm) {
        if (d == 4) {
            if (!hm.containsKey(str)) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(Integer.parseInt(info[4]));
                hm.put(str, list);
            } else {
                hm.get(str).add(Integer.parseInt(info[4]));
            }
            return;
        }

        dfs(d + 1, str + info[d], info, hm);
        dfs(d + 1, str + "-", info, hm);
    }

    // binary search 를 이용한 시작점 찾기
    private static int search(ArrayList<Integer> scores, int criteria) {

        int left = 0, right = scores.size()-1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (criteria > scores.get(mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
