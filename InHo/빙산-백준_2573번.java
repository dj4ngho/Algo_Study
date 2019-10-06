import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BeakJoon_2573 {
	static class Point{
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static int[] nr = {-1, 1, 0, 0};
	static int[] nc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] iceberg = new int[N][M];//빙산을 저장할 배열
		
		int max = 0;
		for (int i = 0; i < iceberg.length; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < iceberg[i].length; j++) {
				iceberg[i][j] = Integer.parseInt(st.nextToken());
				max = Math.max(max, iceberg[i][j]);
			}
		}
		
		int count = 0; //결과값
		int mass = 0; //덩어리의 개수
		int[][] melt = new int[N][M];//녹는 양을 따로 저장할 배열

		if(max != 0){//모두 바닷물(0)이 아닌 경우
			while(mass < 2) {//반복문을 벗어날 조건은 덩어리가 2개 이상, 미만이면 계속 반복
				boolean check = false;//빙산이 남아있는지 체크하는 boolean
				mass = 0;// 덩어리의 개수 세기 위해 초기화
				count++;//결과값
				
				//모든 이중 for문이 1부터 N-1, 1부터 M-1인 이유는 첫행, 첫열, 마지막 행, 마지막 열이 모두 0이기 때문
				for (int i = 1; i < N-1; i++) {
					for (int j = 1; j < M-1; j++) {
						if(iceberg[i][j] > 0) {
							check = true;//아직 녹을 빙산이 남아있다는 뜻
							for (int k = 0; k < 4; k++) {//상하좌우로 빙산 주변의 바닷물 세기
								int nx = i + nr[k];
								int ny = j + nc[k];
							
								if(iceberg[nx][ny] == 0) {
									melt[i][j]++;//얼마나 바닷물과 맞닿아 있는지, 얼마나 녹는지 저장함
								}
							}
						}
					}
				}
				if(!check){//빙산이 다 녹아 없어졌을때
					count = 0;//중간에 모두 녹았다면 결과를 0으로 하라고 했으므로
					break;
				}
			
				for (int i = 1; i < N-1; i++) {
					for (int j = 1; j < M-1; j++) {
						iceberg[i][j] -= melt[i][j]; //맞닿아 있는 양만큼 녹음
						if(iceberg[i][j] < 0) {//음수가 되면 0으로 만들어줌
							iceberg[i][j] = 0;
						}
						melt[i][j] = 0;// 다음 해(다음 반복문에서)의 녹는 양을 위해 다시 0으로 만들어줌
					}
				}
			
				Queue<Point> q = new LinkedList<>();// 빙산 덩어리를 세기 위한 큐
				boolean[][] visited = new boolean[N][M];//방문했는지 확인하는 boolean 배열
			
				loop : for (int i = 1; i < N-1; i++) {
					for (int j = 1; j < M-1; j++) {
					
						if(!visited[i][j] && iceberg[i][j] > 0) {//bfs
							q.add(new Point(i,j));
							visited[i][j] = true;
						
							while(!q.isEmpty()) {
								Point temp = q.poll();
							
								for (int k = 0; k < 4; k++) {
									int nx = temp.x + nr[k];
									int ny = temp.y + nc[k];
									if(nx >= 0 && nx < N && ny >= 0 && ny < M) {
										if(!visited[nx][ny] && iceberg[nx][ny] > 0) {
											q.add(new Point(nx, ny));
											visited[nx][ny] = true;
										}
									}
								}
								if(q.isEmpty()) {//큐에 더이상 값이 없을 때
									mass++;//1덩어리가 생김
								}
							}
						
						}
						if(mass >= 2){//이미 2덩어리 이상 생겼다면 나머지 빙산 덩어리 확인하지 않고 바로 반복문 탈출
							break loop;
						}
					}
				}
			}
		}
		
		System.out.println(count);
	}
}