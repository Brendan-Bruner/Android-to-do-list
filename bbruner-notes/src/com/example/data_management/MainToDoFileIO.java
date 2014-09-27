package com.example.data_management;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.bbruner_notes.ToDo;

public class MainToDoFileIO implements FileIO {
	/* This class is strongly based off of the file io implementation done by guana at 
	 * https://github.com/guana/LonelyTwitter3
	 *
	 * guana - https://github.com/guana/LonelyTwitter3
	 */
	private static final String FILENAME = "todo.sav";
	private Context ctx;
	
	public MainToDoFileIO(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public ArrayList<ToDo> loadToDo()
	{
		ArrayList<ToDo> ltd = new ArrayList<ToDo>();
		
		try
		{
			FileInputStream fis = ctx.openFileInput(MainToDoFileIO.FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ltd = (ArrayList<ToDo>) ois.readObject();
		}
		catch (Exception e)
		{
			Log.i("ToDo FileIO", "Error Casting");
			e.printStackTrace();
		}
		
		return ltd;
	}
	
	public void saveToDo(List<ToDo> ltd)
	{
		try
		{
			FileOutputStream fos = ctx.openFileOutput(MainToDoFileIO.FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ltd);
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
