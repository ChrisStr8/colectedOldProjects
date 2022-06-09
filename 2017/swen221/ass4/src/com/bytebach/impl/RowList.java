package com.bytebach.impl;

import com.bytebach.model.*;

import java.util.*;

/**
 * Created on 25/05/17.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class RowList extends AbstractList<List<Value>>{
    private final List<List<Value>> rows;
    private final MyTable table;

    public RowList(MyTable table){
        this.rows = new ArrayList<>();
        this.table = table;
    }

    @Override
    public List<Value> set(int i, List<Value> row){
        return rows.set(i, row);
    }

    @Override
    public boolean add(List<Value> row){
        List<Field> fields = table.fields();
        if(row.size()!=fields.size()){
            throw new InvalidOperation("row is a different size to this tables rows");
        }
        for (List<Value> r : rows){//check every row in rows to ensure that the row's key values don't match the key values in row
            boolean equal = true;
            for (int index=0; index<r.size(); index++) {
                if (fields.get(index).isKey() && !r.get(index).equals(row.get(index))) {
                    equal = false;//key value in row is different from key value in r
                }
                if(!table.checkValidForField(index, row.get(index))){
                    throw new InvalidOperation("row contains value of wrong type");
                }
            }
            if(equal){
                throw new InvalidOperation("set of key values already exists");
            }
        }
        if(containsInvalidReference(row)){
            throw new InvalidOperation("row contains invalid reference");
        }
        return rows.add(row);
    }

    @Override
    public boolean remove(Object o){
        boolean removed = rows.remove(o);
        MyDatabase database = (MyDatabase)table.database();
        database.removeInvalidRows();//remove any rows made invalid by removing o
        return removed;
    }

    @Override
    public List<Value> remove(int i) {
        List<Value> oldRow = rows.remove(i);
        MyDatabase database = (MyDatabase)table.database();
        database.removeInvalidRows();//remove any rows made invalid by removing the row at i
        return oldRow;
    }

    @Override
    public List<Value> get(int i){
        return new MyRow(rows.get(i), table);
    }

    @Override
    public int size(){
        return rows.size();
    }

    /**
     * @param row the list to be checked
     * @return true if there is an invalid reference in row, otherwise return false.
     */
    private boolean containsInvalidReference(List<Value> row){
        for (int i=0; i<row.size(); i++){//iterate through the values in row
            if(table.fields().get(i).type() == Field.Type.REFERENCE){
                ReferenceValue reference = (ReferenceValue)row.get(i);
                Table table = this.table.database().table(reference.table());
                if(table==null){//table does not exist
                    return true;
                }
                Value[] keys = reference.keys();
                return table.row(keys)==null;
            }
        }
        return false;
    }

    /**
     * @return the table storing the list of rows
     */
    public MyTable getTable() {
        return table;
    }
}
