package com.cc.tilingLizard.command;

interface Command {
	 
	public void execute();
	 
	public void undo();
	 
	public void redo();
	 
}