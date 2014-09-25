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

public class ArchiveFileIO implements FileIO {
	private static final String FILENAME = "archive.sav";
	private Context ctx;
	
	public ArchiveFileIO(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public ArrayList<ToDo> loadToDo()
	{
		ArrayList<ToDo> ltd = new ArrayList<ToDo>();
		
		try
		{
			FileInputStream fis = ctx.openFileInput(ArchiveFileIO.FILENAME);
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
			FileOutputStream fos = ctx.openFileOutput(ArchiveFileIO.FILENAME, Context.MODE_PRIVATE);
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
