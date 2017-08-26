package com.lockscreen.android.activity;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

public class LockSreenReceiver extends BroadcastReceiver {

	private KeyguardManager keyguardManager;
	private KeyguardManager.KeyguardLock keyguardLock;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		System.out.println("if ����");
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			System.out.println("if ����");
			Intent lockIntent = new Intent(context, MainActivity.class);
			lockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			keyguardManager = (KeyguardManager) context
					.getSystemService(Context.KEYGUARD_SERVICE);
			keyguardLock = keyguardManager.newKeyguardLock("");
			keyguardLock.disableKeyguard();

			context.startActivity(lockIntent);

		}

	}

}
