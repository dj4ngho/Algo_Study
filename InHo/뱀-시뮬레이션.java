import java.util.HashMap;
import java.util.Scanner;
/*
 * 더럽게 어려웠음 겨우 이해는 했는데 만약 조금 꼬아서 다른 문제 풀라고 하면 못 풀듯 - 사견
 * 
 * 뱀의 이동은
 * 1) 매초가 지나면 방향에 맞게 전진(상하좌우)
 * 2) 이동한 위치에 사과가 있다면 길이 증가
 * 3) 이동한 위치에 사과가 없다면 꼬리 역시 방향에 맞게 전진
 * 4) 이동한 위치가 맵 밖으로 벗어났거나 몸통을 만나면 게임 끝
 */
public class Main {
	static class snake{//뱀이 이동하는 것을 구조체로 저장
		int x;//x좌표
		int y;//y좌표
		int d;//방향

		public snake(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
		public void direction(int seconds, HashMap<Integer, Character> cd) {
			if(cd.containsKey(seconds)) {//1, 2, 3, 4 순서대로 좌, 상, 우, 하 이므로
				if(cd.get(seconds) == 'L') {//왼쪽으로 방향 바꿀 때
					d = d - 1 == 0 ? 4 : d - 1;//왼쪽으로 방향을 바꾸면 하, 우, 상, 좌 순서로 진행(-1씩 빼줌) & 범위를 벗어나 0이되면 4로 저장.
				}else if(cd.get(seconds) == 'D') {//오른쪽으로 방향 바꿀 때
					d = d + 1 == 5 ? 1 : d + 1;//오른쪽으로 방향을 바꾸면 좌, 상, 우, 하 순서로 진행(1씩 증가) & 범위를 벗어나 5가 되면 1로 저장
				}
			}
		}
	}
	static int[] nx = {0, -1, 0, 1};//좌 상 우 하 - 순서대로 %4 연산으로 방향 설정  
	static int[] ny = {-1, 0, 1, 0};//1 2 3 4

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();

		int[][] board = new int[N][N];//뱀이 움직일 보드

		for (int i = 0; i < K; i++) {
			board[sc.nextInt()-1][sc.nextInt()-1] = -1; //사과 있는 자리는 -1로 
		}

		int L = sc.nextInt();
		HashMap<Integer, Character> cd = new HashMap<>(); //방향 변경에 대한 정보 저장할 해시맵
		for (int i = 0; i < L; i++) {
			cd.put(sc.nextInt(), sc.next().charAt(0));//<정수형, 문자형>인 해시맵에 저장
		}

		int seconds = 0;//게임 진행 시간
		snake head = new snake(0, 0, 3);//머리
		snake tail = head;//꼬리 & 최초의 위치는 머리와 꼬리 모두 0,0에 위치(테스트 케이스 상에서는 1,1)

		while(true) {
			seconds++;//문제상에서 매초가 지날 때마다 방향이 바뀌는데 개인적으로 break문을 만나기 전에 시간을 증가시켜야한다고 생각함
			int next_x = 0;//다음으로 나아갈 위치의 행의 정보를 따로 저장
			int next_y = 0;//다음으로 나아갈 위치의 열의 정보를 따로 저장
			if(head.d == 1) { //왼쪽 방향으로 진행
				next_x = head.x + nx[0];
				next_y = head.y + ny[0];
			}else if(head.d == 2) {//윗쪽 방향으로 진행
				next_x = head.x + nx[1];
				next_y = head.y + ny[1];
			}else if(head.d == 3) {//오른쪽 방향으로 진행
				next_x = head.x + nx[2];
				next_y = head.y + ny[2];
			}else if(head.d == 4) {//아랫쪽 방향으로 진행
				next_x = head.x + nx[3];
				next_y = head.y + ny[3];
			}

			if(next_x >= 0 && next_x < N && next_y >= 0 && next_y < N && board[next_x][next_y] < 1) {//앞으로 나아가야할 위치가 board행렬 안에 있는지 && 이동할 위치가 1,2,3,4가 아닌 0(빈 공간), -1(사과)인지 확인
				int apple = board[next_x][next_y];// 앞으로 나아갈 위치의 값 : 0 or -1
				board[head.x][head.y] = head.d;//현재 위치에 방향의 값 저장(꼬리가 도착해서 나아갈 방향)
				
				head = new snake(next_x, next_y, head.d);//머리는 이동할 것이므로 새로운 객체를 생성해서 값 저장
				if(apple == 0) {//나아갈 위치의 값이 빈 곳이라면
					tail.d = board[tail.x][tail.y];//맨 처음에는 머리가 새로운 객체를 선언하여 이동하기 전의 값 : 0,0의 값 그 이후에는 아래 코드를 통해 꼬리의 위치값을 변경
					if(tail.d == 1) {//왼쪽방향
						board[tail.x][tail.y] = 0;
						tail.x = tail.x + nx[0];
						tail.y = tail.y + ny[0];
					}else if(tail.d == 2) {//위쪽방향
						board[tail.x][tail.y] = 0;
						tail.x = tail.x + nx[1];
						tail.y = tail.y + ny[1];
					}else if(tail.d == 3) {//오른쪽 방향
						board[tail.x][tail.y] = 0;
						tail.x = tail.x + nx[2];
						tail.y = tail.y + ny[2];
					}else if(tail.d == 4) {//아랫쪽방향
						board[tail.x][tail.y] = 0;
						tail.x = tail.x + nx[3];
						tail.y = tail.y + ny[3];
					}
				}
			}else {//board 밖으로 나갔거나, 몸통인 경우
				break;
			}
			head.direction(seconds, cd);//방향을 바꾸는 메소드 - 17번라인
		}
		System.out.println(seconds);
	}
}