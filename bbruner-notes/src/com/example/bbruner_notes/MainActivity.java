package com.example.bbruner_notes;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class MainActivity extends ActionBarActivity {
	private ArrayAdapter<ToDo> adapter;
	private ArrayList<ToDo> toDoItems;
	private ListView toDoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the list view element in the activity_main xml file
		this.toDoList = (ListView) findViewById(R.id.list_view_main);
		
		// Create the array list of all to do items
		this.toDoItems = new ArrayList<ToDo>();
		
		// Make adapter for the to do list
		this.adapter = new ToDoAdapter(this, this.toDoItems);
		
		// Set this adapter to manage to do items being added and removed
		this.toDoList.setAdapter(this.adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void newToDo(View view)
	{
		String msg = ((EditText) findViewById(R.id.new_todo_entry)).getText().toString();
		
		this.toDoItems.add(new ToDo(msg, null, false));
		this.adapter.notifyDataSetChanged();
		((EditText) findViewById(R.id.new_todo_entry)).setText("", TextView.BufferType.EDITABLE);
	}
	
	public void onCheck(View listView)
	{
		ToDo checkedToDo = (ToDo) listView.getTag();
		//adapter.remove(checkedToDo);
		checkedToDo.toggleChecked();
		return;
	}
}
