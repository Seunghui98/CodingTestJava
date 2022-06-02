
// 22. 06. 02 - 2021 카카오 블라인드 - 광고 삽입

public class Solution {

    public static void main(String[] args) {
        System.out.println(solution("99:59:59", "25:00:00",
                new String[]{"69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"}));
    }

    private static String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";

        int playToSnds = timeToSnds(play_time);
        int advToSnds = timeToSnds(adv_time);

        int[] cnts = new int[playToSnds + 1]; // 끝나는 시간까지 포함하기 위해 + 1
        // logs 시간을 초로 바꾸어 기록하기
        for (String log : logs) {
            String[] splitStrs = log.split("-");
            cnts[timeToSnds(splitStrs[0])]++; // 시작 시간 +1
            cnts[timeToSnds(splitStrs[1])]--; // 끝나는 시간 -1
        }

        // 시청자 수(재생 수) 누적 기록
        for (int i = 1, length = cnts.length; i < length; i++) {
            cnts[i] += cnts[i - 1];
        }

        long maxCnt = Long.MIN_VALUE; // 누적 시간 초 (100시간 = 360,000초) * 로그개수(300,000) > int 범위
        int earliestSnd = 0; // 누적 재생 시간 중 가장 빠른 시작 시간

        // 광고 시간 범위 동안의 누적 재생 수가 많은 경우 중 가장 빠르 시간 구하기
        // i = 1이 아닌 i = 0으로 설정하 이유는 0초 부터 초가 시작하기 때문이다. (시작부터 광고가 재생될 수 있음)
        // i = 1을 해줄 경우 테스트에서 2개 정도 잘못되 결과르 도출한다. 이는 광고가 0초부터 시작될 수 있음을 의미
        for (int i = 0, length = cnts.length; i < length - advToSnds; i++) {
            long cntSum = 0;
            for (int j = i; j < i + advToSnds; j++) {
                cntSum += cnts[j];
            }
            if (maxCnt < cntSum) {
                maxCnt = cntSum;
                earliestSnd = i;
            }
        }

        // 초를 '시:분:초'의 형태로 바꾼다.
        int hour = earliestSnd / 3600;
        earliestSnd -= hour * 3600;
        int min = earliestSnd / 60;
        earliestSnd -= min * 60;
        answer = (hour < 10 ? "0" + hour : hour) + ":" + (min< 10? "0" + min : min) + ":" + (earliestSnd < 10 ? "0" + earliestSnd : earliestSnd);
        return answer;
    }

    private static int timeToSnds(String time) { // 시간을 초로 바꾼다.
        String[] splitStrs = time.split(":");
        int hour = Integer.parseInt(splitStrs[0]);
        int min = Integer.parseInt(splitStrs[1]);
        int snd = Integer.parseInt(splitStrs[2]);

        int answer = hour * 3600 + min * 60 + snd;
        return answer;
    }
}
