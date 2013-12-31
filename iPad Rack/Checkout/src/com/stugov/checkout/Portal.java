package com.stugov.checkout;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;
import com.makemyandroidapp.googleformuploader.GoogleFormUploader;

public class Portal extends Activity {
	private final String TAG = Portal.class.getSimpleName();
	private GoogleFormUploader logger;
	private String username;
	
	private static final String ACTION_USB_PERMISSION = "com.google.android.DemoKit.action.USB_PERMISSION";
	private UsbManager mUsbManager;
	UsbAccessory mAccessory;
	private PendingIntent mPermissionIntent;
	private boolean mPermissionRequestPending;
	
	ParcelFileDescriptor mFileDescriptor;
	public static FileInputStream mInputStream;
	public static FileOutputStream mOutputStream;
	
	//private Logger log = new Logger("10loiLEXRtg48iYXPr-uOul1MOO0EuVnaYwSGkXb5VVo");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portal);

		Intent intent = getIntent();
		username = intent.getStringExtra(Login.USER_MESSAGE);
		// String password = intent.getStringExtra(Login.PASS_MESSAGE);
		TextView holder = (TextView) findViewById(R.id.userText);
		holder.setText("Hello: " + username);
	}
	
	public void checkin(View view) {
		Login.startAcc();
   		Login.sendCmd(176);
		/*
		log.add("9119542", username);
		log.add("445880952", "CHECKIN");
		log.log();
		*/
   		
   		GoogleFormUploader logger = new GoogleFormUploader("10loiLEXRtg48iYXPr-uOul1MOO0EuVnaYwSGkXb5VVo");
   		logger.addEntry("9119542", username);
		logger.addEntry("445880952", "CHECKIN");
		logger.upload();
		
		Context context = getApplicationContext();
		CharSequence text = "Success";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		Intent test = new Intent(this, Login.class);
	    test.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
		startActivity(test);
		this.finish();
	}

	public void checkout(View view) {
		Login.startAcc();
		Login.sendCmd(176);
		/*
		log.add("9119542", username);
		log.add("445880952", "CHECKOUT");
		log.log();
		*/
		GoogleFormUploader logger = new GoogleFormUploader("10loiLEXRtg48iYXPr-uOul1MOO0EuVnaYwSGkXb5VVo");
   		logger.addEntry("9119542", username);
		logger.addEntry("445880952", "CHECKOUT");
		logger.upload();
		
		Context context = getApplicationContext();
		CharSequence text = "Success";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		Intent test = new Intent(this, Login.class);
	    test.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
		startActivity(test);
		this.finish();
	}
}
