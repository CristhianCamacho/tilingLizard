package com.cc.tilingLizard.command;

import java.util.Stack;

import javax.swing.JMenuItem;

public class ListaDeAcciones {

	private static ListaDeAcciones instance;
	
	private Stack undoStack;
	private Stack redoStack;
	
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	
	private ListaDeAcciones()
	{
		undoStack = new Stack();
		redoStack = new Stack();
		
		redoStack.clear();
	}
	
	public static ListaDeAcciones getInstance()
	{
		if(instance==null)
		{
			instance = new ListaDeAcciones();
		}
		
		return instance;
	}
	
	public void undoStackPush(Command command) {
		undoStack.push(command);
		redoStack.clear();
	}
		 
	public void undo()
	{
		 
		if (!undoStack.isEmpty())
		{
			Command command = (Command)undoStack.pop();
			command.undo();
			redoStack.push(command);
			redoMenu.setEnabled(true);
		}
		 
		if (undoStack.isEmpty())
		{
			undoMenu.setEnabled(false);
		}
		 
	}
		 
	public void redo()
	{
		 
		if (!redoStack.isEmpty())
		{
			Command command = (Command)redoStack.pop();
			command.redo();
			undoStack.push(command);
			undoMenu.setEnabled(true);
		}
		 
		if (redoStack.isEmpty()) 
		{
			redoMenu.setEnabled(false);
		}
	}

	public void setUndoMenu(JMenuItem undoMenu) {
		this.undoMenu = undoMenu;
	}

	public void setRedoMenu(JMenuItem redoMenu) {
		this.redoMenu = redoMenu;
	}
}
