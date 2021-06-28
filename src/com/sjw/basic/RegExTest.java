/**
 * 
 */
package com.sjw.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sjw
 * 2021年4月7日
 */
public class RegExTest {

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		RegExTest test = new RegExTest();
		test.test2();

	}
	
	/**
	 * @Description
	 */
	public void test2() {
		String line="[  4]   5.00-6.00   sec  300 MBytes  51.4 Kbits/sec  ";
		Pattern pattern = Pattern.compile("(\\d+\\.?\\d+)( Mbits/sec| Kbits/sec)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String rate = matcher.group(1);
			String unit = matcher.group(2);
			System.out.println(rate + unit);
		}

	}
	
	/**
	 * @Description
	 */
	public void test1() {
		String line="下载速度:              \b\b\b\b\b\b\b\b\b\b\b\b   0.00 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  81.27 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 257.39 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 307.28 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 239.52 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 202.85 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 180.64 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 162.04 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 148.52 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 136.86 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 128.15 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 119.24 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 114.87 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 109.32 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 105.98 Mbps\b\b\b\b\b\b\b\b\b\b\b\b 102.30 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  99.39 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  96.98 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  95.18 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  93.04 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  91.35 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  89.40 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  87.07 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  86.70 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  85.27 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  84.54 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  83.31 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  81.69 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  80.97 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  79.71 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  79.19 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  78.24 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  77.62 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  76.90 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  76.12 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  75.70 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  74.86 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  74.22 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  73.86 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  72.94 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  72.79 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  72.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  72.04 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  71.43 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  71.10 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  70.55 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  70.20 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  69.84 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  69.44 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  69.28 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  68.92 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  68.75 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  68.18 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  67.83 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  67.59 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  67.39 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  67.20 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.99 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.80 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.62 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.18 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  66.10 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  65.94 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  65.63 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  65.35 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  65.36 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  65.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.89 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.57 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.58 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.29 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.37 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  64.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.93 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.80 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.86 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.73 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.56 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.32 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.32 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.05 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  63.06 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.91 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.67 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.57 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.56 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.37 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.40 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.96 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.10 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  62.01 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.82 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.82 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.62 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.62 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.46 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.44 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.27 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.27 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.12 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.08 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.15 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  61.08 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.99 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.92 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.79 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.66 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.70 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.58 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.38 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.40 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.15 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.20 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.20 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.18 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  60.01 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.91 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.81 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.87 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.88 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.77 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.73 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.58 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.63 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.53 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.44 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.38 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.45 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.41 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.38 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.24 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.19 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.27 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.07 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.17 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.11 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.98 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  59.00 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.88 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.78 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.79 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.69 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.53 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.52 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.47 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.43 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.42 Mbps\b\b\b\b\b\b\b\b\b\b\b\b  58.29 Mbps";
		Pattern pattern = Pattern.compile("(\\d+\\.\\d+)( Mbps)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		int count = 0;
		while (matcher.find()) {
			String rate = matcher.group(1);
			System.out.println(rate);
			count++;
		}
		System.out.println("数量："+count);

	}

}
