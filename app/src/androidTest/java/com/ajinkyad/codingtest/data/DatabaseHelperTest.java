package com.ajinkyad.codingtest.data;

import android.support.test.runner.AndroidJUnit4;

import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.ReservationDetails;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.j256.ormlite.dao.Dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {

    private DatabaseHelper database;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
        database = new DatabaseHelper(getTargetContext());
        database.deleteCustomers();
        database.deleteReservations();
        database.deleteTables();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void addCustomerTest_SingleCustomer() throws Exception {

        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setCustomerLastName("Spears");
        customerDetailsResponse.setCustomerFirstName("Britney");
        customerDetailsResponse.setId(0);

        Dao<CustomerDetailsResponse, Integer> customerDao = database.getCustomerDao();
        customerDao.create(customerDetailsResponse);

        List<CustomerDetailsResponse> customersList = database.getCustomerDao().queryForAll();
        assertThat(customersList.size(), is(1));
        assertTrue(customersList.get(0).getCustomerFirstName().equals("Britney"));
    }

    @Test
    public void addCustomerTest_MultipleCustomer() throws Exception {

        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setCustomerLastName("Spears");
        customerDetailsResponse.setCustomerFirstName("Britney");
        customerDetailsResponse.setId(0);

        Dao<CustomerDetailsResponse, Integer> customerDao = database.getCustomerDao();
        customerDao.create(customerDetailsResponse);

        customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setCustomerLastName("Abraham");
        customerDetailsResponse.setCustomerFirstName("John");
        customerDetailsResponse.setId(1);

        customerDao = database.getCustomerDao();
        customerDao.create(customerDetailsResponse);

        List<CustomerDetailsResponse> customersList = database.getCustomerDao().queryForAll();
        assertThat(customersList.size(), is(2));
        assertTrue(customersList.get(0).getCustomerFirstName().equals("Britney"));
        assertTrue(customersList.get(1).getCustomerFirstName().equals("John"));
    }

    @Test
    public void deleteCustomersTest_ClearTable() throws Exception {

        database.deleteCustomers();

        List<CustomerDetailsResponse> customersList = database.getCustomerDao().queryForAll();
        assertThat(customersList.size(), is(0));
    }


    @Test
    public void addTableTest_SingleRecord() throws Exception {

        TableDetailsResponse tableDetailsResponse = new TableDetailsResponse();
        tableDetailsResponse.setAvailability(true);

        Dao<TableDetailsResponse, Integer> tableDao = database.getTablesDao();
        tableDao.create(tableDetailsResponse);

        List<TableDetailsResponse> tablesList = database.getTablesDao().queryForAll();
        assertThat(tablesList.size(), is(1));
        assertTrue(tablesList.get(0).isAvailability());
    }

    @Test
    public void addTableTest_MultipleRecords() throws Exception {

        TableDetailsResponse tableDetailsResponse = new TableDetailsResponse();
        tableDetailsResponse.setAvailability(true);

        Dao<TableDetailsResponse, Integer> tableDao = database.getTablesDao();
        tableDao.create(tableDetailsResponse);

        tableDetailsResponse = new TableDetailsResponse();
        tableDetailsResponse.setAvailability(false);

        tableDao = database.getTablesDao();
        tableDao.create(tableDetailsResponse);

        List<TableDetailsResponse> tablesList = database.getTablesDao().queryForAll();
        assertThat(tablesList.size(), is(2));
        assertTrue(tablesList.get(0).isAvailability());
        assertFalse(tablesList.get(1).isAvailability());
    }

    @Test
    public void deleteTableTest_ClearTable() throws Exception {

        database.deleteTables();

        List<TableDetailsResponse> tablesList = database.getTablesDao().queryForAll();
        assertThat(tablesList.size(), is(0));
    }

    @Test
    public void addReservationTest_SingleRecord() throws Exception {

        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setTableId(0);
        reservationDetails.setReservationUnder("Dwayne Johnson");

        Dao<ReservationDetails, Integer> reservationDao = database.getReservationDao();
        reservationDao.create(reservationDetails);

        List<ReservationDetails> reservationsList = database.getReservationDao().queryForAll();
        assertThat(reservationsList.size(), is(1));
        assertTrue(reservationsList.get(0).getTableId() == 0);
    }

    @Test
    public void addReservationTest_MultipleRecords() throws Exception {

        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setTableId(0);
        reservationDetails.setReservationUnder("Dwayne Johnson");

        Dao<ReservationDetails, Integer> reservationDao = database.getReservationDao();
        reservationDao.create(reservationDetails);

        reservationDetails = new ReservationDetails();
        reservationDetails.setTableId(1);
        reservationDetails.setReservationUnder("Peter Parker");

        reservationDao = database.getReservationDao();
        reservationDao.create(reservationDetails);

        List<ReservationDetails> reservationsList = database.getReservationDao().queryForAll();
        assertThat(reservationsList.size(), is(2));
        assertTrue(reservationsList.get(0).getTableId() == 0);
        assertTrue(reservationsList.get(1).getTableId() == 1);
    }

    @Test
    public void deleteReservationsTest_ClearTable() throws Exception {

        database.deleteReservations();

        List<ReservationDetails> reservationsList = database.getReservationDao().queryForAll();
        assertThat(reservationsList.size(), is(0));
    }
}

