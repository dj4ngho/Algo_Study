
import java.util.Scanner;

public class Baek_17472_2 {// 다리만들기 2 retry
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {//지도에 입력값을 저장
				map[i][j] = sc.nextInt();
			}
		}
		//distinguish the island
		int n_th_island = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j] == 1) {
					map[i][j] = n_th_island;
					distinguish(i, j, n_th_island);
					n_th_island++;
				}
			}
		}
		/* TODO 다시 짜는 중...
		 * 지도에 섬 체크
		 * 다리비용 계산
		 * MST
		 * 근데 지금 스택오버플로우 터짐 ㅎㅎ
		 */
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				System.out.print(map[i][j]+" ");
			}System.out.println();
		}System.out.println("-------------------------");
	}
	static int N;
	static int M;
	static int[][] map;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static void distinguish(int x, int y, int n_th_island) {//distinguish the island start at 1
		for (int k = 0; k < 4; k++) {
			int ni = x + dr[k];
			int nj = y + dc[k];
			if(in_map(ni, nj) && map[ni][nj] == 1) {
				map[ni][nj] = n_th_island;
				distinguish(ni, nj, n_th_island);
			}
		}
	}
	static boolean in_map(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}
}