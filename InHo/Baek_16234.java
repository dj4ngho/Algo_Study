package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class Main {//인구이동 - 99퍼에서 시간초과
	static int N;
	static int[][] map;
	static int[][] union_nation;
	static int L;
	static int R;
	static boolean[][] visited;
	static int count;

	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];

		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		count = 0;
		boolean finish = false;//while문 탈출조건
		int[] pop;//연합인 나라들의 합
		int[] nations;//연합인 나라의 수
		Queue<Point> q = null;

		while(!finish) {
			visited = new boolean[N][N];//방문배열 - 매 while문마다 방문배열 필요
			union_nation = new int[N][N];//연합인 나라를 따로 표시할 2차원 배열
			loop:for (int i = 0; i < N; i++) {//while문 탈출 조건 - 한번도 안돌 수 있으므로 while문 처음에 위치
				for (int j = 0; j < N; j++) {

					for (int k = 0; k < 4; k++) {
						int ni = i + dr[k];
						int nj = j + dc[k];
						if(in_map(ni, nj) && Math.abs(map[i][j] - map[ni][nj]) >= L && R >= Math.abs(map[i][j] - map[ni][nj])) {//주위에 연합을 맺을 국가가 하나라도 있다면
							finish = false;//계속 while문을 반복할 수 있도록 finish를 false로
							break loop;//2중 for문 탈출
						}else if(in_map(ni, nj) && (Math.abs(map[i][j] - map[ni][nj]) < L || R < Math.abs(map[i][j] - map[ni][nj]))){//주위에 연합 맺을 국가가 없을경우
							finish = true;//모든 map의 국가가 연합이 되지 못하면 while문을 탈출하도록 finish를 true로
						}
					}
				}
			}
			if(finish) {
				break;
			}

			q = new LinkedList<>();
			//while 반복으로 모든 나라의 인구수 차이가 L보다 작아질 때까지 / 1 - 연합 나라들을 찾아내고 / 2 - 그 나라들의 인구를 조정한다 /
			int unions = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(!visited[i][j]) {//BFS
						unions++;
						visited[i][j] = true;
						union_nation[i][j] = unions;
						q.add(new Point(i, j));
						while(!q.isEmpty()){
							Point temp = q.poll();
							for (int k = 0; k < 4; k++) {
								int ni = temp.x + dr[k];
								int nj = temp.y + dc[k];
								if(in_map(ni, nj) && !visited[ni][nj] && Math.abs(map[temp.x][temp.y] - map[ni][nj]) >= L && R >= Math.abs(map[temp.x][temp.y] - map[ni][nj])) {
									visited[ni][nj] = true;
									union_nation[ni][nj] = unions;
									q.add(new Point(ni, nj));
									//union(ni, nj, unions);
								}
							}
						}
						//union(i, j, unions);//연합인지 확인하는 메소드 호출
					}
				}
			}

			//연합인 국가끼리 조정하기			
			pop = new int[unions+1];
			nations = new int[unions+1];
			
			for (int i = 0; i < N; i++) {//각 연합의 인구합과 나라의 수를 구함
				for (int j = 0; j < N; j++) {
					if(union_nation[i][j] > 0) {
						pop[union_nation[i][j]] += map[i][j];
						nations[union_nation[i][j]]++;
					}
				}
			}
			
			for (int i = 0; i < N; i++) {//인구 이동
				for (int j = 0; j < N; j++) {
					if(union_nation[i][j] > 0) {
						map[i][j] = pop[union_nation[i][j]]/nations[union_nation[i][j]];
						union_nation[i][j] = 0;
					}
				}
			}
			count++;
		}
		System.out.println(count);
	}
	static void union(int x, int y, int unions) {//DFS
		for (int i = 0; i < 4; i++) {
			int ni = x + dr[i];
			int nj = y + dc[i];
			if(in_map(ni, nj) && !visited[ni][nj] && Math.abs(map[x][y] - map[ni][nj]) >= L && R >= Math.abs(map[x][y] - map[ni][nj])) {
				visited[ni][nj] = true;
				union_nation[ni][nj] = unions;
				union(ni, nj, unions);
			}
		}
	}

	static boolean in_map(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

	static class Point{
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}