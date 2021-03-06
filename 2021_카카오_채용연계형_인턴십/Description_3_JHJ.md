# 2021 채용 연계형 인턴십 - 표편집

> 레벨: 3 <br/>
> Date: 2022년 3월 31일 <br/>

</br>

## 1. 문제 요약 및 제한 사항 

- 표의 첫번째 칸(0)의 이전은 (n-1)번째 칸
- 표의 마지막 칸(n-1)의 다음은 0번째 칸
- U '#': '#'칸 위로 이동
- D '#': '#'칸 아래 이동
- C: 현재 행 삭제
  만약 현재 행이 맨 아래이면 선택된 행은 한 칸 위
- Z: 이전으로 되돌리기 

</br>

## 2. 아이디어 - (공통) Stack 사용

### 1) LinkedList 이용 ⭕ - 시간초과
- LinkedList<int[]>:
> int[0] 값: 삭제되었을 때의 위치 - 다시 삭제된 위치로 지워진 칸을 넣어주기 위함
<br>

> int[1] 값: 표 변화가 없을 때의 원래 위치 = 
> 	원래의 위치 기준 칸이 삭제되었는지의 여부를 출력 시 표시하기 위함

- 시간 초과 이유: LinkedList의 동적 할당의 특성을 활용하지 X 
  - 동적 할당의 특성이란? 삭제나 삽입시 O(1) 보장

### 2) LinkedList 이용 ❌
- Stack<Integer>: 삭제 당시 기준, 선택 행의 위치
- 표 변화가 없을 때의 선택 위치와 삭제 당시 선택 위치는 다르므로 **삭제 당시 기준의 위치**에 'X' 값 넣어주기

</br>

## 3. 효율성 테스트 결과
<img src="https://github.com/Seunghui98/CodingTestJava/blob/d52310cc11b2195b45ce008070b9a1d9122abd20/2021_%EC%B9%B4%EC%B9%B4%EC%98%A4_%EC%B1%84%EC%9A%A9%EC%97%B0%EA%B3%84%ED%98%95_%EC%9D%B8%ED%84%B4%EC%8B%AD/image/image2.PNG"/>

## 4. 코드 

### 1) 코드
- 다음 코드는 위치를 찾아 칸을 삭제해주는 것이기 때문에 해당 칸을 찾는데 O(n)의 시간이 걸린다.
- 만약 O(1)으로 삭제하기 위해서는 LinkedList의 한 노드가 앞 노드와 뒤 노드를 가리키도록 구성하면 된다. (동적 할당)
 ```java
 class Node {
      Node prev;
      Node next;
 }
 ```

 ---
 
- 다음 코드는 시간 초과가 난 코드이다. (테스트 케이스 통과, 효율성 검증 X)
```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

class SolutionClass {
	public String solution(int n, int k, String[] cmd) {
		LinkedList<Integer> table = new LinkedList<Integer>();
		char[] result = new char[n];
		Arrays.fill(result, 'O');
		Stack<int[]> history = new Stack<int[]>(); // 0: 삭제된 위치 - 1: 원래의 위치

		for (int i = 0; i < n; i++) {
			table.add(i);
		}

		int curIdx = k;
		for (String each : cmd) {
			char order = each.charAt(0);
			int cnt = 0;
			switch (order) {
			case 'D':
				cnt = Integer.parseInt(each.split(" ")[1]) % n;
				curIdx = (curIdx + cnt) >= n ? (curIdx + cnt) % n : (curIdx + cnt);
				break;

			case 'U':
				cnt = Integer.parseInt(each.split(" ")[1]) % n;
				curIdx = (curIdx - cnt) < 0 ? n + (curIdx - cnt) : (curIdx - cnt);
				break;

			case 'C':
				history.push(new int[] { curIdx, table.get(curIdx) });
				table.remove(curIdx);
				if (curIdx == --n)
					curIdx = n - 1;
				break;

			case 'Z':
				int[] item = history.pop();
				table.add(item[0], item[1]);
				if (item[0] <= curIdx)
					++curIdx;
				n++;
				break;
			}
		}

		// 출력
		while (!history.isEmpty()) { 
			int index = history.pop()[1]; // 삭제된 행의 원래 위치에 X 넣어주기
			result[index] = 'X'; 
		}

		return String.valueOf(result);
	}
}

public class Solution_3_JHJ {

	public static void main(String[] args) {
		SolutionClass sol = new SolutionClass();
		System.out.println(sol.solution(8, 2, new String[] { "D 2","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C" }));
	}
}
```

---

### 2) 코드
```java
import java.util.LinkedList;
import java.util.Stack;

class Solution {
	public String solution(int n, int k, String[] cmd) {

		Stack<Integer> history = new Stack<Integer>();

		int curIdx = k;
		for (String each : cmd) {
			char order = each.charAt(0);
			int cnt = 0;
			switch (order) {
			case 'D':
				cnt = Integer.parseInt(each.split(" ")[1]) % n;
				curIdx = (curIdx + cnt) >= n ? (curIdx + cnt) % n : (curIdx + cnt);
				break;

			case 'U':
				cnt = Integer.parseInt(each.split(" ")[1]) % n;
				curIdx = (curIdx - cnt) < 0 ? n + (curIdx - cnt) : (curIdx - cnt);
				break;

			case 'C':
				history.push(curIdx);
				if (curIdx == --n)
					curIdx--;
				break;

			case 'Z':
				int item = history.pop();
				if (item <= curIdx)
					++curIdx;
				n++;
				break;
			}
		}

		// 출력
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; i++)
			builder.append("O"); // 표에서 남은 칸은 삭제가 되지 않았으므로 'O'를 넣어준다.
		while (!history.isEmpty()) { 
			int index = history.pop(); // 삭제 당시의 선택 행 위치
			builder.insert(index, "X"); // 삭제 당시의 선택 행 위치에 'X' 끼워넣기 (결국 끼워넣다보면 원래 표의 크기가 된다)
		}

		return builder.toString();
	}
}
```
