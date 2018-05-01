package com.ajinkyad.codingtest.modules.table.selection;

import android.support.test.runner.AndroidJUnit4;

import com.ajinkyad.codingtest.entities.TableDetailsResponse;

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
public class TablesPresenterImplementorTest {

    TablesInteractor tablesInteractor;

    TablesView tablesView;

    TablesPresenterImplementor tablesPresenterImplementor;


    @Before
    public void setUp() throws Exception {

        tablesInteractor = mock(TablesInteractor.class);
        tablesView = mock(TablesView.class);
        tablesPresenterImplementor = new TablesPresenterImplementor(getTargetContext(), tablesView);
    }

    @After
    public void tearDown() throws Exception {

        tablesInteractor = null;
        tablesView = null;
        tablesPresenterImplementor = null;
    }

    @Test
    public void fetchTablesTest_InternetAvailableShowProgressBar() {

        when(tablesInteractor.checkForInternet()).thenReturn(true);

        tablesPresenterImplementor.fetchTables(true);

        verify(tablesView).showProgressBar();

    }

    @Test
    public void fetchTablesTest_InternetAvailableNoProgressBar() {

        when(tablesInteractor.checkForInternet()).thenReturn(true);

        tablesPresenterImplementor.fetchTables(false);

        verify(tablesView, Mockito.never()).showProgressBar();

    }

    @Test
    public void fetchTablesTest_InternetNotAvailable_ButRecordsPresentInDB() {

        when(tablesInteractor.checkForInternet()).thenReturn(false);

        when(tablesInteractor.checkifTablesPresentInDB()).thenReturn(true);

        tablesPresenterImplementor.fetchTables(true);

        verify(tablesInteractor).fetchTablesFromDB();

    }

    @Test
    public void fetchTablesTest_InternetNotAvailable_NoRecordsPresentInDB() {

        when(tablesInteractor.checkForInternet()).thenReturn(false);

        when(tablesInteractor.checkifTablesPresentInDB()).thenReturn(false);

        tablesPresenterImplementor.fetchTables(true);

        verify(tablesView).hideProgressBar();


    }

    @Test
    public void onResponseTest_DataIsFetched() {

        ArrayList<TableDetailsResponse> tables = new ArrayList<>();

        TableDetailsResponse tableDetailsResponse = new TableDetailsResponse();
        tableDetailsResponse.setAvailability(true);


        tables.add(tableDetailsResponse);

        tablesPresenterImplementor.onResponse(tables);


        Assert.assertEquals(1, tables.size());
        verify(tablesView).hideProgressBar();
        verify(tablesView).renderTablesList(tables);

    }

    @Test
    public void onResponseTest_ListisEmpty() {

        ArrayList<TableDetailsResponse> tables = new ArrayList<>();

        tablesPresenterImplementor.onResponse(tables);

        Assert.assertEquals(0, tables.size());
        verify(tablesView).hideProgressBar();
        verify(tablesView).renderTablesList(tables);

    }
}
