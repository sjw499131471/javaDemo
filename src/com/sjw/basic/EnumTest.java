package com.sjw.basic;

import java.io.DataOutput;
import java.util.EnumMap;
import java.util.EnumSet;

public class EnumTest {

	public static void main(String[] args) {
		SeasonEnum season = SeasonEnum.SPRING;
		switch (season) {
			case SPRING:
				System.out.println("it is spring");
				break;
			case SUMMER:
				System.out.println("it is summer");
				break;
			default:
				break;
		}
		
		for (SeasonEnum seasonVal: SeasonEnum.values()) {
			System.out.println(seasonVal);
		}
		
		for (Day day: Day.values()) {
			System.out.println(day);
			System.out.println(day.name());
			System.out.println(day.getValue());
		}
		
		//EnumMap
		EnumMap<Day, Integer> map = new EnumMap<Day, Integer>(Day.class);
		map.put(Day.FRIDAY, Day.FRIDAY.getValue());
		map.put(Day.MONDAY, Day.MONDAY.getValue());
		map.put(Day.THURSDAY, Day.THURSDAY.getValue());

		for (Day day : map.keySet()) {
			System.out.println(day.name());
		}
		/*output:
		 * MONDAY
			THURSDAY
			FRIDAY
		 */
		
		//EnumSet
		EnumSet<Day> set = EnumSet.allOf(Day.class);
		for (Day day : set) {
			System.out.println(day.name());
		}
	}

}

//枚举类型里可以定义构造器、变量、方法
enum SeasonEnum{
	SPRING,SUMMER,AUTUMN,WINTER;
}

enum Day{
	SUNDAY(1),//指定数值需要有构造器
	MONDAY(2),
	TUESDAY(3),
	WEDNESDAY(4),
	THURSDAY(5),
	FRIDAY(6),
	SATURDAY(7);
	
	private int value;
	
	private Day(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String toString () {
		return this.name() + ":" + value;
	}
}

//定义抽象方法，每个枚举值分别实现
enum Car {
	AUDI {
		@Override
		public int getPrice() {
			return 25000;
		}
	},
	MERCEDES {
		@Override
		public int getPrice() {
			return 30000;
		}
	},
	BMW {
		@Override
		public int getPrice() {
			return 20000;
		}
	};
	
	public abstract int getPrice();
}
