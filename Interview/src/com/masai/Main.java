package com.masai;

import java.util.Arrays;
import java.util.List;

public class Main {

	
	public static void main(String[] args) {
//		List<Integer> arr=Arrays.asList(1,4,3,2,4,5,6,7);
		List<String> str=Arrays.asList("r","e","e","w","a");
		 
		int[] arr = {1,2,4,5};
		Arrays.stream(arr).map( a-> a+1).forEach(System.out::println);
		
		for(int i : arr) {
			System.out.println(i);
		}
		
	}
	
}
