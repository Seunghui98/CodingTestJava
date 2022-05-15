// 22.05.15. - 2021 Kakao Blind - Lv2. 순위 검색
// 정확성 테스트 통과 O - 효율성 테스트 통과 X

import java.util.*;

public class Kakao_2021B_lv2 {
    public static void main(String[] args) {

        String[] info = new String[] {"java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"};
        String[] query = new String[]{"java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"};
        int[] answer = solution(info, query);
        System.out.println(Arrays.toString(answer));
    }

    public static int[] solution(String[] info, String[] query) {

        int[] answer = new int[query.length];
        // input 분류
        ArrayList<String[]> infos = new ArrayList<>();
        for(int i = 0, size = info.length; i < size; i++) {
            String[] input = info[i].split(" ");
            infos.add(input);
        }

        // 점수에 따라 오름차순 정렬
        Collections.sort(infos, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                int item1 = Integer.parseInt(o1[4]);
                int item2 = Integer.parseInt(o2[4]);
                return item1 - item2;
            }
        });

        // 점수 배열 만들기
        int[] scores = new int[info.length];
        for(int i = 0, size = info.length; i < size; i++) {
            scores[i] = Integer.parseInt(infos.get(i)[4]);
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int q = 0, size = query.length; q < size; q++) {
            String[] order = query[q].replace(" and ", " ").split(" ");
            int score = Integer.parseInt(order[order.length-1]);
            int cnt = 0;

            // 점수를 기준으로 시작점 찾기
            int start_point = search(scores, score);

            for(int i = start_point, size_infos = infos.size(); i < size_infos; i++) {
                String[] each_info = infos.get(i);
                boolean flag = true;
                for(int j = 0; j < 4; j++) {
                    if(order[j].equals("-") || order[j].trim().equals(each_info[j]))
                        continue;
                    else {
                        flag = false;
                        break;
                    }
                }

                if(flag)
                    cnt++;
            }
            answer[q] = cnt;
        }
        return answer;
    }

    // binary search 를 이용한 시작점 찾기
    public static int search(int[] scores, int criteria) {

        int left = 0, right = scores.length;

        while(left < right) {
            int mid = (left + right)/2;
            if(criteria > scores[mid]){
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
