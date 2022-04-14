// 2021 KAKAO BLIND RECRUITMENT - 신규 아이디 추천
// 문자열

public class Solution_1_LSH {
    public static void main(String[] args) {
        System.out.println(solution("...!@BaT#*..y.abcdefghijklm"));
    }
    public static String solution(String new_id) {
        String answer = "";
        // 1단계
        new_id = new_id.toLowerCase();
        // 2단계
        char[] id_arr = new_id.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char i:id_arr){
            if((i >= 'a' && i <= 'z') || (i >= '0' && i <= '9') || i =='-' || i =='_' || i =='.'){
                sb.append(i);
            }
        }

        new_id = sb.toString();
        // 3단계
        while (new_id.contains("..")){
            new_id = new_id.replace("..", ".");
        }
        // 4단계
        if(new_id.length() > 0) {
            if (new_id.charAt(0) == '.') {
                new_id = new_id.substring(1, new_id.length());
            }
        }
        if(new_id.length() > 0) {
            if (new_id.charAt(new_id.length() - 1) == '.') {
                new_id = new_id.substring(0, new_id.length() - 1);
            }
        }
        // 5단계
        if(new_id.length() == 0){
            new_id += 'a';
        }

        // 6단계
        if(new_id.length() >= 16){
            new_id = new_id.substring(0, 15);
            if(new_id.charAt(new_id.length()-1) == '.'){
                new_id = new_id.substring(0, new_id.length()-1);
            }
        }

        // 7단계
        if(new_id.length() <= 2){
            char last = new_id.charAt(new_id.length()-1);
            while (new_id.length() <= 2){
                new_id += last;
            }
        }
        answer = new_id;

        return answer;
    }
}
