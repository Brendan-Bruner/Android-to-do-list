package com.example.bbruner_notes;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArchiveActivity extends ActionBarActivity {
	private ArrayAdapter<ToDo> adapter;
	private ArrayList<ToDo> toDoItems;
	private ListView toDoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		
		// Get the list view element in the activity_archive xml file
		this.toDoList = (ListView) findViewById(R.id.list_view_archive);
		
		// Create the array list of all to do items
		this.toDoItems = new ArrayList<ToDo>();
		
		// Make adapter for the to do list
		this.adapter = new ToDoAdapter(this, this.toDoItems);
		
		// Set this adapter to manage to do items being added and removed
		this.toDoList.setAdapter(this.adapter);

		// Do initial push of data here from the main to do list to the archived to do list
		
		// Set the view to have a listener for when items are selected. Bring up a context menu
		// in that event to deal with all selected items
		this.toDoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		this.toDoList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){

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
					
				case R.id.action_archive:
					// Iterate through the bizarre data structure SparseBooleanArray to archive selected
					// items
					
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
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
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
}
