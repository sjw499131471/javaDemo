/**
 * 
 */
package com.sjw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author sjw
 * 2020年11月24日
 */
public class JSONTest {

	/**
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject object = new JSONObject();
		object.put("name", "jake");
		object.put("age", 22);
		System.out.println(object.toJSONString());
		JSONObject object2 = JSON.parseObject(object.toJSONString());
		System.out.println(object2.get("name"));
		try {
			JSONObject object3 = JSON.parseObject("非json字符串");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
