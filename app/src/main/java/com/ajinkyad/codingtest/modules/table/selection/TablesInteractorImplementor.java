package com.ajinkyad.codingtest.modules.table.selection;

import android.content.Context;

import com.ajinkyad.codingtest.application.CodingTestApplication;
import com.ajinkyad.codingtest.data.DatabaseHelper;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.ReservationDetails;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.ajinkyad.codingtest.utilities.Utilities;
import com.ajinkyad.codingtest.webservicemanager.WebCall;
import com.ajinkyad.codingtest.webservicemanager.WebserviceManager;
import com.ajinkyad.codingtest.webservicemanager.WebserviceResponseListener;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


public class TablesInteractorImplementor implements TablesInteractor, WebserviceResponseListener {

    private WebserviceManager webserviceManager;
    private Context context;
    private Utilities utilities;

    TablesInteractorImplementor(Context context) {
        this.context = context;
        webserviceManager = new WebserviceManager();
        webserviceManager.setWebResponseListener(this);
        utilities = new Utilities();
    }

    @Override
    public void onSuccess(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case TABLES:
                ArrayList<TableDetailsResponse> tables = getTableAvailability(new Gson().fromJson(response.toString(), Boolean[].class));
                deleteTablesFromDB();
                cacheTables(tables);
                CodingTestApplication.getBusInstance().post(tables);
                break;

        }
    }

    private ArrayList<TableDetailsResponse> getTableAvailability(Boolean[] tablesAvailability) {

        ArrayList<TableDetailsResponse> tables = new ArrayList<>();
        for (Boolean availability :
                tablesAvailability) {

            TableDetailsResponse table = new TableDetailsResponse();
            table.setAvailability(availability);
            tables.add(table);
        }

        return tables;

    }

    @Override
    public void onFailure(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case TABLES:
                CodingTestApplication.getBusInstance().post(response);
                break;
        }
    }

    @Override
    public void fetchTablesFromServer() {

        webserviceManager.fetchTablesList();
    }


    private void deleteTablesFromDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        helper.deleteTables();
        helper.close();
    }

    private void cacheTables(ArrayList<TableDetailsResponse> tables) {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<TableDetailsResponse, Integer> tableDao = null;


        try {
            for (TableDetailsResponse table : tables) {
                try {
                    tableDao = helper.getTablesDao();
                    int i = tableDao.create(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            helper.close();
        }


    }

    @Override
    public boolean checkifTablesPresentInDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<TableDetailsResponse, Integer> tableDao = null;

        try {
            tableDao = helper.getTablesDao();
            return tableDao.queryForAll().size() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return false;
    }

    @Override
    public void fetchTablesFromDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<TableDetailsResponse, Integer> tableDao = null;

        try {
            ArrayList<TableDetailsResponse> tables = new ArrayList<>();
            tableDao = helper.getTablesDao();
            tables.addAll(tableDao.queryForAll());
            CodingTestApplication.getBusInstance().post(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

    }

    @Override
    public void addReservationInDB(CustomerDetailsResponse customerDetailsResponse, int tableId) {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<ReservationDetails, Integer> reservationDao = null;

        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setReservationUnder(String.format("%s %s", customerDetailsResponse.getCustomerLastName(), customerDetailsResponse.getCustomerFirstName()));
        reservationDetails.setTableId(tableId);
        try {
            reservationDao = helper.getReservationDao();
            int i = reservationDao.create(reservationDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CodingTestApplication.getBusInstance().post(reservationDetails);
            helper.close();
        }


    }

    @Override
    public boolean checkForInternet() {
        return utilities.isNetworkAvailable(context);
    }
}
