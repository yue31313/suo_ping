package com.lockscreen.android.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {

	private static final int LEFT_DISTANCE = 20;// ����ͼƬ��X���굽��Ļ�ıߣ���X�����ֵ������˵����ͼƬ�Ļ���������Ļ��߿�ľ��롣
	private Context mContext;
	private Bitmap drawBitmap;// ����ͼƬ
	private ImageView leftImageView;// ���ؼ�
	private ImageView rightImageView;// �Ҳ�ؼ�
	private int locationX;// ���λ��
	private static final int MOVE_TIME = 10;// �ƶ�ʱ��
	private static final float MOVE_SPEED = 0.9f;// �ƶ��ٶ�
	private Handler mHandler;// ��Mainͨ��
	private int drawXMax;// ����ͼƬ�ܻ�����X��������ֵ

	public MyRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		getDrawBitmap();// ��ʼ������ͼƬ
		setWillNotDraw(false);// ��ֹonDraw������ִ��
	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		getDrawBitmap();
		setWillNotDraw(false);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		getDrawBitmap();
		setWillNotDraw(false);
	}

	/**
	 * �õ�����ͼƬ
	 */
	private void getDrawBitmap() {

		if (drawBitmap == null) {
			drawBitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.slidebtn_move);
		}

	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		leftImageView = (ImageView) findViewById(R.id.id_main_img_left);
		rightImageView = (ImageView) findViewById(R.id.id_main_img_right);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("action_down");
			locationX = x;
			return isActionDown(event);

		case MotionEvent.ACTION_MOVE:
			System.out.println("action_move");
			locationX = x;
			invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			System.out.println("action_up");
			if (!isLocked()) {
				imageBack(event);
			}
			return true;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * ����ͼƬ����
	 * 
	 * @param event
	 */
	private void imageBack(MotionEvent event) {
		locationX = (int) (event.getX() - LEFT_DISTANCE);
		if (locationX > 0) {
			mHandler.postDelayed(runnableImgBack, MOVE_TIME);
		}

	}

	/**
	 * �����̱߳���
	 */
	private Runnable runnableImgBack = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			locationX = locationX - (int) (MOVE_SPEED * MOVE_TIME);

			System.out.println("location==" + locationX);
			if (locationX > 0) {
				mHandler.postDelayed(runnableImgBack, MOVE_TIME);
				invalidate();
			}else{
				leftImageView.setVisibility(View.VISIBLE);
				clearVariable();
			}
		}
	};

	/**
	 * �������
	 */
	private void clearVariable(){
		
		temp=0;
		tempCount=0;
		mDrawX=0;
		
	}
	
	/**
	 * �ж��Ƿ������˻���ͼƬ
	 * 
	 * @param event
	 * @return
	 */
	private boolean isActionDown(MotionEvent event) {

		Rect rect = new Rect();
		leftImageView.getHitRect(rect);
		boolean isDown = rect.contains((int) event.getX(), (int) event.getY());
		if (isDown) {
			leftImageView.setVisibility(View.GONE);
		}
		System.out.println("idDown==" + isDown);
		return isDown;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawImage(canvas);
	}

	int temp = 0;// ȫ�ֱ������������津����λ���뻬��ͼƬ��ȵĲ�
	int tempCount = 0;// ȫ�ֱ���
	int mDrawX = 0;// ȫ�ֱ���������ÿ�λ滭��X����

	/**
	 * ��ͼƬ
	 */
	private void drawImage(Canvas canvas) {
		// �õ����ֵ
		drawXMax = getScreenWidth() - LEFT_DISTANCE - leftImageView.getWidth();
		// ���û���ͼƬ��X����
		int drawX = locationX - leftImageView.getWidth();
		// ���û���ͼƬ��Y����
		int drawY = leftImageView.getTop();
		// if(drawX<LEFT_DISTANCE){
		// leftImageView.setVisibility(View.VISIBLE);
		// return;
		// }else{
		// if(isLocked()){
		// return;
		// }
		// }
		// ������λ��С�ڻ���ͼƬ��X����ֵ
		if (locationX < LEFT_DISTANCE) {

			return;
		}
		System.out.println("temp=" + temp);
		// ���X����С����߿����
		// ���ߵ�����߿����
		// ���ߵ��λ�õ�����߿����
		// ���������ʱ�ڣ�LEFT_DISTANCE+����ͼƬ�Ŀ�ȣ���Χ�ڻ
		if (drawX < LEFT_DISTANCE || drawX == LEFT_DISTANCE
				|| locationX == LEFT_DISTANCE) {
			// ���X���������һ�ε�X����
			if (drawX > temp) {

				/**
				 * ���ͣ�Ϊʲô�������tempCount�Լӣ� ��������ķ�Χ�ڻ���ͼƬ����ʾ��Χ�ڣ�
				 * �����һ�ε����DrawX=20����ô�ڶ��Σ�DrawX=21��������
				 * ������Ҫ�Լӡ����Һ��滹Ҫ���tempCount����ֵ��
				 */
				// �����Լ�
				tempCount++;
				drawX = LEFT_DISTANCE + tempCount;
			} else {// �����˵���ǵ�һ�ε��
				drawX = LEFT_DISTANCE;
			}
		} else {// ����������Ǵ������λ����һ���Ƚ�����ĵط���LEFT_DISTANCE+����ͼƬ�Ŀ�ȣ�,����֮ǰ��λ�û�����Ȼ������ػ�
			drawX = drawX + tempCount;
		}
		// �жϻ���ͼƬ��X�����Ƿ������ֵ
		if (drawX > drawXMax || drawX == drawXMax) {
			drawX = drawXMax;
		}
		// �ѻ���ͼƬ��X����ȡ�����������ж��Ƿ�����ɹ�
		mDrawX = drawX;
		leftImageView.setVisibility(View.GONE);
		if (locationX == LEFT_DISTANCE) {
			leftImageView.setVisibility(View.VISIBLE);
		}
		canvas.drawBitmap(drawBitmap, drawX < 0 ? LEFT_DISTANCE : drawX, drawY,
				null);
		// �õ����λ�������ͼƬ�Ĳ�
		temp = locationX - leftImageView.getWidth();
	}

	/**
	 * @return �Ƿ��Ѿ��ɹ�����
	 */
	private boolean isLocked() {

		if (mDrawX == drawXMax) {
			Message msg = new Message();
			msg.what = MainActivity.LOCKED_SUCCESS;
			mHandler.sendMessage(msg);
			return true;
		}
		return false;

	}

	/**
	 * @return ��Ļ���
	 */
	private int getScreenWidth() {
		int width = ((WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth();
		return width;
	}

	/**
	 * ��MainActivityͨ��
	 * 
	 * @param handler
	 */
	public void setMainHandler(Handler handler) {
		this.mHandler = handler;
	}

}
