
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int V = sc.nextInt();//정점의 개수
		int E = sc.nextInt();//임의의 정점에서 다른 정점으로 가는 간선의 개수

		int start = sc.nextInt();//시작정점
		
		Queue<Integer> queue = new LinkedList<>();// BFS에 필요한 큐
		boolean[] visited = new boolean[V+1];//방문한지 체크하는 배열
		
		int[][] map = new int[V+1][V+1];//정점과 간선의 정보를 저장할 배열
		for (int i = 0; i < E; i++) {
			int departure = sc.nextInt();//시작정점
			int destination = sc.nextInt();//도착정점
			map[departure][destination] = 1;
			map[destination][departure] = 1;
		}
		
		dfs(map, visited, start);
		System.out.println();
		Arrays.fill(visited, false);//방문배열 초기화
		
		//BFS 알고리즘
		queue.add(start);//시작 정점 입력
		visited[start] = true;//방문 표시
		while(!queue.isEmpty()){//큐에 데이터에 더 없을 때 까지
			int temp = queue.poll();//큐에 들어있는 정점을 꺼냄
			System.out.print(temp + " ");//방문한 정점을 출력
			for(int i = 1; i < map.length; i++){
				if(!visited[i] && map[temp][i] == 1){//방문하지 않았고, 다른 정점으로 가는 간선이 있다면
					queue.add(i);//큐에 집어넣고
					visited[i] = true;//방문했다고 표시
				}
			}
		}
	}
	
	static void dfs(int[][] map, boolean[] visited, int start) {
		visited[start] = true;//정점을 방문했다고 표시
		System.out.print(start + " ");//출력
		for(int i = 1; i < map[start].length; i++){//해당 정점에서 타 정점으로 가는 간선이 있는지 확인
			if(!visited[i] && map[start][i] == 1){//방문하지 않았고 간선이 존재한다면
				dfs(map, visited, i);//재귀호출
			}
		}
	}
}