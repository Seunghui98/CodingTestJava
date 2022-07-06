// 22. 07. 05. 화 - 레벨 2 - 완전탐색 - 소수 찾기

import java.util.*;

class Programmers_소수찾기_JHJ {
    
    private int cnt_numbers, array_numbers[];
    private Set<Integer> primes;
    private boolean visited[];

    public int solution(String numbers) {
        int answer = 0;

        primes = new HashSet<>();
        cnt_numbers = numbers.length();
        array_numbers = new int[cnt_numbers];
        visited = new boolean[cnt_numbers];

        // 1. 각 숫자로 나누기 
        for(int i = 0; i < cnt_numbers; i++) {
            array_numbers[i] = numbers.charAt(i) - '0';
        }

        Arrays.sort(array_numbers);
        // 2. 숫자를 이용하여 소수 리스트 만들기
        dfs(0, "");
        return answer = primes.size();
    }

    private void dfs(int depth, String number) {
        if(cnt_numbers <= depth) return;

        for(int i = 0; i < cnt_numbers; i++) {
            int checking_number = Integer.parseInt(number + array_numbers[i]);
            if(visited[i]) continue;

            if(isPrime(checking_number)) primes.add(checking_number);

            visited[i] = true;
            dfs(depth+1, number + array_numbers[i]);
            visited[i] = false;
        }
    }

    private boolean isPrime(int num) {
        if(num <= 1) return false;

        for(int i = 2; i <= Math.sqrt(num); i++) {
            if(num % i == 0) return false;
        }
        return true;
    }
}
