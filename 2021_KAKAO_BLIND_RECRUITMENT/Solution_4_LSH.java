// 2021 KAKAO BLIND RECRUITMENT - 합승 택시 요금
// 다익스트라
import java.util.*;

public class Solution_4_LSH {
    public static void main(String[] args) {
        int a = solution(6, 4, 6, 2, new int[][]{{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22
        }, {1, 6, 25}});
        System.out.println(a);
    }
    public static int INF = (int)1e9;
    public static int[] distance;
    public static int max_dis;
    public static int min;

    public static ArrayList<Node> mid;
    public static ArrayList<ArrayList<Node>> list;

    public static class Node implements Comparable<Node>{
        int x;
        int dis;
        public Node(int x, int dis){
            this.x = x;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node o) {
            return this.dis - o.dis;
        }
    }
    public static int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = 0;
        max_dis = 0;
        min = 0;
        distance = new int[n+1];
        list = new ArrayList<>();
        for(int i=0;i<n+1;i++){
            distance[i] = INF;
            list.add(new ArrayList<>());
        }

        for(int i=0;i<fares.length;i++){
            int aa = fares[i][0];
            int bb = fares[i][1];
            int cc = fares[i][2];
            list.get(aa).add(new Node(bb, cc));
            list.get(bb).add(new Node(aa, cc));
        }

        dijkstra(s);
        mid = new ArrayList<>();
        for(int i=1;i<=n;i++){
            mid.add(new Node(i, distance[i]));
        }

        max_dis = Math.max(distance[a], distance[b]);
        min = INF;

        Collections.sort(mid);

        for(Node node:mid){
            if(node.dis >= max_dis) continue;
            for(int i=0;i<n+1;i++) {
                distance[i] = INF;
            }

            dijkstra(node.x);
            if(distance[a] != INF && distance[b] != INF) {
                int dis = node.dis + distance[a] + distance[b];
                System.out.println(dis +" "+node.x);
                min = Math.min(min, dis);
            }

        }


        answer = min;
        return answer;
    }

    public static void dijkstra(int start){
        distance[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        while (!pq.isEmpty()){
            Node node = pq.poll();
            if(distance[node.x] < node.dis) continue;
            for(int i=0;i<list.get(node.x).size();i++){
                int idx = list.get(node.x).get(i).x;
                int dis = list.get(node.x).get(i).dis;
                int cost = dis + node.dis;
                if(distance[idx] > cost) {
                    distance[idx] = cost;
                    pq.add(new Node(idx, cost));
                }

            }
        }
    }
}



