# 2021 KAKAO BLIND RECRUITMENT - 신규아이디 추천



# 해설

## 1. 아이디어

- 각 1~7단계 맞춰서 문자열 처리 구현
    - 1단계 : 대문자 → 소문자 변환시키는 메서드 toLowerCase() 사용
    - 2단계 : 허용 가능한 문자 (a~z, A~Z, -, _, .)에 해당되는 문자들을 StringBuilder에 넣어 다시 문자열로 만들기
    - 3단계 : replace() 함수로 연속되는 ‘..’ 제거 하기
    - 4단계 : substring() 함수를 활용하여 첫글자와 마지막 글자에 ‘.’ 존재한다면 제거 처리 → ** 만약 문자열이 공백일 수도 있으므로 길이 조건 필수
    - 5단계 : 만약 문자열이 공백이라면 ‘a’ 추가
    - 6단계 : substring() 메서드 활용해 만약 문자열 길이 16 넘는다면 15까지 줄이고 끝이 ‘.’ 라면 제거처리
    - 7단계 : 만약 문자열의 길이가 2 이하라면 마지막 문자열 3이 될 때까지 뒤에 붙여주기
    

## 2. 코드

```java
public class Kakao_BLIND_2021_1 {
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
```
