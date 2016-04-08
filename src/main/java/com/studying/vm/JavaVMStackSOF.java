package com.studying.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xss128k
 * 
 * @author zzm
 */
public class JavaVMStackSOF {

	private int stackLength = 1;

	public void stackLeak() {
		List<Integer> test = new ArrayList<Integer>();
		stackLength++;
		test.add(stackLength * 100);
		String t2 = "";
		t2.length();
		String t3 = "";
		t3.length();
		stackLeak();
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:" + oom.stackLength);
			throw e;
		}
	}
}