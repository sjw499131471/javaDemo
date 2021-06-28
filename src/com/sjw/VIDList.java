package com.sjw;

import java.util.ArrayList;
import java.util.Collections;

public class VIDList {

	public static void main(String[] args) {
		String allID = "021/035/046+/068+/098/106/110/117/123/140/150/162/163/165/174/177/178/179/181/185/186/187/189/192/197/202/203/206/207/209/212/213/215/235/239+/243/246/251/275/306/311/330/340/343/345/353/373/380/381/383+/388/389/395/405/406/411/418/421/427/428+/429/434/447/449/463/467/468/470/482/487/490/498/513/521/528/531/541/546/547/549/554/557/558/566/572/576/578/581/584/585/596++/597/614/615/618/620/623/628/629/630/631/638/646/655/658/665/671/672/673/674/677/678/687/697/700/723/727/757/";
		String[] allIDArr = allID.split("/");
		ArrayList<String> allIDList = new ArrayList<>();
		Collections.addAll(allIDList, allIDArr);
//		for (String string : allIDList) {
//			System.out.println(string);
//		}
		int maxID = 757;
		StringBuilder sbAll = new StringBuilder();
		StringBuilder sbNotExist = new StringBuilder();
		for (int i = 1; i <= maxID; i++) {
			String vidStr = String.format("%3d", i).replace(" ", "0");;
			//System.out.println(vidStr);
			if (allIDList.contains(vidStr)) {
				sbAll.append(vidStr + "/");
			}else if (allIDList.contains(vidStr + "+")) {
				sbAll.append(vidStr + "+/");
			}else if (allIDList.contains(vidStr + "++")) {
				sbAll.append(vidStr + "++/");
			}else {
				sbAll.append(vidStr + "-/");
				sbNotExist.append(vidStr + "/");
			}
		}
		System.out.println(sbAll.toString());
		System.out.println(sbNotExist.toString());
	}

}
