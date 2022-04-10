# 2021 채용 연계형 인턴십 - 시험장 나누기

> 레벨: 5 <br/>
> Date: 2022년 4월 10일 <br/>

</br>

## 1. 문제 요약 및 제한 사항 

- 하나의 노드는 하나의 시험장을 나타낸다.
- 각 노드마다 고유 번호(ID)가 있다.
- 값은시험장의 응시자 수다.
- 노드 사이의 간선은 노드가 서로 연결되어있음을 의미한다.
- **k개의 그룹으로 나누어 가장 큰 그룹의 인원을 최소화 시킨다.**
</br>

## 2. 아이디어 - Next Permutation 이용

### 1) 재귀를 이용하여 끊을 수 있는 간선 경우의 수를 구하였다. 
- 조합에서 nCr의 경우를 재귀를 이용하여 구할 때, r의 크기가 커질수록 계산량이 무시무시하게 늘어난다는 것이다. - 최적화 실패 원인
- r이 20까지는 재귀를 이용하여도 견딜만 하지만, r의 크기가 커진다면 다른 문제 접근 방법을 생각해보아야 한다.
<br>
- links 정보를 이용하여 edgeList를 만든 뒤, 부모 노드를 파악하였다. 
- 간선을 끊은 후 자신의 그룹에서 최상위 조상이 누구인지 파악하기 위해 findParent()라는 함수를 이용하였다.
- 조상을 파악한 뒤, 조상이 같으면 그룹의 인원수를 더한다.
- 그룹의 인원수 중 가장 인원이 많은 경우가 k-1개의 간선을 자른 다른 경우와 비교했을 때보다 인원이 적으면 가장 큰 그룹의 인원의 최솟값을 변경한다. 
- 위 과정을 k-1개의 간선의 경우의 수가 끝날때까지 반복한다. 

### 2) Next permutation 이용 - 재귀 사용을 줄이기 위해 Next Permutation을 이용하였다.  
- 재귀의 사용을 줄이기 위해 Next Permutation을 이용하였다.
- nCr에서 r의 양이 많을 때, Next Permutation이 유용할 수 있지만, 항상 시간적 이득을 볼 수 있는 것은 아니다.

</br>

## 3. 효율성 테스트 결과
### 1) Next Permutation을 사용하지 않은 경우

<img width = "300" height = "400" src = "https://github.com/Seunghui98/CodingTestJava/blob/08da6646f0caa3b5fc3d50d7523dbe3a175d50f6/2021_%EC%B9%B4%EC%B9%B4%EC%98%A4_%EC%B1%84%EC%9A%A9%EC%97%B0%EA%B3%84%ED%98%95_%EC%9D%B8%ED%84%B4%EC%8B%AD/image/%EC%8B%9C%ED%97%98%EC%9E%A5%EB%82%98%EB%88%84%EA%B8%B0_%EA%B2%B0%EA%B3%BC1.JPG">

### 2) Next Permutation을 사용한 경우

<img width = "300" height = "400" src = "https://github.com/Seunghui98/CodingTestJava/blob/08da6646f0caa3b5fc3d50d7523dbe3a175d50f6/2021_%EC%B9%B4%EC%B9%B4%EC%98%A4_%EC%B1%84%EC%9A%A9%EC%97%B0%EA%B3%84%ED%98%95_%EC%9D%B8%ED%84%B4%EC%8B%AD/image/%EC%8B%9C%ED%97%98%EC%9E%A5%EB%82%98%EB%88%84%EA%B8%B0_%EA%B2%B0%EA%B3%BC2.JPG">

## 4. 효율성 개선 방법 - Parametric Search 사용
<img width = "700" height = "400" src = "https://github.com/Seunghui98/CodingTestJava/blob/6a3ba8fa72d622ae45cdac39502e36123dd9fa64/2021_%EC%B9%B4%EC%B9%B4%EC%98%A4_%EC%B1%84%EC%9A%A9%EC%97%B0%EA%B3%84%ED%98%95_%EC%9D%B8%ED%84%B4%EC%8B%AD/image/Parametric%20Search.jpg">

## 5. 코드 
### 1) Next permutation을 사용하지 않은 경우 
 ```java
import java.util.ArrayList;
import java.util.Arrays;


/*
 * 2022. 04. 10. - 2021 카카오 채용 연계형 인턴십 카카오 - 시험장 나누기 (레벨 5)
 * Parametic Search를 사용하지 않은 경우 
 * dfs로 해결 - 정확성 테스트 통과 / 효율성 테스트 통과 X
 * 
 */

public class Solution_5_JHJ {

	static int parents[], minCnt = Integer.MAX_VALUE;
	static boolean visited[];
	static ArrayList<Edge> edges;

	public static void main(String[] args) {
		int k = 3;
		int[] num = { 12, 30, 1, 8, 8, 6, 20, 7, 5, 10, 4, 1 };
		int[][] links = { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { 8, 5 }, { 2, 10 }, { 3, 0 }, { 6, 1 },
				{ 11, -1 }, { 7, 4 }, { -1, -1 }, { -1, -1 } };
		System.out.println(solution(k, num, links));

	}

	// k: 만들고자 하는 그룹 수
	// num: 각 노드 아이디별 인구 수 저장
	// links: 각 노드의 자식에 관한 정보
	public static int solution(int k, int[] num, int[][] links) {

		// 1. 간선을 참고하여 EdgeList를 만든다.
		// 자식 노드가 -1인 경우 EdgeList에 추가하지 않는다.
		edges = new ArrayList<Edge>();

		// 2. 부모 배열을 만들어 부모 노드를 저장한다.
		parents = new int[num.length]; // 각 노드의 부모에 관한 정보
		Arrays.fill(parents, -1);

		for (int i = 0, size = links.length; i < size; i++) {
			for (int j = 0; j < 2; j++) {
				if (links[i][j] != -1) {
					edges.add(new Edge(i, links[i][j]));
					parents[links[i][j]] = i;
				}
			}
		}
		
		// 3. 경우의 수 구하기 - 경우의 수가 구해졌으면 일 처리하기
		visited = new boolean[edges.size()];
		dfs(0, 0, k, num);

		int answer = minCnt;
		return answer;
	}

	// 어떤 edge를 선택하여 자를 것인지를 선택하는 메소드 
	private static void dfs(int L, int start, int k, int[] num) {
		if (L >= k)
			return;

		// k-1 개의 자를 edge를 모두 골랐다면, 다음의 과정을 수행한다. 
		if (L == k - 1) { 
			
			// 원래의 부모 노드에 대한 정보를 유지해야 하므로, 노드의 부모를 담은 배열을 복사한다. 
			int[] copiedParents = parents.clone();
			
			// k-1 개의 자를 노드의 부모와 자식 관계를 끊는다. 
			for (int i = 0, size = edges.size(); i < size; i++) {
				Edge e = edges.get(i);
				if (visited[i]) {
					copiedParents[e.child] = -1;
				}
			}

			// k-1 개의 간선을 자른 뒤, 연결된 노드들 중 최상위 부모노드를 찾는다.
			for (int i = 0, length = num.length; i < length; i++) {
				copiedParents[i] = findParent(i, copiedParents);
			}

			// 각 노드가 부모노드라 가정하고, 노드의 자식들을 모두 합한 값을 담기 위한 배열을 만든다.
			int[] groups = new int[num.length];
			
			// 자신이 가지고 있는 인원을 먼저 세팅한다. 
			groups = num.clone();
			int max = Integer.MIN_VALUE; // 인원이 가장 많은 그룹의 인원 수 
			
			// 각 노드가 부모 노드라 가정하고, 노드의 자식들을 모두 합하기 위한 과정을 수행한다. 
			for (int i = 0, length = num.length; i < length; i++) {
				
				if (copiedParents[i] != i) {
					groups[copiedParents[i]] += num[i];
				}
				max = Math.max(groups[copiedParents[i]], max);
			}
			
			// k-1개의 그룹을 여러 경우의 수로 나누었을 때, 인원이 가장 많은 그룹의 인원 수가 최소인 인원을 저장한다.  
			minCnt = Math.min(max, minCnt);

			return;
		}

		for (int i = start, size = edges.size(); i < size; i++) {
			if (visited[i])
				continue;
			visited[i] = true;
			dfs(L + 1, i + 1, k, num);
			visited[i] = false;
		}
	}

	// 최상위 부모노드를 찾는다. 
	private static int findParent(int node, int[] copiedParents) {
		// 부모를 가지고 있지 않거나(-1), 해당 노드의 부모노드가 자기 자신인 경우 자신을 반환한다. 
		if (copiedParents[node] == -1 || copiedParents[node] == node) {
			return node;
		} else {
			return findParent(copiedParents[node], copiedParents);
		}
	}

	private static class Edge {
		int parent;
		int child;

		Edge(int parent, int child) {
			this.parent = parent;
			this.child = child;
		}
	}


```

### 2) Next permutation을 사용한 경우
``` java
import java.util.ArrayList;
import java.util.Arrays;

/*
 * 2022. 04. 10. - 2021 카카오 채용 연계형 인턴십 카카오 - 시험장 나누기 (레벨 5)
 * Parametic Search를 사용하지 않은 경우 
 * dfs로 해결 - 정확성 테스트 통과 / 효율성 테스트 통과 X
 * 
 */

public class Solution_5_JHJ2 {

	static int parents[], minCnt = Integer.MAX_VALUE;
	static ArrayList<Edge> edges;

	public static void main(String[] args) {
		int k = 1;
		int[] num = { 6, 9, 7, 5 };
		int[][] links = { { -1, -1 }, { -1, -1 }, { -1, 0 }, { 2, 1 } };
		System.out.println(solution(k, num, links));

	}

	// k: 만들고자 하는 그룹 수
	// num: 각 노드 아이디별 인구 수 저장
	// links: 각 노드의 자식에 관한 정보
	public static int solution(int k, int[] num, int[][] links) {

		// 1. 간선을 참고하여 EdgeList를 만든다.
		// 자식 노드가 -1인 경우 EdgeList에 추가하지 않는다.
		edges = new ArrayList<Edge>();

		// 2. 부모 배열을 만들어 부모 노드를 저장한다.
		parents = new int[num.length]; // 각 노드의 부모에 관한 정보
		Arrays.fill(parents, -1);

		for (int i = 0, size = links.length; i < size; i++) {
			for (int j = 0; j < 2; j++) {
				if (links[i][j] != -1) {
					edges.add(new Edge(i, links[i][j]));
					parents[links[i][j]] = i;
				}
			}
		}

		// 3. 경우의 수 구하기 - 경우의 수가 구해졌으면 일 처리하기
		int[] set = new int[edges.size()];
		// 0보다 큰 값으로 R개를 맨 뒤부터 채운다.
		int cnt = 0;
		int limit = num.length - k > k - 1 ? k - 1 : num.length - k;
		int flag = limit == num.length - k ? 0 : 1;
		while (cnt < limit)
			set[cnt++] = 1;
		Arrays.sort(set); // 오름차순으로 만들기 위해서

		do {
			// 원래의 부모 노드에 대한 정보를 유지해야 하므로, 노드의 부모를 담은 배열을 복사한다.
			int[] copiedParents = parents.clone();
			for (int i = 0, size = edges.size(); i < size; i++) {

				Edge e = edges.get(i);

				if (set[i] == flag) {
					copiedParents[e.child] = -1;
				}
			}

			// k-1 개의 간선을 자른 뒤, 연결된 노드들 중 최상위 부모노드를 찾는다.
			for (int i = 0, length = num.length; i < length; i++) {
				copiedParents[i] = findParent(i, copiedParents);
			}

			// 각 노드가 부모노드라 가정하고, 노드의 자식들을 모두 합한 값을 담기 위한 배열을 만든다.
			int[] groups = new int[num.length];

			// 자신이 가지고 있는 인원을 먼저 세팅한다.
			groups = num.clone();
			int max = Integer.MIN_VALUE; // 인원이 가장 많은 그룹의 인원 수

			// 각 노드가 부모 노드라 가정하고, 노드의 자식들을 모두 합하기 위한 과정을 수행한다.
			for (int i = 0, length = num.length; i < length; i++) {

				if (copiedParents[i] != i) {
					groups[copiedParents[i]] += num[i];
				}
				max = Math.max(groups[copiedParents[i]], max);
			}

			// k-1개의 그룹을 여러 경우의 수로 나누었을 때, 인원이 가장 많은 그룹의 인원 수가 최소인 인원을 저장한다.
			minCnt = Math.min(max, minCnt);

		} while (nextPermutation(set));

		int answer = minCnt;
		return answer;
	}

	private static boolean nextPermutation(int[] set) {

		int N = set.length;
		// 1) 꼭대기 찾기
		int i = N - 1;
		while (i > 0 && set[i - 1] >= set[i])
			--i;

		// i==0이면, 만들 수 있는 형태의 순열이 더이상 없다는 뜻 (만들 수 있는 가장 큰 순열을 다 만들었다)
		if (i == 0)
			return false;

		// 2) 교환 위치에 교환할 값 찾기
		int j = N - 1;
		while (set[i - 1] >= set[j])
			--j;

		// 3) 교환할 위치와 교환할 위치보다 큰 값 바꾸기
		swap(set, i - 1, j);

		// 4) 꼭대기부터 맨 뒤까지 오름 차순 정렬
		int k = N - 1;
		while (i < k) {
			swap(set, i++, k--);
		}

		return true;
	}

	private static void swap(int[] set, int i, int j) {
		int temp = set[i];
		set[i] = set[j];
		set[j] = temp;
	}

	// 최상위 부모노드를 찾는다.
	private static int findParent(int node, int[] copiedParents) {
		// 부모를 가지고 있지 않거나(-1), 해당 노드의 부모노드가 자기 자신인 경우 자신을 반환한다.
		if (copiedParents[node] == -1 || copiedParents[node] == node) {
			return node;
		} else {
			return findParent(copiedParents[node], copiedParents);
		}
	}

	private static class Edge {
		int parent;
		int child;

		Edge(int parent, int child) {
			this.parent = parent;
			this.child = child;
		}
	}
}


```
