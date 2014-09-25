package com.example.bbruner_notes;

import com.example.data_management.EmailInterface;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		if(emailInterface.loadString() != null)
		{
			Intent intentAll = new Intent(this, MainActivity.class);
			startActivity(intentAll);
		}
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
		Intent intentAll = new Intent(this, MainActivity.class);
		startActivity(intentAll);
	}

}
