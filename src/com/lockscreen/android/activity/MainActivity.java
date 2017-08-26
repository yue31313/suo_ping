package com.lockscreen.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private Intent intentService;
	public static final int LOCKED_SUCCESS=1;
	public static final int VIEW_INVALIDATE=2;
	private MyRelativeLayout myRelativeLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		initViews();
		startService();
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==LOCKED_SUCCESS){
				MainActivity.this.finish();
			}else if(msg.what==VIEW_INVALIDATE){
				System.out.println("view_invalidate............");
				myRelativeLayout.invalidate();
			}
		};
	};
	

	/**
	 * 启动服务
	 */
	private void startService() {
		// TODO Auto-generated method stub
		intentService = new Intent(MainActivity.this, LockScreenService.class);
		startService(intentService);
	}

	/**
	 * 初始化视图，并添加相应事件
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		textView = (TextView) findViewById(R.id.id_main_txt_test);
		textView.setOnClickListener(clickListener);
		myRelativeLayout=(MyRelativeLayout) findViewById(R.id.id_main_mrlt_relativelayout);
		myRelativeLayout.setMainHandler(handler);
//		myRelativeLayout.getBackground().setAlpha(180);
	}

	/**
	 * 
	 * 屏蔽掉Home键
	 * 
	 */
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
//		getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		super.onAttachedToWindow();
	}
	
	/**
	 * 
	 * 屏蔽掉返回键
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return true;
		}else {
			return super.onKeyDown(keyCode, event);	
		}
		
	}
	
	/**
	 * 设置点击事件
	 */
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("MainActivity-->onClick-->来了");
			//创建对话框并显示，使用系统的对话框
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("【提示】");
			builder.setMessage("你要退出是不？");
			builder.setPositiveButton("对头",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MainActivity.this.finish();
						}
					});

			builder.setNegativeButton("爬开",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			//创建并显示
			builder.create().show();
		}
	};
}