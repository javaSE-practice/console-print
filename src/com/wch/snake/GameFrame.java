package com.wch.snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author CH W
 * @description	贪吃蛇游戏（控制台打印版）
 * @date 2019年12月12日 上午10:33:02
 * @version 1.0
 */
public class GameFrame implements Runnable {
	static int row = 10, col = 30;		//	游戏界面行、列
	static Thread moveThread = null;	//	蛇移动线程
	private ArrayList<Point> foods = null;		//	存储食物的集合
	private static LinkedList<Point> snakes = null;		//	存储蛇的集合
	private ArrayList<Point> stones = new ArrayList<Point>();		//	游戏内的石头障碍物
	private ArrayList<Point> temp_stones = new ArrayList<Point>();	//	障碍物临时集合（校验用）
	static boolean gameStarted = false;		//	游戏是否已开始
	private boolean gameOver = false;		//	游戏是否已结束
	static String direction = "a";		//	蛇移动方向
	private int threadTime = 1000;		//	线程休眠时间
	private int score = 0;		//	游戏分数
	int speed = 0;		//	蛇的移动速度
	
	/**
	 * --蛇移动线程
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(threadTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			move();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		/**
		 * --游戏初始化
		 */
		GameFrame gameFrame = new GameFrame();
		moveThread = new Thread(gameFrame);
		snakes = new Snake().snakes;
		gameFrame.producedFood();
		
		Random random = new Random();
		for(int i=0; i<6; i++) {
			gameFrame.createStones(random);
		}
		gameFrame.paint();
		/**
		 * --键盘录入
		 */
		moveThread.start();		//	线程启动
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String next = sc.next();
			if(gameStarted) {
				/**
				 * --游戏开始后的键盘输入方向操作逻辑
				 */
				if((next.equals("w") && !direction.equals("s"))
						|| (next.equals("s") && !direction.equals("w"))
						|| (next.equals("a") && !direction.equals("d"))
						|| (next.equals("d") && !direction.equals("a"))) {
					direction = next;
				}
			}
			/**
			 * --键盘输入开始和暂停的操作
			 */
			if(next.equals("k")) {
				gameStarted = true;
			}else if(next.equals("z")) {
				gameStarted = false;
			}
		}
		sc.close();
	}
	/**
	 * --创建石头障碍物
	 */
	private void createStones(Random random) {
		int x = random.nextInt(GameFrame.row - 2) + 1;
		int y = random.nextInt(GameFrame.col - 2) + 1;
		Point point = new Point(x, y);
		temp_stones.add(point);
		if(stones!=null && stones.size()>0 && stones.contains(point)) {
			this.createStones(random);
		}
		if(!snakes.contains(point) && !foods.contains(point)) {
			stones.add(point);
		}
	}
	/**
	 * --生成食物
	 */
	private void producedFood() {
		foods = new Food().foods;
		while (snakes.containsAll(foods)) {
			foods = new Food().foods;
		}
	}
	/**
	 * --蛇移动逻辑
	 */
	private void move() {
		if(gameStarted && !gameOver) {
			/**
			 * --蛇头和蛇尾坐标，不同方向各自坐标发生的改变
			 */
			int sn_topx = snakes.getFirst().getX();
			int sn_topy = snakes.getFirst().getY();
			int sn_endx = snakes.getLast().getX();
			int sn_endy = snakes.getLast().getY();
			if(direction.equals("w")) {
				sn_topx--;
				sn_endx++;
			}else if(direction.equals("s")) {
				sn_topx++;
				sn_endx--;
			}else if(direction.equals("a")) {
				sn_topy--;
				sn_endy++;
			}else if(direction.equals("d")) {
				sn_topy++;
				sn_endy--;
			}
			/**
			 * --判断蛇头撞墙和撞到自身游戏结束
			 */
			Point sn_top = new Point(sn_topx, sn_topy);
			snakes.addFirst(sn_top);
			if(sn_topx>0 && sn_topx<row && sn_topy>0 && sn_topy<col) {
				for(int i=0; i<stones.size(); i++) {
					if(stones.get(i).equals(sn_top)) {
						gameOver = true;
					}
				}
				if(!gameOver) {
					for(int i=1; i<snakes.size(); i++) {
						if(snakes.get(i).equals(sn_top)) {
							gameOver = true;
						}
					}
				}
			}else {
				gameOver = true;
			}
			/**
			 * --蛇吃到食物后添加于蛇尾
			 * --增加蛇的移动速度100
			 */
			if(foods.contains(snakes.getFirst())) {
				snakes.addLast(new Point(sn_endx, sn_endy));
				threadTime = threadTime - 20;
				speed = (1000 - threadTime) / 20;
				score = score + 10;
				producedFood();
			}
			snakes.removeLast();
		}
		if(gameOver) {
			System.out.println("游戏失败，结束");
			System.exit(-1);
		}
		if(gameStarted) {
			paint();
		}
	}
	/**
	 * --初始化显示游戏
	 */
	public void paint() {
		System.out.println("==========================================Snake Game============================================");
		System.out.print("蛇长度：" + snakes.size() + " 节	");
		System.out.print("分数：" + score);
		System.out.println("	               蛇移动速度：" + speed);
		for(int a=0; a<=row; a++) {
			for(int b=0; b<=col; b++) {
				Point point = new Point(a, b);
				if(a==0 || b==0 || a==row || b==col) {
					System.out.print("█");
				}else if(foods.contains(point)) {
					System.out.print("@");
				}else if(snakes.getFirst().equals(point)) {
					if(direction.equals("w")) {
						System.out.print("▲");
					}else if(direction.equals("s")) {
						System.out.print("▼");
					}else if(direction.equals("a")) {
						System.out.print("◄");
					}else if(direction.equals("d")) {
						System.out.print("►");
					}
				}else if(snakes.contains(point)) {
					System.out.print("●");
				}else if(stones.contains(point)) {
					System.out.print("#");
				}else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
}
