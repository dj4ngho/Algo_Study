
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BaekJoon_11559번 {
	static int[] nr = {-1, 1, 0, 0};
	static int[] nc = {0, 0, -1, 1};

	static class Point {
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		char[][] map = new char[12][6];//뿌요뿌요 돌릴 맵

		for (int i = 0; i < map.length; i++) {
			String temp = br.readLine();
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = temp.charAt(j);
			}
		}

		int count = 0;
		Queue<Point> queue = new LinkedList<>();
		ArrayList<Point> list = new ArrayList<>();
		
		int times = 1;
		while(times <= 18) {//뿌요뿌요 맵의 칸수가 총 72, 4개씩 터지므로 최대 횟수 18번
			boolean isBoom = false;
			for (int i = map.length-1; i >= 0; i--) {//맨 아래에서부터 검사
				for (int j = 0; j < map[i].length; j++) {
					if(map[i][j] != '.') {//문자를 만나면

						char temp = map[i][j];//현재 문자의 값을 저장
						list.add(new Point(i, j));//문자가 있는 위치를 저장
						queue.add(new Point(i, j));//bfs돌릴 큐에 저장

						while(!queue.isEmpty()) {//bfs로 4개 이상인지 검사
							Point p = queue.poll();
							boolean check = false;//델타 돌리면서 그 위치의 값을 리스트에 넣었는지 안넣었는지 확인할 boolean값

							for (int k = 0; k < 4; k++) {//델타
								int ni = p.x + nr[k];
								int nj = p.y + nc[k];
								check = false;
								if(ni >= 0 && ni < map.length && nj >= 0 && nj < map[0].length) {//맵 안에 있는지
									for (Point point : list) {//리스트 안에 이미 들어있는지 검사
										if(point.x == ni && point.y == nj) {
											check = true;//들어있다면 check를 바꿔주고 나감
											break;
										}
									}
									if(!check && map[ni][nj] == temp) {//현재 문자(temp)랑 비교, check(리스트에 들어있는지)확인
										queue.add(new Point(ni, nj));//큐랑 리스트에 넣어줌
										list.add(new Point(ni, nj));
									}
								}
							}
						}
						if(list.size() >= 4) {//같은문자가 4개 이상이면
							isBoom = true;//뿌요가 터졌는지 확인
							for (Point point : list) {//뿌요를 없애주고 .으로 바꿈
								map[point.x][point.y] = '.';
							}
						}
						list.clear();//4개 넘든 안넘든 list는 지워줌
					}
				}
			}
			if(isBoom) {//뿌요가 4개 이상 있어서 터졌다면 연쇄횟수 추가
				count++;
			}
			
			//문자를 만났는데 밑에 빈칸있을 경우 내리는 부분
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if(map[i][j] != '.') {//현재 위치의 값이 문자일경우(R, Y, B, P, G)
						for (int k = i+1; k < map.length; k++) {//현재 위치보다 아래부터 .있는지 검사해서 이동시키는
							if(map[k][j] == '.') {//문자 아래 .이 있으면
								for (int l = k; l > i; l--) {//아래로 이동(스왑)
									char temp = map[l][j];
									map[l][j] = map[l-1][j];
									map[l-1][j] = temp;
								}
							}
						}
					}
				}
			}
			times++;//횟수 증가
		}

		System.out.println(count);
	}
}