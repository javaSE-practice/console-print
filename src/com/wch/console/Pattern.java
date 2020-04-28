package com.wch.console;

/**
 * @author CH W
 * @description	打印图案
 * @date 2019年12月23日 下午4:13:53
 * @version 1.0
 */
public class Pattern {
	
	public static void main(String[] args) {
		
		for(int r=1; r<10; r++) {
			for(int c=1; c<10; c++) {
				if(c<=r) {
					System.out.print(c + "*" + r + "=" + c*r + "	");
				}
			}
			System.out.println();
		}
		
	}

}
