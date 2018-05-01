package com.ajinkyad.codingtest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.ReservationDetails;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "codingTest.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<CustomerDetailsResponse, Integer> customerDao = null;
    private Dao<TableDetailsResponse, Integer> tableDao = null;
    private Dao<ReservationDetails, Integer> reservationDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CustomerDetailsResponse.class);
            TableUtils.createTable(connectionSource, TableDetailsResponse.class);
            TableUtils.createTable(connectionSource, ReservationDetails.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CustomerDetailsResponse.class, true);
            TableUtils.dropTable(connectionSource, TableDetailsResponse.class, true);
            TableUtils.dropTable(connectionSource, ReservationDetails.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<CustomerDetailsResponse, Integer> getCustomerDao() throws SQLException {
        if (customerDao == null) {
            customerDao = getDao(CustomerDetailsResponse.class);
        }

        return customerDao;
    }

    public void deleteCustomers() {
        ConnectionSource connectionSource = getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, CustomerDetailsResponse.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteTables() {
        ConnectionSource connectionSource = getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, TableDetailsResponse.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<TableDetailsResponse, Integer> getTablesDao() throws SQLException {
        if (tableDao == null) {
            tableDao = getDao(TableDetailsResponse.class);
        }

        return tableDao;
    }


    public void deleteReservations() {
        ConnectionSource connectionSource = getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, ReservationDetails.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<ReservationDetails, Integer> getReservationDao() throws SQLException {
        if (reservationDao == null) {
            reservationDao = getDao(ReservationDetails.class);
        }

        return reservationDao;
    }

    @Override
    public void close() {
        customerDao = null;
        tableDao = null;
        reservationDao = null;

        super.close();
    }
}