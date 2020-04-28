package com.wch.snake;

import java.util.LinkedList;

/**
 * @author CH W
 * @description	蛇对象
 * @date 2019年12月12日 下午4:59:07
 * @version 1.0
 */
public class Snake {
	LinkedList<Point> snakes = new LinkedList<Point>();

	/**
	 * @param snake
	 */
	public Snake() {
		int x = GameFrame.row / 2;
		int y = GameFrame.col / 2;
		snakes.add(new Point(x, y));
		for(int i=1; i<3; i++) {
			snakes.add(new Point(x, y + i));
		}
	}
	
	
}
