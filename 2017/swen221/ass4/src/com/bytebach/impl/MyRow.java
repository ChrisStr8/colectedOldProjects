package com.bytebach.impl;

import com.bytebach.model.*;
import java.util.AbstractList;
import java.util.List;

/**
 * Created on 27/05/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class MyRow extends AbstractList<Value>{
    private final List<Value> row;
    private final MyTable table;

    public MyRow(List<Value> row, MyTable table){
        this.row = row;
        this.table = table;
    }

    @Override
    public Value get(int index) {
        return row.get(index);
    }

    @Override
    public int size() {
        return row.size();
    }

    @Override
    public Value set(int i, Value value){
        List<Field> fields = table.fields();
        if(fields.get(i).isKey()){
            throw new InvalidOperation("can not change key fields");
        }
        if(!table.checkValidForField(i, value)){
            throw new InvalidOperation("value not of field type");
        }
        if(value instanceof ReferenceValue){
            if(invalidReference((ReferenceValue)value)){
                throw new InvalidOperation("invalid reference");
            }
        }
        return row.set(i, value);
    }

    @Override
    public boolean add(Value value){
        throw new InvalidOperation("can not add values");
    }

    @Override
    public Value remove(int i) {
        throw new InvalidOperation("can not remove values");
    }

    @Override
    public boolean remove(Object o){
        throw new InvalidOperation("can not remove values");
    }

    /**
     * checks if any of the fields is a reference. If a field is a reference its check if the reference is valid
     * @return  returns true if there is an invalid reference in the row, otherwise returns false
     */
    public boolean containsInvalidReference(){
        for (Value v : row) {
            if(v instanceof ReferenceValue){
                ReferenceValue r = (ReferenceValue)v;
                if(invalidReference(r)){
                    return true;
                }
            }
        }
        return false;//no invalid reference found
    }

    /**
     * @param reference the reference to be checked
     * @return true if reference is valid, otherwise return false.
     */
    private boolean invalidReference(ReferenceValue reference){
        Table table = this.table.database().table(reference.table());
        if(table==null){//referenced table does not exist
            return true;
        }
        Value[] keys = reference.keys();
        List<Value> row = table.row(keys);
        if(row==null){//referenced row does not exist
            return true;
        }
        return false;//reference is valid
    }
}
