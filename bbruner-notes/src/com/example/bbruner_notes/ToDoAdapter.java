package com.example.bbruner_notes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ToDoAdapter extends ArrayAdapter<ToDo>{
	private Context adaptersContext;
	private ArrayList<ToDo> toDoList;
	
	public ToDoAdapter(Context context, ArrayList<ToDo> toDo)
	{
		super(context, android.R.layout.simple_list_item_1, android.R.id.text1, toDo);
		this.adaptersContext = context;
		this.toDoList = toDo;
	}
	
	@Override
	public View getView(int listPosition, View listView, ViewGroup parent)
	{		
		class TempToDo{
			public ToDo todo;
			public TextView textView;
			public ImageView imageView;
			public CheckBox checkbox;
		};
		final TempToDo temp = new TempToDo();
		
		LayoutInflater inflater = (LayoutInflater) this.adaptersContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listViewRow = inflater.inflate(R.layout.list_item_view, parent, false);
		
		temp.todo = this.toDoList.get(listPosition);
		temp.textView = (TextView) listViewRow.findViewById(R.id.list_text);
		temp.imageView = (ImageView) listViewRow.findViewById(R.id.list_image);
		temp.checkbox = (CheckBox) listViewRow.findViewById(R.id.todo_checkbox);
		temp.checkbox.setTag(temp.todo);
		
		temp.textView.setText(this.toDoList.get(listPosition).getName());
		
		if(temp.todo.isFinished()){ temp.imageView.setImageResource(R.drawable.ic_action_important); }
		else { temp.imageView.setImageResource(R.drawable.ic_action_not_important); }
		
		temp.checkbox.setChecked(this.toDoList.get(listPosition).isSelected());
		
		listViewRow.setTag(temp);
		
		return listViewRow;
	} 	
}
