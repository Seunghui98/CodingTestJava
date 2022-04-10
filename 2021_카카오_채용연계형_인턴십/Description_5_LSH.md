# 2021 카카오 채용연계형 인턴십 - 시험장 나누기



# 해설

## 1. 아이디어

- 아이디어 1: 나누는 경우의 수를 구해 그 때마다 가장 큰 그룹의 최솟값을 갱신하며 정답 구하기 → 정확성만 맞을 수 있음, 완전탐색의 경우 간선이 너무 많기 때문에 효율성 측면에서는 ... ㅠㅠ
- 아이디어 2: 파라메트릭 서치 + DFS
- 아이디어 3: 파라메트릭 서치 + 트리 DP
- 해설 출처
    - ([https://loosie.tistory.com/342](https://loosie.tistory.com/342))
    - ([https://www.youtube.com/watch?v=wI5T6cQiZIg](https://www.youtube.com/watch?v=wI5T6cQiZIg))

## ** 파라메트릭 서치

<aside>
💡 ***파라메트릭 서치 ?** 주어진 범위 내에서 원하는 값 또는 조건에 일치하는 값을 찾아내는 알고리즘*

</aside>

- 파라메트릭 서치 : 이진 탐색과 유사한 방법으로 최적화 문제를 결정 문제로 바꾸어 풀어야 함
    - 문제에서 주어진 것 : 트리를 k개 이하의 그룹으로 나누어서 얻을 수 있는 최소한의 최대 그룹의 크기는 얼마인가?
    - 파라메트릭 서치에서 변경된 결정 문제 : 최대 그룹의 크기가 L이하 일 때 트리를 k개 이하의 그룹으로 나눌 수 있는가?
        - 트리 k개 그룹으로 나누었을 때, 최대 그룹의 가중치 합이 (전체 가중치 합) / k보다는 작을 수 없기 때문에 가능한 L의 범위는 다음과 같음
            - (전체 가중치 합) / k ≤ L ≤ 전체 가중치의 합
            - 예시 : 다음과 같은 가중치의 트리를 가졌을 때 전체 가중치의 합 112 / 3 = (int)38 이면 최대 가중치의 합이 38보다 작을 수는 없다.
                
                ![IMG_A4EF0532A32E-1](https://user-images.githubusercontent.com/54658745/162617857-aa36f23f-f106-4ec1-9481-8844e4062748.jpeg)

                
            
        - k개 이하로 나눌 수 있다면 → 탐색의 최댓값의 범위를 감소시킨 후 다시 탐색...
        - k개 이하로 나눌 수 없다면 → 탐색의  최솟값의 범위를 증가시킨 후 다시 탐색...
    

## 2. 아이디어 2 - 파라메트릭 서치 + DFS

- DFS 풀이
![IMG_791262571390-1](https://user-images.githubusercontent.com/54658745/162617890-acc041f8-c548-481c-8a8a-18aa30b70eb4.jpeg)



- 파라메트릭 서치에서 설정된 L값을 기준으로 **트리의 후위 순회**하면서 그룹의 최대값이 L 이하인지를 체크하면서 그룹 갯수 나누기

<aside>
💡 **후위 순회(postorder traverse) : 하위 트리(왼쪽 서브 트리 → 오른쪽 서브 트리) 모두 방문 후 뿌리(root)를 방문**

</aside>

- 주어진 트리에서 각 그룹의 수를 x명으로 제한할 때 필요한 그룹의 수를 계산하는 방법
    - 경우 1 : a(현재 노드의 값) + b(왼쪽 서브트리의 그룹의 최대값) + c(오른쪽 서브트리의 그룹의 최대값) ≤ L 일 때 위의 노드로 넘어감
    - 경우 2: a(현재 노드의 값) + (왼쪽 or 오른쪽 서브트리의 그룹의 최대값의 최솟값) ≤ L 일때 그룹을 1개 추가 후 a+min(b,c) 를 위의 노드로 넘김
    - 경우 3: a+min(b,c) > L 일 때 그룹 2개를 추가 후 a를 위로 넘김
- 효율성
    
    ![스크린샷 2022-04-10 오후 7 45 13](https://user-images.githubusercontent.com/54658745/162617903-3b11216d-028f-4f11-84b5-69f2f8de01af.png)

    

- 코드

```jsx
public class Solution_1 {
    int l[] = new int[10005]; // 왼쪽 자식 노드 번호
    int r[] = new int[10005]; // 오른쪽 자식 노드 번호
    int x[] = new int[10005]; // 시험장의 응시 인원
    int p[] = new int[10005]; // 부모 노드 번호
    int n; // 노드의 수
    int root; // 루트

    int cnt = 0;
    public int dfs(int cur, int lim){
        int lv = 0; // 왼쪽 자식 트리에서 넘어오는 인원 수
        if(l[cur] != -1) lv = dfs(l[cur], lim);
        int rv = 0; // 오른쪽 자식 트리에서 넘어오는 인원 수
        if(r[cur] != -1) rv = dfs(r[cur], lim);
        // 경우 1
        if(x[cur] + lv + rv <= lim )
            return x[cur] + lv + rv; // 위 노드로 넘김
        // 경우 2
        if(x[cur] + Math.min(lv, rv) <= lim){
            cnt++; // 둘 중 큰 인원을 가진 곳을 그룹 지어 버림
            return x[cur] + Math.min(lv, rv);
        }

        // 경우 3
        cnt += 2; // 왼쪽 자식 트리와 오른쪽 자식 트리 각각을 따로 그룹을 만듦
        return x[cur];
    }

    public int solve(int lim){
        cnt = 0;
        dfs(root, lim);
        cnt++;  // 맨 마지막으로 남은 인원을 그룹을 지어야 함
        return cnt;
    }

    public int solution(int k, int[] num, int[][] links){
        n = num.length;
        for(int i=0;i<n;i++) p[i] = -1;
        for(int i=0;i<n;i++){
            l[i] = links[i][0];
            r[i] = links[i][1];
            x[i] = num[i];
            if(l[i] != -1) p[l[i]] = i;
            if(r[i] != -1) p[r[i]] = i;
        }
        for(int i=0;i<n;i++){
            if(p[i] == -1){
                root = i;
                break;
            }
        }

        // 파라메터리 서치
        int st = x[0];
        for(int i=0;i<n;i++) st = Math.max(st, x[i]);
        int en = (int)1e8;
        while (st < en){
            int mid = (st+en)/2;
            if(solve(mid) <= k) en = mid;
            else st = mid+1;
        }
        return st;
    }
}
```

## 2. 아이디어 2 - 파라메트릭 서치 + 트리 DP

- 트리 DP
    - DP 데이터 설정
        - dp[현재노드][0] : 현재 노드에 최대 그룹 인원(L)이하가 되도록 하기 위한 최소 그룹 갯수 저장
        - dp[현재노드][1] : 현재 노드에 최대 그룹 인원(L)이하가 되도록 하였을 때, 현재 노드를 포함한 서브 트리의 최솟값을 저장
- DP값 할당 경우의 수
    - 경우 1 : 현재 노드 값 + dp[l][1] (왼쪽 서브트리 최솟값) + dp[r][1] (오른쪽 서브트리 최솟값) ≤ L
    - 경우 2 : 현재 노드 값 + min(dp[l][1], dp[r][1]) ≤ L
    - 경우 3: 현재 노드 값 ≤ L
    - 경우 4 : 위 경우가 아니면 해당 트리는 L이하가 되도록 나눌 수 없음
    
    ![IMG_7499D7F583B9-1](https://user-images.githubusercontent.com/54658745/162617924-50733695-7296-446a-a872-968a6c7ab372.jpeg)
    ![IMG_A23C59583558-1](https://user-images.githubusercontent.com/54658745/162617926-f4a88b87-2300-4a12-be5b-9230c46d8224.jpeg)

    
- 서브 트리 구조에 대한 경우의 수
    - 리프 노드인 경우 : (left == -1 && right == -1)
        - 리프 노드는 후위 순에서 가장 처음으로 값을 할당 받을 노드이므로 초기값을 설정
    - 풀 노드인 경우 : (left ≠ -1 && right ≠ -1)
        - 왼쪽, 오른쪽 자식 트리가 존재하면 위의 경우처럼 그대로 진행
        - 만약 리프노드의 데이터가 L값 보다 크다면 해당 탐색은 불필요함으로 MAX값 넣어줌
    - 왼쪽 자식 또는 오른쪽 자식만 있는 경우 (left ≠ -1 && right == -1) or (left == -1 && right ≠ -1)
        - 한 쪽의 자식 노드만 존재하면 그 자식만 고려해서 계산 진행
- 효율성
    
 ![스크린샷 2022-04-10 오후 7 41 33](https://user-images.githubusercontent.com/54658745/162617951-88ed4ba7-0061-43b4-ac96-c87b162070a9.png)


    
     
    
- 코드

```jsx
import java.util.ArrayList;
import java.util.List;

public class Solution_2 {
    static class Node {
        int data;
        int left,  right;
        public Node(int data, int left, int right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    static int INF = (int)1e9, MAX_SECTION = 10001;
    static List<Node>[] list;
    static int[][] cost;

    public static int solution(int k, int[] num, int[][] links){
        int size = num.length;
        list = new ArrayList[size];
        for(int i=0;i<size;i++){
            list[i] = new ArrayList<>();
        }

        int sum = 0;
        boolean[] check = new boolean[size];
        for(int i=0;i<size;i++){
            int left = links[i][0];
            int right = links[i][1];
            if(left != -1) check[left] = true;
            if(right != -1) check[right] = true;
            list[i].add(new Node(num[i], left, right));
            sum += num[i];
        }

        int root = -1;
        for(int i=0;i<size;i++){
            if(!check[i]) root = i;
        }

        int left = sum/k;
        int right = sum;
        if(left == right) return right;
        else {
            while (left+1 < right){
                int mid = (left+right) / 2;
                cost = new int[size][2];
                traversal(root, mid);
                if(cost[root][0] <= k){
                    right = mid;
                } else {
                    left = mid;
                }
            }
            return right;
        }

    }
    static void traversal(int pos, int w){
        Node curNode = list[pos].get(0);
        int data = curNode.data;
        int l = curNode.left;
        int r = curNode.right;
        if(l != -1) traversal(l, w);
        if(r != -1) traversal(r, w);

        // leaf 노드
        if(l == -1 && r == -1){
            if(data <= w){
                cost[pos][0] = 1;
                cost[pos][1] = data;
            } else {
                cost[pos][0] = MAX_SECTION;
                cost[pos][1] = INF;
            }
        }
        // full 노드
        else if(l != -1 && r != -1){
            // 1) pos + 왼쪽 서브 트리 + 오른쪽 서브 트리 <= L
            // section = l + r -1
            if(data + cost[l][1] + cost[r][1] <= w) {
                cost[pos][0] = cost[l][0] + cost[r][0] -1;
                cost[pos][1] = data + cost[l][1]  + cost[r][1];
            }
            // 2) pos + min(왼쪽, 오른쪽) <= L
            // section = l + r
            else if(data + Math.min(cost[l][1], cost[r][1]) <= w){
                cost[pos][0] = cost[l][0] + cost[r][0];
                cost[pos][1] = data + Math.min(cost[l][1], cost[r][1]);

            }
            // 3) pos <= L
            else if(data <= w){
                cost[pos][0] = cost[l][0] + cost[r][0] + 1;
                cost[pos][1] = data;
            } else {
                cost[pos][0] = MAX_SECTION;
                cost[pos][1] = INF;
            }

        } else {
            // 왼쪽 자식만 있는 경우
            if(r == -1){
                // 1) pos + 왼쪽 트리 <= L
                if(data + cost[l][1] <= w){
                    cost[pos][0] = cost[l][0];
                    cost[pos][1] = data + cost[l][1];
                }
                // 2) pos <= w
                else if(data <= w){
                    cost[pos][0] = cost[l][0] + 1;
                    cost[pos][1] = data;
                } else {
                    cost[pos][0] = MAX_SECTION;
                    cost[pos][1] = INF;
                }
            }
            // 오른쪽 자식만 있는 경우
            if(l == -1){
                if(data  + cost[r][1] <= w){
                    cost[pos][0] = cost[r][0];
                    cost[pos][1] = data + cost[r][1];
                }
                else if(data <= w){
                    cost[pos][0] = cost[r][0] + 1;
                    cost[pos][1] = data;
                } else {
                    cost[pos][0] = MAX_SECTION;
                    cost[pos][1] = INF;

                }
            }

        }
    }
}
```
