package com.bytebach.impl;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;

import java.util.AbstractList;
import java.util.List;

/**
 * Created on 28/05/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class FieldList extends AbstractList<Field> {
    private final List<Field> fields;

    public FieldList(List<Field> fields){
        this.fields = fields;
    }

    @Override
    public Field get(int index) {
        return fields.get(index);
    }

    @Override
    public Field set(int i, Field field){
        throw new InvalidOperation("can't change a field");
    }

    @Override
    public boolean add(Field field){
        throw new InvalidOperation("can't change a field");
    }

    @Override
    public Field remove(int i) {
        throw new InvalidOperation("can't change a field");
    }

    @Override
    public boolean remove(Object o){
        throw new InvalidOperation("can't change a field");
    }

    @Override
    public int size() {
        return fields.size();
    }
}
