package com.wch.snake;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author CH W
 * @description	食物对象
 * @date 2019年12月12日 下午4:10:25
 * @version 1.0
 */
public class Food {
	ArrayList<Point> foods = new ArrayList<Point>();

	/**
	 * @param foods
	 */
	public Food() {
		Random random = new Random();
		int x = random.nextInt(GameFrame.row - 2) + 1;
		int y = random.nextInt(GameFrame.col - 2) + 1;
		foods.add(new Point(x, y));
	}


}
