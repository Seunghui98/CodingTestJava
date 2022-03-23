// 2022. 03. 24. - 2021 카카오 채용 연계형 인턴십
// 숫자 문자열과 영단어

public class Solution_JHJ {
    public int solution(String s) {
        s = s.replaceAll("zero", "0");
        s = s.replaceAll("one", "1");
        s = s.replaceAll("two", "2");
        s = s.replaceAll("three", "3");
        s = s.replaceAll("four", "4");
        s = s.replaceAll("five", "5");
        s = s.replaceAll("six", "6");
        s = s.replaceAll("seven", "7");
        s = s.replaceAll("eight", "8");
        s =  s.replaceAll("nine", "9");
        return Integer.parseInt(s);
    }
}
