
class Solution1_JHJ {
    public String solution(int[] numbers, String hand) {
       String answer = "";

        int[][] keypads = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {-1, 0, -2}}; // -1: '*', -2: '#'
        int[] left = new int[]{3, 0}; // 왼쪽 엄지손가락 위치
        int[] right = new int[]{3, 2}; // 오른쪽 엄지손가락 위치

        for (int i = 0, length = numbers.length; i < length; i++) {
            int number = numbers[i]; // 선택된 숫자

            // left
            if (number != 0 && (number - 1) % 3 == 0) {
                left = new int[]{(number - 1) / 3, 0};
                answer += "L";
                continue;
            }
            // right
            if (number != 0 && number % 3 == 0) {
                right = new int[]{(number / 3) - 1, 2};
                answer += "R";
                continue;
            }

            // center

            int number_r = number == 0 ? 3 : (number - 2) / 3;
            int left_distance = Math.abs(left[0] - number_r) + Math.abs(left[1] - 1);
            int right_distance = Math.abs(right[0] - number_r) + Math.abs(right[1] - 1);

            if (left_distance == right_distance) {
                if (hand.equals("left")) {
                    left[0] = number_r;
                    left[1] = 1;
                    answer += "L";
                } else {
                    right[0] = number_r;
                    right[1] = 1;
                    answer += "R";
                }

            } else if (left_distance < right_distance) {
                left[0] = number == 0 ? 3 : (number - 2) / 3;
                left[1] = 1;
                answer += "L";
            } else {
                right[0] = number == 0 ? 3 : (number - 2) / 3;
                right[1] = 1;
                answer += "R";
            }
        }

        return answer;
    }
}
