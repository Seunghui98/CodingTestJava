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
			int index = history.pop()[1];
			result[index] = 'X';
		}

		return String.valueOf(result);
	}
}

public class Solution3_JHJ {

	public static void main(String[] args) {
		SolutionClass sol = new SolutionClass();
		System.out.println(sol.solution(8, 2, new String[] { "D 2","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C" }));
	}
}