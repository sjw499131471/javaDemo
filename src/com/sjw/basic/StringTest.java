/**
 * 
 */
package com.sjw.basic;

/**
 * @author sjw
 * 2020年6月5日
 */
public class StringTest {

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		//原样输出字符串（字符不转义）
		String str = "aaa\nnn\tttt" + System.getProperty("line.separator") + "rrr";
		for(int i=0; i<str.length();i++){
			if(str.charAt(i)=='\n'){
				System.out.print("\\n");}
			else if(str.charAt(i)=='\t'){
				System.out.print("\\t");}
			else if(str.charAt(i)=='\r'){
				System.out.print("\\r");}
			else
				System.out.print(str.charAt(i));     
		}

	}

}
