# 2021 카카오 블라인드 채용 - 메뉴 리뉴얼

> 레벨: 2 <br/>
> Date: 2022년 4월 15일 <br/>

</br>

## 1. 문제 요약 

- 스카피가 코스 메뉴를 만들고자 한다.
- 이전에 각 손님들이 주문할 때 가장 많이 함께 주문한 단품 메뉴들을 골라 코스 요리 메뉴로 구성한다.
- 단, 코스 메뉴는 **최소 2가지 이상** 단품 메뉴 구성 
- 최소 **2명 이상**의 손님으로부터 주문된 단품 메뉴 조합

</br>

### 1) 제한 사항 (결과 추출에서 헷깔렸던 부분)

1. 코스 길이별 가장 많이 주문된 코스를 추출한다. <br>
- 이때, 코스 길이에 해당하는 메뉴 수가 없을 경우 생략한다. 
2. 단, **가장 많이 주문된 코스의 수와 같은 주문 수를 가진 코스가 있을 경우 같이 추출**한다. <br>

#### 문제에 명시된 지문: <br>
**"가장 많이 함께 주문된 단품메뉴 조합에 따라 "스카피"가 만들게 될 코스요리 메뉴 구성 후보는 다음과 같습니다."** <br>

3. 출력할 때, 오름차순 정렬을 한다. 
4. 또한, 손님이 주문한 코스를 받아올 때, 오름차순 정렬을 해주어야 손님이 메뉴를 주문했는지 주문하지 않았는지 확인할 수 있다. 


### 2) 입력 값
|orders|course|	result|
|:---:|:---:|:---:|
|["ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"]	|[2,3,4]	|["AC", "ACDE", "BCFG", "CDE"]|
|["ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"]|	[2,3,5]|	["ACD", "AD", "ADE", "CD", "XYZ"]|
|["XYZ", "XWY", "WXA"]|	[2,3,4]|	["WX", "XY"]|

---
</br>

## 2. 아이디어
- 조합을 이용하여 코스 후보 추출
- 2번 이상 추출된 알파벳들을 선택하여 코스를 만들고자 했으나, 
- 손님이 주문한 단품메뉴 조합에 포함되었는지 여부를 일일이 검사 -> 복잡
 => 손님이 주문한 단품메뉴 조합에서 코스 후보를 추출하는 것으로 방식 변화
- 추출된 조합을 HashMap에 코스와 각 손님이 주문한 단품메뉴 조합(key)에 얼마나 포함되었는지의 여부(value) 저장
- 코스 길이 별 가장 많이 선택된 코스를 answer에 저장 (단, 같은 횟수로 선택한 같은 길이의 코스가 있을 경우에도 같이 저장)
- 최종 코스요리 메뉴 오름차순 정렬하여 반환

## 3. 코드  

```java
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
	 * 코스 메뉴 후보 모두 추출 (combination)
	 * @param L 코스 길이
	 * @param start
	 * @param end
	 * @param limit 최대 코스 길이
	 * @param flag 해당 위치 메뉴가 선택되었는지 확인
	 * @param target 손님이 주문한 메뉴조합에서 코스 후보 추출
	 * @param key 코스 후보
	 */
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
```

#4. 참고 자료
- HashMap 정렬하는 법
<br>
```java
// Map.Entry의 내장함수 이용
List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
entryList.sort(Map.Entry.comparingByValue());
entryList.sort(Map.Entry.comparingByKey());

// comparator 인터페이스 이용
entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	return o1.getValue() - o2.getValue();
    }
});

```

