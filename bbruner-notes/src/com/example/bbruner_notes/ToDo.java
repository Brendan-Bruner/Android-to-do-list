package com.example.bbruner_notes;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private boolean finished;
	private boolean selected;
	
	public ToDo(String name, String description, boolean finished)
	{
		this.name = name;
		this.description = description;
		this.finished = finished;
		this.selected = false;
	}
	
	/*@Override
	public ToDo(Parcel in)
	{
		String[] inputs = new String[3];
		in.readStringArray(inputs)
		
		this.name = inputs[0];
		this.name description = inputs[1];
		this.finished = Boolean.parseBoolean(inputs[2]);
		this.selected = Boolean.parseBoolean(inputs[3]);
	}*/
	
	public void setSelected(boolean selected){ this.selected = selected; }
	
	public void toggleSelected(){ this.selected = !this.selected; }
	
	public boolean isSelected(){ return this.selected; }
	
	public void setFinished(boolean finished){ this.finished = finished; }
	
	public void toggleFinished(){return;}//{ this.finished = !this.finished; }
	/*{
		if(this.finished)
		{
			this.finished = false;
		}
		else
		{
			this.finished = true;
		}
	}*/
	
	public boolean isFinished(){ return this.finished; }
	
	public void setDescription(String description){ this.description = description; }
	
	public String getDescription(){ return this.description; }
	
	public void setName(String name){ this.name = name; }
	
	public String getName(){ return this.name; }

	
	/*
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
		
	}*/

}
