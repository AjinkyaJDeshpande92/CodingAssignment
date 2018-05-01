package com.ajinkyad.codingtest.modules.table.selection;

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
import com.ajinkyad.codingtest.data.DatabaseHelper;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.entities.ReservationDetails;
import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.ajinkyad.codingtest.modules.common.ItemActionListener;
import com.ajinkyad.codingtest.utilities.Utilities;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.CustomerNameItemHolder> {

    private LayoutInflater inflater;
    private ArrayList<TableDetailsResponse> arrlstData;
    private ItemActionListener itemActionListener;
    private Dao<ReservationDetails, Integer> reservationDao = null;
    private DatabaseHelper helper;

    TablesAdapter(Context context, ArrayList<TableDetailsResponse> arrlstData, ItemActionListener itemActionListener) {
        inflater = LayoutInflater.from(context);
        this.arrlstData = arrlstData;
        this.itemActionListener = itemActionListener;
        helper = new DatabaseHelper(context);
        try {
            reservationDao = helper.getReservationDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public CustomerNameItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerNameItemHolder(inflater.inflate(R.layout.custom_table_item, parent, false));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final CustomerNameItemHolder holder, @SuppressLint("RecyclerView") final int position) {
        final TableDetailsResponse customerDetail = arrlstData.get(position);

        holder.tableName.setText(String.format("Table %d", position));


        if (customerDetail.isAvailability()) {
            holder.cardViewTable.setClickable(true);
            holder.cardViewTable.setEnabled(true);
            try {
                int numRows = (int) reservationDao.queryBuilder().where().eq("tableId", position).countOf();
                holder.tableAvailability.setText("Reservations - " + numRows);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            holder.cardViewTable.setClickable(false);
            holder.cardViewTable.setEnabled(false);
            holder.tableAvailability.setText("N/A");


        }


        holder.cardViewTable.setOnClickListener(new View.OnClickListener() {
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

    public void updateReservationCount(int tableId) {

        notifyItemChanged(tableId);
    }


    static class CustomerNameItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tableName)
        TextView tableName;
        @BindView(R.id.tableAvailability)
        TextView tableAvailability;
        @BindView(R.id.cardViewTable)
        CardView cardViewTable;


        CustomerNameItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
