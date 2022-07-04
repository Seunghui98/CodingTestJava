// 22. 07. 04. 월 - 레벨 2 - String method (startsWith()) - 전화번호 목록 

import java.util.*;

class Programmers_전화번호목록_JHJ {
    public boolean solution(String[] phone_book) {
        boolean answer = true;

        int max_length = Integer.MIN_VALUE;

        // 1. 정렬 - 12, 123, 234 순으로 정렬 시 접두사로 포함하는 문자를 좀 더 빨리 찾을 수 있음
        Arrays.sort(phone_book); 
        
        // 2. 접두사로 포함하는지 체크
        out: for(int i = 0, length = phone_book.length; i < length; i++) {
            String phone = phone_book[i];
            int phone_length = phone.length();

            for(int j = i + 1; j < length; j++) {
                if(phone_book[j].length() <= phone_length) break;

                String target = phone_book[j].substring(0, phone_length); // 비교대상

                if(target.equals(phone)) {
                    answer = false;
                    break out;
                } else {
                    break;
                }
            }
        }

        return answer;
    }
}
