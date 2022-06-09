package com.bytebach.impl;

import java.util.*;

import com.bytebach.model.*;

public class MyDatabase implements Database {
    // This is where you'll probably want to start. You'll need to provide an
	// implementation of Table as well.
	//
	// One of the key challenges in this assignment is to provide you're
	// own implementations of the List interface which can intercept the various
	// operations (e.g. add, set, remove, etc) and check whether they violate
	// the constraints and/or update the database appropriately (e.g. for the
	// cascading delete).
	//
	// HINT: to get started, don't bother providing your own implementations of
	// List as discussed above! Instead, implement MyDatabase and MyTable using
	// conventional Collections. When you have that working, and the web system
	// is doing something sensible, then consider how you're going to get those
	// unit test to past. 

    private final Map<String, Table> tables = new HashMap<>();

    @Override
    public Collection<? extends Table> tables() {
        return tables.values();
    }

    @Override
    public Table table(String name) {
        return tables.get(name);
    }

    @Override
    public void createTable(String name, List<Field> fields) {
        if(tables.containsKey(name)){
            throw new InvalidOperation("Table already exists");
        }
        tables.put(name, new MyTable(name, fields, this));
    }

    @Override
    public void deleteTable(String name) {
        if(!tables.containsKey(name)){
            throw new InvalidOperation("Table dose not exists");
        }
        removeInvalidTables();
        removeInvalidRows();
        tables.remove(name);
    }

    /**
     * iterates through all the tables removing all invalid rows
     */
    public void removeInvalidRows(){
        Set<String> keySet = tables.keySet();
        for (String s : keySet) {
            MyTable table = (MyTable)tables.get(s);
            table.deleteInvalidRows();
        }
    }

    /**
     * checks all tables and removes any with an invalid reference field
     */
    public void removeInvalidTables(){
        Set<String> keySet = tables.keySet();
        for (String s : keySet) {
            MyTable table = (MyTable)tables.get(s);
            if(table.invalidTable()){
                deleteTable(table.name());
            }
        }
    }
}
