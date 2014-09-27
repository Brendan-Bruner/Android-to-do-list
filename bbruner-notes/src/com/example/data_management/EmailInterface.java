package com.example.data_management;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.bbruner_notes.ToDo;

public class EmailInterface{
	private static final String FILENAME = "email.sav";
	private Context ctx;
	
	
	public EmailInterface(Context ctx)
	{
		this.ctx = ctx;
	}

	public void emailToDo(Context ctx, String address, String msg) {
		/* this function implements the code written by fiXedd at 
		 * http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
		 *
		 * fiXedd - http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
		 */
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "bbruner-notes - Your To Do List");
		emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
		try {
		    ctx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(ctx, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public String loadString()
	{
		String ltd = null;
		
		try
		{
			FileInputStream fis = ctx.openFileInput(EmailInterface.FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ltd = (String) ois.readObject();
		}
		catch (Exception e)
		{
			Log.i("ToDo FileIO", "Error Casting");
			e.printStackTrace();
		}
		
		return ltd;
	}
	
	public void saveString(String ltd)
	{
		try
		{
			FileOutputStream fos = ctx.openFileOutput(EmailInterface.FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ltd);
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
