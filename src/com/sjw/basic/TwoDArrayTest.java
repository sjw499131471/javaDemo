package com.sjw.basic;

import java.util.Iterator;

/**
 * @description 二维数组
 * @author sjw
 * @date 2017年8月15日
 */
public class TwoDArrayTest {
	
	static final int ROWS = 4;
	static final int COLS = 2;
	
	private String name;
	public TwoDArrayTest(String name){
		this.name = name;
	}

	public static void main (String[] args) {
		
		//1:基本数据类型数组
		int[][] intArr = new int[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				intArr[i][j] = 	i+j;
			}
		}
		StringBuilder sb = new StringBuilder("table view of intArr:\n");
		for (int[] arr : intArr) {
			for (int i : arr) {
				sb.append("		" + i);
			}
			sb.append('\n');
		}
		System.out.println(sb.toString());
		
		//2：自定义对象类型数组
		TwoDArrayTest[][] objArr = new TwoDArrayTest[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				objArr[i][j] = new TwoDArrayTest("name:" + (i+j));
			}
		}
		
		//3:数组的初始化
		String[][] strArr = new String[][]{
			{"11","12"},{"21","22"},{"31","32"}
		};
		
	}

}
