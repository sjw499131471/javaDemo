/**
 * 
 */
package com.sjw.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;

/**
 * @author sjw
 * 2020年8月18日
 */
public class HttpClientTest {

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		HttpClientTest test = new HttpClientTest();
		test.doGet();

	}
	
	public void doGet() {
		List<String> list = Arrays.asList("辽宁","河北","天津","山东",
                                "江苏","上海","浙江","福建","台湾","广东","香港","澳门",
                                "海南","广西","其他","三沙市");
		StringBuilder sBuilder = new StringBuilder();
		for (String province : list) {
			System.out.println(province);
		    HttpClient client = new HttpClient();
		    String encodeProvince = null;
		    try {
				encodeProvince = URLEncoder.encode(province, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    GetMethod getMethod = new GetMethod("https://www.cnss.com.cn/tide/find.jspx?province=" + encodeProvince);
		    int code = 0;
		    try {
			    code = client.executeMethod(getMethod);
			    if (code == 200) {
				    String res = getMethod.getResponseBodyAsString();
				    //System.out.println(res);
				    List<String> portList = JSON.parseArray(res, String.class);
				    for (String port : portList) {
						//System.out.println(port);
				    	String encodePort = null;
					    try {
							encodePort = URLEncoder.encode(port, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						GetMethod getMethod2 = new GetMethod("https://www.cnss.com.cn/u/cms/www/portJson/"+encodePort+".json?v=1582512059859");
						code = client.executeMethod(getMethod2);
						if (code == 200) {
							String result2 = getMethod2.getResponseBodyAsString();
							//System.out.println(result2);
							Port port2 = JSON.parseObject(result2, Port.class);
							System.out.println(port + "=" + port2.getPortId());
//							sBuilder.append(port2.getPortId() + ",");
						}
					}
			    }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		System.out.println(sBuilder.toString());
	}
	
	

}

class Port{
	private String zone;
	private String portId;
	private String portName;
	private String latitudeFv;
	private String longitudeFv;
	private String tideDatum;
	private String timeZone;
	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}
	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
	/**
	 * @return the portId
	 */
	public String getPortId() {
		return portId;
	}
	/**
	 * @param portId the portId to set
	 */
	public void setPortId(String portId) {
		this.portId = portId;
	}
	/**
	 * @return the portName
	 */
	public String getPortName() {
		return portName;
	}
	/**
	 * @param portName the portName to set
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}
	/**
	 * @return the latitudeFv
	 */
	public String getLatitudeFv() {
		return latitudeFv;
	}
	/**
	 * @param latitudeFv the latitudeFv to set
	 */
	public void setLatitudeFv(String latitudeFv) {
		this.latitudeFv = latitudeFv;
	}
	/**
	 * @return the longitudeFv
	 */
	public String getLongitudeFv() {
		return longitudeFv;
	}
	/**
	 * @param longitudeFv the longitudeFv to set
	 */
	public void setLongitudeFv(String longitudeFv) {
		this.longitudeFv = longitudeFv;
	}
	/**
	 * @return the tideDatum
	 */
	public String getTideDatum() {
		return tideDatum;
	}
	/**
	 * @param tideDatum the tideDatum to set
	 */
	public void setTideDatum(String tideDatum) {
		this.tideDatum = tideDatum;
	}
	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
