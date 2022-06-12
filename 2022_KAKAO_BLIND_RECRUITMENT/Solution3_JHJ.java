import java.util.*;

// 22. 06. 12. 일 - 2022 카카오 블라인드 - 주차 요금 계산

public class Solution3_JHJ{
    public static void main(String[] args) {
//        int[] answer = solution(new int[]{180, 5000, 10, 600},
//                new String[]{"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"});

        int[] answer = solution(new int[]{1, 461, 1, 10}, new String[]{"00:00 1234 IN"});
        System.out.println(Arrays.toString(answer));
    }

    public static int[] solution(int[] fees, String[] records) {

        int basic_time = fees[0];
        int basic_fee = fees[1];
        int unit_time = fees[2];
        int unit_fee = fees[3];

        HashMap<String, Stack<Integer>> infos = new HashMap<>();

        // 각 차량 번호의 주차 시간 계산
        for (int i = 0, length = records.length; i < length; i++) {
            String[] record = records[i].split(" ");
            String[] time = record[0].split(":");
            int mins = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            if (record[2].equals("IN")) {
                if (infos.get(record[1]) == null) { // case 1) 기록된 번호판이 아닌 경우,
                    Stack<Integer> info = new Stack<Integer>();
                    info.push(mins);
                    infos.put(record[1], info);
                } else { // case 2) 기록된 번호판인 경우
                    infos.get(record[1]).push(mins);
                }
            } else { // -의 값이 stack에 있다면 이는 계산된 시간이다.
                int from = infos.get(record[1]).pop();
                int diff = (mins - from) * -1;
                infos.get(record[1]).push(diff);
            }
        }

        int[] answer = new int[infos.size()];
        // HashMap 정렬
        Map<String, Stack<Integer>> sortedInfos = new TreeMap<>(infos);

        // 차량 번호가 작은 순서대로 요금 계산
        Set keys = sortedInfos.keySet();
        Iterator iterator = keys.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Stack<Integer> info = sortedInfos.get(iterator.next());
            int time = 0;
            while (!info.isEmpty()) {
                if (info.peek() >= 0) { // 양의 정수라면 out되지 않고 차가 들어온 시각 의미
                    time += ((23 * 60) + 59) - info.pop();
                } else { // 음의 정수라면 차가 주차한 시간 의미
                    time += info.pop() * -1;
                }
            }
            // 주차 시간이 기본 시간 이내라면, 기본 요금만 부과
            int total_fee = basic_fee;
            if (time > basic_time) { // 주차 시간이 기본 시간을 넘어선다면 단위 시간 별 요금 계산
                total_fee += Math.ceil((float)(time - basic_time) / unit_time) * unit_fee;
            }
            answer[index++] = total_fee;
        }
        return answer;
    }
}
