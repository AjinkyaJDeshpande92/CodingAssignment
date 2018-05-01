package com.ajinkyad.codingtest.modules.customer.selection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajinkyad.codingtest.R;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.modules.common.ItemActionListener;
import com.ajinkyad.codingtest.utilities.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomerNameItemHolder> {

    private LayoutInflater inflater;
    private ArrayList<CustomerDetailsResponse> arrlstData;
    private ItemActionListener itemActionListener;

    CustomersAdapter(Context context, ArrayList<CustomerDetailsResponse> arrlstData, ItemActionListener itemActionListener) {
        inflater = LayoutInflater.from(context);
        this.arrlstData = arrlstData;
        this.itemActionListener = itemActionListener;
    }

    @NonNull
    @Override
    public CustomerNameItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomerNameItemHolder(inflater.inflate(R.layout.custom_customer_name_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final CustomerNameItemHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CustomerDetailsResponse customerDetail = arrlstData.get(position);

        holder.customerName.setText(Utilities.getFormattedName(customerDetail.getCustomerFirstName(), customerDetail.getCustomerLastName()));

        holder.cardViewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemActionListener != null) {
                    itemActionListener.onItemClicked(customerDetail, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrlstData.size();
    }


    static class CustomerNameItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customerName)
        TextView customerName;
        @BindView(R.id.cardViewCustomer)
        CardView cardViewCustomer;


        CustomerNameItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
