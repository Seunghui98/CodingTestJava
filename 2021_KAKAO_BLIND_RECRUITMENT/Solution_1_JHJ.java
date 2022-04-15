package blind_recruit_2021;

/* 
 * 2022.04.15. 신규 아이디 추천
 * 2021 카카오 블라인트 채용
 * 정규식 사용 여부 
 */

public class Solution_1_JHJ {

	public static void main(String[] args) {
		solution("z-+.^.");

	}

	public static String solution(String new_id) {
		String answer = "";

		// 1. 모든 대문자 소문자로 치환
		new_id = new_id.toLowerCase();

		// 2. 알파벳 소문자, 숫자, 빼기(-), 밑줄(_), 마침표(.)를 제외한 모든 문자를 제거
		new_id = new_id.replaceAll("[^\\w\\-.]*", "");

		// 3. 마침표(.)가 2번 이상 연속된 부분을 하나의 마침표(.)로 치환
		new_id = new_id.replaceAll("[.]+", ".");

		// 4. 마침표(.)가 처음이나 끝에 위치한다면 제거
		new_id = new_id.startsWith(".") ? new_id.substring(1) : new_id;
		new_id = new_id.endsWith(".") ? new_id.substring(0, new_id.length() - 1) : new_id;

		// new_id = new_id.replaceAll("^[.]|[.]$", "");

		// 5. new_id가 빈 문자열이라면, new_id에 "a"를 대입
		if (new_id.isEmpty() || new_id.equals(null))
			new_id = "a";

		// 6. new_id의 길이가 16자 이상이면, new_id의 첫 15개의 문자를 제외한 나머지 문자들을 모두 제거
		// 만약 제거 후 마침표(.)가 new_id의 끝에 위치한다면 끝에 위치한 마침표(.) 문자를 제거합니다.
		if (new_id.length() >= 16) {
			new_id = new_id.substring(0, 15);
			if (new_id.endsWith("."))
				new_id = new_id.replaceAll("[.]$", "");
		}

		// 7. new_id의 길이가 2자 이하라면, new_id의 마지막 문자를 new_id의 길이가 3이 될 때까지 반복해서 끝에 붙입니다.
		while (new_id.length() <= 2) {
			new_id += new_id.charAt(new_id.length() - 1);
		}

		System.out.println(new_id);
		return answer = new_id;
	}

}
