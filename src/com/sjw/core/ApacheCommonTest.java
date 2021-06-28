package com.sjw.core;

import java.util.Arrays;

import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;

public class ApacheCommonTest {

	public static void main (String[] args) {
		ArrayConverter converter = new ArrayConverter(TestObj[].class, new StringConverter());
		TestObj [] input = {new TestObj("First", 1), new TestObj("Second", 2), new TestObj("Last", 9)};
		String[] result = (String[])converter.convert(String[].class, input);
		for (String string : result) {
			System.out.println(string);
		}
	}

}

class TestObj{
	private String name;
	private int age;
	public TestObj (String name,int age) {
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString () {
		return name + ":" + age;
	}
}