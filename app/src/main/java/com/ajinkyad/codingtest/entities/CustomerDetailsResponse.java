package com.ajinkyad.codingtest.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "customers")
public class CustomerDetailsResponse implements Serializable {

    @DatabaseField(columnName = "customerFirstName")
    private String customerFirstName;
    @DatabaseField(columnName = "customerLastName")
    private String customerLastName;
    @DatabaseField(columnName = "id")
    private int id;

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
