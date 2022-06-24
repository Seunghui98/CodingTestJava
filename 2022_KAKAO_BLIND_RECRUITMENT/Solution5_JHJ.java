import java.util.*;

// 22. 06. 23. 목 - 카카오 2022 블라인드 - Lv.3 - 양과늑대

public class Solution5_JHJ {

    public static void main(String[] args) {
        int result = solution(new int[]{0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0},
                new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}, {3, 7}, {4, 8}, {6, 9}, {9, 10}});
        System.out.println(result);
    }

    static ArrayList<Integer>[] tree;
    static int max = Integer.MIN_VALUE;

    public static int solution(int[] info, int[][] edges) {
        tree = new ArrayList[info.length];

        for (int[] edge : edges) {
            int parent = edge[0];
            int child = edge[1];
            if (tree[parent] == null) {
                tree[parent] = new ArrayList<>();
            }
            tree[parent].add(child);
        }

        // visit 라는 boolean 배열로 방문을 처리하지 않고 방문할 노드를 담고 있는 리스트를 만든다
        List<Integer> visit_list = new ArrayList<>();
        // 본격적으로 탐색을 시작하기 전, 현재 노드 0 (루트)를 방문할 노드에 추가한다.
        visit_list.add(0);
        // 탐색을 시작한다.
        dfs(0, 0, 0, visit_list, info);
        return max;
    }

    private static void dfs(int current, int sheep, int wolf, List<Integer> visit_list, int[] info) {

        // 방문할 노드가 양이라면 양의 수를 1 증가시킨다.
        // 반대의 경우 늑대의 수를 1 증가시킨다.
        if (info[current] == 0) sheep++;
        else wolf++;


        // 만약 현재 노드 시점에서 늑대의 수가 양의 수보다 많다면 더이상 탐색하지 않는다.
        if (wolf >= sheep) return;

        // 만약 현재 노드 시점에서 양의 수가 최대 양의 수보다 많으면 값을 갱신한다.
        max = Math.max(sheep, max);

        // visit_list를 복사하지 않고 쓸 경우, 같은 객체를 가리켜 visit_list의 값이 훼손될 수 있다.
        List<Integer> copied_list = new ArrayList<>();
        copied_list.addAll(visit_list);
        // 자식노드가 있다면 방문 리스트에 추가시킨다.
        if (tree[current] != null) {
            for (int each : tree[current])
                copied_list.add(each);
        }
        // 현재 탐색하려는 노드는 방문 리스트에서 제거한다.
        copied_list.remove(Integer.valueOf(current));

        // 차례대로 노드들을 방문한다.
        for (int next : copied_list) {
            dfs(next, sheep, wolf, copied_list, info);
        }
    }
}
