package ua.cn.alexeenkogapon.arkanoid;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private boolean init = false;// метка инициализации
	private int cntBlocks = 0;// равно количеству убитых блоков

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new DrawView(this));

	}

	class DrawView extends SurfaceView implements SurfaceHolder.Callback {

		private DrawThread drawThread;

		public DrawView(Context context) {
			super(context);
			getHolder().addCallback(this);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			drawThread = new DrawThread(getHolder());
			drawThread.setRunning(true);
			drawThread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			boolean retry = true;
			drawThread.setRunning(false);
			while (retry) {
				try {
					drawThread.join();
					retry = false;
				} catch (InterruptedException e) {
				}
			}
		}

		class DrawThread extends Thread {

			private boolean running = false;
			private SurfaceHolder surfaceHolder;

			public DrawThread(SurfaceHolder surfaceHolder) {
				this.surfaceHolder = surfaceHolder;
			}

			public void setRunning(boolean running) {
				this.running = running;
			}

			@Override
			public void run() {
				List<Block> block = new LinkedList<Block>();
				Ball ball = new Ball(1, 1);// наш шарик
				ball.setVector(-1, -1);// задаем начальное направление движения
				Plate plate = null;
				Paint p = new Paint();
				Canvas canvas;
				while (running) {
					canvas = null;
					try {
						canvas = surfaceHolder.lockCanvas(null);
						if (canvas == null)
							continue;
						ball.setScreenSize(canvas);// берем размеры канвы
						if (plate == null) {
							plate = new Plate(ball);
						}
						if (!init) {
							ball.setPos(canvas.getWidth() / 2,
									canvas.getHeight() - ball.getRadius()
											- plate.getHeight() - 1);// помещаем
																// шарик внизу
																// экрана
						}
						initBlocks(block, ball);
						canvas.drawColor(Color.WHITE);
						for (Block block2 : block) {
							block2.draw(canvas);// отрисовываем блоки
							block2.checkHit(ball);// проверяем на
													// соприкосновение шарика с
													// блоком
							if (block2.isDead()) {
								cntBlocks++;// кол-во убитых блоков
							}
						}
						plate.draw(canvas);
						plate.checkHit(ball);
						ball.draw(canvas);// рисуем шарик
						if (ball.getLife() == 0) {
							ball.setLife(3);
						}
						canvas.drawText("Lives left: "+ball.getLife(), (float)canvas.getWidth()/2-80, (float)canvas.getHeight()/2+200, p);
						canvas.drawText("Hits: "+ball.getHits(), (float)canvas.getWidth()/2-80, (float)canvas.getHeight()/2+220, p);
						canvas.drawText("Speed: "+ball.getSpeed(), (float)canvas.getWidth()/2-80, (float)canvas.getHeight()/2+240, p);
						plate.move(ball.getX(), canvas.getWidth());
						ball.move();// передвигаем шарик
					} finally {
						if (canvas != null) {
							surfaceHolder.unlockCanvasAndPost(canvas);
						}
					}
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void initBlocks(List<Block> block, Ball ball) {
		/* количество блоков помещающихся на одной строке */
		int column = ball.getSCREEN_WIDTH() / (Block.WIDTH + 5);
		/*
		 * количество блоков помещающихся на одном столбце(только в верхней
		 * половине)
		 */
		int row = ball.getSCREEN_HEIGHT() / 2 / (Block.HEIGHT + 5);
		/* если блоков уже не осталось или мы еще их не проинициализировали */
		if ((cntBlocks == row * column) || (!init)) {
			/* если шар находится в нижней части экрана */
			if (ball.getY() > ball.getSCREEN_HEIGHT() / 2) {
				/* делаем равные отступы слева и справа */
				int tabh = (ball.getSCREEN_WIDTH() - (Block.WIDTH + 5) * column) / 2;
				/* делаем равные отступы сверху и снизу */
				int tabv = ((ball.getSCREEN_HEIGHT() / 2) - (Block.HEIGHT + 5)
						* row) / 2;
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < column; j++) {
						/* если все блоки уже убраны */
						if (cntBlocks == row * column) {
							/* очищаем список блоков */
							block.removeAll(block);
							cntBlocks = 0;
							ball.resetSpeed();
							ball.setHits(0);
							ball.setLife(3);
						}
						block.add(new Block(tabh + j * (Block.WIDTH + 5), tabv
								+ i * (Block.HEIGHT + 5), false));/*
																 * инициализируем
																 * блоки
																 */
					}
				}
			}
		}
		cntBlocks = 0;
		init = true;// инициализация проведена
	}

}
