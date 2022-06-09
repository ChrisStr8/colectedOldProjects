package com.bytebach.impl;

import com.bytebach.model.*;
import java.util.List;

/**
 * Created on 25/05/17.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class MyTable implements Table{
    private final String name;
    private final List<Field> fields;
    private final List<List<Value>> rows;
    private final Database database;

    public MyTable(String name, List<Field> fields, Database database){
        this.name = name;
        this.fields = fields;
        this.database = database;
        this.rows = new RowList(this);
    }
    @Override
    public String name() {
        return name;
    }

    @Override
    public List<Field> fields() {
        return new FieldList(fields);
    }

    @Override
    public List<List<Value>> rows() {
        return rows;
    }

    @Override
    public List<Value> row(Value... keys) {
        int numKeyFields = 0;
        for(int i = 0; i < fields.size(); i++){//find the number of key fields in the table
            if(fields.get(i).isKey()){
                numKeyFields++;
            }
        }
        if(numKeyFields!=keys.length){
            return null;//there are more or less keys than key fields in the table
        }
        for (int i = 0; i < rows.size(); i++) {
            List<Value> row = rows.get(i);
            boolean equal = true;
            for (int index = 0; index < keys.length; index++) {
                if (fields.get(index).isKey() && !row.get(index).equals(keys[index])) {
                    equal = false;//row has a key field that is different from the key for that index
                }
            }
            if(equal) {//all of row's key fields equal the given keys
                return new MyRow(row, this);
            }
        }
        return null;//there is no row matching the given keys
    }

    @Override
    public void delete(Value... keys) {
        int numKeyFields = 0;
        for (Field field : fields) {//find the number of key fields in the table
            if (field.isKey()) {
                numKeyFields++;
            }
        }
        if(numKeyFields!=keys.length){
            throw new InvalidOperation("the number of keys is different from the number key fields in the table");
        }
        for (int i = 0; i < rows.size(); i++) {
            List<Value> row = rows.get(i);
            boolean equal = true;
            for (int index = 0; index < keys.length; index++) {
                if (fields.get(index).isKey() && !row.get(index).equals(keys[index])) {
                    equal = false;//row has a key field that is different from the key for that index
                }
            }
            if(equal){//all of row's key fields equal the given keys
                rows.remove(row);
                return;
            }
        }
        throw new InvalidOperation("there is no row matching the given keys");
    }

    /**
     * finds any invalid rows and deletes them from the table
     */
    public void deleteInvalidRows(){
        for (int i = 0; i < rows.size(); i++) {
            MyRow row = (MyRow)rows.get(i);
            if(row.containsInvalidReference()){
                rows.remove(i);
            }
        }
    }

    /**
     * checks if the table has an Invalid reference field
     * @return returns true if table contains an invalid reference field, otherwise return false
     */
    public boolean invalidTable(){
        for (Field f : fields) {
            if(f.type()==Field.Type.REFERENCE){
                return database.table(f.refTable())==null;
            }
        }
        return false;//there is no reference field
    }

    /**
     * checks that value is the correct type for the field at index i
     * @param i the index of the field
     * @param value the value to check
     * @return
     */
    public boolean checkValidForField(int i, Value value){
        if(fields.get(i).type()==Field.Type.INTEGER){
            if(!(value instanceof IntegerValue)){
                return false;
            }
        }else if(fields.get(i).type()==Field.Type.BOOLEAN){
            if(!(value instanceof BooleanValue)){
                return false;
            }
        }else if(fields.get(i).type()==Field.Type.TEXT){
            if(!(value instanceof StringValue) || value.toString().contains("\n")){//StringValues should not have \n
                return false;
            }
        }else if(fields.get(i).type()==Field.Type.TEXTAREA){
            if(!(value instanceof StringValue)){
                return false;
            }
        }else if(fields.get(i).type()==Field.Type.REFERENCE){
            if(!(value instanceof ReferenceValue)){
                return false;
            }
        }
        return true;
    }

    /**
     * @return the database that stores the table
     */
    public Database database() {
        return database;
    }
}
