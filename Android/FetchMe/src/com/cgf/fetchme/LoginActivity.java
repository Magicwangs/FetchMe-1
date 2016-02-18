package com.cgf.fetchme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";

	protected static final int LOGIN_SUCCESS = 0;
	protected static final int LOGIN_FAIL = 1;

	private EditText mUserNameEditText = null;
	private EditText mUserPwdEditText = null;
	private Button mLoginButton = null;
	private String mUserName = null;
	private String mUserPwd = null;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(TAG, "handleMessage----in----");
			switch (msg.what) {
			case LOGIN_SUCCESS:
				Log.d(TAG, "LOGIN_SUCCESS");
				Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.d(TAG, "LOGIN_FAIED");
				Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		InitUI();
	}

	private void InitUI() {
		// TODO Auto-generated method stub
		mUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
		mUserPwdEditText = (EditText) findViewById(R.id.userPwdEditText);
		mLoginButton = (Button) findViewById(R.id.loginButton);
		mLoginButton.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.loginButton) {
			Log.d(TAG, "loginButton----onClick----in----");
			Login();
		}
	}

	private void Login() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Login----in----");

		mUserNameEditText.clearFocus();
		mUserPwdEditText.clearFocus();
		// hide the soft keyboard
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

		mUserName = mUserNameEditText.getText().toString();
		mUserPwd = mUserPwdEditText.getText().toString();

		LoginOnline(mUserName, mUserPwd);
		Log.d(TAG, "Login----out----" + "userName=" + mUserName + ",userPwd=" + mUserPwd);
	}

	private void LoginOnline(String userName, String userPwd) {
		Log.d(TAG, "LoginOnline----in----" + "userName=" + userName + ",userPwd=" + userPwd);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = null;
				URL url = null;
				HttpURLConnection connection = null;
				InputStreamReader in = null;
				try {
					url = new URL("http://chenguanfu.f3322.net:8000/FetchMe/login?userName=" + mUserName + "&" + "userPwd=" + mUserPwd);
					connection = (HttpURLConnection) url.openConnection();
					in = new InputStreamReader(connection.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(in);
					StringBuffer strBuffer = new StringBuffer();
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						strBuffer.append(line);
					}
					result = strBuffer.toString();
					if(result.equals("login success"))
						mHandler.sendEmptyMessage(LOGIN_SUCCESS);
					else
						mHandler.sendEmptyMessage(LOGIN_FAIL);
						
					Log.d(TAG, "result=" + result);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		Log.d(TAG, "LoginOnline----out----");
	}
}
