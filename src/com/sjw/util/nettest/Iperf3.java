/**
 * 
 */
package com.sjw.util.nettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author sjw
 * 2021年4月7日
 */
public class Iperf3 {
	private static final Log logger = LogFactory.getLog(Iperf3.class);
//	private static final String unitMb = " Mbits/sec";
	private static final String unitKb = " Kbits/sec";
	private static final String unitb = " bits/sec";
	/**
	 * @Description 获取下载速度 Mbits/s
	 */
	public SpeedResult getDownloadSpeed() {
		SpeedResult result = new SpeedResult();
		InputStream inputStream = null;
		try {
			Process pro = Runtime.getRuntime().exec("iperf3 -c 172.16.5.250 -p 25001 -R");
			inputStream = pro.getInputStream();
			BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			int lineNum = 0;
			String[] downSpeedArr = new String[10];
			while ((line = buf.readLine()) != null) {
				System.out.println((lineNum) + line);
				Pattern pattern = Pattern.compile("(\\d+\\.?\\d+)( Mbits/sec| Kbits/sec| bits/sec)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					String speed = matcher.group(1);
					String unit = matcher.group(2);
					if (unitb.equals(unit)) {
						speed = "0";
					}else if (unitKb.equals(unit)) {
						double speedDouble = Double.parseDouble(speed);
						speedDouble = speedDouble/1024;
						speed = String.format("%.2f", speedDouble);
					}
					int testLineNum = lineNum-4;
					if (testLineNum<10) {
						downSpeedArr[testLineNum] = speed;
					}else if (testLineNum == 13) {
						result.setAverageDownSpeed(speed);
					}
				}
				lineNum++;
			}
			result.setDownSpeedArr(downSpeedArr);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * @Description 获取上传速度 Mbits/s
	 */
	public SpeedResult getUploadSpeed() {
		SpeedResult result = new SpeedResult();
		InputStream inputStream = null;
		try {
			Process pro = Runtime.getRuntime().exec("iperf3 -c 172.16.5.250 -p 25001");
			inputStream = pro.getInputStream();
			BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			int lineNum = 0;
			String[] upSpeedArr = new String[10];
			while ((line = buf.readLine()) != null) {
				System.out.println((lineNum) + line);
				Pattern pattern = Pattern.compile("(\\d+\\.?\\d+)( Mbits/sec| Kbits/sec| bits/sec)", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					String speed = matcher.group(1);
					String unit = matcher.group(2);
					if (unitb.equals(unit)) {
						speed = "0";
					}else if (unitKb.equals(unit)) {
						double speedDouble = Double.parseDouble(speed);
						speedDouble = speedDouble/1024;
						speed = String.format("%.2f", speedDouble);
					}
					int testLineNum = lineNum-3;
					if (testLineNum<10) {
						upSpeedArr[testLineNum] = speed;
					}else if (testLineNum == 12) {
						result.setAverageUpSpeed(speed);
					}
				}
				lineNum++;
			}
			result.setUpSpeedArr(upSpeedArr);
			if (result.getAverageUpSpeed() == null) {
				double sumAverageUpSpeed = 0.0;
				int count = 0;
				for (int i = 0; i < upSpeedArr.length; i++) {
					if (upSpeedArr[i] == null) {
						break;
					}
					double speed = Double.parseDouble(upSpeedArr[i]);
					sumAverageUpSpeed+=speed;
					count++;
				}
				double averageUpSpeed = sumAverageUpSpeed/count;
				result.setAverageUpSpeed(String.format("%.2f", averageUpSpeed));
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Iperf3 test = new Iperf3();
		
		SpeedResult result = test.getDownloadSpeed();
		for (int i = 0; i < result.getDownSpeedArr().length; i++) {
			System.out.println(result.getDownSpeedArr()[i]);
		}
		System.out.println("AverageDownSpeed:" + result.getAverageDownSpeed());
		
//		SpeedResult result = test.getUploadSpeed();
//		for (int i = 0; i < result.getUpSpeedArr().length; i++) {
//			System.out.println(result.getUpSpeedArr()[i]);
//		}
//		System.out.println("AverageUpSpeed:" + result.getAverageUpSpeed());
	}
	
	class SpeedResult {
		private String[] downSpeedArr;
		private String[] upSpeedArr;
		private String averageDownSpeed;
		private String averageUpSpeed;
		/**
		 * @return the downSpeedArr
		 */
		public String[] getDownSpeedArr() {
			return downSpeedArr;
		}
		/**
		 * @param downSpeedArr the downSpeedArr to set
		 */
		public void setDownSpeedArr(String[] downSpeedArr) {
			this.downSpeedArr = downSpeedArr;
		}
		/**
		 * @return the upSpeedArr
		 */
		public String[] getUpSpeedArr() {
			return upSpeedArr;
		}
		/**
		 * @param upSpeedArr the upSpeedArr to set
		 */
		public void setUpSpeedArr(String[] upSpeedArr) {
			this.upSpeedArr = upSpeedArr;
		}
		/**
		 * @return the averageDownSpeed
		 */
		public String getAverageDownSpeed() {
			return averageDownSpeed;
		}
		/**
		 * @param averageDownSpeed the averageDownSpeed to set
		 */
		public void setAverageDownSpeed(String averageDownSpeed) {
			this.averageDownSpeed = averageDownSpeed;
		}
		/**
		 * @return the averageUpSpeed
		 */
		public String getAverageUpSpeed() {
			return averageUpSpeed;
		}
		/**
		 * @param averageUpSpeed the averageUpSpeed to set
		 */
		public void setAverageUpSpeed(String averageUpSpeed) {
			this.averageUpSpeed = averageUpSpeed;
		}
	}

}
