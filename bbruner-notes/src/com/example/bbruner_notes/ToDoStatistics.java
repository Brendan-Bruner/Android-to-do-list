package com.example.bbruner_notes;

import java.util.ArrayList;
import java.util.Iterator;

public class ToDoStatistics {
	private long checkedToDo;
	private long uncheckedToDo;
	private long archivedToDo;
	private long checkedArchivedToDo;
	private long uncheckedArchivedToDo;
	
	public ToDoStatistics()
	{
		this.setCheckedToDo(0);
		this.setUncheckedToDo(0);
		this.setArchivedToDo(0);
		this.setCheckedArchivedToDo(0);
		this.setUncheckedArchivedToDo(0);
	}
	
	public long getCheckedToDo() {
		return checkedToDo;
	}
	public void setCheckedToDo(long checkedToDo) {
		this.checkedToDo = checkedToDo;
	}
	
	public long getUncheckedToDo() {
		return uncheckedToDo;
	}
	public void setUncheckedToDo(long uncheckedToDo) {
		this.uncheckedToDo = uncheckedToDo;
	}
	public long getArchivedToDo() {
		return archivedToDo;
	}
	public void setArchivedToDo(long archivedToDo) {
		this.archivedToDo = archivedToDo;
	}
	public long getCheckedArchivedToDo() {
		return checkedArchivedToDo;
	}
	public void setCheckedArchivedToDo(long checkedArchivedToDo) {
		this.checkedArchivedToDo = checkedArchivedToDo;
	}
	public long getUncheckedArchivedToDo() {
		return uncheckedArchivedToDo;
	}
	public void setUncheckedArchivedToDo(long uncheckedArchivedToDo) {
		this.uncheckedArchivedToDo = uncheckedArchivedToDo;
	}
	
	public void calcStatistics(ArrayList<ToDo> main, ArrayList<ToDo> archive)
	{
		ToDo todo;
		Iterator<ToDo> mainIter = main.iterator();
		Iterator<ToDo> archiveIter = archive.iterator();
		
		this.setArchivedToDo(archive.size());
		
		while(mainIter.hasNext())
		{
			todo = mainIter.next();
			if(todo.isFinished()){ this.setCheckedToDo((this.getCheckedToDo()+1)); }
			else{ this.setUncheckedToDo(this.getUncheckedToDo()+1); }
		}
		
		while(archiveIter.hasNext())
		{
			todo = archiveIter.next();
			if(todo.isFinished())
			{
				this.setCheckedToDo(this.getCheckedToDo()+1);
				this.setCheckedArchivedToDo(this.getCheckedArchivedToDo()+1);
			}
			else
			{
				this.setUncheckedToDo(this.getUncheckedToDo()+1);
				this.setUncheckedArchivedToDo(this.getUncheckedArchivedToDo()+1);
			}
		}
	}
	
	public String toString()
	{
		return "Total Finished: "+this.getCheckedToDo()+
				"\nTotal Unfinished: "+this.getUncheckedToDo()+
				"\nTotal Archived: "+this.getArchivedToDo()+
				"\nTotal Finished Archived: "+this.getCheckedArchivedToDo()+
				"\nTotal Unfinished Archived: "+this.getUncheckedArchivedToDo();
	}

}
