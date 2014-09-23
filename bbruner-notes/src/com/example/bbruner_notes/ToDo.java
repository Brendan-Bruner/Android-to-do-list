package com.example.bbruner_notes;

public class ToDo {
	private String name;
	private String description;
	private boolean checked;
	
	public ToDo(String name, String description, boolean checked)
	{
		this.name = name;
		this.description = description;
		this.checked = checked;
	}
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public void toggleChecked()
	{
		if(this.checked)
		{
			this.checked = false;
		}
		else
		{
			this.checked = true;
		}
	}
	
	public boolean isChecked()
	{
		return this.checked;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}

}
