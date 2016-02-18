package com.cgf.fetchme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";

	private EditText mUserNameEditText = null;
	private EditText mUserPwdEditText = null;
	private Button mLoginButton = null;

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

		String userName = mUserNameEditText.getText().toString();
		String userPwd = mUserPwdEditText.getText().toString();

		LoginOnline(userName, userPwd);
		Log.d(TAG, "Login----out----" + "userName=" + userName + ",userPwd=" + userPwd);
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
					url = new URL("http://chenguanfu.f3322.net:8000/polls/?user=cgf");
					connection = (HttpURLConnection) url.openConnection();
					in = new InputStreamReader(connection.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(in);
					StringBuffer strBuffer = new StringBuffer();
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						strBuffer.append(line);
					}
					result = strBuffer.toString();
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
