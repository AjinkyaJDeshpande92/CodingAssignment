package com.ajinkyad.codingtest.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "reservations")
public class ReservationDetails implements Serializable {
    @DatabaseField(columnName = "reservationUnder")
    private String reservationUnder;

    @DatabaseField(columnName = "tableId")
    private int tableId;

    public String getReservationUnder() {
        return reservationUnder;
    }

    public void setReservationUnder(String reservationUnder) {
        this.reservationUnder = reservationUnder;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
