package ua.cn.alexeenkogapon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Plate {
	private double xpos;
	private double ypos;
	public static final int WIDTH = 80;
	private static final int HEIGHT = 20;

	public Plate(Ball ball) {
		super();
		this.xpos = (ball.getSCREEN_WIDTH() - WIDTH) / 2;
		this.ypos = ball.getSCREEN_HEIGHT() - getHeight();
	}

	public double getX() {
		return xpos;
	}

	public void setX(double xpos) {
		this.xpos = xpos;
	}

	public double getY() {
		return ypos;
	}

	public void setY(double ypos) {
		this.ypos = ypos;
	}

	public void draw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.GRAY);
		canvas.drawRect((float) xpos, (float) ypos, (float) (xpos + WIDTH),
				(float) (ypos + getHeight() + 40), p);
	}

	public void move(double x, int width) {
		if ((x >= 40) && (x <= width - 40)) {
			this.xpos = x - 40;
		}else
		if (x < 40) {
			this.xpos = 0;
		}else
		if (x > width - 40) {
			this.xpos = width - 80;
		}
		
	}

	public void checkHit(Ball ball) {
		// отскакиваем от верхней границы
		if ((ball.getY() + ball.getRadius() >= getY())
				&& (ball.getY() + ball.getRadius() < getY() + getHeight())
				&& (ball.getX() >= getX()) && (ball.getX() <= getX() + WIDTH)) {
			ball.setVector(ball.getVx(), -ball.getVy());
		} else
		// отскакиваем от нижней границы
		if ((ball.getY() - ball.getRadius() > getY())
				&& (ball.getY() - ball.getRadius() <= getY() + getHeight())
				&& (ball.getX() >= getX()) && (ball.getX() <= getX() + WIDTH)) {
			ball.setVector(ball.getVx(), -ball.getVy());
		} else
		// отскакиваем от левой границы
		if ((ball.getX() + ball.getRadius() >= getX())
				&& (ball.getX() + ball.getRadius() < getX() + WIDTH)
				&& (ball.getY() >= getY()) && (ball.getY() <= getY() + getHeight())) {
			ball.setVector(-ball.getVx(), ball.getVy());
		} else
		// отскакиваем от правой границы
		if ((ball.getX() - ball.getRadius() > getX())
				&& (ball.getX() - ball.getRadius() <= getX() + WIDTH)
				&& (ball.getY() >= getY()) && (ball.getY() <= getY() + getHeight())) {
			ball.setVector(-ball.getVx(), ball.getVy());
		}
	}

	public static int getHeight() {
		return HEIGHT;
	}
	public static int getWidth() {
		return WIDTH;
	}
	
}
