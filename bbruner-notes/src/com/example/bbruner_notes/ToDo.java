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

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private boolean finished;
	private boolean selected;
	
	public ToDo(String name, String description, boolean finished)
	{
		this.name = name;
		this.description = description;
		this.finished = finished;
		this.selected = false;
	}
	
	public void setSelected(boolean selected){ this.selected = selected; }
	
	public void toggleSelected(){ this.selected = !this.selected; }
	
	public boolean isSelected(){ return this.selected; }
	
	public void setFinished(boolean finished){ this.finished = finished; }
	
	public void toggleFinished(){ this.finished = !this.finished; }
	
	public boolean isFinished(){ return this.finished; }
	
	public void setDescription(String description){ this.description = description; }
	
	public String getDescription(){ return this.description; }
	
	public void setName(String name){ this.name = name; }
	
	public String getName(){ return this.name; }

	public String toString(){ return this.getName(); }

}
