package com.ajinkyad.codingtest.modules.customer.selection;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ajinkyad.codingtest.R;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.modules.common.ItemActionListener;
import com.ajinkyad.codingtest.modules.table.TablesActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomersListingFragment extends Fragment implements CustomersListingView, ItemActionListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recylerViewCustomers)
    RecyclerView recylerViewCustomers;
    @BindView(R.id.swipeRefreshCustomer)
    SwipeRefreshLayout swipeRefreshCustomer;
    @BindView(R.id.progressBarCustomers)
    ProgressBar progressBarCustomers;
    Unbinder unbinder;

    CustomersListingPresenter customersListingPresenter;


    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customers, vg, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customersListingPresenter = new CustomersListingPresenterImplementor(getActivity(), this);
        customersListingPresenter.fetchCustomersList(true);
        swipeRefreshCustomer.setOnRefreshListener(this);

    }

    @Override
    public void showProgressBar() {

        progressBarCustomers.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {

        progressBarCustomers.setVisibility(View.GONE);
        if (swipeRefreshCustomer.isRefreshing()) {
            swipeRefreshCustomer.setRefreshing(false);
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
    public void renderCustomersList(ArrayList<CustomerDetailsResponse> customersList) {

        CustomersAdapter customersAdapter = new CustomersAdapter(getActivity(), customersList, this);

        recylerViewCustomers.setAdapter(customersAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recylerViewCustomers.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClicked(Object currentObject, int position) {

        Intent intent = new Intent(getActivity(), TablesActivity.class);
        intent.putExtra("data", (CustomerDetailsResponse) currentObject);
        startActivity(intent);

    }

    @Override
    public void onRefresh() {

        if (customersListingPresenter != null) {
            customersListingPresenter.fetchCustomersList(false);
        }
    }
}
