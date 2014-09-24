package com.example.bbruner_notes;

public class ToDo {
	private String name;
	private String description;
	private boolean finished;
	
	public ToDo(String name, String description, boolean finished)
	{
		this.name = name;
		this.description = description;
		this.finished = finished;
	}
	
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
	public void toggleFinished()
	{
		if(this.finished)
		{
			this.finished = false;
		}
		else
		{
			this.finished = true;
		}
	}
	
	public boolean isFinished()
	{
		return this.finished;
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
