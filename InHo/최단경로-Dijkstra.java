import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static class Edge{
		int destination;
		int cost;
		Edge(int d, int c){
			destination = d;
			cost = c;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());//정점의 개수
		int E = Integer.parseInt(st.nextToken());//간선의 개수
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());//시작할 정점의 값
		ArrayList<Edge>[] edges = new ArrayList[V+1]; //인접행렬 리스트의 배열
		
		for (int i = 1; i < V+1; i++) {
			edges[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < E; i++) {//각 간선의 정보
			st = new StringTokenizer(br.readLine());
			int dep = Integer.parseInt(st.nextToken());// 시작점
			int des = Integer.parseInt(st.nextToken());// 도착점
			int cost = Integer.parseInt(st.nextToken());// 비용
			
			edges[dep].add(new Edge(des, cost));//시작점에서 도착점으로 가는, 비용이 cost인 간선을 배열에 리스트로 추가
		}
		int[] dist = new int[V+1];//다익스트라이므로 시작점에서 각 인덱스의 정점으로 가는 길의 거리를 갱신할 배열
		boolean[] visited = new boolean[V+1];//방문했는지 검사하는 배열
		
		for (int i = 1; i < dist.length; i++) {
			if(start!=i) {
				dist[i] = Integer.MAX_VALUE;//시작점 본인을 제외하고 나머지 정점까지의 거리를 모두 최대값으로
			}
		}
		
		for (int i = 0; i < edges[start].size(); i++) { // 시작점에서 인접 정점으로 가는 간선의 갯수만큼 반복
			if(dist[edges[start].get(i).destination] > edges[start].get(i).cost) {//거리 배열에 저장된 값(초기값은 int형 최대값)보다 인접정점으로 가는 비용이 더 작다면
				dist[edges[start].get(i).destination] = edges[start].get(i).cost;//거리배열에 값 저장(갱신)
			}
		}
		visited[start] = true;//시작정점은 방문했다고 표시
		
		for (int i = 0; i < V-1; i++) { // 시작정점을 제외한 나머지 정점의 갯수만큼 반복
			int min = Integer.MAX_VALUE;
			int minIdx = -1;
			for (int j = 1; j < V+1; j++) {//거리배열에서 방문하지 않았고, 최단거리가 갱신된 정점에 대한 위치와 비용 저장
				if(!visited[j] && min > dist[j]) {
					minIdx = j;//갱신된 정점의 인덱스
					min = dist[j];//갱신된 정점까지의 비용
				}
			}
			if(minIdx == -1 && min == Integer.MAX_VALUE){
				continue;//거리배열이 갱신되지 않았으면 진행하지않고 배열의 다음 인덱스로 이동
			}
			for (int j = 0; j < edges[minIdx].size(); j++) {//거리 배열 중 최단 거리에 있는 정점의 인덱스. 그 인덱스의 인접 리스트만큼 반복
				if(!visited[edges[minIdx].get(j).destination] //인접 리스트 배열중 방문하지 않은 정점
						&& edges[minIdx].get(j).cost != Integer.MAX_VALUE //int형 최대값이 아닌
						&& dist[minIdx] + edges[minIdx].get(j).cost < dist[edges[minIdx].get(j).destination]) {//최단거리 정점의 값과 인접정점의 비용을 더한것이 기존의 거리배열의 값보다 적으면
					dist[edges[minIdx].get(j).destination] = dist[minIdx] + edges[minIdx].get(j).cost;//최단거리의 정점까지 가는 비용과 그 정점에서 인접정점으로 가는 비용의 값을 거리배열에 갱신
				}
			}
			visited[minIdx] = true;//최단거리의 정점을 방문했다고 표시
		}
		
		for (int i = 1; i < dist.length; i++) {
			if(dist[i] == Integer.MAX_VALUE) {//다익스트라 알고리즘을 모두 진행한 이후에도 거리가 갱신되지 않았다면
				System.out.println("INF");//INF 출력
			}else {
				System.out.println(dist[i]);//거리 갱신됐다면 그 거리 값 출력
			}
		}
	}
}