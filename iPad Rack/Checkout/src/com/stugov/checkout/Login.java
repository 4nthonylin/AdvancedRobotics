package com.stugov.checkout;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Login extends Activity {
	private final String TAG = Portal.class.getSimpleName();
	public final static String USER_MESSAGE = "com.stugov.checkout.USER";
	public final static String PASS_MESSAGE = "com.stugov.checkout.PASS";

	private static final String ACTION_USB_PERMISSION = "com.google.android.DemoKit.action.USB_PERMISSION";

	private static UsbManager mUsbManager;
	private PendingIntent mPermissionIntent;
	private boolean mPermissionRequestPending;
	static Intent intent;

	UsbAccessory mAccessory;
	ParcelFileDescriptor mFileDescriptor;
	public static FileInputStream mInputStream;
	public static FileOutputStream mOutputStream;

	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbAccessory accessory = UsbManager.getAccessory(intent);
					if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						openAccessory(accessory);
					} else {
						Log.d(TAG, "permission denied for accessory "
								+ accessory);
					}
					mPermissionRequestPending = false;
				}
			} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
				UsbAccessory accessory = UsbManager.getAccessory(intent);
				if (accessory != null && accessory.equals(mAccessory)) {
					closeAccessory();
				}
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_login);
		intent = getIntent();
		mUsbManager = UsbManager.getInstance(this);
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
		registerReceiver(mUsbReceiver, filter);
		EditText userText = (EditText) findViewById(R.id.userField);
		userText.setText("");
	}

	public void login(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

		Intent intent = new Intent(this, Portal.class);

		EditText userText = (EditText) findViewById(R.id.userField);
		// EditText passText = (EditText) findViewById(R.id.passField);
		String user = userText.getText().toString();
		// String pass = passText.getText().toString();
		if (user.length() == 8) {
			intent.putExtra(USER_MESSAGE, user);
			// intent.putExtra(PASS_MESSAGE, pass);
			startActivity(intent);
		} else {
			Context context = getApplicationContext();
			CharSequence text = "Incorrect Username Length";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		}
	}

	static UsbAccessory accessory;
	static ParcelFileDescriptor pfd;
	static FileDescriptor fd;
	static InputStream input;
	static OutputStream output;

	public static void startAcc() {
		accessory = UsbManager.getAccessory(intent);
		pfd = mUsbManager.openAccessory(accessory);
		if (pfd != null) {
			fd = pfd.getFileDescriptor();
			input = new FileInputStream(fd);
			output = new FileOutputStream(fd);
		}
	}
	public static void closeAcc(){
		try {
			if (pfd != null) {
				pfd.close();
			}
		} catch (IOException e) {
		} finally {
			pfd = null;
			accessory = null;
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input = null;
			output = null;
		}
	}

	public static void sendCmd(int i) {
		try {
			output.write(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		EditText userText = (EditText) findViewById(R.id.userField);
		userText.setText("");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mUsbReceiver);
		closeAccessory();
	}
	
	public void onStop(){
		super.onStop();
		unregisterReceiver(mUsbReceiver);
		closeAccessory();
	}
	
	public void onRestart(){
		super.onRestart();
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
		registerReceiver(mUsbReceiver, filter);
	}

	private void openAccessory(UsbAccessory accessory) {
		mFileDescriptor = mUsbManager.openAccessory(accessory);
		if (mFileDescriptor != null) {
			mAccessory = accessory;
			FileDescriptor fd = mFileDescriptor.getFileDescriptor();
			mInputStream = new FileInputStream(fd);
			mOutputStream = new FileOutputStream(fd);
			Log.d(TAG, "accessory opened");
		} else {
			Log.d(TAG, "accessory open fail");
		}
	}

	private void closeAccessory() {
		try {
			if (mFileDescriptor != null) {
				mFileDescriptor.close();
			}
		} catch (IOException e) {
		} finally {
			mFileDescriptor = null;
			mAccessory = null;
		}
	}
}
