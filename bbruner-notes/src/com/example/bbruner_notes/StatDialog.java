package com.example.bbruner_notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class StatDialog extends DialogFragment {
	private String dialogMessage = "Statistics currently unavailable.";
	private String emailAddress = null;

	public void showStat(Context ctx)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(this.dialogMessage)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});
		builder.create().show();
	}
	
	public void requestEmail(Context ctx)
	{
		final EditText addressInput = new EditText(ctx);
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setView(addressInput);
		builder.setMessage(this.dialogMessage)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id)
					{
						emailAddress = addressInput.getText().toString();
						dialog.cancel();
					}
				});
		builder.create().show();
	}
	
	public String getEmail(){ return emailAddress; }
	
	public void setMessage(String msg)
	{
		this.dialogMessage = msg;
	}
	
}
