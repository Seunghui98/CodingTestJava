// 2022 KAKAO BLIND RECRUITMENT - 주차 요금 계산
// HashMap + 구현
import java.util.*;

class Solution {
    public static class Park {
		int time = 0;
        int in_time = -1;
        boolean isOut = false;
        public Park(){}
		public Park(int time, int in_time) {
			this.time = time;
            this.in_time = in_time;
		}
		
	}
    
    public int[] solution(int[] fees, String[] records) {
        
         ArrayList<String> car = new ArrayList<String>();
	        HashMap<String, Park> inout = new HashMap<String, Park>();

	        for(int i=0;i<records.length;i++){
	            String[] str = records[i].split(" ");
	            if(str[2].equals("IN")){
	                Park park;
	                int time = str_to_time(str[0]);
	                if(inout.containsKey(str[1])){
	                    park = inout.get(str[1]);
	                } else {
	                    car.add(str[1]);
	                    park = new Park();
	                }
	                park.in_time = time;
	                park.isOut = false;
	                inout.put(str[1], park);
	            } else {
	                int time = str_to_time(str[0]);
	                Park park = inout.get(str[1]);
	                int diff = time - park.in_time;
	                park.time += diff; 
	                park.isOut = true;
	            }
	        }
	        	int[] answer = new int[car.size()];
	            Collections.sort(car);
	            
	
	            for(int i=0;i<car.size();i++){
	            	String car_num = car.get(i);
	                if(inout.get(car_num).isOut){
	                	answer[i] = cal_money(inout.get(car_num).time, fees);
	                } else {
	                    int in_time = inout.get(car_num).in_time;
	                    int out_time = str_to_time("23:59");
	                    answer[i] = cal_money(inout.get(car_num).time + out_time-in_time, fees);
	                }
	                System.out.println(answer[i]);
	            }
	       
	        return answer;
	    }
	    
	    public static int cal_money(int diff, int[] fees){
	        int money = 0;
	        if(diff > fees[0]){
	            int diff_default = diff - fees[0];
	            if(diff_default % fees[2] == 0){
	                return fees[1] + (diff_default / fees[2]) * fees[3];
	            } else {
	                return fees[1] + ((diff_default / fees[2])+1) * fees[3];
	            }
	        } else {
	            return fees[1];
	        }
	    }
	    
	    public static int str_to_time(String t){
	        String[] str = t.split(":");
	        return Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
	    }
}
