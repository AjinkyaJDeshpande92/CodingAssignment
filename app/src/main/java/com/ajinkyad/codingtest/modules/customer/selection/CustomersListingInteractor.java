package com.ajinkyad.codingtest.modules.customer.selection;


public interface CustomersListingInteractor {

    void fetchCustomerListFromServer();

    boolean checkifCustomersPresentInDB();

    void fetchCustomerListFromDB();

    boolean checkForInternet();
}
