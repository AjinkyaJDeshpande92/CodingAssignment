package com.ajinkyad.codingtest.modules.customer.selection;

import android.content.Context;

import com.ajinkyad.codingtest.application.CodingTestApplication;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

class CustomersListingPresenterImplementor implements CustomersListingPresenter {

    private CustomersListingView customersListingView;
    private CustomersListingInteractor customersListingInteractor;
    private Context context;


    CustomersListingPresenterImplementor(Context context, CustomersListingView customersListingView) {

        this.context = context;
        this.customersListingView = customersListingView;
        customersListingInteractor = new CustomersListingInteractorImplementor(context);
    }

    @Subscribe
    public void onResponse(ArrayList<CustomerDetailsResponse> customersList) {

        CodingTestApplication.getBusInstance().unregister(this);
        if (customersListingView != null) {
            customersListingView.hideProgressBar();

            customersListingView.renderCustomersList(customersList);
        }
    }


    @Override
    public void fetchCustomersList(boolean showProgressbar) {

        if (customersListingInteractor.checkForInternet()) {
            //Internet is available
            if (showProgressbar && customersListingView != null) {
                customersListingView.showProgressBar();
            }
            CodingTestApplication.getBusInstance().register(this);
            customersListingInteractor.fetchCustomerListFromServer();
        } else {
            //Internet is not available
            //Check for DB for available data if any
            if (customersListingInteractor.checkifCustomersPresentInDB()) {
                //Return the records from DB
                CodingTestApplication.getBusInstance().register(this);
                customersListingInteractor.fetchCustomerListFromDB();
            } else {
                //We dont have any records to display
                //Throw error to the User .
                if (customersListingView != null) {
                    customersListingView.hideProgressBar();
                    customersListingView.showNoDataAvailableError();
                }
            }


        }


    }


}
