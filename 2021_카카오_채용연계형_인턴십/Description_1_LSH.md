# 2021 카카오 채용연계형 인턴십 - 숫자 문자열과 영단어

유형: 문자열 처리

# 해설

## 1. replaceAll() 함수를 통해 문자열 치환 방법 사용

- String replaceAll(String regex, String replacement)

→ 해당 문자열 내 regex란 문자열이 존재하면 모두 다 replacement 문자열로 교환

 

## 2. 구현

- zero ~ nine은 숫자 형태는 배열의 인덱스의 숫자 같음으로 이를 이용

# 코드

```java
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
```
