package com.ajinkyad.codingtest.modules.table.selection;

import android.app.Fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajinkyad.codingtest.R;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.ajinkyad.codingtest.modules.common.DialogListener;
import com.ajinkyad.codingtest.modules.common.ItemActionListener;
import com.ajinkyad.codingtest.modules.table.TablesActivity;
import com.ajinkyad.codingtest.utilities.AlarmReceiver;
import com.ajinkyad.codingtest.utilities.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TablesFragment extends Fragment implements TablesView, ItemActionListener, SwipeRefreshLayout.OnRefreshListener, DialogListener {

    @BindView(R.id.recylerViewTables)
    RecyclerView recylerViewTables;
    @BindView(R.id.swipeRefreshTables)
    SwipeRefreshLayout swipeRefreshTables;
    @BindView(R.id.progressBarTables)
    ProgressBar progressBarTables;

    TablesPresenter tablesPresenter;
    Unbinder unbinder;

    CustomerDetailsResponse customerDetailsResponse;

    private final int RESERVATION_CONFIRMATION = 1, RESERVATION_CONFIRMED = 2;
    private int tableId = 0;

    TablesAdapter tablesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tables, vg, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customerDetailsResponse = ((TablesActivity) getActivity()).customerDetailsResponse;

        tablesPresenter = new TablesPresenterImplementor(getActivity(), this);
        tablesPresenter.fetchTables(true);
        swipeRefreshTables.setOnRefreshListener(this);

        Utilities.setRepeatingAlarm(getActivity());
    }

    @Override
    public void showProgressBar() {

        progressBarTables.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {

        progressBarTables.setVisibility(View.GONE);
        if (swipeRefreshTables.isRefreshing()) {
            swipeRefreshTables.setRefreshing(false);
        }
    }

    @Override
    public void showNoDataAvailableError() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void renderTablesList(ArrayList<TableDetailsResponse> tables) {

        tablesAdapter= new TablesAdapter(getActivity(), tables, this);

        recylerViewTables.setAdapter(tablesAdapter);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);

        recylerViewTables.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void reservationAddedConfirmation() {
        Utilities.showAlertDialogSingleOption(getActivity(), RESERVATION_CONFIRMED, this, getString(R.string.reservation_confirmed), getString(R.string.reservation_confirmation_message, String.format("Table %d", tableId), String.format("%s %s", customerDetailsResponse.getCustomerLastName(), customerDetailsResponse.getCustomerFirstName())), getString(R.string.ok));

    }

    @Override
    public void onItemClicked(Object currentObject, int position) {
        tableId = position;
        Utilities.showAlertDialogMultipleOptions(getActivity(), RESERVATION_CONFIRMATION, this, getString(R.string.reservation_confirmation), getString(R.string.reservation_confirmation_details_message, String.format("Table %d", position), String.format("%s %s", customerDetailsResponse.getCustomerLastName(), customerDetailsResponse.getCustomerFirstName())), getString(R.string.yes), getString(R.string.no));
    }

    @Override
    public void onRefresh() {
        if (tablesPresenter != null) {
            tablesPresenter.fetchTables(false);
        }
    }

    @Override
    public void onPositiveAction(int dialogID, Object updatedData) {

        switch (dialogID) {
            case RESERVATION_CONFIRMATION:
                tablesPresenter.addReservation(customerDetailsResponse, tableId);
                break;
            case RESERVATION_CONFIRMED:
                if(tablesAdapter!=null)
                {
                    tablesAdapter.updateReservationCount(tableId);
                }
                break;
        }
    }

    @Override
    public void onNegativeAction(int dialogID, Object updatedData) {

    }
}
