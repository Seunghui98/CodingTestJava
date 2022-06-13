// 2022 KAKAO BLIND RECRUITMENT - k진수에서소수개수구하기
// 구현

import java.util.*;

class Solution2_LSH {
    public static void main(String[] args){
    }
    
    public int solution(int n, int k) {
        int answer = 0;
        String num = Integer.toString(n, k);
        String[] num_array = num.split("0");

        for(String s:num_array){
            if(s.equals("")) continue;
            long number = Long.parseLong(s);
            if(isPrime(number)){
                answer++;
            }
        }
        return answer;
    }
    
    public static boolean isPrime(long num){
        if(num == 1){
            return false;
        }
        
        if(num == 2) return true;
        
        for(int i=2;i<=(int)Math.sqrt(num);i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    
    
}
