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
			builder.append("O");
		while (!history.isEmpty()) {
			int index = history.pop();
			builder.insert(index, "X");
		}

		return builder.toString();
	}
}

public class Solution_3_2_JHJ {

	public static void main(String[] args) {
		Solution sol = new Solution();
		System.out.println(
				sol.solution(8, 2, new String[] { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C" }));
	}
}
