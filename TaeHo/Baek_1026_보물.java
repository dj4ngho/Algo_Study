package 백준;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Baek_1026_보물{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        //두 배열의 값의 연산이 최소가 되도록 하는문제
        //list_A를 내림차순으로 정렬
        //list_B를 오름차순으로 정렬
        ArrayList<Integer> list_A = new ArrayList<>();
        ArrayList<Integer> list_B = new ArrayList<>();
        //Input
        for (int i = 0; i < N; i++) {			
        	list_A.add(sc.nextInt());
		}
        for (int i = 0; i < N; i++) {			
        	list_B.add(sc.nextInt());
		}
        //list_A 오름차순 정렬
        Collections.sort(list_A);
        //list_A를 역순으로
        Collections.reverse(list_A);
        //list_B 내림차순 정렬
        Collections.sort(list_B);
        
        int sum =0;
        //최소값 연산
        for (int i = 0; i < N; i++) {
			sum += list_A.get(i) *list_B.get(i);
		}
        //Output
        System.out.println(sum);
    }

}