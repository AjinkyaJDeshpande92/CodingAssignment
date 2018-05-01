package com.ajinkyad.codingtest.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tables")
public class TableDetailsResponse implements Serializable {

    @DatabaseField(columnName = "availability")
    private boolean availability;

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
