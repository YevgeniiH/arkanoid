package ua.cn.alexeenkogapon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
	private double xpos;/* ������� ����� */
	private double ypos;/* ������� ����� */
	private boolean dead;/* ��� �� ���� */
	public static final int WIDTH = 80;
	public static final int HEIGHT = 40;

	public Block(double xpos, double ypos, boolean dead) {
		super();
		this.xpos = xpos;
		this.ypos = ypos;
		this.dead = dead;
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

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setPos(double x, double y) {
		this.xpos = x;
		this.ypos = y;
	}

	public void draw(Canvas canvas) {
		if (!isDead()) {
			Paint p = new Paint();
			p.setColor(Color.RED);
			canvas.drawRect((float) xpos, (float) ypos, (float) (xpos + WIDTH),
					(float) (ypos + HEIGHT), p);
		}
	}

	/*
	 * �������� �� ������ ������ �� �����
	 */
	public void checkHit(Ball ball) {
		if (!isDead()) {// ���� ���� ��� �� ����
			// ����������� �� ������� �������
			if ((ball.getY() + ball.getRadius() >= getY())
					&& (ball.getY() + ball.getRadius() < getY() + HEIGHT)
					&& (ball.getX() >= getX())
					&& (ball.getX() <= getX() + WIDTH)) {
				ball.setVector(ball.getVx(), -ball.getVy());
				setDead(true);// �������� ���� ������
				ball.incrHits();// ����������� ������� ������
			} else
			// ����������� �� ������ �������
			if ((ball.getY() - ball.getRadius() > getY())
					&& (ball.getY() - ball.getRadius() <= getY() + HEIGHT)
					&& (ball.getX() >= getX())
					&& (ball.getX() <= getX() + WIDTH)) {
				ball.setVector(ball.getVx(), -ball.getVy());
				setDead(true);// �������� ���� ������
				ball.incrHits();// ����������� ������� ������
			} else
			// ����������� �� ����� �������
			if ((ball.getX() + ball.getRadius() >= getX())
					&& (ball.getX() + ball.getRadius() < getX() + WIDTH)
					&& (ball.getY() >= getY())
					&& (ball.getY() <= getY() + HEIGHT)) {
				ball.setVector(-ball.getVx(), ball.getVy());
				setDead(true);// �������� ���� ������
				ball.incrHits();// ����������� ������� ������
			} else
			// ����������� �� ������ �������
			if ((ball.getX() - ball.getRadius() > getX())
					&& (ball.getX() - ball.getRadius() <= getX() + WIDTH)
					&& (ball.getY() >= getY())
					&& (ball.getY() <= getY() + HEIGHT)) {
				ball.setVector(-ball.getVx(), ball.getVy());
				setDead(true);// �������� ���� ������
				ball.incrHits();// ����������� ������� ������
			}
		}
	}

}
