package com.ajinkyad.codingtest.modules.customer.selection;

import android.support.test.runner.AndroidJUnit4;

import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class CustomersListingPresenterImplementorTest {

    CustomersListingInteractor customersListingInteractor;

    CustomersListingView customersListingView;

    CustomersListingPresenterImplementor customersListingPresenterImplementor;


    @Before
    public void setUp() throws Exception {

        customersListingInteractor = mock(CustomersListingInteractor.class);
        customersListingView = mock(CustomersListingView.class);
        customersListingPresenterImplementor = new CustomersListingPresenterImplementor(getTargetContext(), customersListingView);
    }

    @After
    public void tearDown() throws Exception {

        customersListingInteractor = null;
        customersListingView = null;
        customersListingPresenterImplementor = null;
    }

    @Test
    public void fetchCustomersListTest_InternetAvailableShowProgressBar() {

        when(customersListingInteractor.checkForInternet()).thenReturn(true);

        customersListingPresenterImplementor.fetchCustomersList(true);

        verify(customersListingView).showProgressBar();

    }

    @Test
    public void fetchCustomersListTest_InternetAvailableNoProgressBar() {

        when(customersListingInteractor.checkForInternet()).thenReturn(true);

        customersListingPresenterImplementor.fetchCustomersList(false);

        verify(customersListingView, Mockito.never()).showProgressBar();

    }

    @Test
    public void fetchCustomersListTest_InternetNotAvailable_ButRecordsPresentInDB() {

        when(customersListingInteractor.checkForInternet()).thenReturn(false);

        when(customersListingInteractor.checkifCustomersPresentInDB()).thenReturn(true);

        customersListingPresenterImplementor.fetchCustomersList(true);

        verify(customersListingInteractor).fetchCustomerListFromDB();

    }

    @Test
    public void fetchCustomersListTest_InternetNotAvailable_NoRecordsPresentInDB() {

        when(customersListingInteractor.checkForInternet()).thenReturn(false);

        when(customersListingInteractor.checkifCustomersPresentInDB()).thenReturn(false);

        customersListingPresenterImplementor.fetchCustomersList(true);

        verify(customersListingView).hideProgressBar();


    }

    @Test
    public void onResponseTest_DataIsFetched() {

        ArrayList<CustomerDetailsResponse> customers = new ArrayList<>();

        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setId(0);
        customerDetailsResponse.setCustomerFirstName("Justin");
        customerDetailsResponse.setCustomerLastName("Timberlake");

        customers.add(customerDetailsResponse);

        customersListingPresenterImplementor.onResponse(customers);


        Assert.assertEquals(1, customers.size());
        verify(customersListingView).hideProgressBar();
        verify(customersListingView).renderCustomersList(customers);

    }

    @Test
    public void onResponseTest_ListisEmpty() {

        ArrayList<CustomerDetailsResponse> customers = new ArrayList<>();

        customersListingPresenterImplementor.onResponse(customers);

        Assert.assertEquals(0, customers.size());
        verify(customersListingView).hideProgressBar();
        verify(customersListingView).renderCustomersList(customers);

    }
}
