package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MinimumRoute_Dijkstra {
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
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		ArrayList<Edge>[] edges = new ArrayList[V+1]; // adjacent array list
		
		for (int i = 1; i < V+1; i++) {
			edges[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int dep = Integer.parseInt(st.nextToken());
			int des = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			edges[dep].add(new Edge(des, cost));
		}
		int[] dist = new int[V+1];
		boolean[] visited = new boolean[V+1];
		
		for (int i = 1; i < dist.length; i++) {
			if(start!=i) {
				dist[i] = Integer.MAX_VALUE;
			}
		}
		
		for (int i = 0; i < edges[start].size(); i++) { // start v's adjacent arraylist
			if(dist[edges[start].get(i).destination] > edges[start].get(i).cost) {// more edges on departure(v) to destination(v), to choose the minimum cost edge
				dist[edges[start].get(i).destination] = edges[start].get(i).cost;
			}
		}
		visited[start] = true;
		
		for (int i = 0; i < V-1; i++) { // V-1 times to find the minimum costs each routes from start(v)
			int min = Integer.MAX_VALUE;
			int minIdx = -1;
			for (int j = 1; j < V+1; j++) { // 
				if(!visited[j] && min > dist[j]) {
					minIdx = j;
					min = dist[j];
				}
			}
			if(minIdx == -1 && min == Integer.MAX_VALUE){
				continue;
			}
			for (int j = 0; j < edges[minIdx].size(); j++) {
				if(!visited[edges[minIdx].get(j).destination] 
						&& edges[minIdx].get(j).cost != Integer.MAX_VALUE 
						&& dist[minIdx] + edges[minIdx].get(j).cost < dist[edges[minIdx].get(j).destination]) {
					dist[edges[minIdx].get(j).destination] = dist[minIdx] + edges[minIdx].get(j).cost;
				}
			}
			visited[minIdx] = true;
		}
		
		for (int i = 1; i < dist.length; i++) {
			if(dist[i] == Integer.MAX_VALUE) {
				System.out.println("INF");
			}else {
				System.out.println(dist[i]);
			}
		}
	}
}