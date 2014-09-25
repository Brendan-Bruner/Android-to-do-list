package com.example.data_management;

import java.util.ArrayList;
import java.util.List;

import com.example.bbruner_notes.ToDo;

public interface FileIO {

	public ArrayList<ToDo> loadToDo();
	public void saveToDo(List<ToDo> ltd);
}
