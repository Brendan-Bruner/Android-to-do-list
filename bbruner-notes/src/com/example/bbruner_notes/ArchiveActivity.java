package com.example.bbruner_notes;

import java.util.ArrayList;

import com.example.data_management.ArchiveFileIO;
import com.example.data_management.FileIO;
import com.example.data_management.MainToDoFileIO;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArchiveActivity extends ActionBarActivity {
	private ArrayAdapter<ToDo> adapter;
	private ArrayList<ToDo> archivedToDoItems;
	private ArrayList<ToDo> mainToDoItems;
	private ListView toDoList;
	private FileIO iOArchive;
	private FileIO iOMain;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		// Get the list view element in the activity_archive xml file
		this.toDoList = (ListView) findViewById(R.id.list_view_archive);
		
		// Get the array list of archived to do items and main to do items
		iOArchive = new ArchiveFileIO(this);
		iOMain = new MainToDoFileIO(this);
		archivedToDoItems = iOArchive.loadToDo();
		mainToDoItems = iOMain.loadToDo();
		
		
		// Make adapter for the to do list
		this.adapter = new ToDoAdapter(this, this.archivedToDoItems);
		
		// Set this adapter to manage to do items being added and removed
		this.toDoList.setAdapter(this.adapter);
		
		// Set the view to have a listener for when items are selected. Bring up a context menu
		// in that event to deal with all selected items
		this.toDoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		this.toDoList.setMultiChoiceModeListener(this.multiChoiceModeListener());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here
		
		int id = item.getItemId();
		
		if (id == R.id.go_to_todo) {
			// Add all archived todos to the intent then start the archive activity
			Intent intent = new Intent(this, MainActivity.class);
			iOArchive.saveToDo(this.archivedToDoItems);
			iOMain.saveToDo(this.mainToDoItems);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		        inflater.inflate(R.menu.archive_to_do_menu, menu);
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
							archivedToDoItems.remove(adapter.getItem(key));
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
					
				case R.id.action_unarchive:
					// Iterate through the bizarre data structure SparseBooleanArray to archive selected
					// items
					for(int i = size-1; i >= 0; i--)
					{
						int key = ids.keyAt(i);
						if(ids.get(key))
						{
							ToDo todo = adapter.getItem(key);
							todo.toggleSelected();
							mainToDoItems.add(todo);
							archivedToDoItems.remove(todo);
						}
					}
					
					// Notify the adapter of the changes then close the menu
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
					
				default:
					return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
}
