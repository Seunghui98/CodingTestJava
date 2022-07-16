import java.util.*;

class Solution {
    public static ArrayList<ArrayList<Integer>> list;
    public static int[] distance;
    public static int INF = Integer.MAX_VALUE;
    
    public static class Node implements Comparable<Node> {
        int idx;
        int dis;
        public Node(int idx, int dis){
            this.idx = idx;
            this.dis = dis;
        }
        
        @Override
        public int compareTo(Node o){
            return this.dis - o.dis;
        }
    }
    
    public int solution(int n, int[][] edge) {
        int answer = 0;
        distance = new int[n+1];
        list = new ArrayList<>();
        for(int i=0;i<=n;i++){
            list.add(new ArrayList<>());
            distance[i] = INF;
        }
        
        for(int i=0;i<edge.length;i++){
            list.get(edge[i][0]).add(edge[i][1]);
            list.get(edge[i][1]).add(edge[i][0]);
        }
        
        dijkstra(n);
        
        int max_value = 0;
        ArrayList<Integer> max_list = new ArrayList<>();

        for(int i=2;i<=n;i++){
            if(distance[i] > max_value){
                max_value = distance[i];
                max_list.clear();
                max_list.add(i);
            } else if(distance[i] == max_value){
                max_list.add(i);
            }
        }
        answer = max_list.size();
        return answer;
    }
    
    public static void dijkstra(int n){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));
        distance[1] = 0;
        
        while(!pq.isEmpty()){
            Node node = pq.poll();
            if(node.dis < distance[node.idx]) continue;
            for(int i=0;i<list.get(node.idx).size();i++){
                int cost = node.dis + 1;
                if(cost < distance[list.get(node.idx).get(i)]){
                    distance[list.get(node.idx).get(i)] = cost;
                    pq.add(new Node(list.get(node.idx).get(i), cost));
                }
            }
        }
        
    }
}
