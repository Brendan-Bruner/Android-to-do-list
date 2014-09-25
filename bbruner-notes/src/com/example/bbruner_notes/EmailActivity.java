package com.example.bbruner_notes;

import com.example.data_management.EmailInterface;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EmailActivity extends Activity {
	private EmailInterface emailInterface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		emailInterface = new EmailInterface(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}
	
	public void registerEmail(View view)
	{
		String msg = ((EditText) findViewById(R.id.email_string)).getText().toString();
		emailInterface.saveString(msg);
		finish();
	}

}
