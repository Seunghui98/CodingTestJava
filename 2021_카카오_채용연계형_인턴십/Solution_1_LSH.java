// 2021_카카오_채용연계형_인턴십 - 숫자 문자열과 영단어
// 문자열 변환

public class Solution_1_LSH {
    public int solution(String s) {
       String[] words = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

       for(int i=0;i<words.length;i++){
           s = s.replaceAll(words[i], String.valueOf(i));
       }

        int answer = Integer.parseInt(s);
        return answer;
    }
}
