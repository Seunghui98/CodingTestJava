// 2021 KAKAO BLIND RECRUITMENT - 광고 삽입
// 구현

public class Solution_5_LSH {
    public static void main(String[] args) {

    }
    public static String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";

        long[] time = new long[str_to_time(play_time)+1];
        for(String l:logs){
            String[] t = l.split("-");
            time[str_to_time(t[0])] += 1;
            time[str_to_time(t[1])] -= 1;
        }

        for(int i=0;i<time.length-1;i++){
            time[i+1] += time[i];
        }

        for(int i=0;i<time.length-1;i++){
            time[i+1] += time[i];
        }

        long max_Time = time[str_to_time(adv_time)-1];
        long max_start = 0;

        for(int i=0;i+str_to_time(adv_time)<str_to_time(play_time);i++){
            long tmp = time[i+str_to_time(adv_time)] - time[i];
            if(tmp > max_Time){
                max_Time = tmp;
                max_start = i+1;
            }
        }

        answer = int_to_time(max_start);

        return answer;
    }


    public static int str_to_time(String time){
        String[] t = time.split(":");

        int h = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        int s = Integer.parseInt(t[2]);
        return h*3600 + m * 60 + s;
    }

    public static String int_to_time(long time){
        long h = time / 3600;
        time %= 3600;
        long m = time / 60;
        time %= 60;
        long s = time;
        String t = "";
        if(h < 10){
            t += ("0"+String.valueOf(h));
        } else {
            t += String.valueOf(h);
        }
        t += ":";
        if(m < 10){
            t += ("0"+String.valueOf(m));
        } else {
            t += String.valueOf(m);
        }
        t += ":";
        if(s < 10){
            t += ("0"+String.valueOf(s));
        } else {
            t += String.valueOf(s);
        }
        return t;
    }

}
