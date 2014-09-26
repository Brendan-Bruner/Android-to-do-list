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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AllActivity extends ActionBarActivity {
	private ArrayAdapter<ToDo> adapter;
	private ArrayList<ToDo> archivedToDoItems;
	private ArrayList<ToDo> mainToDoItems;
	private ArrayList<ToDo> allToDoItems;
	private ListView toDoList;
	private FileIO iOArchive;
	private FileIO iOMain;
	private EmailInterface emailInterface;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		allToDoItems = new ArrayList<ToDo>();
		// Get the list view element in the activity_archive xml file
		this.toDoList = (ListView) findViewById(R.id.list_view_all);
		
		// Get the array list of archived to do items and main to do items
		iOArchive = new ArchiveFileIO(this);
		iOMain = new MainToDoFileIO(this);
		emailInterface = new EmailInterface(this);
		archivedToDoItems = iOArchive.loadToDo();
		mainToDoItems = iOMain.loadToDo();
		
		this.ctx = this;
		
		allToDoItems.addAll(archivedToDoItems);
		allToDoItems.addAll(mainToDoItems);
		
		
		// Make adapter for the to do list
		this.adapter = new ToDoAdapter(this, this.allToDoItems);
		
		// Set this adapter to manage to do items being added and removed
		this.toDoList.setAdapter(this.adapter);
		
		// Set the view to have a listener for when items are selected. Bring up a context menu
		// in that event to deal with all selected items
		this.toDoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		this.toDoList.setMultiChoiceModeListener(this.multiChoiceModeListener());
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		/* must go through all to do items and remove there selected status before saving them */
		Iterator<ToDo> mainIter = mainToDoItems.iterator();
		Iterator<ToDo> archIter = archivedToDoItems.iterator();
		
		while(mainIter.hasNext()){ mainIter.next().setSelected(false); }
		while(archIter.hasNext()){ archIter.next().setSelected(false); }
		
		// save to do items when activity is suspended
		iOMain.saveToDo(mainToDoItems);
		iOArchive.saveToDo(archivedToDoItems);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here
		switch(item.getItemId())
		{
		
		case R.id.go_to_todo_from_all:
			
			// Add all archived todos to the intent then start the archive activity
			Intent intentMain = new Intent(this, MainActivity.class);
			this.updateToDos();
			Toast.makeText(this, "Main To Dos", Toast.LENGTH_SHORT).show();
			startActivity(intentMain);
			return true;
		
		case R.id.go_to_archived_from_all:
			
			// Add all archived todos to the intent then start the archive activity
			Intent intentArchive = new Intent(this, ArchiveActivity.class);
			this.updateToDos();
			Toast.makeText(this, "Archived To Dos", Toast.LENGTH_SHORT).show();
			startActivity(intentArchive);
			return true;
			
		case R.id.statistics:
			
			this.updateToDos();
			ToDoStatistics stat = new ToDoStatistics();
			stat.calcStatistics(mainToDoItems, archivedToDoItems);
			
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
	
	private void updateToDos()
	{
		/* This function will take take the intersection of main and archived to do items with all to do items
		 * then overwrite that intersection into the main and archived to items
		 */
		ArrayList<ToDo> newArchived = new ArrayList<ToDo>();
		ArrayList<ToDo> newMain = new ArrayList<ToDo>();
		
		Iterator<ToDo> iter = allToDoItems.iterator();
		while(iter.hasNext())
		{
			ToDo todo = iter.next();
			if(archivedToDoItems.contains(todo)){ newArchived.add(todo); }
			else if(mainToDoItems.contains(todo)){ newMain.add(todo); }
		}
		
		archivedToDoItems = newArchived;
		mainToDoItems = newMain;
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
		        inflater.inflate(R.menu.all_to_do_menu, menu);
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
							allToDoItems.remove(adapter.getItem(key));
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
					
				case R.id.email:
					
					String emailInterfaceMsg = "To Do Items";
					
					ArrayList<ToDo> all = new ArrayList<ToDo>();
					all.addAll(mainToDoItems);
					all.addAll(archivedToDoItems);
					
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
