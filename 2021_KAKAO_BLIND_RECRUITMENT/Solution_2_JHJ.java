package blind_recruit_2021;

/*
 * 2022. 04. 15. 금요일
 * 카카오 블라인드 채용 2021
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solution_2_JHJ {

	public static void main(String[] args) {
		String[] orders = new String[] { "XYZ", "XWY", "WXA" };
		int[] course = new int[] { 2, 3, 4 };

		String[] answer = solution(orders, course);
	}

	static HashMap<String, Integer> completeCourses;
	static boolean[] isCourse;
	static String items;

	public static String[] solution(String[] orders, int[] course) {
		String[] answer = {};

		isCourse = new boolean[course[course.length - 1] + 1];
		for (int i = 0, length = course.length; i < length; i++) {
			isCourse[course[i]] = true;
		}
		
		// orders 내 String 오름차순 정렬
		for(int i = 0; i < orders.length; i++) {
			char[] chars = orders[i].toCharArray();
			Arrays.sort(chars);
			orders[i] = String.valueOf(chars);
		}
		Arrays.sort(orders);
		completeCourses = new HashMap<String, Integer>();
		// ------------------- input end ---------------------

		for (int i = 0, length = orders.length; i < length; i++) {
			makeCourse(0, 0, orders[i].length(), course[course.length - 1], 0, orders[i], "");
		}

		List<Map.Entry<String, Integer>> entryList = new LinkedList<>(completeCourses.entrySet());
		entryList.sort(Map.Entry.comparingByKey());

		String[] result = new String[course[course.length - 1] + 1];

		// 각 course 길이 별 가장 많이 주문된 횟수
		int[] maxLength = new int[course[course.length - 1] + 1];
		for (int i = 0, size = entryList.size(); i < size; i++) {
			String key = entryList.get(i).getKey();
			int value = entryList.get(i).getValue();
			int keyLength = key.length();

//			System.out.println(key + " " + value);
			if (maxLength[keyLength] < value && value >= 2) {
				maxLength[keyLength] = value;
			}
		}

		// 각 course 길이 별 가장 많이 주문된 횟수와 같은 횟수를 가진 주문 추출
		ArrayList<String> answers = new ArrayList<>();
		for (int i = 0, size = entryList.size(); i < size; i++) {
			String key = entryList.get(i).getKey();
			int value = entryList.get(i).getValue();
			int keyLength = key.length();

			if (maxLength[keyLength] == value) {
				answers.add(key);
			}
		}

		answer = new String[answers.size()];
		for (int i = 0, length = answer.length; i < length; i++)
			answer[i] = answers.get(i);

		Arrays.sort(answer);
		System.out.println(Arrays.toString(answer));
		return answer;
	}

	/**
	 * 
	 * @param L 코스 길이
	 * @param start
	 * @param end
	 * @param limit 최대 코스 길이
	 * @param flag 해당 위치 메뉴가 선택되었는지 확인
	 * @param target 손님이 주문한 메뉴조합에서 코스 후보 추출
	 * @param key 코스 후보
	 */
	// 코스 메뉴 후보 모두 추출 (combination)
	private static void makeCourse(int L, int start, int end, int limit, int flag, String target, String key) {
		if (L > limit || L > target.length())
			return;

		if (isCourse[L]) {
			if (completeCourses.containsKey(key)) {
				completeCourses.put(key, completeCourses.get(key) + 1);
			} else {
				completeCourses.put(key, 1);
			}

			if (key.length() == limit)
				return;
		}

		for (int i = start; i < end; i++) {
			if ((flag & (1 << i)) != 0)
				continue;
			String originalKey = key;
			key += target.charAt(i);
			makeCourse(L + 1, i + 1, end, limit, (flag | (1 << i)), target, key);
			key = originalKey;
		}
	}
}
