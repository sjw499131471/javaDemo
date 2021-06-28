/**
 * 
 */
package com.sjw.util.nettest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sjw
 * 2021年4月5日
 */
public class Ping {
	
	public static boolean connect(String ipAddress) throws Exception {
		int timeOut = 3000; // 超时应该在3钞以上
		boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut); // 当返回值是true时，说明host是可用的，false则不可。
		return status;
	}

	public static PingResult ping(String ipAddress, int pingTimes, int timeOut) throws Exception {
		PingResult pingResult = new PingResult();
		String line = null;
		try {
			Process pro = Runtime.getRuntime().exec("ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(), "GB2312"));
			int connectedCount = 0;
			List<Integer> delayList = new ArrayList<Integer>();
			int lineNum = 0;
			while ((line = buf.readLine()) != null) {
				System.out.println(lineNum + line);
				int testLineNum = lineNum-2;
				Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					int delay = Integer.parseInt(line.substring(matcher.start(), line.indexOf("ms")));
					delayList.add(delay);
//					System.out.println(delay);
					connectedCount++;
				}else {
					if (testLineNum >= 0 && testLineNum < 4) {
						delayList.add(-1);
					}
				}
				lineNum++;
			}
			pingResult.setDelayList(delayList);
			pingResult.setPakageLoss(Math.round((pingTimes-connectedCount)*100/pingTimes));
		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
			
		}
		return pingResult;
	}

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		String ipAddress = "127.0.0.1";
		try {
			PingResult result = ping(ipAddress,4,1000);
			for (Integer string : result.getDelayList()) {
				System.out.println(string);
			}
			System.out.println(result.getPakageLoss());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class PingResult {
	private List<Integer> delayList;
	private Integer pakageLoss;
	private Integer connectRate;
	/**
	 * @return the delayList
	 */
	public List<Integer> getDelayList() {
		return delayList;
	}
	/**
	 * @param delayList the delayList to set
	 */
	public void setDelayList(List<Integer> delayList) {
		this.delayList = delayList;
	}
	/**
	 * @return the pakageLoss
	 */
	public Integer getPakageLoss() {
		return pakageLoss;
	}
	/**
	 * @param pakageLoss the pakageLoss to set
	 */
	public void setPakageLoss(Integer pakageLoss) {
		this.pakageLoss = pakageLoss;
	}
	/**
	 * @return the connectRate
	 */
	public Integer getConnectRate() {
		return connectRate;
	}
	/**
	 * @param connectRate the connectRate to set
	 */
	public void setConnectRate(Integer connectRate) {
		this.connectRate = connectRate;
	}
}
