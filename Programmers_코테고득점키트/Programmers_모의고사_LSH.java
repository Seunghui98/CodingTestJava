import java.util.*;
class Solution {
    public int[] solution(int[] answers) {

        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {2, 1, 2, 3, 2, 4, 2, 5};
        int[] arr3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

        int i1 = 0;
        int sum1 = 0;
        
        int i2 = 0;
        int sum2 = 0;
        
        int i3 = 0;
        int sum3 = 0;
        for(int a:answers){
            if(a == arr1[i1]){
                sum1++;
            }
            
            if(a == arr2[i2]){
                sum2++;
            }
            
            if(a == arr3[i3]){
                sum3++;
            }
            
            
            i1 = (i1+1) % 5;
            i2 = (i2+1) % 8;
            i3 = (i3+1) % 10;
        }
        
        int max = Math.max(Math.max(sum1, sum2), sum3);
        
        ArrayList<Integer> list = new ArrayList<>();
        if(sum1 == max){
            list.add(1);
        }
        
        if(sum2 == max){
            list.add(2);
        }
        
        if(sum3 == max){
            list.add(3);
        }
        
        int[] answer = new int[list.size()];
        for(int l=0;l<list.size();l++){
            answer[l] = list.get(l);
        }
        return answer;
    }
}
