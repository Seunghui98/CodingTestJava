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
}


//테스트 1 〉	통과 (0.03ms, 76.2MB)
//테스트 2 〉	통과 (0.22ms, 79.1MB)
//테스트 3 〉	통과 (0.21ms, 81.2MB)
//테스트 4 〉	통과 (0.29ms, 90.6MB)
//테스트 5 〉	통과 (0.92ms, 76MB)
//테스트 6 〉	통과 (5.40ms, 79.6MB)
//테스트 7 〉	통과 (14.42ms, 97.6MB)
//테스트 8 〉	통과 (5.06ms, 74MB)
//테스트 9 〉	통과 (57.38ms, 108MB)
//테스트 10 〉	통과 (0.37ms, 71.3MB)
//효율성  테스트
//테스트 1 〉	실패 (시간 초과)
//테스트 2 〉	실패 (시간 초과)
//테스트 3 〉	실패 (시간 초과)
//테스트 4 〉	실패 (시간 초과)
//테스트 5 〉	실패 (시간 초과)
//테스트 6 〉	실패 (시간 초과)
//테스트 7 〉	실패 (시간 초과)
//테스트 8 〉	실패 (시간 초과)
//테스트 9 〉	실패 (시간 초과)
//테스트 10 〉실패 (시간 초과)
