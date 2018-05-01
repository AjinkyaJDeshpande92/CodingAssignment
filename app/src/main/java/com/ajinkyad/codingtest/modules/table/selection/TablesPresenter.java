package com.ajinkyad.codingtest.modules.table.selection;


import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;

interface TablesPresenter {

   void fetchTables(boolean showProgressbar);

    void addReservation(CustomerDetailsResponse customerDetailsResponse, int tableId);
}
