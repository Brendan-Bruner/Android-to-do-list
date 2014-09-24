package com.example.bbruner_notes;

public class ToDo {
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

}
