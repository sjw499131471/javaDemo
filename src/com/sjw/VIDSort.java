package com.sjw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VIDSort {

	public static void main(String[] args) {
		String allID = "001/065/089/032/039/023/043/066/082/064";
		String[] allIDArr = allID.split("/");
		ArrayList<String> allIDList = new ArrayList<>();
		for (int i = 0; i < allIDArr.length; i++) {
			if (!allIDList.contains(allIDArr[i])) {
				allIDList.add(allIDArr[i]);
			}
		}
		System.out.println(allIDList);
		Collections.sort(allIDList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				String o1Num = o1.replace("+", "");
				String o2Num = o2.replace("+", "");
				int i1 = Integer.parseInt(o1Num);
				int i2 = Integer.parseInt(o2Num);
				if (i1 > i2) {
					return 1;
				}else if (i1 == i2) {
					return 0;
				}else {
					return -1;
				}
			}
		});
		
		StringBuilder sBuilder = new StringBuilder();
		for (String string : allIDList) {
			sBuilder.append(string + "/");
		}
		System.out.println(sBuilder.toString());
		
	}

}
