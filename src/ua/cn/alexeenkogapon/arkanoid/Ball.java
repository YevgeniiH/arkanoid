package ua.cn.alexeenkogapon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

public class Ball {
	private double xpos;
	private double ypos;
	private double radius = 10;
	private double vx = 0;// вектор направления движения
	private double vy = 0;// вектор направления движения
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private double speed = 1;
	private int life = 3;// счетчик жизней
	private int hits = 0;// счетчик количества ударов

	public Ball(double xpos, double ypos) {
		super();
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	public void decrLife() {
		this.life--;
	}
	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setPos(double xpos, double ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public double getX() {
		return xpos;
	}

	public double getY() {
		return ypos;
	}

	public void setVector(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void resetSpeed() {
		this.speed = 1;
	}

	public void incSpeed() {
		this.speed += 1;
	}

	public int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public void setSCREEN_WIDTH(int sCREEN_WIDTH) {
		SCREEN_WIDTH = sCREEN_WIDTH;
	}

	public int getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}

	public void setSCREEN_HEIGHT(int sCREEN_HEIGHT) {
		SCREEN_HEIGHT = sCREEN_HEIGHT;
	}

	public void setScreenSize(Canvas canvas) {
		/*
		 * узнаем размеры экрана
		 */
		setSCREEN_WIDTH(canvas.getWidth());
		setSCREEN_HEIGHT(canvas.getHeight());
	}

	public void move() {
		if (hits == 3) {// если количество убитых блока = 3
			incSpeed();// увеличиваем скорость
			setHits(0);// обнуляем счетчик
		}
		setPos(getX() + vx * speed, getY() + vy * speed);// передвигаем шар
		if (getX() <= getRadius()) {
			setVector(-vx, vy); // отскакиваем от левой стены
		}
		if (getX() >= getSCREEN_WIDTH() - getRadius()) {
			setVector(-vx, vy); // и от правой
		}
		if (getY() <= getRadius()) {
			setVector(vx, -vy); // и от потолка
		}

		if (getY() >= getSCREEN_HEIGHT() + getRadius()){
			setVector(vx, -vy);
			decrLife();
			if (this.life == 0) {
				
			}
		}
	}

	public void draw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawCircle((float) xpos, (float) ypos, (float) getRadius(), p);
	}

	public void pointsOfTouch() {
		// double x = getRadius() * Math.cos(45);
		// double y = getRadius() * Math.sin(45);
	}

	public void incrHits() {
		hits += 1;
	}

	public double getSpeed() {
		return speed;
	}

}
