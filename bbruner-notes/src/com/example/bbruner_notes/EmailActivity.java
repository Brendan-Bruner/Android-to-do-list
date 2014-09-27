Copyright 2014 Brendan Bruner

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

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
