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

import java.util.ArrayList;
import java.util.Iterator;

import com.example.data_management.ArchiveFileIO;
import com.example.data_management.EmailInterface;
import com.example.data_management.FileIO;
import com.example.data_management.MainToDoFileIO;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	public final static String ARCHIVE_TODO = "com.example.bbruner_notes.MainActivity.ARCHIVE_TODO";
	private ArrayAdapter<ToDo> adapter;
	private ArrayList<ToDo> toDoItems;
	private ArrayList<ToDo> archiveToDo;
	private ListView toDoList;
	private FileIO iOMain;
	private FileIO iOArchive;
	private EmailInterface emailInterface;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		
		// New FileIO class to save ToDos
		iOMain = new MainToDoFileIO(this);
		iOArchive = new ArchiveFileIO(this);
		emailInterface = new EmailInterface(this);
		
		this.toDoItems = iOMain.loadToDo();
		this.archiveToDo = iOArchive.loadToDo();
		
		this.ctx = this;
		
		// Get the list view element in the activity_main xml file
		this.toDoList = (ListView) findViewById(R.id.list_view_main);	
		
		// Make adapter for the to do list
		this.adapter = new ToDoAdapter(this, this.toDoItems);
		
		// Set this adapter to manage to do items being added and removed
		this.toDoList.setAdapter(this.adapter);

		// Set a listener for a cab
		this.toDoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		this.toDoList.setMultiChoiceModeListener(this.multiChoiceModeListener());
	}

	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		/* must go through all to do items and remove there selected status before saving them */
		Iterator<ToDo> mainIter = toDoItems.iterator();
		Iterator<ToDo> archIter = archiveToDo.iterator();
		
		while(mainIter.hasNext()){ mainIter.next().setSelected(false); }
		while(archIter.hasNext()){ archIter.next().setSelected(false); }
		
		
		// save to do items when activity is suspended
		iOMain.saveToDo(toDoItems);
		iOArchive.saveToDo(archiveToDo);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here.
		switch(item.getItemId())
		{
		
		case R.id.go_to_archived_from_main: 

			Intent intentArchived = new Intent(this, ArchiveActivity.class);
			Toast.makeText(this, "Archived To Dos", Toast.LENGTH_SHORT).show();
			startActivity(intentArchived);
			return true;
		
		case R.id.go_to_all_from_main:
		
			Intent intentAll = new Intent(this, AllActivity.class);
			Toast.makeText(this, "All To Dos", Toast.LENGTH_SHORT).show();
			startActivity(intentAll);
			return true;
		
		case R.id.statistics:
			
			ToDoStatistics stat = new ToDoStatistics();
			stat.calcStatistics(toDoItems, archiveToDo);
			
			StatDialog msg = new StatDialog();
			msg.setMessage(stat.toString());
			msg.showStat(this);
			return true;
			
		case R.id.change_email:
			
			Intent emailIntent = new Intent(this, EmailActivity.class);
			emailInterface.saveString(null);
			startActivity(emailIntent);
			
		default:
			
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public void newToDo(View view)
	{
		/* This function is called when the button to create a new todo is pressed
		 * 
		 */
		// Get the name of the todo
		String msg = ((EditText) findViewById(R.id.new_todo_entry)).getText().toString();
		
		// Error check the message
		if(msg == null || msg.trim().length() <= 0){ return; }
		
		// Add the todo to the array list, notify the adapter of the change
		this.toDoItems.add(new ToDo(msg, null, false));
		this.adapter.notifyDataSetChanged();
		
		// Reset the text in the field where a new todo's name is entered
		((EditText) findViewById(R.id.new_todo_entry)).setText("", TextView.BufferType.EDITABLE);
	}
	
	public void onCheck(View listView)
	{
		/* This function is called when a check box is clicked
		 * 
		 */
		// Get ToDo data structure for selected item in list view
		ToDo checkedToDo = (ToDo) listView.getTag();
		
		// Toggle the checked state of that ToDo item with respect to the internal data structure
		checkedToDo.toggleSelected();
		
		// Toggle the checked state of the item with respect to the context menu
		this.toDoList.setItemChecked(this.toDoList.getPositionForView(listView), checkedToDo.isSelected());
	}
		
	private AbsListView.MultiChoiceModeListener multiChoiceModeListener()
	{
		/* This function returns the multi choice mode listener for the list view in activity main
		 * 
		 */
		return new AbsListView.MultiChoiceModeListener()
		{
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.to_do_menu, menu);
		        return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item)
			{
				// Get the position of all checked items
				SparseBooleanArray ids = toDoList.getCheckedItemPositions();
				
				// Get number of checked items
				int size = ids.size();
				
				switch(item.getItemId())
				{
				
				case R.id.action_search:	
					// Iterate through the bizarre data structure SparseBooleanArray to remove selected
					// items from the list view
					for(int i = size-1; i >= 0; i--)
					{
						int key = ids.keyAt(i);
						if(ids.get(key))
						{
							toDoItems.remove(adapter.getItem(key));
						}
					}
					
					// Notify the adapter of the changes then close the menu
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
					
				case R.id.mark_finished:
					// Iterate through the bizarre data structure SparseBooleanArray to mark unfinished
					// selected items as finished
					for(int i = size-1; i >= 0; i--)
					{
						int key = ids.keyAt(i);
						if(ids.get(key))
						{
							ToDo todo = adapter.getItem(key);
							todo.setFinished(true);
							todo.setSelected(false);
						}
					}
					
					// Notify the adapter of the changes then close the menu
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
					
				case R.id.mark_unfinished:
					// Iterate through the bizarre data structure SparseBooleanArray to mark finished
					// selected items as finished
					for(int i = size-1; i >= 0; i--)
					{
						int key = ids.keyAt(i);
						if(ids.get(key))
						{
							ToDo todo = adapter.getItem(key);
							todo.setFinished(false);
							todo.setSelected(false);
						}
					}
					// Notify the adapter of the changes then close the menu
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
					
				case R.id.action_archive:
					// Iterate through the bizarre data structure SparseBooleanArray to archive selected
					// items
					for(int i = size-1; i >= 0; i--)
					{
						int key = ids.keyAt(i);
						if(ids.get(key))
						{
							ToDo todo = adapter.getItem(key);
							todo.toggleSelected();
							archiveToDo.add(todo);
							toDoItems.remove(todo);
						}
					}
					
					// Notify the adapter of the changes then close the menu
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
					
				case R.id.email:
					
					String emailInterfaceMsg = "To Do Items";
					
					ArrayList<ToDo> all = new ArrayList<ToDo>();
					all.addAll(toDoItems);
					all.addAll(archiveToDo);
					
					Iterator<ToDo> iterAll = all.iterator();
					while(iterAll.hasNext())
					{
						ToDo todo = iterAll.next();
						if(todo.isSelected()){ emailInterfaceMsg = emailInterfaceMsg + "\n" + todo.toString(); }
					}
				
					emailInterface.emailToDo(ctx, emailInterface.loadString(), emailInterfaceMsg);
					return true;
					
				default:
					return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				/* must go through all to do items and remove there selected status before saving them */
				Iterator<ToDo> mainIter = toDoItems.iterator();
				Iterator<ToDo> archIter = archiveToDo.iterator();
				
				while(mainIter.hasNext()){ mainIter.next().setSelected(false); }
				while(archIter.hasNext()){ archIter.next().setSelected(false); }
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
}


