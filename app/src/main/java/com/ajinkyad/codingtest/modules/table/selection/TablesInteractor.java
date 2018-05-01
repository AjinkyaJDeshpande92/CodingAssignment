package com.ajinkyad.codingtest.modules.table.selection;


import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;

public interface TablesInteractor {

    void fetchTablesFromServer();

    boolean checkifTablesPresentInDB();

    void fetchTablesFromDB();

    void addReservationInDB(CustomerDetailsResponse customerDetailsResponse, int tableId);

    boolean checkForInternet();
}
