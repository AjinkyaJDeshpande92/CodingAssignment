package com.ajinkyad.codingtest.modules.customer.selection;

import android.content.Context;

import com.ajinkyad.codingtest.application.CodingTestApplication;
import com.ajinkyad.codingtest.data.DatabaseHelper;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.utilities.Utilities;
import com.ajinkyad.codingtest.webservicemanager.WebCall;
import com.ajinkyad.codingtest.webservicemanager.WebserviceManager;
import com.ajinkyad.codingtest.webservicemanager.WebserviceResponseListener;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


public class CustomersListingInteractorImplementor implements CustomersListingInteractor, WebserviceResponseListener {

    private WebserviceManager webserviceManager;
    private Context context;
    private Utilities utilities;

    CustomersListingInteractorImplementor(Context context) {
        this.context = context;
        utilities = new Utilities();
        webserviceManager = new WebserviceManager();
        webserviceManager.setWebResponseListener(this);
    }

    @Override
    public void onSuccess(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case CUSTOMERS_LIST:
                ArrayList<CustomerDetailsResponse> customers = new ArrayList<>();
                CustomerDetailsResponse[] customerDetailsResponses = new Gson().fromJson(response.toString(), CustomerDetailsResponse[].class);
                Collections.addAll(customers, customerDetailsResponses);
                deleteCustomersFromDB();
                cacheCustomers(customers);
                CodingTestApplication.getBusInstance().post(customers);
                break;

        }
    }

    @Override
    public void onFailure(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case CUSTOMERS_LIST:
                CodingTestApplication.getBusInstance().post(response);
                break;
        }
    }

    @Override
    public void fetchCustomerListFromServer() {

        webserviceManager.fetchCustomersList();
    }

    @Override
    public boolean checkifCustomersPresentInDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<CustomerDetailsResponse, Integer> customerDao = null;

        try {
            customerDao = helper.getCustomerDao();
            return customerDao.queryForAll().size() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return false;
    }

    @Override
    public void fetchCustomerListFromDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<CustomerDetailsResponse, Integer> customerDao = null;

        try {
            ArrayList<CustomerDetailsResponse> customers = new ArrayList<>();
            customerDao = helper.getCustomerDao();
            customers.addAll(customerDao.queryForAll());
            CodingTestApplication.getBusInstance().post(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

    }

    @Override
    public boolean checkForInternet() {
        return utilities.isNetworkAvailable(context);
    }

    private void deleteCustomersFromDB() {

        DatabaseHelper helper = new DatabaseHelper(context);
        helper.deleteCustomers();
        helper.close();
    }

    private void cacheCustomers(ArrayList<CustomerDetailsResponse> customersList) {

        DatabaseHelper helper = new DatabaseHelper(context);
        Dao<CustomerDetailsResponse, Integer> customerDao = null;
        try {
            for (CustomerDetailsResponse customerDetailsResponse : customersList) {
                try {
                    customerDao = helper.getCustomerDao();
                    int i = customerDao.create(customerDetailsResponse);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            helper.close();
        }

    }
}
