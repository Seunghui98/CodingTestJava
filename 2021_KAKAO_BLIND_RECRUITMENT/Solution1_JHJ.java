//22.07.10 - 카카오 2020 블라인드 - Lv.2 - 문자열 압축

import java.util.*;

class Solution1_JHJ {
    public static void main(String[] args) {
        System.out.println(solution("aaaaaaaaaaaaaaabbbbbbbbbbc"));
    }

    public static int solution(String s) {
        int answer = Integer.MAX_VALUE;

        Stack<String> slices = new Stack<>();
        int length = 1;
        while (length <= s.length()) {
            for (int i = 0; i < s.length(); i += length) {
                String subString = null;
                if (i + length < s.length()) {
                    subString = s.substring(i, i + length);
                } else { // 마지막 남은 문자열은 자르고자하는 길이보다 길이가 짧을 수 있기 때문에 예외처리를 해주었다. 
                    subString = s.substring(i, s.length());
                }

                if (slices.isEmpty()) { // 빈 스택일 경우 꼭대기가 없기 때문에 에러가 발생한다. 
                    // 1번 반복되는 문자를 #으로 대체한 이유는 추후 1이라는 문자를 없앨 때 10의 1도 없어질 수 있기 때문이다.
                    slices.push("#"); 
                    slices.push(subString);
                } else if (slices.peek().equals(subString)) { // 똑같은 문자열일 경우에 cnt를 증가시킨다. 
                    slices.pop();
                    String popped_cnt = slices.pop();
                    int cnt = 0;
                    if(popped_cnt.equals("#")) 
                        cnt = 1;
                    else cnt = Integer.parseInt(popped_cnt);
                    slices.push(""+ ++cnt);
                    slices.push(subString);
                } else { // 원래의 문자열에서 길이만큼 자르고 남은 문자열의 길이가 자르고자하는 길이보다 짧을 때는 stack에 해당 문자열을 그냥 넣어준다. 
                    slices.push("#");
                    slices.push(subString);
                }
            }
            
            // 자른 문자열의 개수를 파악한 후 최종 출력 문자열을 만든다, StringBuilder를 사용할 경우 공간복잡도 측면에서 이득이 있다. 
            String result = "";
            while (!slices.isEmpty()) {
                String popped_sentence = slices.pop();
                result = popped_sentence + result; // stack이기 때문에 거꾸로 문자열을 더해준다. 
            }
            result = result.replaceAll("#", "");
            
            // answer이 가지고 있는 문자열의 길이보다 짧은 경우에만 값이 대체된다. 
            answer = Math.min(answer, result.length());
            
            length++; // 자르고자하는 문자열의 길이를 1씩 증가시킨다. 
        }
        return answer;
    }
}
