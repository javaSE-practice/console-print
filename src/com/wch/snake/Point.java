package com.wch.snake;

/**
 * @author CH W
 * @description	用于坐标定位
 * @date 2019年12月12日 下午4:13:57
 * @version 1.0
 */
public class Point {
	private int x;
	private int y;
	
	/**
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		Point point = (Point)obj;
		return point.x==this.x && point.y==this.y;
	}
	@Override
	public String toString() {
		return "toString：[x："+x+"，y："+y+"]";
	}
	
}
