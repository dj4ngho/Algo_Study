
import java.util.ArrayList;
import java.util.Scanner;
 
public class Baek_17472 {//다리 만들기2
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
 
        map = new int[N][M];
 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
            }
        }
 
        int islands = 1;
        //각 섬을 위에서부터 차례로 2,3,4... 으로 바꾸는 부분
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(map[i][j] == 1) {
                	islands++;
                    dfs(i, j, islands);
                }
            }
        }
        
        visited = new boolean[N][M];//방문 체크 배열
        edges = new ArrayList[islands];//인접리스트를 저장할 인접리스트배열
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new ArrayList<>();
        }
        //각 섬에서 다른 섬으로 가는 비용 구하는 부분
        for (int i = 0; i < N; i++) {
            int count = 0;
            for (int j = 0; j < M; j++) {
                if(map[i][j] != 0 && !visited[i][j]) {
                    visited[i][j] = true;
                    for (int k = 0; k < 4; k++) {
                        int ni = i + dr[k];
                        int nj = j + dc[k];
                        count(ni, nj, count, k, map[i][j]);
                    }
                }
            }
        }
        
        //mst - 프림 최소신장트리
        ArrayList<Integer> selList = new ArrayList<>();//선택한 섬의 번호를 담는 리스트
        boolean[] select = new boolean[islands];
        
        selList.add(1);//시작은 1번 섬부터
        select[1] = true;//1을 방문했다고 표시
        int result = 0;//결과를 저장할 값
        
        while(selList.size() < islands - 1) {
            int min = Integer.MAX_VALUE;
            int minD = -1;
            boolean not_one = false;
            
            for (int v : selList) {//선택한 섬에서 어떤 섬으로 진행하는데 가장 최소값 구하는 부분
                for (int i = 0; i < edges[v].size(); i++) {
                    Edge temp = edges[v].get(i);
                    if(!select[temp.destination] && temp.cost > 1 && min > temp.cost) {//다리길이가 2이상이어야만 하므로
                    	min = temp.cost;
                        minD = temp.destination;
                        not_one = true;
                    }
                }
            }
            if(!not_one) {
            	result = -1;
            	break;
            }
            if(minD > 0) {
            	selList.add(minD);
                select[minD] = true;
            	result += min;
            }
        }
        System.out.println(result);
    }
     
    static int N;// 지도의 세로길이
    static int M;// 지도의 가로 길이
    static int[][] map;// 지도의 정보
    static boolean[][] visited;
    static ArrayList<Edge>[] edges;
    static int[] dr = {-1, 1, 0, 0};//상, 하, 좌, 우
    static int[] dc = {0, 0, -1, 1};
     
    static class Edge{
        int destination;
        int cost;
         
        public Edge(int d, int c) {
            destination = d;
            cost = c;
        }
    }
 
    static void dfs(int x, int y, int islands) {//각 섬을 분별해 표시하는 dfs (초기 입력값이 1이 섬으로 표시됐으므로 각 섬들을 차례로 2, 3, 4...로 변환)
        map[x][y] = islands;
        for (int i = 0; i < 4; i++) {
            int ni = x + dr[i];
            int nj = y + dc[i];
            if(in_map(ni, nj)) {
                if(map[ni][nj] == 1) {
                    map[ni][nj] = islands;
                    dfs(ni, nj, islands);
                }
            }
        }
    }
 
    static void count(int x, int y, int count, int d, int start_value) {//각 영역에서 몇칸인지 세는 메소드
        if(!in_map(x, y)) {
            return;
        }else {// 이동한 좌표인 x, y 값이 map 내부일 때         
            if(map[x][y] != start_value) {//map에서 방문한 적이 없고 이동한 값이 각 섬 값이 아닐 때
                if(map[x][y] == 0) {//이동한 지점이 바다일 때
                    if(d == 0)//상
                        x = x-1;
                    else if(d == 1)//하
                        x = x+1;
                    else if(d == 2)//좌
                        y = y-1;
                    else if(d == 3)//우
                        y = y+1;
                    count++;//비용 증가
                    count(x, y, count, d, start_value);
                }else if(map[x][y] != 0 ){//map에서 방문한적 없고 이동한 좌표 값이 시작한 섬이 아니며 바다도 아닐 때
                	boolean check = false;
                    for (int i = 0; i < N; i++) {//전체 map을 돌면서 어느 섬에 도달했는지 검사
                        for (int j = 0; j < M; j++) {
                        	
                            if(map[i][j] == map[x][y]) {//진행한 방향에서 만난 섬과 같은 섬을 만난다면
                            	
                            	for (Edge e : edges[start_value-1]) {
									if(e.destination == map[i][j]-1)//기존 리스트에 저장된 섬인가 확인
										check = true;
								}
                            	
                            	if(!check) {//리스트에 저장이 안되어있으면
                            		edges[start_value-1].add(new Edge(map[i][j]-1, count));
                            	}else if(check){//저장되어 있다면
                            		if(count > 1) {//저장되어 있는데 도달하는 다리의 비용이 2이상인 다리가 또 있다면
                            			for (Edge e : edges[start_value-1]) {
                                			if(e.destination == map[i][j]-1) {
                                				e.cost = count;//새로 갱신
                                			}
    									}
                            		}
                            	}
                            	
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
     
    static boolean in_map(int x, int y) {//인덱스 값들이 map안에 있는지 검사하는 메소드
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}