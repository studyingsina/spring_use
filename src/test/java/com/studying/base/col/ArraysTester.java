package com.studying.base.col;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

/**
 * Desc
 * 
 * @Date: 2015年3月26日
 */
public class ArraysTester {

//	@Test
	public void test() {
		// initializing unsorted int array
		int intArr[] = { 30, 20, 5, 12, 55 };
		// sorting array
		Arrays.sort(intArr);

		// let us print all the elements available in list
		System.out.println("The sorted int array is:");
		for (int number : intArr) {
			System.out.println("Number = " + number);
		}

		// entering the value to be searched
		int searchVal = 12;

		int retVal = Arrays.binarySearch(intArr, searchVal);

		System.out.println("The index of element 12 is : " + retVal);
		searchVal = 0;
		System.out.println(Arrays.binarySearch(intArr, searchVal));

	}
	
//	@Test
	public void testString(){
		String[] iVersion = {"5.5", "3.6", "4.2", "5.4"};
		Arrays.sort(iVersion);
		for(String s : iVersion){
			System.out.println(s);
		}
		System.out.println("insetion one:" + Arrays.binarySearch(iVersion, "4.3.2"));
		System.out.println("insetion two:" + Arrays.binarySearch(iVersion, "3.5.2.1"));
		System.out.println("insetion three:" + Arrays.binarySearch(iVersion, "5.5.1"));
		List<String> lVersion = new ArrayList<String>();
		lVersion.add("180");
		lVersion.add("100");
		lVersion.add("190");
		lVersion.add("200");
		Collections.sort(lVersion);
		for(String s : lVersion){
			System.out.println(s);
		}
		System.out.println("insetion one:" + Collections.binarySearch(lVersion, "176"));
		System.out.println("insetion two:" + Collections.binarySearch(lVersion, "10"));
		System.out.println("insetion three:" + Collections.binarySearch(lVersion, "240"));
	}
	
	@Test
	public void testTime(){
		ConfigurationClassPostProcessor p = null;
		System.out.println(new Date(1431766796000L));
	}
}
