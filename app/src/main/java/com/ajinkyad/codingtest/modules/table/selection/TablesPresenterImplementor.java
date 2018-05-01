package com.ajinkyad.codingtest.modules.table.selection;

import android.content.Context;

import com.ajinkyad.codingtest.application.CodingTestApplication;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.ReservationDetails;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.ajinkyad.codingtest.utilities.Utilities;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

class TablesPresenterImplementor implements TablesPresenter {

    private TablesView tablesView;
    private TablesInteractor tablesInteractor;
    private Context context;

    TablesPresenterImplementor(Context context, TablesView tablesView) {
        this.context = context;
        this.tablesView = tablesView;
        tablesInteractor = new TablesInteractorImplementor(context);
    }

    @Subscribe
    public void onResponse(ArrayList<TableDetailsResponse> tables) {

        CodingTestApplication.getBusInstance().unregister(this);
        if (tablesView != null) {
            tablesView.hideProgressBar();
            tablesView.renderTablesList(tables);
        }
    }

    @Subscribe
    public void onResponse(ReservationDetails reservationDetails) {

        CodingTestApplication.getBusInstance().unregister(this);
        if (tablesView != null) {
            tablesView.reservationAddedConfirmation();
        }
    }

    @Override
    public void fetchTables(boolean showProgressbar) {

        if (tablesInteractor.checkForInternet()) {
            //Internet is available
            if (showProgressbar && tablesView != null) {
                tablesView.showProgressBar();
            }
            CodingTestApplication.getBusInstance().register(this);
            tablesInteractor.fetchTablesFromServer();
        } else {
            //Internet is not available
            //Check for DB for available data if any
            if (tablesInteractor.checkifTablesPresentInDB()) {
                //Return the records from DB
                CodingTestApplication.getBusInstance().register(this);
                tablesInteractor.fetchTablesFromDB();
            } else {
                //We dont have any records to display
                //Throw error to the User .
                if (tablesView != null) {
                    tablesView.hideProgressBar();
                    tablesView.showNoDataAvailableError();
                }
            }

        }

    }

    @Override
    public void addReservation(CustomerDetailsResponse customerDetailsResponse, int tableId) {

        CodingTestApplication.getBusInstance().register(this);
        tablesInteractor.addReservationInDB(customerDetailsResponse, tableId);

    }
}
